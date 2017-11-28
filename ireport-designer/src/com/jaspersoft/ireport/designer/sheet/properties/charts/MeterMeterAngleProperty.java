/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.IntegerProperty;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
    
    
/**
 *  Class to manage the JRDesignMeterPlot.PROPERTY_METER_ANGLE property
 */
public final class MeterMeterAngleProperty extends IntegerProperty {

    private final JRDesignMeterPlot plot;

    @SuppressWarnings("unchecked")
    public MeterMeterAngleProperty(JRDesignMeterPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignMeterPlot.PROPERTY_METER_ANGLE;
    }

    @Override
    public String getDisplayName()
    {
        return "Meter Angle";
    }

    @Override
    public String getShortDescription()
    {
        return "Meter Angle.";
    }

    @Override
    public Integer getInteger()
    {
        return plot.getMeterAngle();
    }

    @Override
    public Integer getOwnInteger()
    {
        return plot.getMeterAngle();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return 180;
    }

    @Override
    public void setInteger(Integer height)
    {
        plot.setMeterAngle(height);
    }

    @Override
    public void validateInteger(Integer angle)
    {
        if (angle < 0 || angle > 360)
        {
            throw annotateException("The angle should be in the range 0-360.");
        }
    }
    
}
