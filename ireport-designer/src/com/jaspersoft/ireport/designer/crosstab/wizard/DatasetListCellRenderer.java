/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.crosstab.wizard;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import net.sf.jasperreports.engine.design.JRDesignDataset;

/**
 *
 * @author gtoffoli
 */
public class DatasetListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value instanceof JRDesignDataset)
        {
            JRDesignDataset dataset = (JRDesignDataset)value;
            if (dataset.isMainDataset())  setText("Main report dataset");
            else setText(dataset.getName());
        }
    
        return this;
    }

}
