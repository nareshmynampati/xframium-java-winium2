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
package org.xframium.page.element.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xframium.page.ElementDescriptor;
import org.xframium.page.PageContainer;
import org.xframium.page.element.Element;
import org.xframium.page.keyWord.KeyWordDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractElementProvider.
 */
public abstract class AbstractElementProvider implements ElementProvider 
{
    protected Map<String,PageContainer> elementTree = new HashMap<String,PageContainer>( 20 );
	private static XPathFactory xPathFactory = XPathFactory.newInstance();
	/** The log. */
	protected Log log = LogFactory.getLog(ElementProvider.class);
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.element.provider.ElementProvider#getElement(com.perfectoMobile.page.ElementDescriptor)
	 */
	@Override
	public Element getElement( ElementDescriptor elementDescriptor )
	{
		return _getElement( elementDescriptor );
	}
	
	private boolean initialized = false;
	
	
	
	public boolean isInitialized()
    {
        return initialized;
    }

    public void setInitialized( boolean initialized )
    {
        this.initialized = initialized;
    }
    
    public Map<String,PageContainer> getElementTree()
    {
        return elementTree;
    }

    /**
	 * _get element.
	 *
	 * @param elementDescriptor the element descriptor
	 * @return the element
	 */
	protected abstract Element _getElement( ElementDescriptor elementDescriptor	 );
	
	protected boolean validateElement( ElementDescriptor elementDescriptor, Element currentElement ) throws Exception
	{
	    PageContainer elementList = elementTree.get( elementDescriptor.getPageName() );
	    if ( elementList == null )
	    {
	        Class className = KeyWordDriver.instance().getPage( elementDescriptor.getPageName() );
	        elementList = new PageContainer( elementDescriptor.getPageName(), className != null ? className.getName() : "" );
	        elementTree.put( elementDescriptor.getPageName(), elementList );
	    }
	    elementList.getElementList().add( currentElement );
	    
	    
	    try
	    {
    	    switch ( currentElement.getBy() )
    	    {
    	        case CLASS:
    	        case ID:
    	        case LINK_TEXT:
    	        case NAME:
    	        case TAG_NAME:
    	        case V_TEXT:
    	            break;
    	            
    	        case CSS:
    	        case PROP:    
    	        case HTML:
    	        case V_IMAGE:
    	            break;
    	        
    	        case XPATH:
    	            xPathFactory.newXPath().compile( currentElement.getKey().replace( "{", "" ).replace( "}", "" ) );
    	            
    	    }
	    }
	    catch( Exception e )
	    {
	        log.fatal( "Could not process page element identified by [" + elementDescriptor.toString() + "] as [" + currentElement.getKey() + "]" );
	        return false;
	    }
	    
	    return true;
	}

}
