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
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_VALUE_AXIS_TICK_LABEL_MASK property
 */
public final class LineValueAxisTickLabelMaskProperty extends StringProperty
{
    private final JRDesignLinePlot plot;

    
    public LineValueAxisTickLabelMaskProperty(JRDesignLinePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_VALUE_AXIS_TICK_LABEL_MASK;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Value_Axis_Tick_Label_Mask");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Value_Axis_Tick_Label_Mask.");
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
