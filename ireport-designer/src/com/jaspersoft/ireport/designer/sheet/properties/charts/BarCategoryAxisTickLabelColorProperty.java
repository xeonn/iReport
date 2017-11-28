/*
 * BarCategoryAxisTickLabelColorProperty.java
 * 
 * Created on 20-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
    
    
/**
 *  Class to manage the JRDesignBarPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class BarCategoryAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignBarPlot element;

    @SuppressWarnings("unchecked")
    public BarCategoryAxisTickLabelColorProperty(JRDesignBarPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBarPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Category Axis Tick Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Category Axis Tick Label.";
    }

    @Override
    public Color getColor() 
    {
        return element.getCategoryAxisTickLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnCategoryAxisTickLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setCategoryAxisTickLabelColor(color);
    }
    
}
