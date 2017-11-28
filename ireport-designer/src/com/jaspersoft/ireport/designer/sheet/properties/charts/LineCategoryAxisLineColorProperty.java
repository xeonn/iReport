/*
 * LineCategoryAxisLineColorProperty.java
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
 *  Class to manage the JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class LineCategoryAxisLineColorProperty extends ColorProperty {

    private final JRDesignLinePlot element;

    @SuppressWarnings("unchecked")
    public LineCategoryAxisLineColorProperty(JRDesignLinePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Category Axis Line Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Category Axis Line.";
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
