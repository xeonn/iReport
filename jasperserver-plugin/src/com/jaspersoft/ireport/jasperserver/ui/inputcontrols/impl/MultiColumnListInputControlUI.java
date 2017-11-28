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
 */
package com.jaspersoft.ireport.jasperserver.ui.inputcontrols.impl;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.InputControlQueryDataRow;


/**
 *
 * @author gtoffoli
 */
public class MultiColumnListInputControlUI extends BasicInputControlUI {
    
    /**
     * Creates a new instance of ListInputControlUI
     */
    public MultiColumnListInputControlUI() {
        super();
        setComboEditable(false);
        getJComboBoxValue().setMinimumSize(new java.awt.Dimension(400,26));
    }
    
    public void setHistory(java.util.List values){
        
        getJComboBoxValue().removeAllItems();
        if (values == null) return;
        
        // Try to understand how much columns...
        int maxColumns = 1;
        for (int i=0; i<values.size(); ++i)
        {
            InputControlQueryDataRow qd =  (InputControlQueryDataRow)values.get(i);
            maxColumns = (qd.getColumnValues().size()>maxColumns) ? qd.getColumnValues().size() : maxColumns;
        }
        //System.out.println("ItemRenderer set to" + maxColumns );
        getJComboBoxValue().setRenderer(new ItemRenderer(maxColumns));
        
        for (int i=0; i<values.size(); ++i)
        {
            getJComboBoxValue().addItem( values.get(i));
        }
        
        if (getJComboBoxValue().getItemCount() > 0)
        {
            getJComboBoxValue().setSelectedIndex(0);
        }
        
        getJComboBoxValue().updateUI();
    }
    
    public void setValue(Object v)
    {
        for (int i=0; i<getJComboBoxValue().getItemCount(); ++i)
        {
            Object val = getJComboBoxValue().getItemAt(i);
            
            if (val instanceof InputControlQueryDataRow)
            {
                val = ((InputControlQueryDataRow)val).getValue();
                if ( ((val == null) ? val == v : val.equals(v)) )
                {
                    getJComboBoxValue().setSelectedIndex(i);
                    return;
                }
            }
        }
        
        getJComboBoxValue().setSelectedItem(v);
    }
     
     public Object getValue()
    {
        Object val = getJComboBoxValue().getSelectedItem();
        if (val == null) return null;
        if (val instanceof InputControlQueryDataRow)
        {
            return ((InputControlQueryDataRow)val).getValue();
        }
        
        return val;
    }
     
     
     
}
