/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import java.awt.Color;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class ColorProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public ColorProperty(Object object)
    {
        super(Color.class, object);
    }

    @Override
    public Object getPropertyValue()
    {
        return getColor();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnColor();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultColor();
    }

    @Override
    public void validate(Object value)
    {
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setColor((Color)value);
    }

    public abstract Color getColor();

    public abstract Color getOwnColor();

    public abstract Color getDefaultColor();

    public abstract void setColor(Color color);

}
