/*
 * ElementPasteType.java
 * 
 * Created on 31-ott-2007, 15.56.26
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.AddElementGroupUndoableEdit;
import com.jaspersoft.ireport.designer.undo.AddElementUndoableEdit;
import com.jaspersoft.ireport.designer.undo.AggregatedUndoableEdit;
import com.jaspersoft.ireport.designer.undo.DeleteElementGroupUndoableEdit;
import com.jaspersoft.ireport.designer.undo.DeleteElementUndoableEdit;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.List;
import javax.swing.undo.UndoableEdit;
import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author gtoffoli
 */
public class ElementPasteType extends PasteType {

    JRElementGroup newContainer = null;
    JRElementGroup oldContainer = null;
    Object element = null;
    int dropAction = 0;
    Node destinatioNode = null;
    
    
    
    AggregatedUndoableEdit unduableEdit = new  AggregatedUndoableEdit("paste");
    
    public ElementPasteType(
            JRElementGroup oldContainer,
            JRElementGroup newContainer,
            Object element,
            int dropAction,
            Node destinatioNode)
    {
        this.oldContainer = oldContainer;
        this.newContainer = newContainer;
        this.element = element;
        this.dropAction = dropAction;
        this.destinatioNode = destinatioNode;
    }
    
    @SuppressWarnings("unchecked")
    public Transferable paste() throws IOException {
            
            
        List list = newContainer.getChildren();
        int currentIndex = -1; //Current position in the list

        for (int i = 0; i < list.size(); ++i) {
            Object ele = list.get(i);
            if (ele == element) {
                currentIndex = i;
            }
        }

        if( (dropAction & NodeTransfer.MOVE) != 0 ) // Moving field...
        {
            int newIndex = -1;
            if (currentIndex != -1) // Case 1: Moving inside the band
            { 
                //System.out.println("Moving inside this band");
            } 
            else // Case 2: Moving in this band from another band...
            {
               //System.out.println("Moving in this band from another band");
                // Find the position in which the node was dropped...
                Node[] nodes = destinatioNode.getChildren().getNodes();
                for (int i = 0; i < nodes.length; ++i) {
                    if (nodes[i] instanceof ElementNode &&
                        ((ElementNode)nodes[i]).getElement() == element) 
                    {
                        newIndex = i;
                        break;
                    }
                    else if (nodes[i] instanceof ElementGroupNode &&
                        ((ElementGroupNode)nodes[i]).getElementGroup() == element) 
                    {
                        newIndex = i;
                        break;
                    }
                }

                if (element instanceof JRDesignElement)
                {
                    removeElement(oldContainer, (JRDesignElement)element);
                }
                else if (element instanceof JRDesignElementGroup)
                {
                    removeElementGroup(oldContainer, (JRDesignElementGroup)element);
                }
                
                if (newIndex == -1) 
                {
                    if (element instanceof JRDesignElement)
                    {
                        addElement(newContainer, (JRDesignElement)element);
                    }
                    else if (element instanceof JRDesignElementGroup)
                    {
                        addElementGroup(newContainer, (JRDesignElementGroup)element);
                    }
                }
                else
                {
                    if (element instanceof JRDesignElement)
                    {
                        ((JRDesignElement)element).setElementGroup(newContainer);
                    }
                    else if (element instanceof JRDesignElementGroup)
                    {
                        ((JRDesignElementGroup)element).setElementGroup(newContainer);
                    }
                    
                    newContainer.getChildren().add(newIndex, element );
                    // Fire an event...
                    getEventSupport(newContainer).fireCollectionElementAddedEvent(JRDesignElementGroup.PROPERTY_CHILDREN, 
                            element, newContainer.getChildren().size() - 1);
                    
                    // Try to select this new element too...
                    IReportManager.getInstance().setSelectedObject(element);                    
                }
            }
        }
        else // Duplicating
        {
           if (element instanceof JRCloneable)
           {
                Object newElement = ((JRCloneable) element).clone();

                if (newElement instanceof JRDesignElement) {
                    addElement(newContainer, (JRDesignElement) newElement);
                } else if (newElement instanceof JRDesignElementGroup) {
                    addElementGroup(newContainer, (JRDesignElementGroup) newElement);
                }
           }
            
            
        }
        
        if (unduableEdit.getAggregatedEditCount() > 0)
        {
             IReportManager.getInstance().addUndoableEdit(unduableEdit, true);
        }
        return null;
    }
    
    private void removeElement(JRElementGroup container, JRDesignElement element)
    {
        int index = 0;
        if (container instanceof JRDesignElementGroup)
        {
            index = ((JRDesignElementGroup)container).getChildren().indexOf(element);
            ((JRDesignElementGroup)container).removeElement(element);
        }
        else if (container instanceof JRDesignFrame)
        {
            index = ((JRDesignFrame)container).getChildren().indexOf(element);
            ((JRDesignFrame)container).removeElement(element);
        }
        
        DeleteElementUndoableEdit edit = new DeleteElementUndoableEdit(element,container,index);
        unduableEdit.concatenate(edit);
    }
    
    private void removeElementGroup(JRElementGroup container, JRDesignElementGroup group)
    {
        int index=0;
        if (container instanceof JRDesignElementGroup)
        {
            index = ((JRDesignElementGroup)container).getChildren().indexOf(group);
            ((JRDesignElementGroup)container).removeElementGroup(group);
        }
        else if (container instanceof JRDesignFrame)
        {
            index = ((JRDesignFrame)container).getChildren().indexOf(group);
            ((JRDesignFrame)container).removeElementGroup(group);
        }
        DeleteElementGroupUndoableEdit edit = new DeleteElementGroupUndoableEdit(group,container,index);
        unduableEdit.concatenate(edit);
        
    }
    
    private void addElement(JRElementGroup container, JRDesignElement element)
    {
        if (container instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)container).addElement(element);
        }
        else if (container instanceof JRDesignFrame)
        {
            ((JRDesignFrame)container).addElement(element);
        }
        AddElementUndoableEdit edit = new AddElementUndoableEdit(element,container);
        unduableEdit.concatenate(edit);
    }
    
    private void addElementGroup(JRElementGroup container, JRDesignElementGroup group)
    {
        if (container instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)container).addElementGroup(group);
            
        }
        else if (container instanceof JRDesignFrame)
        {
            ((JRDesignFrame)container).addElementGroup(group);
        }
        
        AddElementGroupUndoableEdit edit = new AddElementGroupUndoableEdit(group,container);
        unduableEdit.concatenate(edit);
    }
    
    private JRPropertyChangeSupport getEventSupport(JRElementGroup container)
    {
        if (container instanceof JRDesignElementGroup)
        {
            return ((JRDesignElementGroup)container).getEventSupport();
        }
        else if (container instanceof JRDesignFrame)
        {
            return ((JRDesignFrame)container).getEventSupport();
        }
        
        return null;
    }

    
}
