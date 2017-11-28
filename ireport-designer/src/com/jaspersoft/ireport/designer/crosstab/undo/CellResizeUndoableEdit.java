/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.crosstab.undo;

import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;

/**
 *
 * @author gtoffoli
 */
public class CellResizeUndoableEdit extends ObjectPropertyUndoableEdit {

    private boolean main = false;
    private JRDesignCrosstab crosstab = null;
    
    @Override
    public String getPresentationName() {
        return "Cell resize";
    }

    
    public CellResizeUndoableEdit(Object object, String propertyName, Class propertyClass, Object oldValue,  Object newValue)
    {
        super(object, propertyName, propertyClass, oldValue,  newValue);
    }

    public

    boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        if (isMain() && crosstab != null) 
        {
            crosstab.preprocess();
            // This trick is used to force a document rebuild...
           crosstab.getEventSupport().firePropertyChange( JRDesignCrosstab.PROPERTY_CELLS , null, null);
        }
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        if (isMain() && crosstab != null) 
        {
            crosstab.preprocess();
            // This trick is used to force a document rebuild...
           crosstab.getEventSupport().firePropertyChange( JRDesignCrosstab.PROPERTY_CELLS , null, null);
        }
    }

    public JRDesignCrosstab getCrosstab() {
        return crosstab;
    }

    public void setCrosstab(JRDesignCrosstab crosstab) {
        this.crosstab = crosstab;
    }
    
    
    
    
}
