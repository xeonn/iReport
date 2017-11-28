/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.base.JRBaseImage;
import net.sf.jasperreports.engine.design.JRDesignImage;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class LazyProperty extends BooleanProperty
{
    private final JRDesignImage image;

    @SuppressWarnings("unchecked")
    public LazyProperty(JRDesignImage image)
    {
        super(image);
        this.image = image;
    }
    @Override
    public String getName()
    {
        return JRBaseImage.PROPERTY_LAZY;
    }

    @Override
    public String getDisplayName()
    {
        return "Is Lazy";
    }

    @Override
    public String getShortDescription()
    {
        return "If set to true, this property avoid the load of the image at fill time.";
    }

    @Override
    public Boolean getBoolean()
    {
        return image.isLazy();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return image.isLazy();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return false;
    }

    @Override
    public void setBoolean(Boolean isUsingCache)
    {
        image.setUsingCache(isUsingCache);
    }

}
