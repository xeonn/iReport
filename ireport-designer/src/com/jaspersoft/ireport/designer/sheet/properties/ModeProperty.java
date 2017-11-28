/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class ModeProperty extends BooleanProperty
{
    private JRDesignElement element = null;

    @SuppressWarnings("unchecked")
    public ModeProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_MODE;
    }

    @Override
    public String getDisplayName()
    {
        return "Opaque";
    }

    @Override
    public String getShortDescription()
    {
        return "Set if an element is opaque or transparent.";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.getMode() == JRElement.MODE_OPAQUE;
    }

    @Override
    public Boolean getOwnBoolean()
    {
        if (element.getOwnMode() == null)
            return null;
        return element.getOwnMode() == JRElement.MODE_OPAQUE;
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isPrint)
    {
        element.setMode(isPrint == null ? null : (isPrint ? JRElement.MODE_OPAQUE : JRElement.MODE_TRANSPARENT));
    }

}
