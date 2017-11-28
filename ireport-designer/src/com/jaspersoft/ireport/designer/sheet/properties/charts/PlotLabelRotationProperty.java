/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.DoubleProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
    
    
/**
 *  Class to manage the JRBaseChartPlot.PROPERTY_LABEL_ROTATION property
 */
public final class PlotLabelRotationProperty extends DoubleProperty {

    private final JRBaseChartPlot plot;

    
    public PlotLabelRotationProperty(JRBaseChartPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRBaseChartPlot.PROPERTY_LABEL_ROTATION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Label_rotation");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Label_rotation.");
    }

    @Override
    public Double getDouble()
    {
        return plot.getLabelRotationDouble();
    }

    @Override
    public Double getOwnDouble()
    {
        return plot.getLabelRotationDouble();
    }

    @Override
    public Double getDefaultDouble()
    {
        return null;
    }

    @Override
    public void setDouble(Double labelRotation)
    {
        plot.setLabelRotation(labelRotation);
    }

    @Override
    public void validateDouble(Double labelRotation)
    {
        //FIXME: are there some constraints to be taken into account?
    }

}
