/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;
import net.sf.jasperreports.chartthemes.simple.PlotSettings;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class PlotBackgroundPaintProperty extends PaintProviderProperty
{
    private final PlotSettings plotSettings;

    @SuppressWarnings("unchecked")
    public PlotBackgroundPaintProperty(PlotSettings plotSettings)
    {
        super(plotSettings);
        this.plotSettings = plotSettings;
    }

    @Override
    public String getName()
    {
        return PlotSettings.PROPERTY_backgroundPaint;
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
    public PaintProvider getPaintProvider() 
    {
        return plotSettings.getBackgroundPaint();
    }

    @Override
    public PaintProvider getOwnPaintProvider()
    {
        return plotSettings.getBackgroundPaint();
    }

    @Override
    public PaintProvider getDefaultPaintProvider()
    {
        return null;
    }

    @Override
    public void setPaintProvider(PaintProvider PaintProvider)
    {
        plotSettings.setBackgroundPaint(PaintProvider);
    }

}
