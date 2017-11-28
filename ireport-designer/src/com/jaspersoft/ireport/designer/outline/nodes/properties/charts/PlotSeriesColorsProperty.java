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
import com.jaspersoft.ireport.designer.sheet.SeriesColorsProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
    
    
/**
 *  Class to manage the JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA property
 */
public final class PlotSeriesColorsProperty extends SeriesColorsProperty {

    JRBaseChartPlot element = null;

    @SuppressWarnings("unchecked")
    public PlotSeriesColorsProperty(JRBaseChartPlot element)
    {
        super(JRBaseChartPlot.PROPERTY_SERIES_COLORS, 
              "Series Colors",
              "Series Colors");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getSeriesColors();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        setPropertyValue(val);
    }

    @SuppressWarnings("unchecked")
    private void setPropertyValue(Object val)
    {
        if (val instanceof SortedSet)
        {
            if (val == element.getSeriesColors()) return;
            SortedSet oldValue = new TreeSet();
            if (element.getSeriesColors() != null) oldValue.addAll(element.getSeriesColors());
            SortedSet newValue =  (SortedSet)val;
            
            element.setSeriesColors(newValue);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "SeriesColors", 
                        Collection.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
}
