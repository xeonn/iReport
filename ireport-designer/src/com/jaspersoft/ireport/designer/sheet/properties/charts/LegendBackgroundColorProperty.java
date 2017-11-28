/*
 * LegendBackgroundColorProperty.java
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
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.design.JRDesignChart;
    
    
/**
 *  Class to manage the JRBaseChart.PROPERTY_LEGEND_BACKGROUND_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class LegendBackgroundColorProperty extends ColorProperty {

    private final JRDesignChart element;

    
    public LegendBackgroundColorProperty(JRDesignChart element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseChart.PROPERTY_LEGEND_BACKGROUND_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Legend_Background_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_background_color_of_the_legend.");
    }

    @Override
    public Color getColor() 
    {
        return element.getLegendBackgroundColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnLegendBackgroundColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setLegendBackgroundColor(color);
    }
    
}
