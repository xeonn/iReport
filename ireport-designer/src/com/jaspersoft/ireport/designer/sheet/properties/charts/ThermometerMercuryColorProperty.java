/*
 * ThermometerMercuryColorProperty.java
 * 
 * Created on 22-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
    
    
/**
 *  Class to manage the JRDesignThermometerPlot.PROPERTY_MERCURY_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class ThermometerMercuryColorProperty extends ColorProperty {

    private final JRDesignThermometerPlot element;

    
    public ThermometerMercuryColorProperty(JRDesignThermometerPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignThermometerPlot.PROPERTY_MERCURY_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Mercury_Color");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("The_color_of_the_mercury_inside_the_thermometer.");
    }

    @Override
    public Color getColor() 
    {
        return element.getMercuryColor();
    }

    @Override
    public Color getOwnColor()
    {
        // FIXME: check this
        // There is no own mercury color
        return element.getMercuryColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setMercuryColor(color);
    }

}
