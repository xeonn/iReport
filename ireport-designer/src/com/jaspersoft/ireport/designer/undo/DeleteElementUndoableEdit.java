/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;

/**
 *
 * @author gtoffoli
 */
public class DeleteElementUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignElement element = null;
    private Object container = null;
    private int index = -1;
    
    public DeleteElementUndoableEdit(JRDesignElement element, Object container, int index)
    {
        this.element = element;
        this.container = container;
        this.index = index;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        if (container instanceof JRDesignElementGroup)
        {
            JRDesignElementGroup cg = ((JRDesignElementGroup)container);
            element.setElementGroup(cg);
            cg.getChildren().add(index, element);
            cg.getEventSupport().fireCollectionElementAddedEvent(JRDesignElementGroup.PROPERTY_CHILDREN, element, cg.getChildren().size() - 1);
        }
        else if (container instanceof JRDesignFrame)
        {
            JRDesignFrame cg = ((JRDesignFrame)container);
            element.setElementGroup(cg);
            cg.getChildren().add(index, element);
            cg.getEventSupport().fireCollectionElementAddedEvent(JRDesignElementGroup.PROPERTY_CHILDREN, element, cg.getChildren().size() - 1);
        }
        
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        if (container instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)container).removeElement(element);
        }
        else if (container instanceof JRDesignFrame)
        {
            ((JRDesignFrame)container).removeElement(element);
        }
        
    }
    
    @Override
    public String getPresentationName() {
        
        return "Delete element";
    }

    public JRDesignElement getElement() {
        return element;
    }

    public void setElement(JRDesignElement element) {
        this.element = element;
    }
}
