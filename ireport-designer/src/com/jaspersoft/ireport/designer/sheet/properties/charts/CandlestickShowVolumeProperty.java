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
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
    
    
/**
 *  Class to manage the JRDesignCandlestickPlot.PROPERTY_SHOW_VOLUME property
 */
public final class CandlestickShowVolumeProperty extends BooleanProperty {

    private final JRDesignCandlestickPlot plot;

    @SuppressWarnings("unchecked")
    public CandlestickShowVolumeProperty(JRDesignCandlestickPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignCandlestickPlot.PROPERTY_SHOW_VOLUME;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ShowVolume");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ShowVolumedetail");
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.getShowVolume();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.getShowVolume();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowVolume(isShow);
    }

}
