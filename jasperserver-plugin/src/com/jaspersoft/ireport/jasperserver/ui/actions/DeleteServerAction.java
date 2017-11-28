package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ServerChildren;
import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class DeleteServerAction extends NodeAction {

    public String getName() {
        return NbBundle.getMessage(DeleteServerAction.class, "CTL_DeleteServerAction");
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
        
        for (int i=0; i<activatedNodes.length; ++i)
        {
            ResourceNode node = (ResourceNode)activatedNodes[i];
            JServer server = node.getRepositoryObject().getServer();
            
            if (JOptionPane.showConfirmDialog(Misc.getMainFrame(), 
                "Are you sure you want to delete the server '" + server + "' ?",
                "Deleting server",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
                ) == JOptionPane.YES_OPTION)
            {
                JasperServerManager.getMainInstance().saveConfiguration();
                Node root = activatedNodes[0].getParentNode();
                while (root.getParentNode() != null) root = root.getParentNode();
                if (root.getChildren() instanceof ServerChildren)
                {
                    ((ServerChildren)root.getChildren()).recalculateKeys();
                }
                JasperServerManager.getMainInstance().getJServers().remove(server);
            }
        }
        
        
    }

    // Check all the selected nodes are servers or their children...
    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length < 1) return false;
        
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (!(activatedNodes[i] instanceof ResourceNode))
            {
                return false;
            }
        }
        
        return true;
    }
}