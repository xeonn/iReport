package com.jaspersoft.ireport.designer.logpane;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows IRConsole component.
 */
public class IRConsoleAction extends AbstractAction {

    public IRConsoleAction() {
        super(NbBundle.getMessage(IRConsoleAction.class, "CTL_IRConsoleAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(IRConsoleTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = IRConsoleTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}