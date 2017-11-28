/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 6-mar-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
    
    
/**
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR property
 */
public final class Bar3DCategoryAxisTickLabelColorProperty extends ColorProperty {


    private final JRDesignBar3DPlot element;

    @SuppressWarnings("unchecked")
    public Bar3DCategoryAxisTickLabelColorProperty(JRDesignBar3DPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.CategoryAxisTickLabelColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.CategoryAxisTickLabelColordetail");
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
