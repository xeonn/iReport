/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.FloatProperty;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
    
    
/**
 *  Class to manage the JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA property
 */
public final class PlotForegroundAlphaProperty extends FloatProperty {

    private final JRBaseChartPlot plot;

    @SuppressWarnings("unchecked")
    public PlotForegroundAlphaProperty(JRBaseChartPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA;
    }

    @Override
    public String getDisplayName()
    {
        return "Foreground Alpha (%)";
    }

    @Override
    public String getShortDescription()
    {
        return "Foreground Alpha.";
    }

    @Override
    public Float getFloat()
    {
        return plot.getForegroundAlpha();
    }

    @Override
    public Float getOwnFloat()
    {
        return plot.getForegroundAlpha();
    }

    @Override
    public Float getDefaultFloat()
    {
        return 1f;
    }

    @Override
    public void setFloat(Float foregroundAlpha)
    {
        plot.setForegroundAlpha(foregroundAlpha);
    }

    @Override
    public void validateFloat(Float foregroundAlpha)
    {
        if (foregroundAlpha < 0f || foregroundAlpha > 1f)
        {
            throw annotateException("The value must be between 0 and 1.");
        }
    }

}
