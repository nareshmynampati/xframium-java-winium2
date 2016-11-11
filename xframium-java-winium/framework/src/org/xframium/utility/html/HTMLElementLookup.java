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
package org.xframium.utility.html;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLElementLookup
{
    private static final Pattern FUNCTION = Pattern.compile( "(\\w+)\\(([^\\)]+)" );
    private List<HTMLFunction> functionPath = new ArrayList<HTMLFunction>( 10 );
    
    public HTMLElementLookup( String elementDefinition )
    {
        Matcher functionMatcher = FUNCTION.matcher( elementDefinition );
        while( functionMatcher.find() )
        {
            functionPath.add( new HTMLFunction( functionMatcher.group( 1 ), functionMatcher.group( 2 ) ) );
        }
    }
    
    public List<HTMLFunction> getFunctionPaths()
    {
        return functionPath;
    }

    public void setFunctionPaths( List<HTMLFunction> functionPath )
    {
        this.functionPath = functionPath;
    }

    public String toString()
    {
        return functionPath.toString();
    }
    
    public String toXPath()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for ( HTMLFunction htmlFunction : functionPath )
        {
            stringBuilder.append( htmlFunction.toXPath( false ) );
        }
        
        return stringBuilder.toString();
    }
    
}
