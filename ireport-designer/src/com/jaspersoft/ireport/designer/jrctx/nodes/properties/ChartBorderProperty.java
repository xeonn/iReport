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
import net.sf.jasperreports.chartthemes.simple.ChartSettings;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class ChartBorderProperty extends AbstractPenProperty
{
    private final ChartSettings chartSettings;

    @SuppressWarnings("unchecked")
    public ChartBorderProperty(ChartSettings chartSettings)
    {
        super(chartSettings);
        this.chartSettings = chartSettings;
    }

    @Override
    public String getName()
    {
        return ChartSettings.PROPERTY_borderPaint;//FIXMETHEME
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
        return chartSettings.getBorderPaint();
    }

    public void setPaintProvider(PaintProvider paintProvider)
    {
        chartSettings.setBorderPaint(paintProvider);
    }

    public Stroke getStroke()
    {
        return chartSettings.getBorderStroke();
    }

    public void setStroke(Stroke stroke)
    {
        chartSettings.setBorderStroke(stroke);
    }

}
