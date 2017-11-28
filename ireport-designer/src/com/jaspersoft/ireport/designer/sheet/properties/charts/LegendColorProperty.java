/*
 * LegendColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.design.JRDesignChart;
    
    
/**
 *  Class to manage the JRBaseChart.PROPERTY_LEGEND_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class LegendColorProperty extends ColorProperty {

    private final JRDesignChart element;

    @SuppressWarnings("unchecked")
    public LegendColorProperty(JRDesignChart element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseChart.PROPERTY_LEGEND_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Legend Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the legend.";
    }

    @Override
    public Color getColor() 
    {
        return element.getLegendColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnLegendColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setLegendColor(color);
    }
    
}
