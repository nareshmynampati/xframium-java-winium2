/*******************************************************************************
 * xFramium
 *
 * Copyright 2016 by Moreland Labs, Ltd. (http://www.morelandlabs.com)
 *
 * Some open source application is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *  
 * Some open source application is distributed in the hope that it will 
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with xFramium.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0+ <http://spdx.org/licenses/GPL-3.0+>
 *******************************************************************************/
/*
 * 
 */
package org.xframium.device.factory.spi;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.xframium.application.ApplicationRegistry;
import org.xframium.content.ContentManager;
import org.xframium.device.DeviceManager;
import org.xframium.device.artifact.api.PerfectoArtifactProducer;
import org.xframium.device.cloud.CloudDescriptor;
import org.xframium.device.cloud.CloudRegistry;
import org.xframium.device.cloud.action.CloudActionProvider;
import org.xframium.device.factory.AbstractDriverFactory;
import org.xframium.device.factory.DeviceWebDriver;
import org.xframium.exception.DeviceConfigurationException;
import org.xframium.spi.Device;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ANDROIDDriver objects.
 */
public class ANDROIDDriverFactory extends AbstractDriverFactory
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.factory.AbstractDriverFactory#_createDriver(com.perfectoMobile.device.Device)
	 */
	@Override
	protected DeviceWebDriver _createDriver( Device currentDevice )
	{
		DeviceWebDriver webDriver = null;
		try
		{
			DesiredCapabilities dc = new DesiredCapabilities( "", "", Platform.ANY );
			
			CloudDescriptor useCloud = CloudRegistry.instance().getCloud();
            
            if ( currentDevice.getCloud() != null )
            {
                useCloud = CloudRegistry.instance().getCloud( currentDevice.getCloud() );
                if (useCloud == null)
                {
                    useCloud = CloudRegistry.instance().getCloud();
                    log.warn( "A separate grid instance was specified but it does not exist in your cloud registry [" + currentDevice.getCloud() + "] - using the default Cloud instance" );
                }
            }
            
            DeviceManager.instance().setCurrentCloud( useCloud );
            URL hubUrl = new URL( useCloud.getCloudUrl() );

			if ( currentDevice.getDeviceName() != null && !currentDevice.getDeviceName().isEmpty() )
			{
				dc.setCapability( ID, currentDevice.getDeviceName() );
			}
			else
			{					
				//dc.setCapability( PLATFORM_NAME, currentDevice.getOs() );
				dc.setCapability( useCloud.getCloudActionProvider().getCloudPlatformName(currentDevice), currentDevice.getOs() );
				dc.setCapability( PLATFORM_VERSION, currentDevice.getOsVersion() );
				dc.setCapability( MODEL, currentDevice.getModel() );
			}
			
			dc.setCapability( USER_NAME, useCloud.getUserName() );
			dc.setCapability( PASSWORD, useCloud.getPassword() );
			
			for ( String name : currentDevice.getCapabilities().keySet() )
				dc = setCapabilities(currentDevice.getCapabilities().get(name), dc, name);
			
			for ( String name : ApplicationRegistry.instance().getAUT().getCapabilities().keySet() )
				dc = setCapabilities(ApplicationRegistry.instance().getAUT().getCapabilities().get( name ), dc, name);
			
			dc.setCapability( AUTOMATION_NAME, "Appium" );
			
            if (( ContentManager.instance().getCurrentContentKey() != null ) &&
                ( ContentManager.instance().getContentValue( Device.LOCALE ) != null ))
            {
                String localeToConfigure = ContentManager.instance().getContentValue( Device.LOCALE );

                dc.setCapability( Device.LOCALE, localeToConfigure );
            }		
			
            if ( log.isDebugEnabled() )
                log.debug( Thread.currentThread().getName() + ": Acquiring Device as: \r\n" + capabilitiesToString( dc ) + "\r\nagainst " + hubUrl );
			
			webDriver = new DeviceWebDriver( new AndroidDriver( hubUrl, dc ), DeviceManager.instance().isCachingEnabled(), currentDevice );
	
			webDriver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
			
			Capabilities caps = ( (AndroidDriver) webDriver.getWebDriver() ).getCapabilities();
			webDriver.setExecutionId( useCloud.getCloudActionProvider().getExecutionId( webDriver ) );
			webDriver.setReportKey( caps.getCapability( "reportKey" ).toString() );
			webDriver.setDeviceName( caps.getCapability( "deviceName" ).toString() );
			if ( useCloud.getProvider().equals( "PERFECTO" ) )
			    webDriver.setWindTunnelReport( caps.getCapability( "windTunnelReportUrl" ).toString() );
			webDriver.context( "NATIVE_APP" );
			
			if( ApplicationRegistry.instance().getAUT().getAndroidIdentifier() != null && !ApplicationRegistry.instance().getAUT().getAndroidIdentifier().isEmpty() )
            {
			    if ( ( (AndroidDriver) webDriver.getNativeDriver() ).isAppInstalled( ApplicationRegistry.instance().getAUT().getAndroidIdentifier() ) )
                {
                    log.warn( "Attempting to start " + ApplicationRegistry.instance().getAUT().getAndroidIdentifier() );
                    CloudActionProvider actionProvider = (CloudActionProvider) Class.forName( CloudActionProvider.class.getPackage().getName() + "." + useCloud.getProvider() + "CloudActionProvider" ).newInstance();
                    actionProvider.startApp( caps.getCapability( "executionId" ) + "", caps.getCapability( "deviceName" ) + "", ApplicationRegistry.instance().getAUT().getName(), ApplicationRegistry.instance().getAUT().getAndroidIdentifier() );
                }
                else
                {
                    throw new DeviceConfigurationException( ApplicationRegistry.instance().getAUT().getAndroidIdentifier() );
                }
            }
			
			String interruptString = ApplicationRegistry.instance().getAUT().getCapabilities().get( "deviceInterrupts" )  != null ? (String)ApplicationRegistry.instance().getAUT().getCapabilities().get( "deviceInterrupts" ) : DeviceManager.instance().getDeviceInterrupts();
			webDriver.setDeviceInterrupts( getDeviceInterrupts( interruptString, webDriver.getExecutionId(), webDriver.getDeviceName() ) );
			webDriver.setArtifactProducer( getCloudActionProvider( useCloud ).getArtifactProducer() );
			webDriver.setCloud( useCloud );
			return webDriver;
		}
	
		catch( Exception e )
		{
		    log.fatal( "Could not connect to Cloud instance for " + currentDevice, e );
			if ( webDriver != null )
			{
				try { webDriver.close(); } catch( Exception e2 ) {}
				try { webDriver.quit(); } catch( Exception e2 ) {}
			}
			return null;
		}
	}
}
