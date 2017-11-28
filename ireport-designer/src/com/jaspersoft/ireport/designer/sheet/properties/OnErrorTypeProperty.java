/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
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
