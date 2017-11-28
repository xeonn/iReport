/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.options.jasperreports;

import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.util.JRProperties;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class StringProperty extends PropertySupport.ReadOnly {

    public StringProperty(String name)
    {
        super(name, String.class, name, name);
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        if (getName() != null)
        {
            String val = JRProperties.getProperty(getName());
            return val == null ? "" : val+"";
        }

        return "";
    }

    @Override
    public void setValue(Object arg0) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    }

}
