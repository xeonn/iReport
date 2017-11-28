/*
 * Copyright (C) 2005 - 2008 JasperSoft Corporation.  All rights reserved. 
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
 * Prompter.java
 * 
 * Created on 4 maggio 2005, 0.02
 *
 */

package com.jaspersoft.ireport.designer.compiler.prompt;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ThreadUtils;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Exceptions;


/**
 * @author Administrator
 */
public class Prompter
{

    /**
     * DOCUMENT ME!
     * 
     * @param report DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    public static HashMap promptForParameters(final JasperDesign report)
    {
        final HashMap hm = new HashMap();
        
        Runnable runner = new Runnable() {

            public void run() {
                if (report == null) return;
                List<JRDesignParameter> params = (List<JRDesignParameter>)report.getParametersList();
        for (JRDesignParameter param : params)
        {

            if (param.isForPrompting() && param.getValueClassName() != null && 
                !param.isSystemDefined())
            {

                PromptDialog pd = new PromptDialog(Misc.getMainFrame(), true);
                
                pd.setParameter(param);
                pd.setVisible(true);
                
                boolean isCollection = false;

                if (pd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION)
                {

                    Object value = pd.getValue();


                    if (param.getValueClassName().equals("java.lang.String"))
                    {
                        hm.put(param.getName(), value);
                    }
                    else if (param.getValueClassName().equals("java.lang.Integer"))
                    {

                        try
                        {
                            hm.put(param.getName(), new Integer("" + value));
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.lang.Long"))
                    {

                        try
                        {
                            hm.put(param.getName(), new Long("" + value));
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.lang.Double"))
                    {

                        try
                        {
                            hm.put(param.getName(), new Double("" + value));
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.lang.Float"))
                    {

                        try
                        {
                            hm.put(param.getName(), new Float("" + value));
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.lang.Boolean"))
                    {

                        try
                        {
                            hm.put(param.getName(), new Boolean("" + value));
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.util.Date"))
                    {

                        try
                        {

                            //java.text.SimpleDateFormat sdf = 
                            //        new java.text.SimpleDateFormat(it.businesslogic.ireport.gui.MainFrame.getMainInstance().getProperties().getProperty(
                            //                                               "dateformat", 
                            //                                               "d/M/y"));
                            //hm.put(param.getName(), sdf.parse("" + value));
                            if (value != null) hm.put(param.getName(), value);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.sql.Time"))
                    {

                        try
                        {

                            //java.text.SimpleDateFormat sdf = 
                            //        new java.text.SimpleDateFormat(it.businesslogic.ireport.gui.MainFrame.getMainInstance().getProperties().getProperty(
                            //                                               "timeformat", 
                            //                                               "d/M/y H:m:s"));
                            java.util.Date d = (java.util.Date)value; //sdf.parse("" + value);
                            java.sql.Time time = new java.sql.Time(d.getTime());
                            hm.put(param.getName(), time);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if (param.getValueClassName().equals("java.sql.Timestamp"))
                    {

                        try
                        {

                            //java.text.SimpleDateFormat sdf = 
                            //        new java.text.SimpleDateFormat(it.businesslogic.ireport.gui.MainFrame.getMainInstance().getProperties().getProperty(
                            //                                               "timeformat", 
                            //                                               "d/M/y H:m:s"));
                            java.util.Date d = (java.util.Date)value; // sdf.parse("" + value);
                            java.sql.Timestamp time = new java.sql.Timestamp(d.getTime());
                            hm.put(param.getName(), time);
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                    else 
                    {
                        try {
                            Class clazz = param.getValueClass();
                            
                            if ( java.util.Collection.class.isAssignableFrom(clazz) )
                            {
                                    isCollection = true;
                                    java.util.Collection collection = null;
                                    collection = new java.util.ArrayList();
                                    
                                    if (value != null)
                                    {
                                        fillCollection( collection, ""+value);
                                        
                                        IReportManager.getInstance().setLastParameterValue(param, ""+value);
                                        value = collection;
                                        
                                        try
                                        {
                                            hm.put(param.getName(), collection);
                                        }
                                        catch (Exception ex)
                                        {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                            }
                            else if (value instanceof String && value != null)
                            {
                                Constructor c;
                                c = param.getValueClass().getConstructor(String.class);
                                if (c != null)
                                {
                                        Object obj = c.newInstance((String)value);
                                        hm.put(param.getName(), obj);
                                }
                            }

                        } catch (Exception ex)
                        {
                              ex.printStackTrace();
                        }
                        
                        
                    }

                    if (value != null && !isCollection)
                    {
                        IReportManager.getInstance().setLastParameterValue(param,value);
                    }
                }
            }
        }
            }
        };
        
        if (SwingUtilities.isEventDispatchThread())
        {
            runner.run();
        }
        else
        {
            try {
                SwingUtilities.invokeAndWait(runner);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return hm;
    }
    
    public static void  fillCollection( java.util.Collection collection, String str)
    {
        if (str == null || str.length() == 0) return;
        
        StringTokenizer st = new StringTokenizer(str,",",false);
        
        while (st.hasMoreTokens())
        {
            String s = st.nextToken();
            
            s = s.trim();
            //if (s.startsWith("\"")) s= s.substring(1);
            //if (s.endsWith("\"")) s = s.substring(0,s.length()-1);
            collection.add(s);
        }
        
        
        
    }
}
