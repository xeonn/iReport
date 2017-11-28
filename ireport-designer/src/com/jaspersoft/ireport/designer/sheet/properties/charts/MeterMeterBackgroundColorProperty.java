/*
 * MeterMeterBackgroundColorProperty.java
 * 
 * Created on 21-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
    
    
/**
 *  Class to manage the JRDesignMeterPlot.PROPERTY_METER_BACKGROUND_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class MeterMeterBackgroundColorProperty extends ColorProperty {

    private final JRDesignMeterPlot element;

    
    public MeterMeterBackgroundColorProperty(JRDesignMeterPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignMeterPlot.PROPERTY_METER_BACKGROUND_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Meter_Background_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_background_color_of_the_meter.");
    }

    @Override
    public Color getColor() 
    {
        return element.getMeterBackgroundColor();
    }

    @Override
    public Color getOwnColor()
    {
        // FIXME: check this;  
        // there is no own background color for the meterPlot element
        return element.getMeterBackgroundColor();
    }

    @Override
    public Color getDefaultColor()
    {
        // FIXME: check this; 
        // there is no own background color for the meterPlot element
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setMeterBackgroundColor(color);
    }
}
