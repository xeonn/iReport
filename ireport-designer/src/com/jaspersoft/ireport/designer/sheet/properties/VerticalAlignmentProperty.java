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
import java.util.List;
import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.base.JRBaseStyle;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class VerticalAlignmentProperty extends ByteProperty
{
    private final JRAlignment element;

    @SuppressWarnings("unchecked")
    public VerticalAlignmentProperty(JRAlignment element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT;
    }

    @Override
    public String getDisplayName()
    {
        return "Vertical Alignment";
    }

    @Override
    public String getShortDescription()
    {
        return "How to align the element.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRAlignment.VERTICAL_ALIGN_TOP), "Top"));
        tags.add(new Tag(new Byte(JRAlignment.VERTICAL_ALIGN_MIDDLE), "Middle"));
        tags.add(new Tag(new Byte(JRAlignment.VERTICAL_ALIGN_BOTTOM), "Bottom"));
        tags.add(new Tag(new Byte(JRAlignment.VERTICAL_ALIGN_JUSTIFIED), "Justified"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getVerticalAlignment();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnVerticalAlignment();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte alignment)
    {
        element.setVerticalAlignment(alignment);
    }

}
