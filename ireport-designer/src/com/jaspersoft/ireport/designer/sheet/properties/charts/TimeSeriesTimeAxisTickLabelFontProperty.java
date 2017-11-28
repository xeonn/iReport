/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractFontProperty;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_FONT property
 */
public final class TimeSeriesTimeAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignTimeSeriesPlot plot;
        
    @SuppressWarnings("unchecked")
    public TimeSeriesTimeAxisTickLabelFontProperty(JRDesignTimeSeriesPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "Time Axis Tick Label Font";
    }

    @Override
    public String getShortDescription()
    {
        return "Time Axis Tick Label Font.";
    }

    @Override
    public JRFont getFont()
    {
        return plot.getTimeAxisTickLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setTimeAxisTickLabelFont(font);
    }
    
}
