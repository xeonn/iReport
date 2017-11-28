/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDisplay;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRDesignValueDisplay.PROPERTY_COLOR property
 */
public final class ThermometerValueColorProperty extends PropertySupport.ReadWrite {
    
    // FIXME: it should extend ColorProperty
    JRDesignThermometerPlot element = null;

    @SuppressWarnings("unchecked")
    public ThermometerValueColorProperty(JRDesignThermometerPlot element)
    {
        super( JRDesignValueDisplay.PROPERTY_COLOR, java.awt.Color.class,
              "Value Color",
              "The color of the ticks.");
        this.element = element;
    }

    @Override
    public Object getValue() {
        return element.getValueDisplay() == null ? null : element.getValueDisplay().getColor();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val)
    {
        if (val == null || val instanceof Color)
        {
            JRValueDisplay oldValue = element.getValueDisplay();
            JRDesignValueDisplay newValue = new JRDesignValueDisplay( element.getValueDisplay());
            newValue.setColor((Color)val);
            element.setValueDisplay(newValue);
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "ValueDisplay", 
                        JRValueDisplay.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
    
    @Override
    public boolean isDefaultValue() {
        return null == element.getValueDisplay() || null == element.getValueDisplay().getColor();
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }
}
