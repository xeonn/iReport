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
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDisplay;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 * Class to manage the JRDesignValueDisplay.PROPERTY_FONT
 */
public final class MeterValueFontProperty extends AbstractFontProperty
{
    private final JRDesignMeterPlot plot;
        
    public MeterValueFontProperty(JRDesignMeterPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }
    
    @Override
    public String getName()
    {
        return JRDesignValueDisplay.PROPERTY_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Value_Font");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Value_Font");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getValueDisplay() == null ? null : plot.getValueDisplay().getFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        JRDesignValueDisplay newValue = 
            new JRDesignValueDisplay(plot.getValueDisplay(), plot.getChart());
        newValue.setFont(font);
        plot.setValueDisplay(newValue);
    }

}
