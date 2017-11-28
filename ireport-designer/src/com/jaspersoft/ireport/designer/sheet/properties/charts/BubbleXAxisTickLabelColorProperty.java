/*
 * BubbleXAxisTickLabelColorProperty.java
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
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_TICK_LABEL_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class BubbleXAxisTickLabelColorProperty extends ColorProperty {

    private final JRDesignBubblePlot element;

    @SuppressWarnings("unchecked")
    public BubbleXAxisTickLabelColorProperty(JRDesignBubblePlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_TICK_LABEL_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.X_AxisTickLabelColor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.X_AxisTickLabelColordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getXAxisTickLabelColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnXAxisTickLabelColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setXAxisTickLabelColor(color);
    }
        
}
