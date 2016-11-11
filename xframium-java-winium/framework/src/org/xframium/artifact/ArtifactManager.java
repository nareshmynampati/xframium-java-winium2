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
package org.xframium.artifact;

import java.util.ArrayList;
import java.util.List;

public class ArtifactManager
{
    /** The singleton. */
    private static ArtifactManager singleton = new ArtifactManager();

    /**
     * Instance.
     *
     * @return the RunDetails
     */
    public static ArtifactManager instance()
    {
        return singleton;
    }

    /**
     * Instantiates a RunDetails.
     */
    private ArtifactManager()
    {

    }
    
    private List<ArtifactListener> artifactListeners = new ArrayList<ArtifactListener>( 10 );
    
    public void addArtifactListener( ArtifactListener aListener )
    {
        artifactListeners.add( aListener );
    }
    
    public void notifyArtifactListeners( ArtifactType aType, Object aRecord )
    {
        for ( ArtifactListener aListener : artifactListeners )
            aListener.addArtifactRecord( aType, aRecord );
    }
    
}
