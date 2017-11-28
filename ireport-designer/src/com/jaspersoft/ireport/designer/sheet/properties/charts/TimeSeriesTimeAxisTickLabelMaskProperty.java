/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_MASK property
 */
public final class TimeSeriesTimeAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignTimeSeriesPlot plot;

    @SuppressWarnings("unchecked")
    public TimeSeriesTimeAxisTickLabelMaskProperty(JRDesignTimeSeriesPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return "Time Axis Tick Label Mask";
    }

    @Override
    public String getShortDescription()
    {
        return "Time Axis Tick Label Mask.";
    }

    @Override
    public String getString()
    {
        return plot.getTimeAxisTickLabelMask();
    }

    @Override
    public String getOwnString()
    {
        return plot.getTimeAxisTickLabelMask();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setTimeAxisTickLabelMask(mask);
    }
    
}
