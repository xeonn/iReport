/*
 * HighLowValueAxisLabelColorProperty.java
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
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
    
    
/**
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_VALUE_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class HighLowValueAxisLabelColorProperty extends ColorProperty {

    private final JRDesignHighLowPlot element;

    
    public HighLowValueAxisLabelColorProperty(JRDesignHighLowPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_VALUE_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Value_Axis_Label_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_Value_Axis_Label.");
    }

    @Override
    public Color getColor() 
    {
        return element.getValueAxisLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnValueAxisLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setValueAxisLabelColor(color);
    }
    
}
