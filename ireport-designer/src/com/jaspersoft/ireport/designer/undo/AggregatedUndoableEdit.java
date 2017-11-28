/*
 * ExpressionPropertyEditor.java
 * 
 * Created on Oct 12, 2007, 11:38:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author gtoffoli
 */
public class AggregatedUndoableEdit extends AbstractUndoableEdit {

    private String presentationName = "";
    private List<UndoableEdit> otherEdits = new ArrayList<UndoableEdit>();

    public AggregatedUndoableEdit()
    {
        this("");
    }
    
    public AggregatedUndoableEdit(String name)
    {
        super();
        this.presentationName = name;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        for (int i=otherEdits.size()-1; i>=0; --i)
        {
            UndoableEdit undoOp = otherEdits.get(i);
            undoOp.undo();
        }
        
        super.undo();
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        for (UndoableEdit undoOp : otherEdits)
        {
            undoOp.redo();
        }
        
    }

    public String getPresentationName() {
        return presentationName;
    }

    /**
     *  concatenate add an ObjectPropertyUndoableEdit edit.
     *  The concatenated edits are undo in the inverse order they were added, and
     *  redo in the same order.
     * 
     */
    public boolean concatenate(UndoableEdit anEdit) {
        otherEdits.add( anEdit  );
        return true;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }
    
    public int getAggregatedEditCount()
    {
        return otherEdits.size();
    }

    

}
