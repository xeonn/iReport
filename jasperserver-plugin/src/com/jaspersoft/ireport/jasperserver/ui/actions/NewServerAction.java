package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.ui.ServerDialog;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ServerChildren;
import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class NewServerAction extends NodeAction {

    public String getName() {
        return NbBundle.getMessage(NewServerAction.class, "CTL_NewServerAction");
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

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {
        
        ServerDialog sd = new ServerDialog(Misc.getMainFrame(), true);
        sd.setVisible(true);
        if (sd.getDialogResult() == JOptionPane.OK_OPTION) {
            JasperServerManager.getMainInstance().getJServers().add(sd.getJServer());
            JasperServerManager.getMainInstance().saveConfiguration();
            
            Node root = activatedNodes[0].getParentNode();
            while (root.getParentNode() != null) root = root.getParentNode();
            if (root.getChildren() instanceof ServerChildren)
            {
                ((ServerChildren)root.getChildren()).recalculateKeys();
            }
        }
        
        
    }

    // Check all the selected nodes are servers or their children...
    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        return true;
    }
}