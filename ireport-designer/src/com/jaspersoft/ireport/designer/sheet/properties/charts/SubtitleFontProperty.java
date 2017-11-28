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
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;


/**
 *  Class to manage the JRDesignChart.PROPERTY_SUBTITLE_FONT property
 */
public final class SubtitleFontProperty extends AbstractFontProperty
{
    private final JRDesignChart element;
        
    @SuppressWarnings("unchecked")
    public SubtitleFontProperty(JRDesignChart element, JasperDesign jasperDesign)
    {
        super(element, jasperDesign);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignChart.PROPERTY_SUBTITLE_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "Subtitle Font";
    }

    @Override
    public String getShortDescription()
    {
        return "Subtitle font.";
    }

    @Override
    public JRFont getFont()
    {
        return element.getSubtitleFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        element.setSubtitleFont(font);
    }

}
