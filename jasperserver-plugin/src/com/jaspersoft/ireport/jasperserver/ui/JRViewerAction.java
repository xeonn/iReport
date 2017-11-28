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
 * Action which shows JRViewer component.
 */
public class JRViewerAction extends AbstractAction {

    public JRViewerAction() {
        super(NbBundle.getMessage(JRViewerAction.class, "CTL_JRViewerAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(JRViewerTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = JRViewerTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
