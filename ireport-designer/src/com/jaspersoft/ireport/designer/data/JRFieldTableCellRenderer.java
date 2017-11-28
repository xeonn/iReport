/*
 * JRFieldTableCellRenderer.java
 * 
 * Created on 25-ott-2007, 17.28.48
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.data;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author gtoffoli
 */
public class JRFieldTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (value != null && value instanceof JRField)
        {
            label.setText( ((JRField)value).getName());
        }
        
        return label;
    }

}
