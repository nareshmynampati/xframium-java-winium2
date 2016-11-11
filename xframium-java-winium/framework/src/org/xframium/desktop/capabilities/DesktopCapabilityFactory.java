package org.xframium.desktop.capabilities;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.winium.DesktopOptions;

/**
 * @author user
 * Interface for creating browser specific options.
 */
public interface DesktopCapabilityFactory {
	
	/**
	 * Interface method for creating browser options.
	 * @param DesiredCapabilities - desired capabilities object dc
	 * @param Map <String,List<String>> - browser specific options
	 * @return DesiredCapabilities
	 * @throws IOException 
	 */
	public DesktopOptions createDesktopOptions(DesktopOptions dc, Map <String,Object> options) throws IOException;



}
