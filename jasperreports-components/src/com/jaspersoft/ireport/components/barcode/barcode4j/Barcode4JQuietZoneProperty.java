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
package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.properties.DoubleProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;


public final class Barcode4JQuietZoneProperty  extends DoubleProperty
{
        private BarcodeComponent component;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public Barcode4JQuietZoneProperty(BarcodeComponent component)
        {
           super(component);
           this.component = component;
           setName(BarcodeComponent.PROPERTY_QUIET_ZONE);
           setDisplayName(I18n.getString("barcode4j.property.quietZone.name"));
           setShortDescription(I18n.getString("barcode4j.property.quietZone.description"));
        }



    @Override
    public Double getDouble() {
        return component.getQuietZone();
    }

    @Override
    public Double getOwnDouble() {
        return getDouble();
    }

    @Override
    public Double getDefaultDouble() {
        return null;
    }

    @Override
    public void validateDouble(Double value) {
    }

    @Override
    public void setDouble(Double value) {
        component.setQuietZone(value);
    }

    @Override
    public boolean isDefaultValue() {
        return getDouble() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setDouble(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }


}

