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
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_SHOW_SHAPES property
 */
public final class TimeSeriesShowShapesProperty extends BooleanProperty {

    private final JRDesignTimeSeriesPlot plot;

    
    public TimeSeriesShowShapesProperty(JRDesignTimeSeriesPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_SHOW_SHAPES;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Show_Shapes");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Show_Shapes.");
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.getShowShapes();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.getShowShapes();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowShapes(isShow);
    }

}
