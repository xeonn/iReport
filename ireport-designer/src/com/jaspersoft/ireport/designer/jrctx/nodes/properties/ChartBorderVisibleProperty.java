/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.chartthemes.simple.ChartSettings;

/**
 * Class to manage the JRBaseStyle.PROPERTY_UNDERLINE property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class ChartBorderVisibleProperty extends BooleanProperty {

    private final ChartSettings settings;

    @SuppressWarnings("unchecked")
    public ChartBorderVisibleProperty(ChartSettings settings)
    {
        super(settings);
        this.settings = settings;
    }
    @Override
    public String getName()
    {
        return ChartSettings.PROPERTY_borderVisible;
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
        return settings.getBorderVisible();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return settings.getBorderVisible();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean b)
    {
    	settings.setBorderVisible(b);
    }
    
}
