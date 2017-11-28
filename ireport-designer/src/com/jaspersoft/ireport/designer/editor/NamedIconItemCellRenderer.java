/*
 * EditorContext.java
 * 
 * Created on Oct 11, 2007, 4:25:53 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author gtoffoli
 */
public class NamedIconItemCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    
        if (c instanceof JLabel && value instanceof NamedIconItem && value != null)
        {
            NamedIconItem item = (NamedIconItem)value;
            ((JLabel)c).setText( item.getDisplayName() );
            ((JLabel)c).setIcon( item.getIcon() );
        }
        
        return c;
    }

    
    
}
