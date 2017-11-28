/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.styles;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows StylesLibrary component.
 */
public class StylesLibraryAction extends AbstractAction {

    public StylesLibraryAction() {
        super(NbBundle.getMessage(StylesLibraryAction.class, "CTL_StylesLibraryAction"));
        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(StylesLibraryTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = StylesLibraryTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
