package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.BandNode;
import com.jaspersoft.ireport.designer.undo.DeleteGroupUndoableEdit;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class DeleteGroupAction extends NodeAction {

    private static DeleteGroupAction instance = null;
    
    public static synchronized DeleteGroupAction getInstance()
    {
        if (instance == null)
        {
            instance = new DeleteGroupAction();
        }
        
        return instance;
    }
    
    private DeleteGroupAction()
    {
        super();
    }
    
    
    public String getName() {
        return NbBundle.getMessage(DeleteGroupAction.class, "CTL_DeleteGroupAction");
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
        
        BandNode bandNode = (BandNode)activatedNodes[0];
        // Remove the group...
        String groupName = bandNode.getBand().getOrigin().getGroupName();
        
        
        JRDesignGroup grp = (JRDesignGroup)bandNode.getJasperDesign().getGroupsMap().get(groupName);
        int index = bandNode.getJasperDesign().getGroupsList().indexOf(grp);
        bandNode.getJasperDesign().removeGroup(grp);
        
        DeleteGroupUndoableEdit edit = new DeleteGroupUndoableEdit(grp, bandNode.getJasperDesign().getMainDesignDataset(), index);
        IReportManager.getInstance().addUndoableEdit(edit);
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if ( activatedNodes[0] instanceof BandNode &&
            ( ((BandNode)activatedNodes[0]).getBand().getOrigin().getBandType() == JROrigin.GROUP_FOOTER ||
              ((BandNode)activatedNodes[0]).getBand().getOrigin().getBandType() == JROrigin.GROUP_HEADER))
        {
            return true;
        }
        return false;
    }
}