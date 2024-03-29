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
package org.xframium.device.data.perfectoMobile;

import org.w3c.dom.Node;
import org.xframium.device.cloud.CloudRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservedHandsetValidator.
 */
public class ReservedHandsetValidator extends AbstractHandsetValidator implements PerfectoMobileHandsetValidator
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.data.perfectoMobile.PerfectoMobileHandsetValidator#validate(org.w3c.dom.Node)
	 */
	public boolean validate( Node handSet )
	{
		if ( Boolean.parseBoolean( getValue( handSet, "available" ) + "" ) )
		{
			if ( Boolean.parseBoolean( getValue( handSet, "reserved" ) + "" ) )
			{
				return CloudRegistry.instance().getCloud().getUserName().toLowerCase().equals (getValue( handSet, "reservedTo" ) );
			}
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.data.perfectoMobile.PerfectoMobileHandsetValidator#getMessage()
	 */
	public String getMessage()
	{
		return "There were no devices registered to the logged in user running the tests";
	}
}
