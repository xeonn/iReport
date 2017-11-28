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
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
    
    
/**
 *  Class to manage the JRDesignPiePlot.PROPERTY_CIRCULAR property
 */
public final class PieCircularProperty extends BooleanProperty {

    private final JRDesignPiePlot plot;

    
    public PieCircularProperty(JRDesignPiePlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignPiePlot.PROPERTY_CIRCULAR;
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
