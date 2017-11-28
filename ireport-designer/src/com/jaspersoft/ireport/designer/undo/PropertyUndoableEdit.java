/*
 * ExpressionPropertyEditor.java
 * 
 * Created on Oct 12, 2007, 11:38:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractProperty;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class PropertyUndoableEdit extends AbstractUndoableEdit
{
    private AbstractProperty property = null;
    private Object oldValue = null;
    private Object newValue = null;
    
    public PropertyUndoableEdit(
        AbstractProperty property, 
        Object oldValue, 
        Object newValue
        )
    {
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    @Override
    public void undo() throws CannotUndoException
    {
        super.undo();
        property.setPropertyValue(oldValue);
    }
    
    @Override
    public void redo() throws CannotRedoException
    {
        super.redo();
        property.setPropertyValue(newValue);
    }

    @Override
    public String getPresentationName() 
    {
        return property.getDisplayName();
    }
    
}
