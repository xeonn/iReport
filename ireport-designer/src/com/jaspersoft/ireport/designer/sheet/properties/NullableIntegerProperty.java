/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class NullableIntegerProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public NullableIntegerProperty(Object object)
    {
        super(Float.class, object);
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public Object getPropertyValue()
    {
        return getInteger();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnInteger();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultInteger();
    }

    @Override
    public void validate(Object value)
    {
        validateInteger(convert(value));
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setInteger(convert(value));
    }

    private Integer convert(Object value)
    {
        Number number = (Number)value;
        return number == null ? null : new Integer(number.intValue());
    }

    public abstract Integer getInteger();

    public abstract Integer getOwnInteger();

    public abstract Integer getDefaultInteger();

    public abstract void validateInteger(Integer value);

    public abstract void setInteger(Integer value);

}
