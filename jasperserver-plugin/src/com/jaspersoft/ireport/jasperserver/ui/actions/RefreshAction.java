package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.ui.nodes.FolderChildren;
import com.jaspersoft.ireport.jasperserver.ui.nodes.FolderNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Mutex;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class RefreshAction extends NodeAction {

    public String getName() {
        return NbBundle.getMessage(RefreshAction.class, "CTL_RefreshAction");
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
            if (activatedNodes[i] instanceof ResourceNode)
            {
                final ResourceNode node = (ResourceNode)activatedNodes[i];
                final RepositoryFolder rf = node.getRepositoryObject();
                
                Runnable run = new Runnable() {

                    public void run() {
                        try {
                            
                            ResourceDescriptor rd = rf.getServer().getWSClient().get(rf.getDescriptor(), null);
                            if (!rf.isRoot())
                            {
                                rf.setDescriptor(rd);
                            }
                            
                            SwingUtilities.invokeAndWait(new Runnable() {

                                public void run() {
                                    node.updateDisplayName();
                                    node.refreshChildrens(true);
                                }
                            });
                        } catch (Exception ex) {
                            // Do nothing in case of fail...
                            final String msg = ex.getMessage();
                            Mutex.EVENT.readAccess(new Runnable() {

                                public void run() {
                                    JOptionPane.showMessageDialog(Misc.getMainFrame(),JasperServerManager.getFormattedString("messages.error.3", "Error:\n {0}", new Object[] {msg}));

                                }
                            }); 
                            ex.printStackTrace();
                        }
                    }
                };

                Thread t = new Thread(run);
                t.start();
                
            }
            else
            {
                System.out.println("Other node type...");
                System.out.flush();

            }
        }
        
        
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null) return false;
        return true;
    }
}