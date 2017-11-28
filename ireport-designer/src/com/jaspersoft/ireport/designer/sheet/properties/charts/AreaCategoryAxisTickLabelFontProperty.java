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
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignAreaPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_FONT property
 */
public final class AreaCategoryAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignAreaPlot plot;
        
    @SuppressWarnings("unchecked")
    public AreaCategoryAxisTickLabelFontProperty(JRDesignAreaPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignAreaPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "Category Axis Tick Label Font";
    }

    @Override
    public String getShortDescription()
    {
        return "Category Axis Tick Label Font.";
    }

    @Override
    public JRFont getFont()
    {
        return plot.getCategoryAxisTickLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setCategoryAxisTickLabelFont(font);
    }
    
}
