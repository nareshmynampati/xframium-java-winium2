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
package org.xframium.device.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Platform;
import org.xframium.device.DeviceManager;
import org.xframium.device.SimpleDevice;
import org.xframium.spi.Device;
import org.xframium.utility.SQLUtil;

/**
 * The Class SQLDataProvider.
 */
public class SQLDataProvider implements DataProvider
{
    //
    // class data
    //

    private static final String DEF_QUERY = "SELECT NAME, DEVICE_ID, MANUFACTURER, MODEL, OS, OS_VERSION, BROWSER_NAME, BROWSER_VERSION, ACTIVE, AVAILABLE, CLOUD \n" + "FROM DEVICES";

    private static final String DEF_CAP_QUERY = "SELECT DEVICE_NAME, NAME, CLASS, VALUE \n" + "FROM DEVICE_CAPABILITIES";

    //
    // instance data
    //

    /** The log. */
    private Log log = LogFactory.getLog( SQLDataProvider.class );

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The JDBC URL. */
    private String url;

    /** The driver class name. */
    private String driver;

    /** The device query. */
    private String deviceQuery;

    /** The capability query. */
    private String capabilityQuery;

    /** The driver type. */
    private DriverType driverType;

    /**
     * Instantiates a new SQL data provider.
     *
     */
    public SQLDataProvider( String username, String password, String url, String driver, DriverType driverType )
    {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driver = driver;
        this.driverType = driverType;
        this.deviceQuery = DEF_QUERY;
        this.capabilityQuery = DEF_CAP_QUERY;
    }

    /**
     * Instantiates a new SQL data provider.
     *
     */
    public SQLDataProvider( String username, String password, String url, String driver, String deviceQuery, String capabilityQuery, DriverType driverType )
    {
        this( username, password, url, driver, driverType );

        this.deviceQuery = ((deviceQuery != null) ? deviceQuery : DEF_QUERY);
        this.capabilityQuery = ((capabilityQuery != null) ? capabilityQuery : DEF_CAP_QUERY);
    }

    private String parseString( String currentValue, String defaultValue, boolean emptyCheck )
    {
        if ( currentValue == null )
            return defaultValue;
        else
        {
            if ( emptyCheck && currentValue.trim().isEmpty() )
                return defaultValue;
            else
                return currentValue;
        }
    }

    public List<Device> readData()
    {
        List<Device> deviceList = new ArrayList<Device>( 10 );
        try
        {
            Object[][] deviceData = SQLUtil.getResults( username, password, url, driver, deviceQuery, null );
            Object[][] capabilityData = SQLUtil.getResults( username, password, url, driver, capabilityQuery, null );
            HashMap devicesByName = new HashMap();

            for ( int i = 0; i < deviceData.length; ++i )
            {
                String name = parseString( (String) deviceData[i][0], null, true );
                String id = parseString( (String) deviceData[i][1], null, true );
                String manuf = parseString( (String) deviceData[i][2], null, true );
                String model = parseString( (String) deviceData[i][3], null, true );
                String os = parseString( (String) deviceData[i][4], null, true );
                String os_ver = parseString( (String) deviceData[i][5], null, true );
                String browser = parseString( (String) deviceData[i][6], null, true );
                String browser_ver = parseString( (String) deviceData[i][7], null, true );
                String active = parseString( (String) deviceData[i][8], null, true );
                Number available = (Number) deviceData[i][9];
                String cloud = parseString( (String) deviceData[i][10], null, true );

                String driverName = "";
                switch ( driverType )
                {
                    case APPIUM:
                        if ( os.toUpperCase().equals( "IOS" ) )
                            driverName = "IOS";
                        else if ( os.toUpperCase().equals( "ANDROID" ) )
                            driverName = "ANDROID";
                        else
                            log.warn( "Appium is not supported on the following OS " + os.toUpperCase() + " - this device will be ignored" );
                        break;

                    case PERFECTO:
                        driverName = "PERFECTO";
                        break;

                    case WEB:
                        driverName = "WEB";
                        break;
                }

                SimpleDevice currentDevice = new SimpleDevice( name, manuf, model, os, os_ver, browser, browser_ver, available.intValue(), driverName, "Y".equals( active ), id );

                if ( cloud != null && !cloud.isEmpty() )
                    currentDevice.setCloud( cloud );

                devicesByName.put( name, currentDevice );
                deviceList.add( currentDevice );
            }

            for ( int i = 0; i < capabilityData.length; ++i )
            {
                String dev_name = (String) capabilityData[i][0];
                String name = (String) capabilityData[i][1];
                String type = (String) capabilityData[i][2];
                String value = (String) capabilityData[i][3];

                Device currentDevice = (Device) devicesByName.get( dev_name );

                if ( currentDevice != null )
                {
                    switch ( type )
                    {
                        case "BOOLEAN":
                            currentDevice.addCapability( name, Boolean.parseBoolean( value ) );
                            break;

                        case "OBJECT":
                            currentDevice.addCapability( name, value );
                            break;

                        case "STRING":
                            currentDevice.addCapability( name, value );
                            break;

                        case "PLATFORM":
                            currentDevice.addCapability( name, Platform.valueOf( value.toUpperCase() ) );
                            break;
                    }
                }
            }
        }
        catch ( Exception e )
        {
            log.fatal( "Error reading device data frim DB: ", e );
        }
        
        return deviceList;

    }

}
