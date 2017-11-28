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
        return I18n.getString("Global.Property.ShowTickMarks");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ShowTickMarksdetail");
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.getShowTickMarks();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.getShowTickMarks();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowTickMarks(isShow);
    }

}
