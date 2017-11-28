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
        return "Pen";
    }

    @Override
    public String getShortDescription()
    {
        return "Pen.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_THIN), "Thin"));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_1_POINT), "1 point"));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_2_POINT), "2 point"));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_4_POINT), "4 point"));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_DOTTED), "Dotted"));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_NONE), "None"));
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
