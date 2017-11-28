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
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseImage;
import net.sf.jasperreports.engine.design.JRDesignImage;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class OnErrorTypeProperty extends ByteProperty
{
    private final JRDesignImage image;

    @SuppressWarnings("unchecked")
    public OnErrorTypeProperty(JRDesignImage image)
    {
        super(image);
        this.image = image;
    }

    @Override
    public String getName()
    {
        return JRBaseImage.PROPERTY_ON_ERROR_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.OnErrorType");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.OnErrorTypedetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignImage.ON_ERROR_TYPE_ERROR), I18n.getString("Global.Property.Error")));
        tags.add(new Tag(new Byte(JRDesignImage.ON_ERROR_TYPE_BLANK), I18n.getString("Global.Property.Blank")));
        tags.add(new Tag(new Byte(JRDesignImage.ON_ERROR_TYPE_ICON), I18n.getString("Global.Property.Icon")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return image.getOnErrorType();
    }

    @Override
    public Byte getOwnByte()
    {
        return image.getOnErrorType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignImage.ON_ERROR_TYPE_ERROR;
    }

    @Override
    public void setByte(Byte onErrorType)
    {
        image.setOnErrorType(onErrorType);
    }

}
