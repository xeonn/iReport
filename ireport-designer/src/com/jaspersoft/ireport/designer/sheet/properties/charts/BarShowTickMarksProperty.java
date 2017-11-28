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
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
    
    
/**
 *  Class to manage the JRDesignBarPlot.PROPERTY_SHOW_TICK_MARKS property
 */
public final class BarShowTickMarksProperty extends BooleanProperty {

    private final JRDesignBarPlot plot;

    @SuppressWarnings("unchecked")
    public BarShowTickMarksProperty(JRDesignBarPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignBarPlot.PROPERTY_SHOW_TICK_MARKS;
    }

    @Override
    public String getDisplayName()
    {
        return "Show Tick Marks";
    }

    @Override
    public String getShortDescription()
    {
        return "Show Tick Marks.";
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowTickMarks();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowTickMarks();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowTickMarks(isShow);
    }

}
