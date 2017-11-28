/*
 * TimeSeriesTimeAxisLineColorProperty.java
 * 
 * Created on 22-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class TimeSeriesTimeAxisLineColorProperty extends ColorProperty {

    private final JRDesignTimeSeriesPlot element;

    
    public TimeSeriesTimeAxisLineColorProperty(JRDesignTimeSeriesPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Time_Axis_TimeSeries_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_Time_Axis_TimeSeries.");
    }

    @Override
    public Color getColor() 
    {
        return element.getTimeAxisLineColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnTimeAxisLineColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setTimeAxisLineColor(color);
    }
}
