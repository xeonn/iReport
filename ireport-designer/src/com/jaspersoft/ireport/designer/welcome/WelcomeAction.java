/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.welcome;

import com.jaspersoft.ireport.locale.I18n;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.windows.TopComponent;

/**
 * Action which shows Welcome component.
 */
public class WelcomeAction extends AbstractAction {

    public WelcomeAction() {
        super(I18n.getString("CTL_WelcomeAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(WelcomeTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = WelcomeTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
