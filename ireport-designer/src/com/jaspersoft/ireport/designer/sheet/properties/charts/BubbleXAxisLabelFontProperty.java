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
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_LABEL_FONT property
 */
public final class BubbleXAxisLabelFontProperty extends AbstractFontProperty
{
    private final JRDesignBubblePlot plot;
        
    @SuppressWarnings("unchecked")
    public BubbleXAxisLabelFontProperty(JRDesignBubblePlot plot, JasperDesign jasperDesign)
    {
        super(plot, jasperDesign);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_LABEL_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "X Axis Label Font";
    }

    @Override
    public String getShortDescription()
    {
        return "X Axis Label Font.";
    }

    @Override
    public JRFont getFont()
    {
        return plot.getXAxisLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        plot.setXAxisLabelFont(font);
    }

}
