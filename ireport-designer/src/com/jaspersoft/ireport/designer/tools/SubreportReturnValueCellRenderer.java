/*
 * SubreportReturnValueCellRenderer.java
 * 
 * Created on 8-nov-2007, 16.17.59
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.tools;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.design.JRDesignSubreportReturnValue;

/**
 *
 * @author gtoffoli
 */
public class SubreportReturnValueCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof JRDesignSubreportReturnValue)
        {
            label.setText( ((JRDesignSubreportReturnValue)value).getSubreportVariable() );
        }
        
        return label;
    }

}
