/*
 * ParameterNameProperty.java
 * 
 * Created on Sep 20, 2007, 10:12:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class ParameterDescriptionProperty extends PropertySupport.ReadWrite {

    JRDesignParameter parameter = null;
    JRDesignDataset dataset = null;
    
    @SuppressWarnings("unchecked")
    public ParameterDescriptionProperty(JRDesignParameter parameter, JRDesignDataset dataset)
    {
        super("description", String.class,
              "Description",
              "Description");
        this.parameter = parameter;
        this.dataset = dataset;
        
    }
    
    public boolean canWrite()
    {
        return !getParameter().isSystemDefined();
    }
    
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return parameter.getDescription() == null ? "" : parameter.getDescription();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        
        if (val == null || val.equals(""))
        {
            parameter.setDescription(null);
        }
        
        String s = val+"";
        getParameter().setDescription(s);
    }
    
    public JRDesignDataset getDataset() {
        return dataset;
    }

    public void setDataset(JRDesignDataset dataset) {
        this.dataset = dataset;
    }

    public JRDesignParameter getParameter() {
        return parameter;
    }

    public void setParameter(JRDesignParameter parameter) {
        this.parameter = parameter;
    }
    
    @Override
    public boolean isDefaultValue() {
        return getParameter().getDescription() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        getParameter().setDescription(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }
}
