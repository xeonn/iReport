/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;

/**
 *
 * @author gtoffoli
 */
public class AddElementGroupUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignElementGroup group = null;
    private Object container = null;
    
    public AddElementGroupUndoableEdit(JRDesignElementGroup group, Object container)
    {
        this.group = group;
        this.container = container;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        
        if (container instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)container).removeElementGroup(group);
        }
        else if (container instanceof JRDesignFrame)
        {
            ((JRDesignFrame)container).removeElementGroup(group);
        }
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        if (container instanceof JRDesignElementGroup)
        {
            ((JRDesignElementGroup)container).addElementGroup(group);
        }
        else if (container instanceof JRDesignFrame)
        {
            ((JRDesignFrame)container).addElementGroup(group);
        }
        
    }
    
    @Override
    public String getPresentationName() {
        
        return "Add element group";
    }

    public JRDesignElementGroup getGroup() {
        return group;
    }

    public void setGroup(JRDesignElementGroup group) {
        this.group = group;
    }
}
