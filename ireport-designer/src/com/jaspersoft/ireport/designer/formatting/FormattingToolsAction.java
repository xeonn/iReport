/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.formatting;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows FormattingTools component.
 */
public class FormattingToolsAction extends AbstractAction {

    public FormattingToolsAction() {
        super(NbBundle.getMessage(FormattingToolsAction.class, "CTL_FormattingToolsAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(FormattingToolsTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = FormattingToolsTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
    
}
