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
package org.xframium.device.cloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVApplicationProvider.
 */
public class CSVCloudProvider extends AbstractCloudProvider
{
	
	/** The file name. */
	private File fileName;
	
	/** The resource name. */
	private String resourceName;
	
	/**
	 * Instantiates a new CSV application provider.
	 *
	 * @param fileName the file name
	 */
	public CSVCloudProvider( File fileName )
	{
		this.fileName = fileName;
	}
	
	/**
	 * Instantiates a new CSV application provider.
	 *
	 * @param resourceName the resource name
	 */
	public CSVCloudProvider( String resourceName )
	{
		this.resourceName = resourceName;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.application.ApplicationProvider#readData()
	 */
	public List<CloudDescriptor> readData()
	{
	    List<CloudDescriptor> cList = new ArrayList<CloudDescriptor>( 10 );
		if ( fileName == null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Reading from CLASSPATH as " + resourceName );
			return readElements( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				if ( log.isInfoEnabled() )
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				return readElements( new FileInputStream( fileName ) );
			}
			catch( FileNotFoundException e )
			{
				log.fatal( "Could not read from " + fileName, e );
				return null;
			}
		}
	}
	
	/**
	 * Read elements.
	 *
	 * @param inputStream the input stream
	 */
	private List<CloudDescriptor> readElements( InputStream inputStream )
	{
		BufferedReader fileReader = new BufferedReader( new InputStreamReader( inputStream ) );
		String currentLine = null;
		List<CloudDescriptor> cList = new ArrayList<CloudDescriptor>( 10 );
		
		try
		{
			while ( ( currentLine = fileReader.readLine() ) != null )
			{
				if ( log.isDebugEnabled() )
					log.debug( "Attempting to parse current line as [" + currentLine + "]" );
				
				String[] lineData = currentLine.split( "," );
				
				cList.add( new CloudDescriptor( lineData[ 0 ], lineData[ 1 ], lineData[ 2 ], lineData[ 3 ], lineData[ 4 ], lineData[ 5 ], lineData[ 7 ], lineData[ 6 ], lineData[ 8 ], lineData[ 9 ], lineData[ 10 ] ) );
				
			}
			return cList;
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
			return null;
		}
	}
}
