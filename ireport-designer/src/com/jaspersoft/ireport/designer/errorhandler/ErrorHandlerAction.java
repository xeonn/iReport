/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.errorhandler;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows ErrorHandler component.
 */
public class ErrorHandlerAction extends AbstractAction {

    public ErrorHandlerAction() {
        super(NbBundle.getMessage(ErrorHandlerAction.class, "CTL_ErrorHandlerAction"));
        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(ErrorHandlerTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = ErrorHandlerTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
