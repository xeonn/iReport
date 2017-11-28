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

package com.jaspersoft.ireport.designer.editor.functions;

import java.awt.Component;

/**
 *
 * @version $Id: Parameter.java 0 2010-08-10 12:20:50 CET gtoffoli $
 * @author Giulio Toffoli (giulio@jaspersoft.com)
 *
 */
public interface Parameter {

    /**
     * Localized name of this parameter
     */
    public String getLabel();

    /**
     * Usually a class name. This may be used to display the UI to get
     * the value of this parameter
     * @return
     */
    public String getType();

    /**
     * Optionally, a parameter can return a custom UI to be defined.
     * @return
     */
    public Component getUI();

    /**
     * The chunk of code used as parameter for the function
     */
    public String getValue();
}
