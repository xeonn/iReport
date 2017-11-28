/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.options.jasperreports;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gtoffoli
 */
public class JRPropertyTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value == null) return this;

        if (column == 0)
        {
            if (IReportManager.getInstance().getDefaultJasperReportsProperties().containsKey(value))
            setText("<html><i>" + value );
        }
        else if (column == 1)
        {
            if (value instanceof String)
            {
                setText( Misc.removeSlashesString((String)value));
            }
        }

        return this;

    }



}
