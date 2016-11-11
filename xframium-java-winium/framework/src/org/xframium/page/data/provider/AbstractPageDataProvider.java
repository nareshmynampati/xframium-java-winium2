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
package org.xframium.page.data.provider;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xframium.page.data.PageData;
import org.xframium.page.data.PageDataContainer;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractPageDataProvider.
 */
public abstract class AbstractPageDataProvider implements PageDataProvider
{
	
	/** The log. */
	protected Log log = LogFactory.getLog( PageDataProvider.class );
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.PageDataProvider#readPageData()
	 */
	public abstract void readPageData();
	
	/** The wait time out. */
	private long waitTimeOut = 240;
	
	/** The record map. */
	private Map<String,PageDataContainer> recordMap = new HashMap<String,PageDataContainer>( 10 );
	
	/** The id map. */
	private Map<String,PageData> idMap = new HashMap<String,PageData>( 10 );
	
	private List<PageDataContainer> pC = new ArrayList<PageDataContainer>( 10 );
	private Map<String,PageDataContainer> allRecordMap = new HashMap<String,PageDataContainer>( 10 );

	
	/**
	 * Populate trees.
	 */
	protected void populateTrees()
	{
	    for ( PageData pageData : idMap.values() )
	    {
	        if ( pageData.containsChildren() )
	            pageData.populateTreeStructure( this );
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.PageDataProvider#getRecord(java.lang.String)
	 */
	@Override
	public PageData getRecord( String recordType )
	{
		try
		{
			Deque<PageData> dataList = recordMap.get( recordType ).getRecordList();
			
			if ( dataList instanceof LinkedBlockingDeque )
				return ( (LinkedBlockingDeque<PageData>) dataList ).pollFirst( waitTimeOut, TimeUnit.SECONDS );
			else
			{
				PageData pageData = dataList.pollFirst();
				dataList.offer( pageData );
				return pageData;
			}
		}
		catch( Exception e )
		{
			log.error( "Error acquiring page data [" + recordType + "] - " + e.getMessage() );
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.PageDataProvider#getRecord(java.lang.String, java.lang.String)
	 */
	public PageData getRecord( String recordType, String recordId )
	{
	    return (PageData) _getRecord( recordType, recordId );
	}
	
	private Object _getRecord( String recordType, String recordId )
	{
	    if ( recordId.contains( "." ) )
        {
            String[] fieldArray = recordId.split( "\\." );
            
            List<PageData> dataList = (List<PageData>) recordMap.get( recordType );
            
            for ( PageData p : dataList )
            {
                if ( p.getName().equals( fieldArray[ 0 ] ) )
                {
                    String newName = recordId.substring( recordId.indexOf( "." ) + 1 );
                    
                    if ( newName.trim().isEmpty() )
                        return p;
                    else
                        return p.get( newName );
                }
            }
            
            return null;
                        
        }
        else
        {
            return idMap.get( recordType + "." + recordId );
        }
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.PageDataProvider#getRecords(java.lang.String)
	 */
	public PageData[] getRecords( String recordType )
	{
	    if ( recordType.contains( "." ) )
	    {
	        String[] fieldArray = recordType.split( "\\." );
	        
	        Deque<PageData> dataList = recordMap.get( fieldArray[ 0 ] ).getRecordList();
            
            for ( PageData p : dataList )
            {
                if ( p.getName().equals( fieldArray[ 0 ] ) )
                {
                    String newName = recordType.substring( recordType.indexOf( "." ) + 1 );
                    
                    if ( newName.trim().isEmpty() )
                        return new PageData[] { p };
                    else
                        return (PageData[]) p.get( newName );
                }
            }
            return dataList.toArray( new PageData[ 0 ] );
	    }
	    else
	    {
	        if ( recordMap.get( recordType  ) == null )
	            return null;
	        
    		Deque<PageData> dataList = recordMap.get( recordType ).getRecordList();
    		if ( dataList != null )
    			return dataList.toArray( new PageData[ 0 ] );
    		else
    			return null;
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.PageDataProvider#putRecord(com.perfectoMobile.page.data.PageData)
	 */
	public void putRecord( PageData pageData )
	{
		if ( pageData != null )
		{
			Deque<PageData> dataList = recordMap.get( pageData.getType() ).getRecordList();
			if ( dataList instanceof LinkedBlockingDeque )
				dataList.offer( pageData );
		}
	}
	
	/**
	 * Adds the record type.
	 *
	 * @param typeName the type name
	 * @param lockRecords the lock records
	 */
	public void addRecordType( String typeName, boolean lockRecords )
	{
	    PageDataContainer dC = recordMap.get( typeName );
		
	    if ( dC == null )
	    {
	        dC = new PageDataContainer( typeName, lockRecords );
	        pC.add( dC );
			recordMap.put( typeName, dC );
			allRecordMap.put( typeName, dC );
		}
	}
	
	/**
	 * Adds the record.
	 *
	 * @param pageData the page data
	 */
	public void addRecord( PageData pageData )
	{
	    allRecordMap.get( pageData.getType() ).addRecord( pageData );
	    
	    if ( !pageData.isActive() )
	        return;
	    
		Deque<PageData> dataList = recordMap.get( pageData.getType() ).getRecordList();
		idMap.put( pageData.getType() + "." + pageData.getName(), pageData );
		dataList.offer( pageData );
	}
	
	/**
	 * Gets the wait time out.
	 *
	 * @return the wait time out
	 */
	public long getWaitTimeOut()
	{
		return waitTimeOut;
	}

	/**
	 * Sets the wait time out.
	 *
	 * @param waitTimeOut the new wait time out
	 */
	public void setWaitTimeOut( long waitTimeOut )
	{
		this.waitTimeOut = waitTimeOut;
	}

}
