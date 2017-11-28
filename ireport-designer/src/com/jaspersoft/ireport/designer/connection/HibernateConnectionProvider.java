/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;

/**
 *
 * @author gtoffoli
 */
public class HibernateConnectionProvider implements org.hibernate.connection.ConnectionProvider {

    Connection conn = null;
    int useCounter = 0;
    JDBCConnection irConnection = null;


    public void configure(Properties props) throws HibernateException {
        irConnection = new JDBCConnection();
        irConnection.setJDBCDriver( props.getProperty(Environment.DRIVER));
        irConnection.setUsername( props.getProperty(Environment.USER));
        irConnection.setPassword( props.getProperty(Environment.PASS));
        irConnection.setUrl( props.getProperty(Environment.URL));
    }

    public Connection getConnection() throws SQLException {
        try {

            if (conn == null || conn.isClosed())
            {
                conn = irConnection.getConnection();
                useCounter=1;
            }
        } catch (SQLException sqlEx)
        {
            throw sqlEx;
        } catch (Exception ex)
        {
            throw new SQLException(ex.getMessage());
        }
        return conn;
    }

    public void closeConnection(Connection c) throws SQLException {
        if (conn != null && useCounter==1)
        {
            conn.close();
        }
        useCounter--;

    }

    public void close() throws HibernateException {

    }

    public boolean supportsAggressiveRelease() {
        return false;
    }

}
