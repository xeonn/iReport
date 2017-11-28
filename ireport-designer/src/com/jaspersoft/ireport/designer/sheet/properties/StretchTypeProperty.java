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
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class StretchTypeProperty extends ByteProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public StretchTypeProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_STRETCH_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.StretchType");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.StretchTypedetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_NO_STRETCH), I18n.getString("Global.Property.Nostretch")));
        tags.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_RELATIVE_TO_BAND_HEIGHT), I18n.getString("Global.Property.RelativeBandHeight")));
        tags.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT), I18n.getString("Global.Property.RBHdetail")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getStretchType();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getStretchType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRElement.STRETCH_TYPE_NO_STRETCH;
    }

    @Override
    public void setByte(Byte stretchType)
    {
        element.setStretchType(stretchType);
    }

}
