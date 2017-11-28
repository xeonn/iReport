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
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_COLOR property
 */
public final class BubbleXAxisLineColorProperty extends PropertySupport.ReadWrite {

    JRDesignBubblePlot element = null;

    @SuppressWarnings("unchecked")
    public BubbleXAxisLineColorProperty(JRDesignBubblePlot element)
    {
        super(JRDesignBubblePlot.PROPERTY_X_AXIS_LINE_COLOR, java.awt.Color.class,
              "X Axis Bubble Color",
              "The color of the X Axis Bubble.");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getXAxisLineColor();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val)
    {
        if (val == null || val instanceof Color)
        {
            Color oldValue = element.getOwnXAxisLineColor();
            Color newValue =  (Color)val;
            element.setXAxisLineColor(newValue);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "XAxisLineColor", 
                        Color.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
    
    @Override
    public boolean isDefaultValue() {
        return null == element.getOwnXAxisLineColor();
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
