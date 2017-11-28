/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.base.JRBaseImage;
import net.sf.jasperreports.engine.design.JRDesignImage;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class ImageUsingCacheProperty extends BooleanProperty
{
    private final JRDesignImage image;

    @SuppressWarnings("unchecked")
    public ImageUsingCacheProperty(JRDesignImage image)
    {
        super(image);
        this.image = image;
    }

    @Override
    public String getName()
    {
        return JRBaseImage.PROPERTY_USING_CACHE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.UsingCache");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.UsingCachedetail");
    }

    @Override
    public Boolean getBoolean()
    {
        return image.isUsingCache();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return image.isOwnUsingCache();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isUsingCache)
    {
        image.setUsingCache(isUsingCache);
    }

}
