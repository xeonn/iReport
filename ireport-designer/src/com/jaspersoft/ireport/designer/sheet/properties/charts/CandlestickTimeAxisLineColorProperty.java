/*
 * CandlestickTimeAxisLineColorProperty.java
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
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
    
    
/**
 *  Class to manage the JRDesignCandlestickPlot.PROPERTY_TIME_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class CandlestickTimeAxisLineColorProperty extends ColorProperty {

    private final JRDesignCandlestickPlot element;

    @SuppressWarnings("unchecked")
    public CandlestickTimeAxisLineColorProperty(JRDesignCandlestickPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignCandlestickPlot.PROPERTY_TIME_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.TimeAxisLineColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.TimeAxisLineColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getTimeAxisLineColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnTimeAxisLineColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setTimeAxisLineColor(color);
    }
}
