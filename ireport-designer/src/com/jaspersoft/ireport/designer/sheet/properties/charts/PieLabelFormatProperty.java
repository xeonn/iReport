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
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_MASK property
 */
public final class PieLabelFormatProperty extends StringProperty
{
    private final JRDesignPiePlot plot;

    
    public PieLabelFormatProperty(JRDesignPiePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignPiePlot.PROPERTY_LABEL_FORMAT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("LabelFormat");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("LabelFormat.desc");
    }

    @Override
    public String getString()
    {
        return plot.getLabelFormat();
    }

    @Override
    public String getOwnString()
    {
        return plot.getLabelFormat();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String mask)
    {
        plot.setLabelFormat(mask);
    }
    
}
