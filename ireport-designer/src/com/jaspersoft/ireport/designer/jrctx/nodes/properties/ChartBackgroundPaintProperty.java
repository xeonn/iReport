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
import net.sf.jasperreports.chartthemes.simple.ChartSettings;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class ChartBackgroundPaintProperty extends PaintProviderProperty
{
    private final ChartSettings chartSettings;

    @SuppressWarnings("unchecked")
    public ChartBackgroundPaintProperty(ChartSettings chartSettings)
    {
        super(chartSettings);
        this.chartSettings = chartSettings;
    }

    @Override
    public String getName()
    {
        return ChartSettings.PROPERTY_backgroundPaint;
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
        return chartSettings.getBackgroundPaint();
    }

    @Override
    public PaintProvider getOwnPaintProvider()
    {
        return chartSettings.getBackgroundPaint();
    }

    @Override
    public PaintProvider getDefaultPaintProvider()
    {
        return null;
    }

    @Override
    public void setPaintProvider(PaintProvider paintProvider)
    {
        chartSettings.setBackgroundPaint(paintProvider);
    }

}
