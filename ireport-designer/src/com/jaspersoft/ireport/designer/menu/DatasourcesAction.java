package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.connection.gui.ConnectionsDialog;
import com.jaspersoft.ireport.designer.tools.ClassPathDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class DatasourcesAction extends CallableSystemAction {

    public void performAction() {
        
        Window pWin = Misc.getMainWindow();
        ConnectionsDialog connectionsDialog = null;
        if (pWin instanceof Dialog) connectionsDialog = new ConnectionsDialog((Dialog)pWin, true);
        else if (pWin instanceof Frame) connectionsDialog = new ConnectionsDialog((Frame)pWin, true);
        else connectionsDialog = new ConnectionsDialog((Dialog)null, true);
        
        connectionsDialog.setVisible(true);
    }

    public String getName() {
        return NbBundle.getMessage(DatasourcesAction.class, "CTL_DatasourcesAction");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.FALSE);
    }

    /** The action's icon location.  
     * @return the action's icon location  
     */  
    protected String iconResource () {  
      return "com/jaspersoft/ireport/designer/menu/datasources.png"; // NOI18N  
    }  


    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
    
}