package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryFile;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ReportUnitNode;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ResourceNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Mutex;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class OpenFileAction extends NodeAction {

    /**
     * When a Jrxml file is opened, the action listen for the loading of the jrxml.
     * When a JasperDesign appears in the lookup, it is analyzed to cache his images.
     * On the first resultChange the listener is removed.
     */
    private static List jrxmlListeners = new ArrayList();
    
    public String getName() {
        return NbBundle.getMessage(OpenFileAction.class, "CTL_OpenFileAction");
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
            final Node node = activatedNodes[i];
            final RepositoryFile rf = (RepositoryFile)((ResourceNode)node).getRepositoryObject();
         
            Runnable run = new Runnable() {

                public void run() {
                    // 1. Download the file...
                    try {
                        String fname = rf.getFile();
                        
                        DataObject obj = DataObject.find(FileUtil.toFileObject(new File(fname)));
                        
                        OpenCookie ocookie = obj.getCookie(OpenCookie.class);
                        if (ocookie != null)
                        {
                            ocookie.open();
                            
                            RepositoryReportUnit reportUnit = ReportUnitNode.getParentReportUnit(node);
                               
                            // Add temporary info about where this jd comes from...
                            JasperServerManager.getMainInstance().getJrxmlReportUnitMap().put(fname , reportUnit);

                            if (rf.getDescriptor().getWsType().equals( ResourceDescriptor.TYPE_JRXML))
                            {
                                JrxmLookupListener listener = new JrxmLookupListener(obj, jrxmlListeners, reportUnit, rf.getServer());
                                jrxmlListeners.add(listener);
                            }
                        }
                        
                    } catch (Exception ex)
                    {
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
        
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length < 1) return false;
        
        for (int i=0; i<activatedNodes.length; ++i)
        {
            if (!(activatedNodes[i] instanceof ResourceNode))
            {
                return false;
            }
            
            ResourceNode node = (ResourceNode)activatedNodes[i];
            if (!(node.getRepositoryObject() instanceof RepositoryFile)) return false;
        }
        
        return true;
    }

    
}