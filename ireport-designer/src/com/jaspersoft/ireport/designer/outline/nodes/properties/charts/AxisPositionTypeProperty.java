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
package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.*;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.charts.design.JRDesignChartAxis;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class AxisPositionTypeProperty extends ByteProperty
{
    private final JRDesignChartAxis element;

    @SuppressWarnings("unchecked")
    public AxisPositionTypeProperty(JRDesignChartAxis element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignChartAxis.PROPERTY_POSITION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.AxisPositionType");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.AxisPositionType.detail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignChartAxis.POSITION_LEFT_OR_TOP), I18n.getString("Global.Property.AxisPositionType.LeftTop")));
        tags.add(new Tag(new Byte(JRDesignChartAxis.POSITION_RIGHT_OR_BOTTOM), I18n.getString("Global.Property.AxisPositionType.RightBottom")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getPosition();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getPosition();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignChartAxis.POSITION_LEFT_OR_TOP;
    }

    @Override
    public void setByte(Byte positionType)
    {
        element.setPosition(positionType);
    }

}
