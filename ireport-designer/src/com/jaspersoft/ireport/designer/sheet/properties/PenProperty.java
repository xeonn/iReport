/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;

/**
 * 
 *  Class to manage the JRDesignGraphicElement.PROPERTY_PEN property
 */
public final class PenProperty extends ByteProperty
{
    private final JRDesignGraphicElement element;

    @SuppressWarnings("unchecked")
    public PenProperty(JRDesignGraphicElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        //FIXME: get an appropriate String constant
        return null;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("PenProperty.Property.Pen");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("PenProperty.Property.DescriptionPen");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_THIN), I18n.getString("Global.Property.Thin")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_1_POINT), I18n.getString("Global.Property.1_point")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_2_POINT), I18n.getString("Global.Property.2_point")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_4_POINT), I18n.getString("Global.Property.4_point")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_DOTTED), I18n.getString("Global.Property.Dotted")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_NONE), I18n.getString("Global.Property.None")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getPen();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnPen();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte pen)
    {
        element.setPen(pen);
    }

}
