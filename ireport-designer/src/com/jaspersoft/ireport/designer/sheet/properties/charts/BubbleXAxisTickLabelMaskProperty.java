/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
    
    
/**
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_TICK_LABEL_MASK property
 */
public final class BubbleXAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignBubblePlot plot;

    @SuppressWarnings("unchecked")
    public BubbleXAxisTickLabelMaskProperty(JRDesignBubblePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.X_AxisTickLabelMask");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.X_AxisTickLabelMaskdetail");
    }

    @Override
    public String getString()
    {
        return plot.getXAxisTickLabelMask();
    }

    @Override
    public String getOwnString()
    {
        return plot.getXAxisTickLabelMask();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setXAxisTickLabelMask(mask);
    }

}
