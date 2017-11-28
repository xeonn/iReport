/*
 * AreaValueAxisLineColorProperty.java
 * 
 * Created on 20-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_VALUE_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class AreaValueAxisLineColorProperty  extends ColorProperty {

    private final JRDesignAreaPlot element;

    @SuppressWarnings("unchecked")
    public AreaValueAxisLineColorProperty(JRDesignAreaPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignAreaPlot.PROPERTY_VALUE_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ValueAxisLineColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ValueAxisLineColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getValueAxisLineColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnValueAxisLineColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setValueAxisLineColor(color);
    }
}
