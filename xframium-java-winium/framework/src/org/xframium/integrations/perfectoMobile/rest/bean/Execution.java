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
package org.xframium.integrations.perfectoMobile.rest.bean;

import org.xframium.integrations.rest.bean.AbstractBean;
import org.xframium.integrations.rest.bean.Bean.BeanDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Class Execution.
 */
@BeanDescriptor( beanName="response" )
public class Execution extends AbstractBean
{
	
	/** The execution id. */
	@FieldDescriptor ( )
	private String executionId;
	
	/** The report key. */
	@FieldDescriptor ( )
	private String reportKey;

	/** The status. */
	@FieldDescriptor ( fieldPath = "description" )
	private String status;

	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId()
	{
		return executionId;
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId( String executionId )
	{
		this.executionId = executionId;
	}

	/**
	 * Gets the report key.
	 *
	 * @return the report key
	 */
	public String getReportKey()
	{
		return reportKey;
	}

	/**
	 * Sets the report key.
	 *
	 * @param reportKey the new report key
	 */
	public void setReportKey( String reportKey )
	{
		this.reportKey = reportKey;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus( String status )
	{
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Execution [executionId=" + executionId + ", reportKey=" + reportKey + ", status=" + status + "]";
	}
	
	
	

}
