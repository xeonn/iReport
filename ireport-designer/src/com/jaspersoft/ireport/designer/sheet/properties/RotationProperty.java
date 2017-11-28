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
import com.jaspersoft.ireport.locale.I18n;
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
        return I18n.getString("Global.Property.Rotation");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Rotationdetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_NONE), I18n.getString("Global.Property.None")));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_RIGHT), I18n.getString("Global.Property.Right")));
        tags.add(new Tag(new Byte(JRDesignTextElement.ROTATION_UPSIDE_DOWN), I18n.getString("Global.Property.UpsideDown")));
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
