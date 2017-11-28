/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import javax.swing.JList;
import org.openide.explorer.view.ListView;

/**
 *
 * @author gtoffoli
 */
public class StyleListView extends ListView {

    public StyleListView() {
        super();
        setDropTarget(true);
        setDragSource(true);
        
    }


    @Override
    protected JList createList() {
        JList l = super.createList();
        l.setCellRenderer(new StyleListCellRenderer());
        return l;
    }


}
