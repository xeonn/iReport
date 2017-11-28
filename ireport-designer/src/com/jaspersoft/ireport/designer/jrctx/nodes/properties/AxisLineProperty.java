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

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class AxisLineProperty extends AbstractPenProperty
{
    private final AxisSettings settings;

    @SuppressWarnings("unchecked")
    public AxisLineProperty(AxisSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return AxisSettings.PROPERTY_linePaint;//FIXMETHEME
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
        return settings.getLinePaint();
    }

    public void setPaintProvider(PaintProvider paintProvider)
    {
        settings.setLinePaint(paintProvider);
    }

    public Stroke getStroke()
    {
        return settings.getLineStroke();
    }

    public void setStroke(Stroke stroke)
    {
        settings.setLineStroke(stroke);
    }

}
