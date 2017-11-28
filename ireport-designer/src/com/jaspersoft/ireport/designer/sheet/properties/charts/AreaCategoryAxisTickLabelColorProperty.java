/*
 * AreaCategoryAxisTickLabelColorProperty.java
 * 
 * Created on 20-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
    
    
/**
 *  Class to manage the JRDesignAreaPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class AreaCategoryAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignAreaPlot element;

    @SuppressWarnings("unchecked")
    public AreaCategoryAxisTickLabelColorProperty(JRDesignAreaPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignAreaPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.CategoryAxisTickLabelColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.CATLdetail");
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
