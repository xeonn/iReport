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
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
    
    
/**
 *  Class to manage the JRDesignThermometerPlot.PROPERTY_SHOW_VALUE_LINES property
 */
public final class ThermometerShowValueLinesProperty extends BooleanProperty {

    private final JRDesignThermometerPlot plot;

    @SuppressWarnings("unchecked")
    public ThermometerShowValueLinesProperty(JRDesignThermometerPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignThermometerPlot.PROPERTY_SHOW_VALUE_LINES;
    }

    @Override
    public String getDisplayName()
    {
        return "Show Value Lines";
    }

    @Override
    public String getShortDescription()
    {
        return "Show Value Lines.";
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowValueLines();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowValueLines();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.FALSE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowValueLines(isShow);
    }

}
