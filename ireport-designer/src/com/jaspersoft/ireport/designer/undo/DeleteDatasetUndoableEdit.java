/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class DeleteDatasetUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignDataset dataset = null;
    private JasperDesign jasperDesign = null;
    private int index = -1;
    
    public DeleteDatasetUndoableEdit(JRDesignDataset dataset, JasperDesign jasperDesign, int index)
    {
        this.dataset = dataset;
        this.index = index;
        if (index < 0) index = jasperDesign.getDatasetsList().size();
        this.jasperDesign = jasperDesign;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        // readd the dataset at the right index...
        jasperDesign.getDatasetsList().add(index,getDataset());
        jasperDesign.getEventSupport().firePropertyChange(JasperDesign.PROPERTY_DATASETS, null, null);
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        jasperDesign.removeDataset(getDataset());
    }
    
    @Override
    public String getPresentationName() {
        return "Delete Dataset " + getDataset().getName();
    }

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void setJasperDesign(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
    }

    public JRDesignDataset getDataset() {
        return dataset;
    }

    public void setDataset(JRDesignDataset dataset) {
        this.dataset = dataset;
    }
}
