/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import java.awt.Rectangle;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *
 * @author gtoffoli
 */
public class ElementTransformationUndoableEdit extends AggregatedUndoableEdit {

    
    private JRDesignElement element = null;
    protected Rectangle oldBounds = null;
    protected Rectangle newBounds = null;
    
    public ElementTransformationUndoableEdit(JRDesignElement element, Rectangle oldBounds)
    {
        this.element = element;
        this.oldBounds = oldBounds;
        this.newBounds = new Rectangle( element.getX(), element.getY(), element.getWidth(), element.getHeight());
    }
    
    @Override
    public void redo() throws CannotRedoException 
    {
        super.redo();
        this.element.setX(  newBounds.x );
        this.element.setY(  newBounds.y );
        this.element.setWidth(  newBounds.width );
        this.element.setHeight(  newBounds.height );
    }

    @Override
    public void undo() throws CannotUndoException
    {
        super.undo();
        this.element.setX(  oldBounds.x );
        this.element.setY(  oldBounds.y );
        this.element.setWidth(  oldBounds.width );
        this.element.setHeight(  oldBounds.height );
    }

    public JRDesignElement getElement() {
        return element;
    }

    public void setElement(JRDesignElement element) {
        this.element = element;
    }
    
    
}
