/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed WITHOUT ANY WARRANTY; and without the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt 
 * or write to:
 * 
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 */

package com.jaspersoft.ireport.jasperserver.ws;

import javax.activation.DataSource;

/**
 * @author Lucian Chirita
 *
 */
public class RequestAttachment {

	private DataSource dataSource;
	private String contentID;
	
	public RequestAttachment() {
		this(null, null);
	}
	
	public RequestAttachment(DataSource dataSource) {
		this(dataSource, null);
	}
	
	public RequestAttachment(DataSource dataSource, String contentID) {
		this.dataSource = dataSource;
		this.contentID = contentID;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getContentID() {
		return contentID;
	}

	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	
	
	
}
