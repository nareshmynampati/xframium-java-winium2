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
package org.xframium.page.keyWord.step.spi;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.xframium.page.Page;
import org.xframium.page.data.PageData;
import org.xframium.page.keyWord.step.AbstractKeyWordStep;
import org.xframium.spi.driver.NativeDriverProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class KWGetCookies.
 */
public class KWSGetCookies extends AbstractKeyWordStep
{
    public KWSGetCookies()
    {
        orMapping = false;
    }
    /* (non-Javadoc)
     * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
     */
    @Override
    public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
    {
        if ( pageObject == null )
        {
            throw new IllegalStateException( "Page Object was not defined" );
        }

        Set<Cookie> cookieSet = null;
        if ( webDriver instanceof RemoteWebDriver )
            cookieSet = ((RemoteWebDriver) webDriver).manage().getCookies();
        else if ( webDriver instanceof NativeDriverProvider && ( (NativeDriverProvider) webDriver).getNativeDriver() instanceof RemoteWebDriver )
        {
            cookieSet = ( (RemoteWebDriver) ( (NativeDriverProvider) webDriver).getNativeDriver() ).manage().getCookies();
        }

        StringBuilder buffer = new StringBuilder();
        Iterator<Cookie> cookies = cookieSet.iterator();

        while( cookies.hasNext() )
        {
            Cookie cookie = cookies.next();

            buffer.append( cookie.getName() + ":" + cookie.getValue() );
            if ( cookies.hasNext() )
            {
                buffer.append( ";" );
            }
        }

        Object result = buffer.toString();

        if ( log.isDebugEnabled() )
                log.debug( "Cookie List [" + result + "]" );
		
        if (( result instanceof String ) &&
            ( !validateData( result + "" )) )
        {
            throw new IllegalStateException( "Get cookies Expected a format of [" + getValidationType() + "(" + getValidation() + ") for [" + result + "]" );
        }
		
        if ( getContext() != null )
        {
            if ( log.isDebugEnabled() )
                log.debug( "Setting Context Data to [" + result + "] for [" + getContext() + "]" );
            contextMap.put( getContext(), result );
        }
		
        return true;
    }
	
    /* (non-Javadoc)
     * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
     */
    public boolean isRecordable()
    {
        return false;
    }

}
