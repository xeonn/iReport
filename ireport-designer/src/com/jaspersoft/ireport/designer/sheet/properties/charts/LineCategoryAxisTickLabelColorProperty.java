/*
 * LineCategoryAxisTickLabelColorProperty.java
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
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class LineCategoryAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignLinePlot element;

    
    public LineCategoryAxisTickLabelColorProperty(JRDesignLinePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Category_Axis_Tick_Label_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_Category_Axis_Tick_Label.");
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
