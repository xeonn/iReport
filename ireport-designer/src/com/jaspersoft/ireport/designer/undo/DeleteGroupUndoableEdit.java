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
public class DeleteGroupUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignGroup group = null;
    private JRDesignDataset dataset = null;
    private int index = 0;
    
    public DeleteGroupUndoableEdit(JRDesignGroup group, JRDesignDataset dataset, int index)
    {
        this.group = group;
        this.dataset = dataset;
        this.index = index;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        try {

            getDataset().addGroup(getGroup());
        
            // We need to use a trick to put the group in the right position...
            getDataset().getGroupsList().remove(getGroup());
            getDataset().getGroupsList().add(index, getGroup());
            getDataset().getEventSupport().fireCollectionElementAddedEvent(JRDesignDataset.PROPERTY_GROUPS, group, getDataset().getGroupsList().size() - 1);
        
        } catch (JRException ex) {
            Exceptions.printStackTrace(ex);
            throw new CannotRedoException();
        }
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        getDataset().removeGroup(getGroup());
        
    }
    
    @Override
    public String getPresentationName() {
        
        String groupName = "";
        if (getGroup() != null && getGroup().getName() != null)
        {
            groupName = getGroup().getName();
        }
        
        return "Delete group " + groupName;
        
    }

    public JRDesignGroup getGroup() {
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
