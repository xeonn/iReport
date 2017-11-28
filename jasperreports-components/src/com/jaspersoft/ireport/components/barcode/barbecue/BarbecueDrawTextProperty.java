/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barbecue;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * Class to manage the JRBaseStyle.PROPERTY_ITALIC property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class BarbecueDrawTextProperty extends BooleanProperty{

    private final StandardBarbecueComponent component;

    @SuppressWarnings("unchecked")
    public BarbecueDrawTextProperty(StandardBarbecueComponent component)
    {
        super(component);
        this.component = component;
    }
    @Override
    public String getName()
    {
        return StandardBarbecueComponent.PROPERTY_DRAW_TEXT;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("barbecue.property.drawText.name");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("barbecue.property.drawText.description");
    }

    @Override
    public Boolean getBoolean()
    {
        return getOwnBoolean();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return component.isDrawText();
    }

    @Override
    public boolean supportsDefaultValue() {
        return false;
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return false;
    }

    @Override
    public void setBoolean(Boolean isItalic)
    {
        if (isItalic != null)
        {
            component.setDrawText(isItalic.booleanValue());
        }
        else
        {
            component.setDrawText(false);
        }
    }
    
}
