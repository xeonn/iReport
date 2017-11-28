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
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
    
    
/**
 *  Class to manage the JRDesignPie3DPlot.PROPERTY_DEPTH_FACTOR property
 */
public final class Pie3DDepthFactorProperty extends DoubleProperty {

    private final JRDesignPie3DPlot plot;

    @SuppressWarnings("unchecked")
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
        return "Depth Factor";
    }

    @Override
    public String getShortDescription()
    {
        return "Depth Factor.";
    }

    @Override
    public Double getDouble()
    {
        return plot.getDepthFactor();
    }

    @Override
    public Double getOwnDouble()
    {
        return plot.getDepthFactor();
    }

    @Override
    public Double getDefaultDouble()
    {
        return 0.2;
    }

    @Override
    public void setDouble(Double depth)
    {
        plot.setDepthFactor(depth);
    }

    @Override
    public void validateDouble(Double depth)
    {
        if (depth < 0)
        {
            throw annotateException("The depth factor must be a positive value.");
        }
    }

}
