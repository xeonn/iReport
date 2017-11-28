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
 *  Class to manage the JRDesignElement.PROPERTY_Y property
 */
public final class TopProperty extends IntegerProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public TopProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_Y;
    }

    @Override
    public String getDisplayName()
    {
        return "Top";
    }

    @Override
    public String getShortDescription()
    {
        return "Top position of this element in the band/frame/cell.";
    }

    @Override
    public Integer getInteger()
    {
        return element.getY();
    }

    @Override
    public Integer getOwnInteger()
    {
        return element.getY();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return 0;//FIXMETD is this a fair default? do we even have a default?
    }

    @Override
    public void setInteger(Integer y)
    {
        element.setY(y);
    }

    @Override
    public void validateInteger(Integer y)
    {
        if (y < 0)
        {
            throw annotateException("The property cannot be a negative number.");
        }
    }

}
