/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportManager;

/**
 *
 * @author gtoffoli
 */
public class DefaultIReportConnectionFactory implements IReportConnectionFactory {

    private String connectionClassName = null;

    public DefaultIReportConnectionFactory(String connectionClassName)
    {
        this.connectionClassName = connectionClassName;
    }


    public IReportConnection createConnection() {

        try {
            IReportConnection c = (IReportConnection)Class.forName(connectionClassName,true, IReportManager.getReportClassLoader()).newInstance();
            return c;
        } catch (Throwable tw)
        {
            tw.printStackTrace();
        }
        return null;
    }

    /**
     * @return the connectionClassName
     */
    public String getConnectionClassName() {
        return connectionClassName;
    }

    /**
     * @param connectionClassName the connectionClassName to set
     */
    public void setConnectionClassName(String connectionClassName) {
        this.connectionClassName = connectionClassName;
    }



}
