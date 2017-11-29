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
package com.jaspersoft.ireport.components.map;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.components.map.StandardMapComponent;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;


/**
 *  Class to manage the StandardChartSettings.PROPERTY_TITLE_EXPRESSION property
 */
public final class LongitudeExpressionProperty extends ExpressionProperty 
{
    private final StandardMapComponent component;

    public LongitudeExpressionProperty(StandardMapComponent component, JRDesignDataset dataset)
    {
        super(component, dataset);
        this.component = component;
    }

    @Override
    public String getName()
    {
        return StandardMapComponent.PROPERTY_LONGITUDE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.LongitudeExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.LongitudeExpression.desc");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Number.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)component.getLongitudeExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        component.setLongitudeExpression(expression);
    }

}
