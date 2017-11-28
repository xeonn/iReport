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
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_SHOW_CLOSE_TICKS property
 */
public final class HighLowShowCloseTicksProperty extends BooleanProperty {

    private final JRDesignHighLowPlot plot;

    @SuppressWarnings("unchecked")
    public HighLowShowCloseTicksProperty(JRDesignHighLowPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_SHOW_CLOSE_TICKS;
    }

    @Override
    public String getDisplayName()
    {
        return "Show Close Ticks";
    }

    @Override
    public String getShortDescription()
    {
        return "Show Close Ticks.";
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowCloseTicks();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowCloseTicks();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowCloseTicks(isShow);
    }

}
