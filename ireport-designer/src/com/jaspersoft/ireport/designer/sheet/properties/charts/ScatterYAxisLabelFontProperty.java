/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractFontProperty;
import net.sf.jasperreports.charts.design.JRDesignScatterPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignScatterPlot.PROPERTY_Y_AXIS_LABEL_FONT property
 */
public final class ScatterYAxisLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignScatterPlot plot;
        
    @SuppressWarnings("unchecked")
    public ScatterYAxisLabelFontProperty(JRDesignScatterPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignScatterPlot.PROPERTY_Y_AXIS_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "Y Axis Label Font";
    }

    @Override
    public String getShortDescription()
    {
        return "Y Axis Label Font.";
    }

    @Override
    public JRFont getFont()
    {
        return plot.getYAxisLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setYAxisLabelFont(font);
    }

}
