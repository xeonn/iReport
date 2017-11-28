/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractFontProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.chartthemes.simple.AxisSettings;
import net.sf.jasperreports.engine.JRFont;
    
    
/**
 * @autor Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class AxisLabelFontProperty extends AbstractFontProperty
{
    private final AxisSettings settings;
        
    public AxisLabelFontProperty(AxisSettings settings)
    {
        super(settings, null);
        this.settings = settings;
    }
    
    @Override
    public String getName()
    {
        return AxisSettings.PROPERTY_labelFont;
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
    public JRFont getFont()
    {
        return settings.getLabelFont();
    }

    @Override
    public void setFont(JRFont font)
    {
        settings.setLabelFont(font);
    }

}
