/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author gtoffoli
 */
public class SimpleSQLDataSource implements javax.sql.DataSource {

    private IReportConnection iRConnection = null;
    private PrintWriter pw = new PrintWriter(System.out);
    private int loginTimeout = 0;

    public SimpleSQLDataSource(IReportConnection c)
    {
        iRConnection = c;
    }
    
    public Connection getConnection() throws SQLException {
        return iRConnection.getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        // Ignoring username and password
        return getConnection();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return pw;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        pw = out;
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        loginTimeout = seconds;
    }

    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    /**
     * @return the iRConnection
     */
    public IReportConnection getIRConnection() {
        return iRConnection;
    }

    /**
     * @param iRConnection the iRConnection to set
     */
    public void setIRConnection(IReportConnection iRConnection) {
        this.iRConnection = iRConnection;
    }

}
