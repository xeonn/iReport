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
package com.jaspersoft.ireport.components;

import com.jaspersoft.ireport.locale.I18n;
import java.util.ResourceBundle;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // By default, do nothing.
        // Put your startup code here.

        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/list/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/barcode/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/barcode/barbecue/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/barcode/barcode4j/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/table/Bundle"));
        I18n.addBundleLocation(ResourceBundle.getBundle("/com/jaspersoft/ireport/components/genericelement/Bundle"));
    }
}
