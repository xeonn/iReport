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
 * EJBQLConnection.java
 * 
 * Created on 4 giugno 2003, 18.15
 *
 */

package com.jaspersoft.ireport.designer.connection;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ReportClassLoader;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.designer.connection.gui.EJBQLConnectionEditor;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author  Administrator
 */
public class EJBQLConnection extends IReportConnection {
    
    private String name;
    
    private java.util.HashMap map = null;
    private String persistenceUnit;
    
    private EntityManager em = null;
    private EntityManagerFactory emf = null;
    
    private int usedby = 0;
    
    /** Creates a new instance of JDBCConnection */
    
    
    public EJBQLConnection() {
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
    
    public EntityManager getEntityManager() throws Exception 
    {           
            if (em == null)
            {
                if (emf == null)
                {
                    ClassLoader cl = Thread.currentThread().getContextClassLoader();
                    if (cl instanceof ReportClassLoader)
                    {
                        List items = ((ReportClassLoader)cl).getCachedItems();
                        
                        java.net.URL[] urls = new java.net.URL[items.size()];
                        for (int i=0; i<items.size(); ++i)
                        {
                            urls[i] = new java.io.File(""+items.get(i)).toURI().toURL();
                        }
                        URLClassLoader urlClassLoader = new URLClassLoader(urls,  cl );
                        Thread.currentThread().setContextClassLoader(urlClassLoader  );
                    }
                    
                    
                    emf = Persistence.createEntityManagerFactory( 
                            Misc.nvl(getProperties().get("PersistenceUnit"), null), new HashMap());
                    //if (emf == null) throw new Exception("Unable to create the EntityManagerFactory for persistence unit " + Misc.nvl(getProperties().get("PersistenceUnit"), null));
                    //Thread.currentThread().setContextClassLoader().
                }
                em = emf.createEntityManager();
            }
            usedby ++;
            return em;
    }
    
    public void closeEntityManager()
    {
        try {
            if (em != null)
            {
                usedby--;
                if (usedby == 0)
                {
                    em.close();
                    em = null;
                }
            }
        } catch (Exception ex)
        {
        }
    }   
    
    public String getDescription(){ return "EJBQL connection"; } //"connectionType.ejbql"
    
    
    @Override
    public IReportConnectionEditor getIReportConnectionEditor()
    {
        return new EJBQLConnectionEditor();
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

                              getEntityManager();
                              closeEntityManager();
                              JOptionPane.showMessageDialog(Misc.getMainWindow(),
                                      //I18n.getString("messages.connectionDialog.connectionTestSuccessful",
                                      "Connection test successful!","",JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(Misc.getMainWindow(),
                                    ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
