/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import com.jaspersoft.ireport.locale.I18n;

/**
 * This undo operation is just an aggregation of basic operations on the model...
 * @author gtoffoli
 */
public class GroupElementsUndoableEdit extends AggregatedUndoableEdit {
    
    @Override
    public String getPresentationName() {
        
        return I18n.getString("GroupElementsAction.name");
    }
}
