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
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class StretchWithOverflowProperty extends BooleanProperty
{
    private final JRDesignTextField element;

    @SuppressWarnings("unchecked")
    public StretchWithOverflowProperty(JRDesignTextField element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseTextField.PROPERTY_STRETCH_WITH_OVERFLOW;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.StretchWithOverflow");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.SWOdetail");
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isStretchWithOverflow();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isStretchWithOverflow();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isStretch)
    {
        element.setStretchWithOverflow(isStretch);
    }

}
