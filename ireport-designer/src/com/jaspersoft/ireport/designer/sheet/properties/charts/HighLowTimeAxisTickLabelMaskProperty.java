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
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
    
    
/**
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_TIME_AXIS_TICK_LABEL_MASK property
 */
public final class HighLowTimeAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignHighLowPlot plot;

    
    public HighLowTimeAxisTickLabelMaskProperty(JRDesignHighLowPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_TIME_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Time_Axis_Tick_Label_Mask");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Time_Axis_Tick_Label_Mask.");
    }

    @Override
    public String getString()
    {
        return plot.getTimeAxisTickLabelMask();
    }

    @Override
    public String getOwnString()
    {
        return plot.getTimeAxisTickLabelMask();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setTimeAxisTickLabelMask(mask);
    }

}
