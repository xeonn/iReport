/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved. 
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
 * TreeTransfertHandler.java
 * 
 * Created on 15 settembre 2004, 2.19
 *
 */

package com.jaspersoft.ireport.designer.data.fieldsproviders.xml;

import com.jaspersoft.ireport.designer.dnd.TransferableObject;
import java.awt.datatransfer.Transferable;
import javax.swing.*;
import javax.swing.tree.*;
import net.sf.jasperreports.engine.design.JRDesignField;
/**
 *
 * @author  Administrator
 */
public class XMLTreeTransfertHandler extends javax.swing.TransferHandler  
//iR20 implements DragSourceMotionListener, TimingTarget 
{
    XMLFieldMappingEditor xmlEditor = null;

    public XMLFieldMappingEditor getXmlEditor() {
        return xmlEditor;
    }

    public void setXmlEditor(XMLFieldMappingEditor xmlEditor) {
        this.xmlEditor = xmlEditor;
    }
    
    /** Creates a new instance of TreeTransfertHandler 
     * @param xmlEditor 
     */
    public XMLTreeTransfertHandler(XMLFieldMappingEditor xmlEditor) {
        super();
        this.xmlEditor = xmlEditor;
    }
    
    @Override
    public int getSourceActions(JComponent c) 
    {
        return COPY_OR_MOVE;
        
    }
    
    @Override
    protected Transferable createTransferable(JComponent c) 
    {
        if (c instanceof JTree)
        {
            JTree tree = (JTree)c;
            TreePath path = tree.getLeadSelectionPath();
	    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)path.getLastPathComponent();
            
            JRDesignField field = getXmlEditor().createField(path, true);
            
            return new TransferableObject(field);           
        }
        
        return new TransferableObject(c);
    }
}
