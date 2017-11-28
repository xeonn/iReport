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
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
    
    
/**
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_Y_OFFSET property
 */
public final class Bar3DYOffsetProperty extends DoubleProperty {

    private final JRDesignBar3DPlot plot;

    @SuppressWarnings("unchecked")
    public Bar3DYOffsetProperty(JRDesignBar3DPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_Y_OFFSET;
    }

    @Override
    public String getDisplayName()
    {
        return "Y Offset";
    }

    @Override
    public String getShortDescription()
    {
        return "Y Offset.";
    }

    @Override
    public Double getDouble()
    {
        return plot.getYOffset();
    }

    @Override
    public Double getOwnDouble()
    {
        return plot.getYOffset();
    }

    @Override
    public Double getDefaultDouble()
    {
        return null;//FIXME is this a fair default?
    }

    @Override
    public void setDouble(Double yOffset)
    {
        plot.setYOffset(yOffset);
    }

    @Override
    public void validateDouble(Double yOffset)
    {
        //FIXME: are there some constraints to be taken into account?
    }

}
