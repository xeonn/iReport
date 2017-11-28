/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import java.beans.PropertyVetoException;
import org.netbeans.core.spi.multiview.CloseOperationHandler;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.openide.text.DataEditorSupport;

/**
 *
 * @author gtoffoli
 */
public class GenericCloseOperationHandler implements CloseOperationHandler{

    DataEditorSupport support;

    public GenericCloseOperationHandler(DataEditorSupport support)
    {
        this.support = support;
    }

    public boolean resolveCloseOperation(CloseOperationState[] elements) {
        try {
            support.getDataObject().setValid(false);
        } catch (PropertyVetoException ex) {
            return false;
        }
        System.gc();
        return true;
        

        /*
        
        //support.getDataObject().setModified(false);

        NotifyDescriptor nd = new NotifyDescriptor.Confirmation(
                "Save before closing?");
        DialogDisplayer.getDefault().notify(nd);
        if (nd.getValue().equals(NotifyDescriptor.YES_OPTION)) {
            // Let's consider only the first...
            for (CloseOperationState element : elements) {
                System.out.println("Executing action procees on element: " + element.getCloseWarningID());
                System.out.flush();
                element.getProceedAction().actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "xxx"));
            }
            return true;
        } else if (nd.getValue().equals(NotifyDescriptor.NO_OPTION)) {
            for (CloseOperationState element : elements) {
                System.out.println("Executing action procees on element: " + element.getCloseWarningID());
                System.out.flush();
                element.getDiscardAction().actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "xxx"));
            }
            return true;
        } else {
            // Cancel
            return false;
        }
        */
    }
}
