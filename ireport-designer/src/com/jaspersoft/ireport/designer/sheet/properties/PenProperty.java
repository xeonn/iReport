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
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;

/**
 * 
 *  Class to manage the JRDesignGraphicElement.PROPERTY_PEN property
 */
public final class PenProperty extends ByteProperty
{
    private final JRDesignGraphicElement element;

    @SuppressWarnings("unchecked")
    public PenProperty(JRDesignGraphicElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        //FIXME: get an appropriate String constant
        return null;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("PenProperty.Property.Pen");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("PenProperty.Property.DescriptionPen");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_THIN), I18n.getString("Global.Property.Thin")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_1_POINT), I18n.getString("Global.Property.1_point")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_2_POINT), I18n.getString("Global.Property.2_point")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_4_POINT), I18n.getString("Global.Property.4_point")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_DOTTED), I18n.getString("Global.Property.Dotted")));
        tags.add(new Tag(new Byte(JRDesignGraphicElement.PEN_NONE), I18n.getString("Global.Property.None")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getPen();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnPen();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte pen)
    {
        element.setPen(pen);
    }

}
