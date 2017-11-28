/*
 * TimeSeriesTimeAxisLabelColorProperty.java
 * 
 * Created on 22-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class TimeSeriesTimeAxisLabelColorProperty extends ColorProperty {

    private final JRDesignTimeSeriesPlot element;

    @SuppressWarnings("unchecked")
    public TimeSeriesTimeAxisLabelColorProperty(JRDesignTimeSeriesPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Time Axis Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Time Axis Label.";
    }

    @Override
    public Color getColor() 
    {
        return element.getTimeAxisLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnTimeAxisLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setTimeAxisLabelColor(color);
    }
    
}
