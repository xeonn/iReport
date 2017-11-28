/*
 * ScatterXAxisTickLabelColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
    
    
/**
 *  Class to manage the JRDesignScatterPlot.PROPERTY_X_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class ScatterXAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignScatterPlot element;

    @SuppressWarnings("unchecked")
    public ScatterXAxisTickLabelColorProperty(JRDesignScatterPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignScatterPlot.PROPERTY_X_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "X Axis Tick Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the X Axis Tick Label.";
    }

    @Override
    public Color getColor() 
    {
        return element.getXAxisTickLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnXAxisTickLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setXAxisTickLabelColor(color);
    }
    
}
