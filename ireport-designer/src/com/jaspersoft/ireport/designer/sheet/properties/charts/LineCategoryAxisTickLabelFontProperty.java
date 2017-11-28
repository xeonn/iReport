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
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_FONT property
 */
public final class LineCategoryAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignLinePlot plot;
        
    @SuppressWarnings("unchecked")
    public LineCategoryAxisTickLabelFontProperty(JRDesignLinePlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_FONT;
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
