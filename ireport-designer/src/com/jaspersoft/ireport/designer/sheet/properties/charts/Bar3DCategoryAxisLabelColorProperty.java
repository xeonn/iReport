/*
 * Bar3DCategoryAxisLabelColorProperty.java
 * 
 * Created on 20-feb-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
    
    
/**
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class Bar3DCategoryAxisLabelColorProperty extends ColorProperty {

    private final JRDesignBar3DPlot element;

    @SuppressWarnings("unchecked")
    public Bar3DCategoryAxisLabelColorProperty(JRDesignBar3DPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.CategoryAxisLabelColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.CategoryAxisLabelColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getCategoryAxisLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnCategoryAxisLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setCategoryAxisLabelColor(color);
    }
    
}
