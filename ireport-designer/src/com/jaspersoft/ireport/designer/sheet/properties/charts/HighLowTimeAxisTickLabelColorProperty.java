/*
 * HighLowTimeAxisTickLabelColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
    
    
/**
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_TIME_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class HighLowTimeAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignHighLowPlot element;

    @SuppressWarnings("unchecked")
    public HighLowTimeAxisTickLabelColorProperty(JRDesignHighLowPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_TIME_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Time Axis Tick Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Time Axis Tick Label.";
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
