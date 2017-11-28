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
 *  Class to manage the JRDesignElement.PROPERTY_HEIGHT property
 */
public final class HeightProperty extends IntegerProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public HeightProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_HEIGHT;
    }

    @Override
    public String getDisplayName()
    {
        return "Height";
    }

    @Override
    public String getShortDescription()
    {
        return "Element height.";
    }

    @Override
    public Integer getInteger()
    {
        return element.getHeight();
    }

    @Override
    public Integer getOwnInteger()
    {
        return element.getHeight();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return 10;//FIXMETD is this a fair default? do we even have a default height?
    }

    @Override
    public void setInteger(Integer height)
    {
        element.setHeight(height);
    }

    @Override
    public void validateInteger(Integer height)
    {
        if (height < 0)
        {
            throw annotateException("The height cannot be a negative number.");
        }
    }

}
