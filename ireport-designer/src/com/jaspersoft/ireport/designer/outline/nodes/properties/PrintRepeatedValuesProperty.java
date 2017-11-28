/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_PRINT_REPEATED_VALUES property
 */
public final class PrintRepeatedValuesProperty extends PropertySupport.ReadWrite {

    JRDesignElement element = null;

    @SuppressWarnings("unchecked")
    public PrintRepeatedValuesProperty(JRDesignElement element)
    {
        super(JRDesignElement.PROPERTY_PRINT_REPEATED_VALUES, Boolean.class,
              "Print Repeated Values",
              "Print Repeated Values.");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return new Boolean( element.isPrintRepeatedValues());
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    @Override
    public boolean isDefaultValue() {
        return element.isPrintRepeatedValues() == true;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setPropertyValue(true);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }

    private void setPropertyValue(Object val)
    {
        if (val != null && val instanceof Boolean)
        {
            boolean oldValue = element.isPrintRepeatedValues();
            boolean newValue =  (Boolean)val;
            element.setPrintRepeatedValues(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "PrintRepeatedValues", 
                        Boolean.TYPE,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

}
