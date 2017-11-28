/*
 * ScatterXAxisLabelColorProperty.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
    
    
/**
 *  Class to manage the JRDesignScatterPlot.PROPERTY_X_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class ScatterXAxisLabelColorProperty extends ColorProperty {

    private final JRDesignScatterPlot element;

    @SuppressWarnings("unchecked")
    public ScatterXAxisLabelColorProperty(JRDesignScatterPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignScatterPlot.PROPERTY_X_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "X Axis Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the X Axis Label.";
    }

    @Override
    public Color getColor() 
    {
        return element.getXAxisLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnXAxisLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setXAxisLabelColor(color);
    }
}
