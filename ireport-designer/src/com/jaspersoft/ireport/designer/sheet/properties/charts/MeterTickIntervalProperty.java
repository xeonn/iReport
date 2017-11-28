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
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
    
    
/**
 *  Class to manage the JRDesignMeterPlot.PROPERTY_TICK_INTERVAL property
 */
public final class MeterTickIntervalProperty extends DoubleProperty {

    private final JRDesignMeterPlot plot;

    @SuppressWarnings("unchecked")
    public MeterTickIntervalProperty(JRDesignMeterPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignMeterPlot.PROPERTY_TICK_INTERVAL;
    }

    @Override
    public String getDisplayName()
    {
        return "Tick Interval";
    }

    @Override
    public String getShortDescription()
    {
        return "Tick Interval.";
    }

    @Override
    public Double getDouble()
    {
        return plot.getTickInterval();
    }

    @Override
    public Double getOwnDouble()
    {
        return plot.getTickInterval();
    }

    @Override
    public Double getDefaultDouble()
    {
        return 10d;
    }

    @Override
    public void setDouble(Double tickInterval)
    {
        plot.setTickInterval(tickInterval);
    }

    @Override
    public void validateDouble(Double tickInterval)
    {
        //FIXME: are there some constraints to be taken into account?
    }

}
