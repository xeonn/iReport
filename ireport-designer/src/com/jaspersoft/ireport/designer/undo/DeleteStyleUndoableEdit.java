/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class DeleteStyleUndoableEdit extends AggregatedUndoableEdit {

    private JRDesignStyle style = null;
    private JasperDesign jasperDesign = null;
    
    private int index = -1;
    
    public DeleteStyleUndoableEdit(JRDesignStyle style, JasperDesign jasperDesign, int index)
    {
        this.style = style;
        this.jasperDesign = jasperDesign;
        this.index = index;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        
        super.undo();
        if (index > -1)
        {
            jasperDesign.getStylesList().add(index,getStyle());
        }
        else
        {
            jasperDesign.getStylesList().add(getStyle());
        }
        jasperDesign.getEventSupport().firePropertyChange( JasperDesign.PROPERTY_STYLES, null, null);
    }

    @Override
    public void redo() throws CannotRedoException {
        
        super.redo();
        
        index = jasperDesign.getStylesList().indexOf(getStyle());
        jasperDesign.removeStyle(getStyle());
        
    }
    
    @Override
    public String getPresentationName() {
        return "Delete Style " + getStyle().getName();
    }

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void setJasperDesign(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
    }

    public JRDesignStyle getStyle() {
        return style;
    }

    public void setStyle(JRDesignStyle style) {
        this.style = style;
    }
}
