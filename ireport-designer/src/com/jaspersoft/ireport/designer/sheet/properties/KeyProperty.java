/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class KeyProperty extends StringProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public KeyProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_KEY;
    }

    @Override
    public String getDisplayName()
    {
        return "Key";
    }

    @Override
    public String getShortDescription()
    {
        return "Optional identifier for this element.";
    }

    @Override
    public String getString()
    {
        return element.getKey();
    }

    @Override
    public String getOwnString()
    {
        return element.getKey();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String key)
    {
        element.setKey(key);
    }

}
