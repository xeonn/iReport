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
import net.sf.jasperreports.chartthemes.simple.AxisSettings;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;
import net.sf.jasperreports.chartthemes.simple.PlotSettings;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class TickMarksProperty extends AbstractPenProperty
{
    private final AxisSettings settings;

    @SuppressWarnings("unchecked")
    public TickMarksProperty(AxisSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return AxisSettings.PROPERTY_tickMarksPaint;//FIXMETHEME
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
        return settings.getTickMarksPaint();
    }

    public void setPaintProvider(PaintProvider paintProvider)
    {
        settings.setTickMarksPaint(paintProvider);
    }

    public Stroke getStroke()
    {
        return settings.getTickMarksStroke();
    }

    public void setStroke(Stroke stroke)
    {
        settings.setTickMarksStroke(stroke);
    }

}
