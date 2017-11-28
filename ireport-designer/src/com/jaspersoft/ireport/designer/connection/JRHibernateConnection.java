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
 * JRHibernateConnection.java
 * 
 * Created on 4 giugno 2003, 18.15
 *
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.designer.connection.gui.JRHibernateConnectionEditor;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
/**
 *
 * @author  Administrator
 */
public class JRHibernateConnection extends IReportConnection {
    
    private String name;
    
    /** Creates a new instance of JRHibernateConnection */   
    public JRHibernateConnection() {
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
        java.util.HashMap map = new java.util.HashMap();
        return map;
    }
    
    @Override
    public void loadProperties(java.util.HashMap map)
    {
    }
    
    
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    @Override
    public java.lang.String getName() {
        return name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    @Override
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /**
     *  This method return an instanced JRDataDource to the database.
     *  If isJDBCConnection() return true => getJRDataSource() return false
     */
    @Override
    public net.sf.jasperreports.engine.JRDataSource getJRDataSource() { 
        return null;
    }
    
    public Session createSession() throws org.hibernate.HibernateException
    {
         return getSessionFactory().openSession(); 
    }

    public SessionFactory getSessionFactory() throws org.hibernate.HibernateException {
        
          return new Configuration ().configure().buildSessionFactory();
    }
    
    public String getDescription(){ return "Hibernate connection"; } //"connectionType.hibernate"
    
    @Override
    public IReportConnectionEditor getIReportConnectionEditor()
    {
        return new JRHibernateConnectionEditor();
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
                        SessionFactory hb_sessionFactory = null;
                        try {
                            hb_sessionFactory = new Configuration().configure().buildSessionFactory();

                            // Try to execute an hibernate query...
                            Session hb_session = hb_sessionFactory.openSession();
                            Transaction  transaction = hb_session.beginTransaction();
                            Query q = hb_session.createQuery("select address as address from Address as address");
                        
                            q.setFetchSize(100);
                            java.util.Iterator iterator = q.iterate();
                            // this is a stupid thing: iterator.next();

                            while (iterator.hasNext())
                            {
                                Object obj = iterator.next();
                            }
                            
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

