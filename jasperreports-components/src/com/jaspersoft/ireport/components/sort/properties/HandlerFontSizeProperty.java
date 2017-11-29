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
package com.jaspersoft.ireport.components.sort.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.properties.IntegerProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.components.sort.SortComponent;


    
/**
 * Class to manage the JRDesignElement.PROPERTY_FORECOLOR property
 * 
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class HandlerFontSizeProperty extends IntegerProperty
{
    private final SortComponent component;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public HandlerFontSizeProperty(SortComponent component)
    {
        super(component);
        this.component = component;
        setValue("canEditAsText", true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor()
    {
        if (editor == null)
        {
            editor = new ComboBoxPropertyEditor(true, getTagList());

        }
        return editor;
    }

    @Override
    public String getName()
    {
        return SortComponent.PROPERTY_HANDLER_FONT_SIZE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.HandlerFontSize");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.HandlerFontSize.desc");
    }

    @Override
    public Integer getInteger() {
        if (component.getHandlerFontSize() == null) return new Integer(0);

        try {
            return new Integer(component.getHandlerFontSize());
        } catch (Exception ex)
        {
            System.out.println("Invalid font size: " + component.getHandlerFontSize());
                 ex.printStackTrace();
            return getDefaultInteger();
        }
    }

    @Override
    public Integer getOwnInteger() {
        return getInteger();
    }

    @Override
    public Integer getDefaultInteger() {
        return 0;
    }

    @Override
    public void validateInteger(Integer value) {
        if (value != null && value.intValue() < 0)
        {
            throw new IllegalArgumentException("The font size must be more than 0, or 0 to use the default: " + value);
        }
    }

    @Override
    public void setInteger(Integer value) {

        System.out.println("Setting value to: " + value);
        if (value != null && value.intValue() == 0)
        {
            value = null;
        }
        component.setHandlerFontSize( (value == null) ? null : value.toString());

    }

    private List getTagList() {
        List<Tag> tags = new ArrayList<Tag>();

            tags.add(new Tag(new Integer(0), "Default"));
            for (int i = 6; i < 100;) {
                tags.add(new Tag(new Integer(i), "" + i));

                if (i < 16) {
                    i++;
                } else if (i < 32) {
                    i += 2;
                } else if (i < 48) {
                    i += 4;
                } else if (i < 72) {
                    i += 6;
                } else {
                    i += 8;
                }
            }
            return tags;
    }


    @Override
    public boolean isDefaultValue() {
        return component.getHandlerFontSize() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(0);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }
    

    @Override
    public void validate(Object value)
    {
        if (value != null) validateInteger(null);

        System.out.println("validating " + value + " of type " + value.getClass());

        if (value instanceof Integer) validateInteger((Integer)value);
        else if (value instanceof String) validateInteger(new Integer(""+value));
    }

    @Override
    public void setPropertyValue(Object value)
    {
        if (value != null) setInteger(null);
        if (value instanceof Integer) setInteger((Integer)value);
        else if (value instanceof String) setInteger(new Integer(""+value));
    }
}
