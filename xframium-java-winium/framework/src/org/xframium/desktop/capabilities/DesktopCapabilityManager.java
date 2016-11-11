package org.xframium.desktop.capabilities;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xframium.device.factory.DriverFactory;

/**
 * @author user
 * Factory class for creating instances for the browser capabilities.
 */
public class DesktopCapabilityManager {

	private static DesktopCapabilityManager singleton=new DesktopCapabilityManager();
	private Map<String,DesktopCapabilityFactory> desktopCapabilityMap = new HashMap<String,DesktopCapabilityFactory>( 20 );

	public static DesktopCapabilityManager instance()
	{
		return singleton;
	}

	private DesktopCapabilityManager()
	{

	}


	private Log log=LogFactory.getLog(DesktopCapabilityManager.class);

	/**Method will create instances for factory.
	 * @param String - factoryName
	 * @return BrowserCapabilityFactory
	 */
	public synchronized DesktopCapabilityFactory getBrowsercapabilityFactory(String factoryName)
	{
		if ( log.isDebugEnabled() )
			log.debug( "Getting Driver Factory for " + factoryName );

		DesktopCapabilityFactory desktopCapabilityFactory = (DesktopCapabilityFactory) desktopCapabilityMap.get( factoryName );

		if ( desktopCapabilityFactory == null )
		{
			String className = DesktopCapabilityFactory.class.getPackage().getName() + "." + factoryName + "Factory";

			if ( log.isInfoEnabled() )
				log.info( "Creating Driver Factory as " + className );

			try
			{
				desktopCapabilityFactory = (DesktopCapabilityFactory)Class.forName( className ).newInstance();
				desktopCapabilityMap.put( factoryName, desktopCapabilityFactory );

			}
			catch( Exception e )
			{
				log.fatal( "Could not create DriverFactory for " + className, e );
			}
		}

		return desktopCapabilityFactory;
	}

}


