/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author gtoffoli
 */
public class TemplateItemActionCellRenderer extends DefaultListCellRenderer {

    public Color normalColor = new Color(109,109,109);
    public Color selectedColor = new Color(22,152,212);
    public Color hoverColor = new Color(210,82,31);

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, false, false);
        label.setFont(new java.awt.Font("SansSerif", 1, 14));
        label.setForeground( isSelected ?  selectedColor : normalColor);

        if (value instanceof TemplateItemAction)
        {
            label.setText( ((TemplateItemAction)value).getDisplayName() );
            label.setIcon( ((TemplateItemAction)value).getIcon() );
        }
        return label;

    }




}
