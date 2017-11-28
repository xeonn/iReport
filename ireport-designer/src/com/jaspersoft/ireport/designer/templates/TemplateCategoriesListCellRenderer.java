/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author gtoffoli
 */
public class TemplateCategoriesListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        setOpaque(isSelected);
        setBorder(null);



        if (value instanceof TemplateCategory)
        {
            setText("   " + ((TemplateCategory)value).getSubCategory());
        }
        else
        {
            if (value.equals(TemplateCategory.CATEGORY_ALL_REPORTS))
            {
                setText("<html><b>All");
            }
            else if (value.equals(TemplateCategory.CATEGORY_OTHER_REPORTS))
            {
                setText("<html><b>Other");
            }
            else
            {
                setText("<html><b>" + getText());
            }
        }
        //setIcon(null);

        return this;
    }

    


}
