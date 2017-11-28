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
 *  Class to manage the JRDesignElement.PROPERTY_WIDTH property
 */
public final class WidthProperty extends IntegerProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public WidthProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_WIDTH;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.Width");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Widthdetail");
    }

    @Override
    public Integer getInteger()
    {
        return element.getWidth();
    }

    @Override
    public Integer getOwnInteger()
    {
        return element.getWidth();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return 10;//FIXMETD is this a fair default? do we even have a default?
    }

    @Override
    public void setInteger(Integer width)
    {
        element.setWidth(width);
    }

    @Override
    public void validateInteger(Integer width)
    {
        if (width < 0)
        {
            throw annotateException(I18n.getString("Global.Property.Widthexception"));
        }
    }
}
