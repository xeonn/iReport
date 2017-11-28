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
 * QueryExecuterConnection.java
 * 
 * Created on 4 giugno 2003, 18.15
 *
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;
import com.jaspersoft.ireport.designer.connection.gui.QueryExecuterConnectionEditor;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import javax.swing.JOptionPane;

/**
 *
 * @author  Administrator
 */
public class QueryExecuterConnection extends IReportConnection {
    
    private String name;
    
    private java.util.HashMap map = null;
    
    /** Creates a new instance of JDBCConnection */
    public QueryExecuterConnection() {
        map = new java.util.HashMap();
    }

    @Override
    public void test() throws Exception {
        JOptionPane.showMessageDialog( Misc.getMainFrame() ,I18n.getString("messages.connectionDialog.connectionTestSuccessful"),"",JOptionPane.INFORMATION_MESSAGE);
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
    
    public String getDescription(){ return "Query Executer mode"; } //"connectionType.queryExecuter"
    
            
    @Override
    public IReportConnectionEditor getIReportConnectionEditor()
    {
        return new QueryExecuterConnectionEditor();
    }
}
