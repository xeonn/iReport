/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *  Class to manage the JRDesignElement.PROPERTY_X property
 * @author gtoffoli
 */
public final class LeftProperty extends IntegerProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public LeftProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_X;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.Left");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Leftdetail");
    }

    @Override
    public Integer getInteger()
    {
        return element.getX();
    }

    @Override
    public Integer getOwnInteger()
    {
        return element.getX();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return 0;//FIXMETD is this a fair default? do we even have a default?
    }

    @Override
    public void setInteger(Integer x)
    {
        element.setX(x);
    }

    @Override
    public void validateInteger(Integer radius)
    {
        if (radius < 0)
        {
            throw annotateException(I18n.getString("Global.Property.Leftexception"));
        }
    }

}
