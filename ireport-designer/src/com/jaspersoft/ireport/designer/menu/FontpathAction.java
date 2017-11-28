package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.fonts.FontPathDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class FontpathAction extends CallableSystemAction {

    public void performAction() {
       
        Window pWin =Misc.getMainWindow();
        FontPathDialog cpd = null;
        if (pWin instanceof Dialog) cpd = new FontPathDialog((Dialog)pWin, true);
        else if (pWin instanceof Frame) cpd = new FontPathDialog((Frame)pWin, true);
        else cpd = new FontPathDialog((Dialog)null, true);
        
        
        cpd.setFontspath(IReportManager.getInstance().getFontpath(), IReportManager.getInstance().getClasspath());
        cpd.setVisible(true);
        if (cpd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION )
        {
            IReportManager.getInstance().setFontpath( cpd.getFontspath() );
        }
        
    }

    public String getName() {
        return NbBundle.getMessage(FontpathAction.class, "CTL_FontpathAction");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}