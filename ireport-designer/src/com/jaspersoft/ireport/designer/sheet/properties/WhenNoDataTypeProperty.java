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
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *  Class to manage the WhenNoDataType property
 */
public final class WhenNoDataTypeProperty extends ByteProperty
{
    private final JasperDesign jasperDesign;

    @SuppressWarnings("unchecked")
    public WhenNoDataTypeProperty(JasperDesign jasperDesign)
    {
        super(jasperDesign);
        this.jasperDesign = jasperDesign;
    }

    @Override
    public String getName()
    {
        return JasperDesign.PROPERTY_WHEN_NO_DATA_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("WhenNoDataTypeProperty.Property");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("WhenNoDataTypeProperty.Property.Message");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL), I18n.getString("WhenNoDataTypeProperty.Property.All")));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_BLANK_PAGE), I18n.getString("WhenNoDataTypeProperty.Property.Blank")));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_NO_DATA_SECTION), I18n.getString("WhenNoDataTypeProperty.Property.Section")));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES), I18n.getString("WhenNoDataTypeProperty.Property.Pages")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return jasperDesign.getWhenNoDataType();
    }

    @Override
    public Byte getOwnByte()
    {
        return jasperDesign.getWhenNoDataType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES;
    }

    @Override
    public void setByte(Byte type)
    {
        jasperDesign.setWhenNoDataType(type);
    }

}
