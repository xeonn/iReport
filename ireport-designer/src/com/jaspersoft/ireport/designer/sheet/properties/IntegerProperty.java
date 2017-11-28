/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.outline.nodes.properties.*;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class IntegerProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public IntegerProperty(Object object)
    {
        super(Integer.class, object);
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
        validateInteger((Integer)value);
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setInteger((Integer)value);
    }

    public abstract Integer getInteger();

    public abstract Integer getOwnInteger();

    public abstract Integer getDefaultInteger();

    public abstract void validateInteger(Integer value);

    public abstract void setInteger(Integer value);

}
