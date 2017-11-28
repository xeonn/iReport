package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.RepositoryFolder;
import com.jaspersoft.ireport.jasperserver.RepositoryReportUnit;
import com.jaspersoft.ireport.jasperserver.ui.ResourceChooser;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ReportUnitInputControlsNode;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import javax.swing.JOptionPane;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class AddExistingInputControlAction extends NodeAction {

    public String getName() {
        return NbBundle.getMessage(AddExistingInputControlAction.class, "CTL_AddExistingInputControlAction");
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

        if (activatedNodes == null || activatedNodes.length != 1) return;

        if (activatedNodes[0] instanceof ReportUnitInputControlsNode)
        {
            ReportUnitInputControlsNode ruicn = (ReportUnitInputControlsNode)activatedNodes[0];
            RepositoryReportUnit ru = ruicn.getReportUnit();

            // In this case we choose an input control from the repository....
            ResourceChooser rc = new ResourceChooser();

            String reportUnitUri = null;
            reportUnitUri = ru.getDescriptor().getUriString();
            rc.setServer( ru.getServer() );

            if (rc.showDialog(Misc.getMainFrame(), null) == JOptionPane.OK_OPTION)
            {
                ResourceDescriptor rd = rc.getSelectedDescriptor();
                if (rd == null || rd.getUriString() == null)
                {
                    return;
                }

                if (!rd.getWsType().equals( ResourceDescriptor.TYPE_INPUT_CONTROL))
                {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(),
                            JasperServerManager.getFormattedString("repositoryExplorer.message.invalidInputControl","{0} is not an Input Control!",new Object[]{rd.getName()}),
                                 "",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ResourceDescriptor newRd = new ResourceDescriptor();
                newRd.setWsType( ResourceDescriptor.TYPE_INPUT_CONTROL);
                newRd.setIsReference(true);
                newRd.setReferenceUri( rd.getUriString() );
                newRd.setIsNew(true);
                newRd.setUriString(reportUnitUri+"/<cotnrols>");
                try {
                    newRd = ru.getServer().getWSClient().modifyReportUnitResource(reportUnitUri, newRd, null);

                    RepositoryFolder obj = RepositoryFolder.createRepositoryObject(ru.getServer(), newRd);
                    if (ruicn.getRepositoryObject().isLoaded())
                    {
                        ruicn.getResourceDescriptor().getChildren().add( newRd );
                        ruicn.getRepositoryObject().getChildren().add(obj);
                        ruicn.refreshChildrens(false);
                    }

                } catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(),JasperServerManager.getFormattedString("messages.error.3", "Error:\n {0}", new Object[] {ex.getMessage()}));
                    ex.printStackTrace();
                }
            }

        }
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;


        if (!(activatedNodes[0] instanceof ReportUnitInputControlsNode))
        {
                return false;
        }
        
        return true;
    }

    
}