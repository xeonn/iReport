/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved. 
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 *
 *
 *
 *
 * MeterIntervalTableCellRenderer.java
 * 
 * Created on September 29, 2006, 11:05 AM
 *
 */

package com.jaspersoft.ireport.designer.tools;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.charts.util.JRMeterInterval;
import net.sf.jasperreports.engine.JRExpression;

/**
 *
 * @author gtoffoli
 */
public class MeterIntervalTableCellRenderer extends DefaultTableCellRenderer {
    
    /** Creates a new instance of TableCellRenderer */
    public MeterIntervalTableCellRenderer() {
        super();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, 
                                            Object value,
                                            boolean isSelected,
                                            boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
        
        setIcon(null);
        
        if (value instanceof JRMeterInterval && value != null)
        {
            setText( ((JRMeterInterval)value).getLabel() );

            setIcon( new ColorIcon( ( (JRMeterInterval)value).getBackgroundColor() ));
        }
        else if (value instanceof JRExpression)
        {
            JRExpression exp = (JRExpression)value;
            if (exp == null) setText("");
            else if (exp.getText() == null) setText("");
            else setText(exp.getText());
        }
        
        return this;
    }
}
