package com.jaspersoft.ireport.designer.outline;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows Outline component.
 */
public class OutlineAction extends AbstractAction {

    public OutlineAction() {
        super(NbBundle.getMessage(OutlineAction.class, "CTL_OutlineAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(OutlineTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = OutlineTopComponent.getDefault();
        win.open();
        win.requestActive();
    }
}