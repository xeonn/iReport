/*
 * LineCategoryAxisLabelColorProperty.java
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
 *  Class to manage the JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class LineCategoryAxisLabelColorProperty extends ColorProperty {

    private final JRDesignLinePlot element;

    @SuppressWarnings("unchecked")
    public LineCategoryAxisLabelColorProperty(JRDesignLinePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Category Axis Label Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the Category Axis Label.";
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
