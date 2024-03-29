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
package org.xframium.gesture;

import org.openqa.selenium.ScreenOrientation;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractRotateGesture.
 */
public abstract class AbstractRotateGesture extends AbstractGesture
{
	
	/** The s orientation. */
	private ScreenOrientation sOrientation;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.Gesture#setParameters(java.lang.Object[])
	 */
	public void setParameters( Object[] parameterArray )
	{
		setOrientation( (ScreenOrientation) parameterArray[ 0 ] );
	}

	/**
	 * Gets the orientation.
	 *
	 * @return the orientation
	 */
	public ScreenOrientation getOrientation()
	{
		return sOrientation;
	}

	/**
	 * Sets the orientation.
	 *
	 * @param sOrientation the new orientation
	 */
	public void setOrientation( ScreenOrientation sOrientation )
	{
		this.sOrientation = sOrientation;
	}

}
