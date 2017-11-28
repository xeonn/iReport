/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_COLOR property
 */
public final class HighLowValueAxisTickLabelColorProperty extends PropertySupport.ReadWrite {

    JRDesignHighLowPlot element = null;

    @SuppressWarnings("unchecked")
    public HighLowValueAxisTickLabelColorProperty(JRDesignHighLowPlot element)
    {
        super(JRDesignHighLowPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_COLOR, java.awt.Color.class,
              "Value Axis Tick Label Color",
              "The color of the Value Axis Tick Label.");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getValueAxisTickLabelColor();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val)
    {
        if (val == null || val instanceof Color)
        {
            Color oldValue = element.getOwnValueAxisTickLabelColor();
            Color newValue =  (Color)val;
            element.setValueAxisTickLabelColor(newValue);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "ValueAxisTickLabelColor", 
                        Color.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
    
    @Override
    public boolean isDefaultValue() {
        return null == element.getOwnValueAxisTickLabelColor();
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
