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
import net.sf.jasperreports.chartthemes.simple.ChartSettings;
import org.jfree.ui.RectangleInsets;

    
/**
 *
 */
public final class ChartPaddingProperty extends IntegerProperty
{
    private final ChartSettings settings;

    @SuppressWarnings("unchecked")
    public ChartPaddingProperty(ChartSettings settings)
    {
        super(settings);
        this.settings = settings;
    }

    @Override
    public String getName()
    {
        return ChartSettings.PROPERTY_padding;
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
    public Integer getInteger()
    {
        return settings.getPadding() == null ? new Integer(0) : new Integer((int)settings.getPadding().getTop());
    }

    @Override
    public Integer getOwnInteger()
    {
        return getInteger();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return 0;
    }

    @Override
    public void setInteger(Integer width)
    {
        settings.setPadding(new RectangleInsets(width, width, width, width));
    }

    @Override
    public void validateInteger(Integer width)
    {
        if (width != null && width < 0)
        {
            throw annotateException(I18n.getString("Global.Property.Widthexception"));
        }
    }
}
