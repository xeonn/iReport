/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.resourcebundle;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows ResourceBundle component.
 */
public class ResourceBundleAction extends AbstractAction {

    public ResourceBundleAction() {
        super(NbBundle.getMessage(ResourceBundleAction.class, "CTL_ResourceBundleAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(ResourceBundleTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = ResourceBundleTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
