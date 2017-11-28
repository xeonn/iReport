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

import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @version $Id: StringParameter.java 0 2010-08-10 12:36:33 CET gtoffoli $
 * @author Giulio Toffoli (giulio@jaspersoft.com)
 *
 */
public class IntegerParameter extends AbstractParameter {

    public IntegerParameter(String name)
    {
        super(name, "java.lang.Integer");
    }

    JTextField textField = null;


    public JComponent getUI() {
        if (textField != null)
        {
            textField = new JTextField();
            textField.setText("0");
        }
        return textField;
    }

    public String getValue() {
        return textField.getText();
    }

    public void setValue(String s) {
        ((JTextField)getUI()).setText(s == null ? "" : s);
    }

}
