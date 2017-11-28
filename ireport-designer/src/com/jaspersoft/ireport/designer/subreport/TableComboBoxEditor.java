/*
 * Copyright (C) 2005 - 2008 JasperSoft Corporation.  All rights reserved. 
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 *
 *
 *
 *
 * TableComboBoxEditor.java
 * 
 * Created on March 23, 2006, 4:46 PM
 *
 */

package com.jaspersoft.ireport.designer.subreport;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.editor.ExpObject;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.editor.ExpressionEditor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author gtoffoli
 */
public class TableComboBoxEditor extends DefaultCellEditor {
    
    public TableComboBoxEditor(Vector items) {
        super(new JComboBox(items));
//        JComboBox cb = (JComboBox)this.getComponent();
//        cb.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//            
//                if (SwingUtilities.isRightMouseButton(e) || e.getClickCount() == 2)
//                {
//                    JComboBox cb = (JComboBox)getComponent();
//                    ExpressionEditor editor = new ExpressionEditor();
//                    editor.setExpressionContext( new ExpressionContext( IReportManager.getInstance().getActiveReport().getMainDesignDataset() ) );
//                    if (editor.showDialog(getComponent()) == JOptionPane.OK_OPTION)
//                    {
//                        cb.setSelectedItem(editor.getExpression());
//                    }
//                }
//            };
//        
//        });
    }
    
    

    @Override
    public Object getCellEditorValue() {

        Object obj = super.getCellEditorValue();
        if (obj instanceof String) return obj;
        ExpObject ooo = new ExpObject(obj);
        return ooo.getExpression();
    }
        
}
