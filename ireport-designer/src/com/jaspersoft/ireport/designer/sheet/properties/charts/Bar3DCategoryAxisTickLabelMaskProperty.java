/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
    
    
/**
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_MASK property
 */
public final class Bar3DCategoryAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignBar3DPlot plot;

    @SuppressWarnings("unchecked")
    public Bar3DCategoryAxisTickLabelMaskProperty(JRDesignBar3DPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return "Category Axis Tick Label Mask";
    }

    @Override
    public String getShortDescription()
    {
        return "Category Axis Tick Label Mask.";
    }

    @Override
    public String getString()
    {
        return plot.getCategoryAxisTickLabelMask();
    }

    @Override
    public String getOwnString()
    {
        return plot.getCategoryAxisTickLabelMask();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setCategoryAxisTickLabelMask(mask);
    }

}
