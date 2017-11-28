/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.connection.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author gtoffoli
 */
public class JDBCDriverListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        list.setToolTipText(null);
        if (value instanceof JDBCDriverDefinition &&
            !((JDBCDriverDefinition)value).isAvailable())
        {
            label.setForeground(Color.RED.darker());
            list.setToolTipText("<html>Driver class <i>" + ((JDBCDriverDefinition)value).getDriverName() + "</i><br>non found in the classpath!");
        }

        return label;
    }




}
