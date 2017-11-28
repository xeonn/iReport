/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;


/**
 *
 * @author gtoffoli
 */
public class BarcodeListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value != null && value instanceof BarcodeDescriptor)
        {
            BarcodeDescriptor bd = (BarcodeDescriptor)value;
            label.setIcon(bd.getIcon());
            //label.setHorizontalTextPosition(JLabel.LEFT);
            String desc = "";
//            if (bd.getDescription() != null &&
//                bd.getDescription().length() > 0)
//            {
//                desc = "<br><font color=\"lightGray\">" + bd.getDescription() + "</font>";
//            }
            label.setText("<html>" + bd.getName() + desc);
            label.setAlignmentX( JLabel.TOP_ALIGNMENT);
        }

        return label;
    }

}
