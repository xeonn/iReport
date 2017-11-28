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
public final class DomainGridlineProperty extends AbstractPenProperty
{
    private final PlotSettings settings;

    @SuppressWarnings("unchecked")
    public DomainGridlineProperty(PlotSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return PlotSettings.PROPERTY_domainGridlinePaint;//FIXMETHEME
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
        return settings.getDomainGridlinePaint();
    }

    public void setPaintProvider(PaintProvider paintProvider)
    {
        settings.setDomainGridlinePaint(paintProvider);
    }

    public Stroke getStroke()
    {
        return settings.getDomainGridlineStroke();
    }

    public void setStroke(Stroke stroke)
    {
        settings.setDomainGridlineStroke(stroke);
    }

}
