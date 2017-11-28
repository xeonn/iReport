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
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
    
    
/**
 *  Class to manage the JRBaseChartPlot.PROPERTY_FOREGROUND_ALPHA property
 */
public final class PlotForegroundAlphaProperty extends FloatProperty {

    private final JRBaseChartPlot plot;

    
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
        return I18n.getString("Foreground_Alpha_(%)");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Foreground_Alpha.");
    }

    @Override
    public Float getFloat()
    {
        return plot.getForegroundAlphaFloat();
    }

    @Override
    public Float getOwnFloat()
    {
        return plot.getForegroundAlphaFloat();
    }

    @Override
    public Float getDefaultFloat()
    {
        return null;
    }

    @Override
    public void setFloat(Float foregroundAlpha)
    {
        plot.setForegroundAlpha(foregroundAlpha);
    }

    @Override
    public void validateFloat(Float foregroundAlpha)
    {
        if (foregroundAlpha != null && (foregroundAlpha < 0f || foregroundAlpha > 1f))
        {
            throw annotateException(I18n.getString("The_value_must_be_between_0_and_1."));
        }
    }

}
