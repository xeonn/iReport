/*
 * MeterTickColorProperty.java
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
 *  Class to manage the JRDesignMeterPlot.PROPERTY_TICK_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class MeterTickColorProperty extends ColorProperty {

    private final JRDesignMeterPlot element;

    
    public MeterTickColorProperty(JRDesignMeterPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignMeterPlot.PROPERTY_TICK_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Tick_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_ticks.");
    }

    @Override
    public Color getColor() 
    {
        return element.getTickColor();
    }

    @Override
    public Color getOwnColor()
    {
        // FIXME: check this;  
        // there is no own tick color for the meterPlot element
        return element.getTickColor();
    }

    @Override
    public Color getDefaultColor()
    {
        // FIXME: check this; 
        // there is no own tick color for the meterPlot element
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setTickColor(color);
    }

}
