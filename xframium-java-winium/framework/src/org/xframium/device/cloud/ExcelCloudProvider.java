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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelApplicationProvider.
 */
public class ExcelCloudProvider extends AbstractCloudProvider
{
	
	/** The tab name. */
	private String tabName;
	
	/** The file name. */
	private File fileName;
	
	/** The resource name. */
	private String resourceName;

	/**
	 * Instantiates a new Excel application provider.
	 *
	 * @param fileName            the file name
	 * @param tabName the tab name
	 */
	public ExcelCloudProvider( File fileName, String tabName )
	{
		this.fileName = fileName;
		this.tabName = tabName;
	}

	/**
	 * Instantiates a new Excel application provider.
	 *
	 * @param resourceName            the resource name
	 * @param tabName the tab name
	 */
	public ExcelCloudProvider( String resourceName, String tabName )
	{
		this.resourceName = resourceName;
		this.tabName = tabName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.perfectoMobile.device.application.ApplicationProvider#readData()
	 */
	public List<CloudDescriptor> readData()
	{

		if (fileName == null)
		{
			if (log.isInfoEnabled())
				log.info( "Reading from CLASSPATH as " + resourceName );
			return readElements( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				if (log.isInfoEnabled())
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				return readElements( new FileInputStream( fileName ) );
			}
			catch (FileNotFoundException e)
			{
				log.fatal( "Could not read from " + fileName, e );
				return null;
			}
		}
	}

	/**
	 * Gets the cell value.
	 *
	 * @param cell the cell
	 * @return the cell value
	 */
	private String getCellValue( XSSFCell cell )
	{
		if (cell != null)
		{
			switch (cell.getCellType())
			{
				case XSSFCell.CELL_TYPE_BLANK:
					return null;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					return String.valueOf( cell.getBooleanCellValue() );
				case XSSFCell.CELL_TYPE_NUMERIC:
					return String.valueOf( cell.getNumericCellValue() );
				case XSSFCell.CELL_TYPE_STRING:
					return cell.getRichStringCellValue().toString();
			}
		}
		return null;
	}

	/**
	 * Read elements.
	 *
	 * @param inputStream the input stream
	 */
	private List<CloudDescriptor> readElements( InputStream inputStream )
	{
	    List<CloudDescriptor> cList = new ArrayList<CloudDescriptor>( 10 );
		XSSFWorkbook workbook = null;

		try
		{
			workbook = new XSSFWorkbook( inputStream );
			XSSFSheet sheet = workbook.getSheet( tabName );

			for (int i = 1; i <= sheet.getLastRowNum(); i++)
			{
				XSSFRow currentRow = sheet.getRow( i );

				if ( getCellValue( currentRow.getCell( 0 ) ) == null || getCellValue( currentRow.getCell( 0 ) ).isEmpty() )
					break;
				
				cList.add( new CloudDescriptor( getCellValue( currentRow.getCell( 0 ) ), getCellValue( currentRow.getCell( 1 ) ), getCellValue( currentRow.getCell( 2 ) ), getCellValue( currentRow.getCell( 3 ) ), getCellValue( currentRow.getCell( 4  ) ), getCellValue( currentRow.getCell( 5  ) ), getCellValue( currentRow.getCell( 7 ) ), getCellValue( currentRow.getCell( 6 ) ), getCellValue( currentRow.getCell( 8 ) ), getCellValue( currentRow.getCell( 9 ) ), getCellValue( currentRow.getCell( 10 ) ) ) );
			}
			
			return cList;

			
		}
		catch (Exception e)
		{
			log.fatal( "Error reading Excel Element File", e );
			return null;
		}
		finally
		{
			try
			{
				workbook.close();
			}
			catch (Exception e)
			{
			}
		}
	}
}
