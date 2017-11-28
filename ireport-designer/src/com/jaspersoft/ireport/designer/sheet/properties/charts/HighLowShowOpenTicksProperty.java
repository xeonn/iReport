/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
    
    
/**
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_SHOW_OPEN_TICKS property
 */
public final class HighLowShowOpenTicksProperty extends BooleanProperty {

    private final JRDesignHighLowPlot plot;

    @SuppressWarnings("unchecked")
    public HighLowShowOpenTicksProperty(JRDesignHighLowPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_SHOW_OPEN_TICKS;
    }

    @Override
    public String getDisplayName()
    {
        return "Show OpenTicks";
    }

    @Override
    public String getShortDescription()
    {
        return "Show OpenTicks.";
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowOpenTicks();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowOpenTicks();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowOpenTicks(isShow);
    }

}
