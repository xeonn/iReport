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
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.design.JRDesignChart;
    
    
/**
 *  Class to manage the JRBaseChart.PROPERTY_SHOW_LEGEND property
 */
public final class ShowLegendProperty extends BooleanProperty {

    private final JRDesignChart element;

    @SuppressWarnings("unchecked")
    public ShowLegendProperty(JRDesignChart element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseChart.PROPERTY_SHOW_LEGEND;
    }

    @Override
    public String getDisplayName()
    {
        return "Show Legend";
    }

    @Override
    public String getShortDescription()
    {
        return "Show Legend.";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isShowLegend();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isShowLegend();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.TRUE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	element.setShowLegend(isShow);
    }

}
