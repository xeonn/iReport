/*
 * ScatterYAxisLineColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
    
    
/**
 *  Class to manage the JRDesignScatterPlot.PROPERTY_Y_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class ScatterYAxisLineColorProperty extends ColorProperty {

    private final JRDesignScatterPlot element;

    
    public ScatterYAxisLineColorProperty(JRDesignScatterPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignScatterPlot.PROPERTY_Y_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Y_Axis_Scatter_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_Y_Axis_Scatter.");
    }

    @Override
    public Color getColor() 
    {
        return element.getYAxisLineColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnYAxisLineColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setYAxisLineColor(color);
    }
}
