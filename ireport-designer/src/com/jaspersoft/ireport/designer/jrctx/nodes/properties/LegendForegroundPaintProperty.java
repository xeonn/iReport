/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.*;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.chartthemes.simple.AxisSettings;
import net.sf.jasperreports.chartthemes.simple.ColorProvider;
import net.sf.jasperreports.chartthemes.simple.LegendSettings;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class LegendForegroundPaintProperty extends ColorProperty 
{
    private final LegendSettings settings;

    @SuppressWarnings("unchecked")
    public LegendForegroundPaintProperty(LegendSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return LegendSettings.PROPERTY_foregroundPaint;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property." + getName());
    }

    @Override
    public String getShortDescription()
    {
        return getDisplayName();
    }

    @Override
    public Color getColor() 
    {
        ColorProvider colorProvider = 
            settings.getForegroundPaint() instanceof ColorProvider
            ? (ColorProvider)settings.getForegroundPaint() : null;
        return colorProvider == null ? null : colorProvider.getColor();
    }

    @Override
    public Color getOwnColor()
    {
        return getColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        settings.setForegroundPaint(color == null ? null : new ColorProvider(color));
    }

}
