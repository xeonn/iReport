/*
 * Copyright (C) 2005-2007 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
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
 * SortFieldCellRenderer.java
 *
 * Created on November 13, 2006, 6:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.data;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.sf.jasperreports.engine.design.JRDesignSortField;

/**
 *
 * @author gtoffoli
 */
public class SortFieldCellRenderer extends DefaultListCellRenderer {
    
    static ImageIcon ascIcon;
    static ImageIcon descIcon;
    
    /** Creates a new instance of SortFieldCellRenderer */
    public SortFieldCellRenderer() {
        if (ascIcon == null) ascIcon = new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/data/arrow_down.png"));
        if (descIcon == null) descIcon = new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/data/arrow_up.png"));
    }

    @Override
    public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        java.awt.Component retValue;
        
        retValue = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof JRDesignSortField && retValue instanceof JLabel)
        {
            JRDesignSortField sf = (JRDesignSortField)value;
            JLabel label = (JLabel)retValue;
            label.setText( sf.getName()  ); 
            
            label.setIcon( sf.getOrder() == JRDesignSortField.SORT_ORDER_DESCENDING ? descIcon : ascIcon );
        }
        
        return retValue;
    }
    
    
    
}
