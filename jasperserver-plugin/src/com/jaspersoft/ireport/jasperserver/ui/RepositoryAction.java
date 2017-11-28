/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.jasperserver.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows Repository component.
 */
public class RepositoryAction extends AbstractAction {

    public RepositoryAction() {
        super(NbBundle.getMessage(RepositoryAction.class, "CTL_RepositoryAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(RepositoryTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = RepositoryTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
