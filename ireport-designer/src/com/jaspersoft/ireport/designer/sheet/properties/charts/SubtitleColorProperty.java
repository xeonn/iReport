/*
 * SubtitleColorProperty.java
 * 
 * Created on 22-feb-2008, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import java.awt.Color;
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.design.JRDesignChart;
    
    
/**
 *  Class to manage the JRBaseChart.PROPERTY_SUBTITLE_COLOR property
 *  @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class SubtitleColorProperty extends ColorProperty {

    private final JRDesignChart element;

    @SuppressWarnings("unchecked")
    public SubtitleColorProperty(JRDesignChart element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseChart.PROPERTY_SUBTITLE_COLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Subtitle Color";
    }

    @Override
    public String getShortDescription()
    {
        return "The color of the subtitle.";
    }

    @Override
    public Color getColor() 
    {
        return element.getSubtitleColor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnSubtitleColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setSubtitleColor(color);
    }
    
}
