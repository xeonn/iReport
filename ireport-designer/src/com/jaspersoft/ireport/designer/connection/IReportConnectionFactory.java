/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
/**
 *
 * @author gtoffoli
 */
public interface IReportConnectionFactory {

    /**
     * Creates a new instance of IReportConnection
     * @return an instance of IReportConnection
     */
    public IReportConnection createConnection();

    /**
     * This method returns the class name of the IReportConnection implementation.
     * This is used from the code that must check if this connection factory
     * is the good one to instance the connection serialized with a specific class
     * name. Since due to the ClassLoading limitation iReport is not able to
     * instance the class by its self, it looks for the appropriate registered
     * IReportConnectionFactory
     * @return the class name of the IReportConnection implementation created by this factory class
     */
    public String getConnectionClassName();
}
