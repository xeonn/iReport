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
        return I18n.getString("Global.Property.ScaleImage");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ScaleImagedetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_CLIP), I18n.getString("Global.Property.Clip")));
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_FILL_FRAME), I18n.getString("Global.Property.FillFrame")));
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE), I18n.getString("Global.Property.RetainShape")));
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_REAL_SIZE), I18n.getString("Global.Property.RealSize")));
        tags.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_REAL_HEIGHT), I18n.getString("Global.Property.RealHeight")));
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
