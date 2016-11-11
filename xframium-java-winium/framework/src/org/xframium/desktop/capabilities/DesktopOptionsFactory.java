package org.xframium.desktop.capabilities;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.winium.DesktopOptions;

/**
 * @author user
 * Class for adding Desktop options.
 */
public class DesktopOptionsFactory extends AbstractDesktopCapability{

	private Log log = LogFactory.getLog(DesktopOptionsFactory.class);
	
	/* (non-Javadoc)
	 * @see org.xframium.browser.capabilities.AbstractBrowserCapability#_createBrowserOptions(org.openqa.selenium.remote.DesiredCapabilities, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected DesktopOptions _createDesktopOptions(DesktopOptions dc, Map <String,Object> options) throws IOException {
		
		if ( log.isDebugEnabled() )
			log.debug( "Creating Desktop Options " );
		
		Runtime.getRuntime().exec("C:\\Users\\mnares10\\git\\xframium-java\\framework\\test\\resources\\Winium.Desktop.Driver.exe");
		dc = new DesktopOptions();
		
		
		
		if ( log.isDebugEnabled() )
			log.debug( "Desktop Options are set " );
		
		return dc;
	}
}
