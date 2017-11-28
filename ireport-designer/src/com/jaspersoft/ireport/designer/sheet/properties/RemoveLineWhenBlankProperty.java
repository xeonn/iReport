/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class RemoveLineWhenBlankProperty extends BooleanProperty
{
    private JRDesignElement element = null;

    @SuppressWarnings("unchecked")
    public RemoveLineWhenBlankProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }
    
    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_REMOVE_LINE_WHEN_BLANK;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.RemoveLineWhenBlank");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.RemoveLineWhenBlank.");
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isRemoveLineWhenBlank();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isRemoveLineWhenBlank();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return true;
    }

    @Override
    public void setBoolean(Boolean isRemove)
    {
        element.setRemoveLineWhenBlank(isRemove);
    }

}
