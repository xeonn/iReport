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
public class AddDatasetUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignDataset dataset = null;
    private JasperDesign jasperDesign = null;
    
    private int index = -1;
    
    public AddDatasetUndoableEdit(JRDesignDataset dataset, JasperDesign jasperDesign)
    {
        this.dataset = dataset;
        this.jasperDesign = jasperDesign;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        index = jasperDesign.getDatasetsList().indexOf(getDataset());
        jasperDesign.removeDataset(getDataset());
        
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        
        // add the dataset again at the correct index...
        if (index > -1)
        {
            jasperDesign.getDatasetsList().add(index,getDataset());
        }
        else
        {
            jasperDesign.getDatasetsList().add(getDataset());
        }
        jasperDesign.getEventSupport().firePropertyChange( JasperDesign.PROPERTY_DATASETS, null, null);
    }
    
    @Override
    public String getPresentationName() {
        return "Add Dataset " + getDataset().getName();
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
