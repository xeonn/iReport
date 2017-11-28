/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.DoubleProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
    
    
/**
 *  Class to manage the JRDesignPie3DPlot.PROPERTY_DEPTH_FACTOR property
 */
public final class Pie3DDepthFactorProperty extends DoubleProperty {

    private final JRDesignPie3DPlot plot;

    
    public Pie3DDepthFactorProperty(JRDesignPie3DPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignPie3DPlot.PROPERTY_DEPTH_FACTOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Depth_Factor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Depth_Factor.");
    }

    @Override
    public Double getDouble()
    {
        return plot.getDepthFactorDouble();
    }

    @Override
    public Double getOwnDouble()
    {
        return plot.getDepthFactorDouble();
    }

    @Override
    public Double getDefaultDouble()
    {
        return null;
    }

    @Override
    public void setDouble(Double depth)
    {
        plot.setDepthFactor(depth);
    }

    @Override
    public void validateDouble(Double depth)
    {
        if (depth != null && depth < 0)
        {
            throw annotateException(I18n.getString("The_depth_factor_must_be_a_positive_value."));
        }
    }

}
