/*
 * Bar3DCategoryAxisLineColorProperty.java
 * 
 * Created on 5-nov-2007, 19.43.44
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
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
*/
public final class Bar3DCategoryAxisLineColorProperty extends ColorProperty {

    private final JRDesignBar3DPlot element;

    @SuppressWarnings("unchecked")
    public Bar3DCategoryAxisLineColorProperty(JRDesignBar3DPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.CategoryAxisLineColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.CategoryAxisLineColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getCategoryAxisLineColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnCategoryAxisLineColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setCategoryAxisLineColor(color);
    }
}
