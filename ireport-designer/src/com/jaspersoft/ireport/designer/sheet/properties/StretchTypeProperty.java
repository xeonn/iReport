/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import java.util.List;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class StretchTypeProperty extends ByteProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public StretchTypeProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_STRETCH_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return "Stretch Type";
    }

    @Override
    public String getShortDescription()
    {
        return "How to stretch the element.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_NO_STRETCH), "No stretch"));
        tags.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_RELATIVE_TO_BAND_HEIGHT), "Relative to Band Height"));
        tags.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT), "Relative to Tallest Object"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getStretchType();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getStretchType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRElement.STRETCH_TYPE_NO_STRETCH;
    }

    @Override
    public void setByte(Byte stretchType)
    {
        element.setStretchType(stretchType);
    }

}
