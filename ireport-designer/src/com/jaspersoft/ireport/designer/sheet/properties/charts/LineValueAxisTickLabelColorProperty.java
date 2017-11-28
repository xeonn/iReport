/*
 * LineValueAxisTickLabelColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_VALUE_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class LineValueAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignLinePlot element;

    @SuppressWarnings("unchecked")
    public LineValueAxisTickLabelColorProperty(JRDesignLinePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_VALUE_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Value Axis Tick Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Value Axis Tick Label.";
    }

    @Override
    public Color getColor() 
    {
        return element.getValueAxisTickLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnValueAxisTickLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setValueAxisTickLabelColor(color);
    }
    
}
