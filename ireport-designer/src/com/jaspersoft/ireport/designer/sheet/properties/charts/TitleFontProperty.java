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
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_FONT property
 */
public final class TitleFontProperty extends AbstractFontProperty
{
    private final JRDesignChart element;
        
    @SuppressWarnings("unchecked")
    public TitleFontProperty(JRDesignChart element, JasperDesign jasperDesign)
    {
        super(element, jasperDesign);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignChart.PROPERTY_TITLE_FONT;
    }

    @Override
    public String getDisplayName()
    {
        return "Title Font";
    }

    @Override
    public String getShortDescription()
    {
        return "Title font.";
    }

    @Override
    public JRFont getFont()
    {
        return element.getTitleFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        element.setTitleFont(font);
    }
    
}
