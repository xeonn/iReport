/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignRectangle;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class RadiusProperty extends IntegerProperty
{
    private final JRDesignRectangle rectangle;

    @SuppressWarnings("unchecked")
    public RadiusProperty(JRDesignRectangle rectangle)
    {
        super(rectangle);
        this.rectangle = rectangle;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_RADIUS;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("RadiusPropertyRadius.Property.Radius");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Radiusdetail");
    }

    @Override
    public Integer getInteger()
    {
        return rectangle.getRadius();
    }

    @Override
    public Integer getOwnInteger()
    {
        return rectangle.getOwnRadius();
    }

    @Override
    public Integer getDefaultInteger()
    {
        return null;
    }

    @Override
    public void setInteger(Integer radius)
    {
        rectangle.setRadius(radius);
    }

    @Override
    public void validateInteger(Integer radius)
    {
        if (radius != null && radius < 0)
        {
            throw annotateException(I18n.getString("Global.Property.Radiusexception"));
        }
    }

}
