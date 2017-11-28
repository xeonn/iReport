/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class BooleanProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public BooleanProperty(Object object)
    {
        super(Boolean.class, object);
    }

    @Override
    public Object getValue()
    {
        Object value = getPropertyValue();
        return value == null ? null : value;
    }

    @Override
    public Object getPropertyValue()
    {
        return getBoolean();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnBoolean();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultBoolean();
    }

    @Override
    public void validate(Object value)
    {
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setBoolean((Boolean)value);
    }

    public abstract Boolean getBoolean();

    public abstract Boolean getOwnBoolean();

    public abstract Boolean getDefaultBoolean();

    public abstract void setBoolean(Boolean value);
}
