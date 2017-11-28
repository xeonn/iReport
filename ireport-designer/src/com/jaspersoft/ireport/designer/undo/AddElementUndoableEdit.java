/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;

/**
 *
 * @author gtoffoli
 */
public class AddElementUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignElement element = null;
    private Object container = null;
    private int index = -1;
    
    public AddElementUndoableEdit(JRDesignElement element, Object container)
    {
        this.element = element;
        this.container = container;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        
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
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        if (container instanceof JRDesignElementGroup)
        {
            // To add the element at the correct index we need to work a bit
            // since the API is limited here...
            JRDesignElementGroup cg = ((JRDesignElementGroup)container);
            element.setElementGroup(cg);
            cg.getChildren().add(index, element);
            cg.getEventSupport().fireCollectionElementAddedEvent(JRDesignElementGroup.PROPERTY_CHILDREN, element, cg.getChildren().size() - 1);
        }
        else if (container instanceof JRDesignFrame)
        {
            // To add the element at the correct index we need to work a bit
            // since the API is limited here...
            JRDesignFrame cg = ((JRDesignFrame)container);
            element.setElementGroup(cg);
            cg.getChildren().add(index, element);
            cg.getEventSupport().fireCollectionElementAddedEvent(JRDesignElementGroup.PROPERTY_CHILDREN, element, cg.getChildren().size() - 1);
        }
        
    }
    
    @Override
    public String getPresentationName() {
        
        return "Add element";
    }

    public JRDesignElement getElement() {
        return element;
    }

    public void setElement(JRDesignElement element) {
        this.element = element;
    }
}
