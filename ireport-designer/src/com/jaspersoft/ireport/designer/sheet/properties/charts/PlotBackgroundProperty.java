/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
    
    
/**
 *  Class to manage the JRBaseChartPlot.PROPERTY_BACKCOLOR property
 */
public final class PlotBackgroundProperty extends ColorProperty {

    private final JRBaseChartPlot plot;

    @SuppressWarnings("unchecked")
    public PlotBackgroundProperty(JRBaseChartPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRBaseChartPlot.PROPERTY_BACKCOLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Background";
    }

    @Override
    public String getShortDescription()
    {
        return "Background of the chart.";
    }

    @Override
    public Color getColor() 
    {
        return plot.getBackcolor();
    }

    @Override
    public Color getOwnColor()
    {
        return plot.getOwnBackcolor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        plot.setBackcolor(color);
    }

}
