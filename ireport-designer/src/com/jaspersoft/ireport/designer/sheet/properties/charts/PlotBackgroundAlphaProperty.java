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
 *  Class to manage the JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA property
 */
public final class PlotBackgroundAlphaProperty extends FloatProperty {

    private final JRBaseChartPlot plot;

    @SuppressWarnings("unchecked")
    public PlotBackgroundAlphaProperty(JRBaseChartPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA;
    }

    @Override
    public String getDisplayName()
    {
        return "Background Alpha (%)";
    }

    @Override
    public String getShortDescription()
    {
        return "Background Alpha.";
    }

    @Override
    public Float getFloat()
    {
        return plot.getBackgroundAlpha();
    }

    @Override
    public Float getOwnFloat()
    {
        return plot.getBackgroundAlpha();
    }

    @Override
    public Float getDefaultFloat()
    {
        return 1f;
    }

    @Override
    public void setFloat(Float backgroundAlpha)
    {
        plot.setBackgroundAlpha(backgroundAlpha);
    }

    @Override
    public void validateFloat(Float backgroundAlpha)
    {
        if (backgroundAlpha < 0f || backgroundAlpha > 1f)
        {
            throw annotateException("The value must be between 0 and 1.");
        }
    }

}
