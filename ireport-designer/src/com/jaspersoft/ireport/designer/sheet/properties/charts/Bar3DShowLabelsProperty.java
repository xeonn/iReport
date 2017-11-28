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
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
    
    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class Bar3DShowLabelsProperty extends BooleanProperty
{
    private final JRDesignBar3DPlot plot;

    @SuppressWarnings("unchecked")
    public Bar3DShowLabelsProperty(JRDesignBar3DPlot plot)
    {
        super(plot);
        this.plot = plot;
    }
    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_SHOW_LABELS;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ShowLabels");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ShowLabelsdetail");
    }

    @Override
    public Boolean getBoolean()
    {
        return plot.isShowLabels();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return plot.isShowLabels();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return Boolean.FALSE;
    }

    @Override
    public void setBoolean(Boolean isShow)
    {
    	plot.setShowLabels(isShow);
    }

}
