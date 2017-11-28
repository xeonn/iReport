/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * Class to manage the JRBaseStyle.PROPERTY_BOLD property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class BoldProperty extends BooleanProperty
{
    private final JRFont font;

    @SuppressWarnings("unchecked")
    public BoldProperty(JRFont font)
    {
        super(font);
        this.font = font;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_BOLD;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.Bold");
    }

    @Override
    public String getShortDescription()
    {
        return "";
    }

    @Override
    public Boolean getBoolean()
    {
        return font.isBold();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return font.isOwnBold();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isBold)
    {
    	font.setBold(isBold);
    }
    
}
