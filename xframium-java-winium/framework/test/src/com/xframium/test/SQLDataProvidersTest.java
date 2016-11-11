/*******************************************************************************
 * xFramium
 *
 * Copyright 2016 by Moreland Labs LTD (http://www.morelandlabs.com)
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
package com.xframium.test;
    
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xframium.application.ApplicationDescriptor;
import org.xframium.application.ApplicationRegistry;
import org.xframium.content.ContentData;
import org.xframium.content.ContentManager;
import org.xframium.device.DeviceManager;
import org.xframium.device.cloud.CloudDescriptor;
import org.xframium.device.cloud.CloudRegistry;
import org.xframium.page.ElementDescriptor;
import org.xframium.page.PageManager;
import org.xframium.page.data.PageData;
import org.xframium.page.data.PageDataManager;
import org.xframium.page.element.Element;
import org.xframium.page.keyWord.KeyWordDriver;
import org.xframium.page.keyWord.KeyWordStep;
import org.xframium.page.keyWord.KeyWordTest;
import org.xframium.spi.Device;


public class SQLDataProvidersTest
{
    @Test
    public void testSQLCloudProvider()
    {
        CloudDescriptor cloud = CloudRegistry.instance().getCloud( "partners" );

        Assert.assertTrue( cloud != null, "Got a cloud" );
        Assert.assertTrue( "partners.perfectomobile.com".equals(cloud.getHostName()), "Got the right cloud" );
    }

    @Test
    public void testSQLDeviceProvider()
    {
        Device device = DeviceManager.instance().getDevice( "Samsung S6" );

        Assert.assertTrue( device != null, "Got a device" );
        Assert.assertTrue( "Android".equals( device.getOs()), "Got the right device" );
    }

    @Test
    public void testSQLApplicationProvider()
    {
        ApplicationDescriptor app = ApplicationRegistry.instance().getApplication( "Google" );

        Assert.assertTrue( app != null, "Got an application" );
        Assert.assertTrue( "http://www.google.com".equals( app.getUrl()), "Got the right application" );
    }

    @Test
    public void testSQLContentProvider()
    {
        ContentData content = ContentManager.instance().getContent( "test" );

        Assert.assertTrue( content != null, "Got some content" );
        Assert.assertTrue( "two".equals( content.getValue( 1 )) , "Got the right content" );
    }

    @Test
    public void testSQLPageDataProvider()
    {
        PageData[] data = PageDataManager.instance().getRecords( "searchData" );

        Assert.assertTrue( data != null, "Got some data" );
        Assert.assertTrue( data.length > 0, "Got some data II" );
        Assert.assertTrue( "oracle".equals( data[0].getData( "text" )) , "Got the right data" );
    }

    @Test
    public void testSQLPageElementProvider()
    {
        ElementDescriptor elementDescriptor = new ElementDescriptor( "Google",
                                                                     "Home",
                                                                     "SEARCH_FOR" );
        
        Element element = PageManager.instance().getElementProvider().getElement( elementDescriptor );

        Assert.assertTrue( element != null, "Got an element" );
        Assert.assertTrue( "//input[@id='lst-ib']".equals( element.getKey()) , "Got the right element" );
    }

    @Test
    public void testSQLKeyWordDriver()
    {
        KeyWordTest theTest = KeyWordDriver.instance().getTest( "MyTest!one" );

        Assert.assertTrue( theTest != null, "Got a test" );

        KeyWordStep theFirstStep = theTest.getStepAt( 0 );
        
        Assert.assertTrue( "LOAD".equals( theFirstStep.getName()) , "Got the right step" );
    }

}
