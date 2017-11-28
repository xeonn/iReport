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
import java.awt.Stroke;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;
import net.sf.jasperreports.chartthemes.simple.PlotSettings;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class RangeGridlineProperty extends AbstractPenProperty
{
    private final PlotSettings settings;

    @SuppressWarnings("unchecked")
    public RangeGridlineProperty(PlotSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return PlotSettings.PROPERTY_rangeGridlinePaint;//FIXMETHEME
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

    public PaintProvider getPaintProvider()
    {
        return settings.getRangeGridlinePaint();
    }

    public void setPaintProvider(PaintProvider paintProvider)
    {
        settings.setRangeGridlinePaint(paintProvider);
    }

    public Stroke getStroke()
    {
        return settings.getRangeGridlineStroke();
    }

    public void setStroke(Stroke stroke)
    {
        settings.setRangeGridlineStroke(stroke);
    }

}
