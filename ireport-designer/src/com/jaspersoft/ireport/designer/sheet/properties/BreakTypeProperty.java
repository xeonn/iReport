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

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseBreak;
import net.sf.jasperreports.engine.design.JRDesignBreak;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class BreakTypeProperty extends ByteProperty
{
    private final JRDesignBreak breakElement;

    @SuppressWarnings("unchecked")
    public BreakTypeProperty(JRDesignBreak breakElement)
    {
        super(breakElement);
        this.breakElement = breakElement;
    }

    @Override
    public String getName()
    {
        return JRBaseBreak.PROPERTY_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.BreakType");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.BreakType");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRBaseBreak.TYPE_PAGE), I18n.getString("Global.Property.BreakTypePage")));
        tags.add(new Tag(new Byte(JRBaseBreak.TYPE_COLUMN), I18n.getString("Global.Property.BreakTypeColumn")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return breakElement.getType();
    }

    @Override
    public Byte getOwnByte()
    {
        return breakElement.getType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRBaseBreak.TYPE_PAGE;
    }

    @Override
    public void setByte(Byte type)
    {
        breakElement.setType(type);

        if (IReportManager.getPreferences().getBoolean("designer_debug_mode", false))
        {
            System.out.println(new java.util.Date() + ": setting break type to: " + type + ". If the value is unattended or null, please report this notification to http://jasperforge.org/plugins/mantis/view.php?id=4139");
            Thread.dumpStack();
        }

        
    }

}
