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
package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
    
    
/**
 *  Class to manage the JRDesignThermometerPlot.PROPERTY_VALUE_LOCATION property
 */
public final class ThermometerValueLocationProperty extends ByteProperty
{
    private final JRDesignThermometerPlot plot;

    
    public ThermometerValueLocationProperty(JRDesignThermometerPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignThermometerPlot.PROPERTY_VALUE_LOCATION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ValueLocation");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ValueLocation");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_BULB), I18n.getString("Global.Property.Bulb")));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_RIGHT), I18n.getString("Global.Property.Right")));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_NONE), I18n.getString("Global.Property.None")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return plot.getValueLocationByte();
    }

    @Override
    public Byte getOwnByte()
    {
        return plot.getValueLocationByte();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte position)
    {
        plot.setValueLocation(position);
    }
    
}
