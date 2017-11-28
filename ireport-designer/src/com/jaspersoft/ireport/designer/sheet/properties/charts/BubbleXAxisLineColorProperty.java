/*
 * BubbleXAxisLineColorProperty.java
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
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
    
    
/**
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_LINE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class BubbleXAxisLineColorProperty extends ColorProperty {

    private final JRDesignBubblePlot element;

    @SuppressWarnings("unchecked")
    public BubbleXAxisLineColorProperty(JRDesignBubblePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_LINE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.X_AxisLineColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.X_AxisLineColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getXAxisLineColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnXAxisLineColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setXAxisLineColor(color);
    }

}
