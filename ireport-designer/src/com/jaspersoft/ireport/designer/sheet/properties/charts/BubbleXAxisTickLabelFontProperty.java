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
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_TICK_LABEL_FONT property
 */
public final class BubbleXAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignBubblePlot plot;
        
    @SuppressWarnings("unchecked")
    public BubbleXAxisTickLabelFontProperty(JRDesignBubblePlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_TICK_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.X_AxisTickLabelFont");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.X_AxisTickLabelFontdetail");
    }

    @Override
    public JRFont getFont()
    {
        return plot.getXAxisTickLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setXAxisTickLabelFont(font);
    }

}
