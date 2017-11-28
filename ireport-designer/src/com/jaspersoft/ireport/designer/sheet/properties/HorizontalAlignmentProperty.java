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
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class HorizontalAlignmentProperty extends ByteProperty
{
    private final JRAlignment element;

    @SuppressWarnings("unchecked")
    public HorizontalAlignmentProperty(JRAlignment element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT;
    }

    @Override
    public String getDisplayName()
    {
        return "Horizontal Alignment";
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
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_LEFT), "Left"));
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_CENTER), "Center"));
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_RIGHT), "Right"));
        tags.add(new Tag(new Byte(JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED), "Justified"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getHorizontalAlignment();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnHorizontalAlignment();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte alignment)
    {
        element.setHorizontalAlignment(alignment);
    }

}
