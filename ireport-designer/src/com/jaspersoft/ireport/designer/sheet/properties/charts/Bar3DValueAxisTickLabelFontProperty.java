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
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignBar3DPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_FONT property
 */
public final class Bar3DValueAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignBar3DPlot plot;
        
    @SuppressWarnings("unchecked")
    public Bar3DValueAxisTickLabelFontProperty(JRDesignBar3DPlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ValueAxisTickLabelFont");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ValueAxisTickLabelFontdetail");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getValueAxisTickLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setValueAxisTickLabelFont(font);
    }

}
