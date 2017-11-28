
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
 *  Class to manage the JRDesignHighLowPlot.PROPERTY_VALUE_AXIS_LABEL_FONT property
 */
public final class HighLowValueAxisLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignHighLowPlot plot;
        
    
    public HighLowValueAxisLabelFontProperty(JRDesignHighLowPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignHighLowPlot.PROPERTY_VALUE_AXIS_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Value_Axis_Label_Font");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Value_Axis_Label_Font.");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getValueAxisLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setValueAxisLabelFont(font);
    }
    
}
