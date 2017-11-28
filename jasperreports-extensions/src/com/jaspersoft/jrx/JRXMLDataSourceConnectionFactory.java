/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.jrx;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.connection.IReportConnectionFactory;

/**
 *
 * @author gtoffoli
 */
public class JRXMLDataSourceConnectionFactory implements IReportConnectionFactory {

    public IReportConnection createConnection() {
        return new JRXMLDataSourceConnection();
    }

    public String getConnectionClassName() {
        return JRXMLDataSourceConnection.class.getName();
    }

}
