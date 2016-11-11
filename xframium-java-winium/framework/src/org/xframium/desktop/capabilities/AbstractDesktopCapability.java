package org.xframium.desktop.capabilities;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.winium.DesktopOptions;

/**
 * @author user
 * A abstract class for defining the Browser capabilities.
 */
public abstract class AbstractDesktopCapability implements DesktopCapabilityFactory {
	
	/**
	 * This method will be overridden by the class which extends AbstractBrowserCapability for implementing different browser options.
	 * @param DesiredCapabilities - desired capabilities objectdc
	 * @param Map <String,List<String>> - browser options with Key and values
	 * @return DesiredCapabilities
	 * @throws IOException 
	 */

	protected abstract DesktopOptions _createDesktopOptions(DesktopOptions dc, Map <String,Object> options) throws IOException;
	
	@Override
	public DesktopOptions createDesktopOptions(DesktopOptions dc, Map <String,Object> options) throws IOException {
		return _createDesktopOptions(dc, options);
	}

}
