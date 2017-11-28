/*
 * TimeSeriesTimeAxisTickLabelColorProperty.java
 * 
 * Created on 5-nov-2007, 19.43.44
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
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class TimeSeriesTimeAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignTimeSeriesPlot element;

    
    public TimeSeriesTimeAxisTickLabelColorProperty(JRDesignTimeSeriesPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Time_Axis_Tick_Label_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_Time_Axis_Tick_Label.");
    }

    @Override
    public Color getColor() 
    {
        return element.getTimeAxisTickLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnTimeAxisTickLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setTimeAxisTickLabelColor(color);
    }
    
}
