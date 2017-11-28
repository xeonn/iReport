/*
 * BubbleXAxisLabelColorProperty.java
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
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class BubbleXAxisLabelColorProperty extends ColorProperty {

    private final JRDesignBubblePlot element;

    @SuppressWarnings("unchecked")
    public BubbleXAxisLabelColorProperty(JRDesignBubblePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.X_AxisLabelColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.X_AxisLabelColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getXAxisLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnXAxisLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setXAxisLabelColor(color);
    }
    
}
