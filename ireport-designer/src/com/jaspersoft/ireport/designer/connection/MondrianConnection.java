/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved. 
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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
 *
 *
 *
 *
 * MondrianConnection.java
 * 
 * Created on 4 giugno 2003, 18.15
 *
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.designer.connection.gui.MondrianConnectionEditor;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import mondrian.olap.DriverManager;

/**
 *
 * @author  Administrator
 */
public class MondrianConnection extends IReportConnection {
    
    public static final String CATALOG_URI = "CatalogUri";
    public static final String CONNECTION_NAME = "ConnectionName";
    
    private java.util.HashMap map = null;
    
    private mondrian.olap.Connection mondrianConnection = null;
    
    private int usedby = 0;
    
    /** Creates a new instance of JDBCConnection */
    
    
    public MondrianConnection() {
        map = new java.util.HashMap();
    }
    
    /**  This method return an instanced connection to the database.
     *  If isJDBCConnection() return false => getConnection() return null
     *
     */
    @Override
    public java.sql.Connection getConnection() {       
            return null;
    }
    
    @Override
    public boolean isJDBCConnection() {
        return false;
    }
    
    @Override
    public boolean isJRDataSource() {
        return false;
    }
    
    /*
     *  This method return all properties used by this connection
     */
    @Override
    public java.util.HashMap getProperties()
    {    
        return map;
    }
    
    @Override
    public void loadProperties(java.util.HashMap map)
    {
        this.map = map;
    }
    
    
    
    /**
     *  This method return an instanced JRDataDource to the database.
     *  If isJDBCConnection() return true => getJRDataSource() return false
     */
    @Override
    public net.sf.jasperreports.engine.JRDataSource getJRDataSource()
    { 
        return null;
    }
    
    
    public void closeMondrianConnection()
    {
        try {
            if (getMondrianConnection() != null)
            {
                usedby--;
                if (usedby == 0)
                {
                    mondrianConnection.close();
                    mondrianConnection = null;
                }
            }
        } catch (Exception ex)
        {
        }
    }   

    public mondrian.olap.Connection getMondrianConnection() throws Exception {
        
        if (mondrianConnection == null)
        {
            JDBCConnection con = getJDBCConnection();
            
            try {
            mondrianConnection  = 
			DriverManager.getConnection(
					"Provider=mondrian;" + 
					"JdbcDrivers=" + escapeProperty( con.getJDBCDriver() )  + ";" +
					"Jdbc=" + escapeProperty( con.getUrl() ) + ";" +
					"JdbcUser=" + escapeProperty( con.getUsername() ) + ";" +
					"JdbcPassword=" + escapeProperty( con.getPassword() ) + ";" +
					"Catalog=" + escapeProperty( getCatalogUri() ) + ";", 
					null, false);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        usedby++;
        return mondrianConnection;
    }

    public void setMondrianConnection(mondrian.olap.Connection mondrianConnection) {
        this.mondrianConnection = mondrianConnection;
    }

    public String getCatalogUri() {
        return (String)getProperties().get( CATALOG_URI );
    }

    @SuppressWarnings("unchecked")
    public void setCatalogUri(String catalogUri) {
        getProperties().put( CATALOG_URI, catalogUri);
    }

    public String getConnectionName() {
        return (String)getProperties().get( CONNECTION_NAME );
    }

    @SuppressWarnings("unchecked")
    public void setConnectionName(String connectionName) {
        getProperties().put( CONNECTION_NAME, connectionName);
    }
    
    
    private JDBCConnection getJDBCConnection()
    {
            String name = getConnectionName();
            List<IReportConnection> conns = IReportManager.getInstance().getConnections();
            for (IReportConnection con : conns)
            {
                if (con instanceof JDBCConnection &&
                    con.getName().equals(name))
                {
                    return (JDBCConnection)con;
                }
            }
            
            return null;
    }
    
    public String escapeProperty( String s)
    {
        if (s == null) s = "";
        s = Misc.string_replace("\"\"","\"",s);
    
        return s;
    }
    
    public String getDescription(){ return "Mondrian OLAP connection"; } //"connectionType.olap"
    
    @Override
    public IReportConnectionEditor getIReportConnectionEditor()
    {
        return new MondrianConnectionEditor();
    }
    
    @Override
    public void test() throws Exception
    {
        try {
                SwingUtilities.invokeLater( new Runnable()
                {
                    public void run()
                    {
                        
                        
                        Thread.currentThread().setContextClassLoader( IReportManager.getInstance().getReportClassLoader() );
                        try {

                              getMondrianConnection();
                              closeMondrianConnection();
                              JOptionPane.showMessageDialog(Misc.getMainWindow(),
                                      //I18n.getString("messages.connectionDialog.connectionTestSuccessful",
                                      "Connection test successful!","",JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(Misc.getMainWindow(),ex.getMessage(),
                                    "Error",JOptionPane.ERROR_MESSAGE);
                            return;					
                        } 
                        finally
                        {

                        }
                    }
                });
            } catch (Exception ex)
            {}
    }
}
