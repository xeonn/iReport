/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.chartthemes.simple.PlotSettings;

/**
 * Class to manage the JRBaseStyle.PROPERTY_UNDERLINE property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class RangeGridlineVisibleProperty extends BooleanProperty {

    private final PlotSettings settings;

    @SuppressWarnings("unchecked")
    public RangeGridlineVisibleProperty(PlotSettings settings)
    {
        super(settings);
        this.settings = settings;
    }
    @Override
    public String getName()
    {
        return PlotSettings.PROPERTY_rangeGridlineVisible;
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
    public Boolean getBoolean()
    {
        return settings.getRangeGridlineVisible();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return settings.getRangeGridlineVisible();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean b)
    {
    	settings.setRangeGridlineVisible(b);
    }
    
}
