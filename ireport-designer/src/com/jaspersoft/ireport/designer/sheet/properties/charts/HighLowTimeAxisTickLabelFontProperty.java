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
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignHighLowPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_TIME_AXIS_TICK_LABEL_FONT property
 */
public final class HighLowTimeAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignHighLowPlot plot;
        
    
    public HighLowTimeAxisTickLabelFontProperty(JRDesignHighLowPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_TIME_AXIS_TICK_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Time_Axis_Tick_Label_Font");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Time_Axis_Tick_Label_Font.");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getTimeAxisTickLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setTimeAxisTickLabelFont(font);
    }

}
