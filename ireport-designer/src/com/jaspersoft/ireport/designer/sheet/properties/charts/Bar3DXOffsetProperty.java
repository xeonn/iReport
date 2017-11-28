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
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_X_OFFSET property
 */
public final class Bar3DXOffsetProperty extends DoubleProperty {

    private final JRDesignBar3DPlot plot;

    @SuppressWarnings("unchecked")
    public Bar3DXOffsetProperty(JRDesignBar3DPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_X_OFFSET;
    }

    @Override
    public String getDisplayName()
    {
        return "X Offset";
    }

    @Override
    public String getShortDescription()
    {
        return "X Offset.";
    }

    @Override
    public Double getDouble()
    {
        return plot.getXOffset();
    }

    @Override
    public Double getOwnDouble()
    {
        return plot.getXOffset();
    }

    @Override
    public Double getDefaultDouble()
    {
        return null;//FIXME is this a fair default?
    }

    @Override
    public void setDouble(Double xOffset)
    {
        plot.setXOffset(xOffset);
    }

    @Override
    public void validateDouble(Double xOffset)
    {
        //FIXME: are there some constraints to be taken into account?
    }
    
}
