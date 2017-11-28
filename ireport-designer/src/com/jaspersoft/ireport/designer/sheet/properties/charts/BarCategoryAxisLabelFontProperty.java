/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractFontProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignBarPlot.PROPERTY_CATEGORY_AXIS_LABEL_FONT property
 */
public final class BarCategoryAxisLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignBarPlot plot;
        
    @SuppressWarnings("unchecked")
    public BarCategoryAxisLabelFontProperty(JRDesignBarPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBarPlot.PROPERTY_CATEGORY_AXIS_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.CategoryAxisLabelFont");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.CategoryAxisLabelFontdetail");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getCategoryAxisLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setCategoryAxisLabelFont(font);
    }

}
