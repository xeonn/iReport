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
package com.jaspersoft.ireport.jasperserver.validation;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gtoffoli
 */
public class AlignedTableCellRenderer extends DefaultTableCellRenderer {
    
    private int alignment = JLabel.RIGHT;
    static ImageIcon imageIcon;
    static ImageIcon subreportIcon;
    
    /** Creates a new instance of AlignedTableCellRenderer */
    public AlignedTableCellRenderer() {
        this(JLabel.RIGHT);
    }
    
    /** Creates a new instance of AlignedTableCellRenderer */
    public AlignedTableCellRenderer(int alignment) {
        super();
        if (subreportIcon == null) subreportIcon = new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/jasperserver/ui/resources/subreport-16.png"));
        if (imageIcon == null) imageIcon = new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/jasperserver/ui/resources/image-16.png"));
        this.alignment = alignment;
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (c instanceof JLabel)
                {
                    ((JLabel)c).setHorizontalAlignment( getAlignment());
                    if (value instanceof ImageElementValidationItem)
                    {
                         ((JLabel)c).setIcon( imageIcon );
                         ((JLabel)c).setText( "Image" );
                    }
                    else if (value instanceof SubReportElementValidationItem)
                    {
                         ((JLabel)c).setIcon( subreportIcon );
                         ((JLabel)c).setText( "Subreport" );
                    }
                }
                
                return c;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
    
}
