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
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
    
    
/**
 *  Class to manage the JRDesignPie3DPlot.PROPERTY_CIRCULAR property
 */
public final class Pie3DCircularProperty extends BooleanProperty {

    private final JRDesignPie3DPlot plot;

    
    public Pie3DCircularProperty(JRDesignPie3DPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignPie3DPlot.PROPERTY_CIRCULAR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Circular");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Circular.");
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.getCircular();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.getCircular();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setCircular(isShow);
    }

}
