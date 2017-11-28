/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class BlankWhenNullProperty extends BooleanProperty
{
    private final JRDesignTextField element;

    @SuppressWarnings("unchecked")
    public BlankWhenNullProperty(JRDesignTextField element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_BLANK_WHEN_NULL;
    }

    @Override
    public String getDisplayName()
    {
        return "Blank When Null";
    }

    @Override
    public String getShortDescription()
    {
        return "Print a blank string instead of null.";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isBlankWhenNull();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isOwnBlankWhenNull();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isBlankWhenNull)
    {
        element.setBlankWhenNull(isBlankWhenNull);
    }

}
