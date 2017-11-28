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
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.MeterIntervalsProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_COLOR property
 */
public final class MeterPlotIntervalsProperty extends MeterIntervalsProperty {

    JRDesignMeterPlot element = null;
    private final JRDesignDataset dataset;

    @SuppressWarnings("unchecked")
    public MeterPlotIntervalsProperty(JRDesignMeterPlot element, JRDesignDataset dataset)
    {
        super( JRDesignMeterPlot.PROPERTY_INTERVALS,
              "Meter Intervals",
              "Meter Intervals.");
        this.element = element;
        this.dataset = dataset;
        this.setValue("expressionContext", new ExpressionContext(dataset));
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getIntervals();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    @SuppressWarnings("unchecked")
    private void setPropertyValue(Object val)
    {
        if (val == null || val instanceof List)
        {
            if (val == element.getIntervals()) return;
            List oldValue = element.getIntervals();
            List newValue =  (List)val;
            
            element.setIntervals(newValue);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "Intervals", 
                        List.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
}
