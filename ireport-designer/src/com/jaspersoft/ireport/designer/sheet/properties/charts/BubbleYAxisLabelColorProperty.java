/*
 * BubbleYAxisLabelColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
    
    
/**
 *  Class to manage the JRDesignBubblePlot.PROPERTY_Y_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class BubbleYAxisLabelColorProperty extends ColorProperty {

    private final JRDesignBubblePlot element;

    @SuppressWarnings("unchecked")
    public BubbleYAxisLabelColorProperty(JRDesignBubblePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_Y_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Y Axis Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Y Axis Label.";
    }

    @Override
    public Color getColor() 
    {
        return element.getYAxisLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnYAxisLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setYAxisLabelColor(color);
    }
    
}
