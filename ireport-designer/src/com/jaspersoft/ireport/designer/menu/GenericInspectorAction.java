/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.GenericInspectorTopComponent;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows JRCTXInspector component.
 */
public class GenericInspectorAction extends AbstractAction {

    public GenericInspectorAction() {
        super(I18n.getString("CTL_GenericInspectorAction"));
        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(GenericInspectorTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = GenericInspectorTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
