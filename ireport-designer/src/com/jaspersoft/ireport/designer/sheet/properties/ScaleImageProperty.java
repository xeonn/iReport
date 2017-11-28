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
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignImage;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class ScaleImageProperty extends ByteProperty
{
    private final JRDesignImage image;

    @SuppressWarnings("unchecked")
    public ScaleImageProperty(JRDesignImage image)
    {
        super(image);
        this.image = image;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_SCALE_IMAGE;
    }

    @Override
    public String getDisplayName()
    {
        return "Scale Image";
    }

    @Override
    public String getShortDescription()
    {
        return "How to scale the image.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_CLIP), "Clip"));
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_FILL_FRAME), "Fill Frame"));
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE), "Retain Shape"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return image.getScaleImage();
    }

    @Override
    public Byte getOwnByte()
    {
        return image.getOwnScaleImage();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte scaleImage)
    {
        image.setScaleImage(scaleImage);
    }

}
