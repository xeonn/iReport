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
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_SHOW_LINES property
 */
public final class LineShowLinesProperty extends BooleanProperty {

    private final JRDesignLinePlot plot;

    
    public LineShowLinesProperty(JRDesignLinePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_SHOW_LINES;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Show_Lines");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Show_Lines.");
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowLines();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowLines();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowLines(isShow);
    }

}
