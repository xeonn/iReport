/*
 * Copyright (C) 2007 JasperSoft http://www.jaspersoft.com
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

package com.jaspersoft.ireport.jasperserver.ws.scheduling;

/**
 * ReportSchedulerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */
public interface ReportSchedulerService extends javax.xml.rpc.Service {
    public java.lang.String getReportSchedulerAddress();

    public com.jaspersoft.ireport.jasperserver.ws.scheduling.ReportScheduler getReportScheduler() throws javax.xml.rpc.ServiceException;

    public com.jaspersoft.ireport.jasperserver.ws.scheduling.ReportScheduler getReportScheduler(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
