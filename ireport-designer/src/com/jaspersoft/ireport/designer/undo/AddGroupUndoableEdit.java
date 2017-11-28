/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import org.openide.util.Exceptions;

/**
 *
 * @author gtoffoli
 */
public class AddGroupUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignGroup group = null;
    private JRDesignDataset dataset = null;
    
    public AddGroupUndoableEdit(JRDesignGroup group, JRDesignDataset dataset)
    {
        this.group = group;
        this.dataset = dataset;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        getDataset().removeGroup(getGroup());
        
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        try {

            getDataset().addGroup(getGroup());
        } catch (JRException ex) {
            Exceptions.printStackTrace(ex);
            throw new CannotRedoException();
        }
    }
    
    @Override
    public String getPresentationName() {
        
        String groupName = "";
        if (getGroup() != null && getGroup().getName() != null)
        {
            groupName = getGroup().getName();
        }
        
        return "Add group " + groupName;
    }

    public

    JRDesignGroup getGroup() {
        return group;
    }

    public void setGroup(JRDesignGroup group) {
        this.group = group;
    }

    public JRDesignDataset getDataset() {
        return dataset;
    }

    public void setDataset(JRDesignDataset dataset) {
        this.dataset = dataset;
    }
}
