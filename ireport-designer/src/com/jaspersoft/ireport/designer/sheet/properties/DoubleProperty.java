/*
 * DoubleProperty.java
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
public abstract class DoubleProperty extends AbstractProperty
{
    @SuppressWarnings("unchecked")
    public DoubleProperty(Object object)
    {
        super(Double.class, object);
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public Object getPropertyValue()
    {
        return getDouble();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnDouble();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultDouble();
    }

    @Override
    public void validate(Object value)
    {
        validateDouble((Double)value);
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setDouble((Double)value);
    }

    public abstract Double getDouble();

    public abstract Double getOwnDouble();

    public abstract Double getDefaultDouble();

    public abstract void validateDouble(Double value);

    public abstract void setDouble(Double value);

}
