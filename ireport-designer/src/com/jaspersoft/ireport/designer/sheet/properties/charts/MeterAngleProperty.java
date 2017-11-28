/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.NullableIntegerProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
    
    
/**
 *  Class to manage the JRDesignMeterPlot.PROPERTY_METER_ANGLE property
 */
public final class MeterAngleProperty extends NullableIntegerProperty {

    private final JRDesignMeterPlot plot;

    
    public MeterAngleProperty(JRDesignMeterPlot plot)
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
        return I18n.getString("Meter_Angle");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Meter_Angle.");
    }

    @Override
    public Integer getInteger()
    {
        return plot.getMeterAngleInteger();
    }

    @Override
    public Integer getOwnInteger()
    {
        return plot.getMeterAngleInteger();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return null;
    }

    @Override
    public void setInteger(Integer height)
    {
        plot.setMeterAngle(height);
    }

    @Override
    public void validateInteger(Integer angle)
    {
        if (angle != null && (angle < 0 || angle > 360))
        {
            throw annotateException(I18n.getString("The_angle_should_be_in_the_range_0-360."));
        }
    }
    
}
