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
public abstract class StringProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public StringProperty(Object object)
    {
        super(String.class, object);
        //setValue("oneline", Boolean.TRUE);
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public Object getPropertyValue()
    {
        return getString();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnString();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultString();
    }

    @Override
    public void validate(Object value)
    {
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setString((String)value);
    }

    public abstract String getString();

    public abstract String getOwnString();

    public abstract String getDefaultString();

    public abstract void setString(String value);
}
