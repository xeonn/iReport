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
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class RotationProperty extends ByteProperty
{
    private final JRDesignTextElement element;

    @SuppressWarnings("unchecked")
    public RotationProperty(JRDesignTextElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_ROTATION;
    }

    @Override
    public String getDisplayName()
    {
        return "Rotation";
    }

    @Override
    public String getShortDescription()
    {
        return "How to rotate the text.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_NONE), "None"));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_LEFT), "Left"));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_RIGHT), "Right"));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_UPSIDE_DOWN), "Upside Down"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getRotation();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnRotation();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte rotation)
    {
        element.setRotation(rotation);
    }

}
