/*
 * JasperDesignCookie.java
 * 
 * Created on Sep 19, 2007, 11:58:18 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline;

import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node.Cookie;

/**
 *
 * @author gtoffoli
 */
public class GenericCookie<T> implements Cookie {

    T obj = null;
    public GenericCookie(T obj)
    {
        this.obj = obj;
    }
    
    public T getObject()
    {
        return obj;
    }
    
    public static class JasperDesignCookie extends GenericCookie<JasperDesign> {
        
        public JasperDesignCookie(JasperDesign obj)
        {
            super(obj);
        }
    }
    
    public static class JRDesignDatasetCookie extends GenericCookie<JRDesignDataset> {
        
        public JRDesignDatasetCookie(JRDesignDataset obj)
        {
            super(obj);
        }
    }
    
    
}
