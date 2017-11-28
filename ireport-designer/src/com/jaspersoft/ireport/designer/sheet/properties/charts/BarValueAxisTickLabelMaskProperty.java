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
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
    
    
/**
 *  Class to manage the JRDesignBarPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_MASK property
 */
public final class BarValueAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignBarPlot plot;

    @SuppressWarnings("unchecked")
    public BarValueAxisTickLabelMaskProperty(JRDesignBarPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignBarPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return "Value Axis Tick Label Mask";
    }

    @Override
    public String getShortDescription()
    {
        return "Value Axis Tick Label Mask.";
    }

    @Override
    public String getString()
    {
        return plot.getValueAxisTickLabelMask();
    }

    @Override
    public String getOwnString()
    {
        return plot.getValueAxisTickLabelMask();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setValueAxisTickLabelMask(mask);
    }

}
