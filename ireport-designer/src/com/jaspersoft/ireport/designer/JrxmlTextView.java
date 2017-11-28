/*
 * JrxmlTextView.java
 * 
 * Created on Aug 27, 2007, 11:09:59 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
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
 */

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.JrxmlDataObject;
import java.awt.EventQueue;
import java.awt.Image;
import java.beans.BeanInfo;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.windows.TopComponent;

/**
 *
 * @author gtoffoli
 */
public class JrxmlTextView implements MultiViewDescription {

    private JrxmlSourceEditor editor;
    private JrxmlEditorSupport support;
    
    public JrxmlTextView(JrxmlEditorSupport ed) {
        this.support = ed;
    }
    
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }
    
    public String getDisplayName() {
        return "XML";
    }
    
    public Image getIcon() {
        Node nd = ((JrxmlDataObject)support.getDataObject()).getNodeDelegate();
        return nd.getIcon( BeanInfo.ICON_COLOR_16x16);
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    public String preferredID() {
        return "XML";
    }
    
    public MultiViewElement createElement() {
        return getEd();
    }
    
    public JrxmlSourceEditor getEd() {
        assert EventQueue.isDispatchThread();
        if (editor == null) {
            editor = new JrxmlSourceEditor(support);
        }
        return editor;
    }

    

}
