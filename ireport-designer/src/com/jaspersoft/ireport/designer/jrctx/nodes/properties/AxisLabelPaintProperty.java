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

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class AxisLabelPaintProperty extends ColorProperty 
{
    private final AxisSettings settings;

    @SuppressWarnings("unchecked")
    public AxisLabelPaintProperty(AxisSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return AxisSettings.PROPERTY_labelPaint;
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
            settings.getLabelPaint() instanceof ColorProvider
            ? (ColorProvider)settings.getLabelPaint() : null;
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
        settings.setLabelPaint(color == null ? null : new ColorProvider(color));
    }

}
