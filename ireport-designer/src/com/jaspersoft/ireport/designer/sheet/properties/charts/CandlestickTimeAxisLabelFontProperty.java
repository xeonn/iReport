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
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignCandlestickPlot.PROPERTY_TIME_AXIS_LABEL_FONT property
 */
public final class CandlestickTimeAxisLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignCandlestickPlot plot;
        
    @SuppressWarnings("unchecked")
    public CandlestickTimeAxisLabelFontProperty(JRDesignCandlestickPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignCandlestickPlot.PROPERTY_TIME_AXIS_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.TimeAxisLabelFont");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.TimeAxisLabelFontdetail");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getTimeAxisLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setTimeAxisLabelFont(font);
    }

}
