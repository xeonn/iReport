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
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignBubblePlot.PROPERTY_Y_AXIS_TICK_LABEL_FONT property
 */
public final class BubbleYAxisTickLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignBubblePlot plot;
        
    @SuppressWarnings("unchecked")
    public BubbleYAxisTickLabelFontProperty(JRDesignBubblePlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_Y_AXIS_TICK_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "Y Axis Tick Label Font";
    }

    @Override
    public String getShortDescription()
    {
        return "Y Axis Tick Label Font.";
    }

    @Override
    public JRFont getFont()
    {
        return plot.getYAxisTickLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setYAxisTickLabelFont(font);
    }

}
