/*
 * FloatProperty.java
 * 
 * Created on 20-Mar-2008, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.outline.nodes.properties.*;

    
/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public abstract class FloatProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public FloatProperty(Object object)
    {
        super(Float.class, object);
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public Object getPropertyValue()
    {
        return getFloat();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnFloat();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultFloat();
    }

    @Override
    public void validate(Object value)
    {
        validateFloat((Float)value);
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setFloat((Float)value);
    }

    public abstract Float getFloat();

    public abstract Float getOwnFloat();

    public abstract Float getDefaultFloat();

    public abstract void validateFloat(Float value);

    public abstract void setFloat(Float value);

}
