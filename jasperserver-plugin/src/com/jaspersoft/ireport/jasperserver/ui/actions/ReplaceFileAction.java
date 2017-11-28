package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.JrxmlDataObject;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryFile;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.io.File;
import javax.swing.JOptionPane;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.Mutex;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class ReplaceFileAction extends NodeAction {

    public String getName() {
        return NbBundle.getMessage(ReplaceFileAction.class, "CTL_ReplaceFileAction");
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
        
        if (!(activatedNodes[0] instanceof ResourceNode)) return;
        
        ResourceNode node = (ResourceNode)activatedNodes[0];
        
        if (((ResourceNode)activatedNodes[0]).getRepositoryObject() instanceof RepositoryFile)
        {
            final RepositoryFile rf = (RepositoryFile)((ResourceNode)activatedNodes[0]).getRepositoryObject();

            // Check if we are inside a report unit...
            String reportUnitUri = null;
            if (activatedNodes[0].getParentNode() instanceof ResourceNode &&
                ((ResourceNode)activatedNodes[0].getParentNode()).getResourceDescriptor().getWsType().equals( ResourceDescriptor.TYPE_REPORTUNIT))
            {
                reportUnitUri = ((ResourceNode)activatedNodes[0].getParentNode()).getResourceDescriptor().getUriString();
            }
            
            // Get the current file...
            JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
            if (view != null && view.getLookup() != null)
            {
                JrxmlDataObject dobject = view.getLookup().lookup(JrxmlDataObject.class);
                if (dobject != null)
                {
                    final String fileName = FileUtil.toFile(dobject.getPrimaryFile()).getPath();
                    final String ruUri = reportUnitUri;
                    Thread t = new Thread(new Runnable() {

                        public void run() {
                            try {
                                rf.getServer().getWSClient().modifyReportUnitResource(ruUri, rf.getDescriptor(), new File(fileName));
                            
                                Mutex.EVENT.readAccess(new Runnable() {

                                    public void run() {
                                        JOptionPane.showMessageDialog(Misc.getMainFrame(),
                                        JasperServerManager.getString("repositoryExplorer.message.fileUpdated", "File succesfully updated."),
                                        JasperServerManager.getString("repositoryExplorer.message.operationResult", "Operation result"), JOptionPane.INFORMATION_MESSAGE);
                           
                                    }
                                }); 
                            } catch (Exception ex)
                            {
                                final String msg = ex.getMessage();
                                Mutex.EVENT.readAccess(new Runnable() {

                                    public void run() {
                                         JOptionPane.showMessageDialog(Misc.getMainFrame(),JasperServerManager.getFormattedString("messages.error.3", "Error:\n {0}", new Object[] {msg}),"Operation result", JOptionPane.ERROR_MESSAGE);
                                    }
                                });
                                ex.printStackTrace();
                            }

                        }
                    });
                    
                    t.start();
                }
            }
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if ( activatedNodes[0] instanceof ResourceNode &&
             ((ResourceNode)activatedNodes[0]).getResourceDescriptor().getWsType().equals( ResourceDescriptor.TYPE_JRXML))
        {
            JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
            if (view != null && view.getLookup() != null)
            {
                JrxmlDataObject dobject = view.getLookup().lookup(JrxmlDataObject.class);
                if (dobject != null)
                {
                    return true;
                }
            }
        }
        return false;
    }
}