package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.GroupNode;
import com.jaspersoft.ireport.designer.undo.DeleteGroupUndoableEdit;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import org.openide.util.HelpCtx;
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
        return I18n.getString("DeleteGroupAction.Name.CTL_DeleteGroupAction");
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
        
        GroupNode groupNode = (GroupNode)activatedNodes[0];
        // Remove the group...
        JRDesignGroup grp = groupNode.getGroup();

        JRDesignDataset dataset = groupNode.getDataset();
        int index = dataset.getGroupsList().indexOf(grp);
        dataset.removeGroup(grp);

        // We should add an undo here...
        IReportManager.getInstance().notifyReportChange();
        
        DeleteGroupUndoableEdit edit = new DeleteGroupUndoableEdit(grp, dataset, index);
        IReportManager.getInstance().addUndoableEdit(edit);
        
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if ( activatedNodes[0] instanceof GroupNode &&
            ( ((GroupNode)activatedNodes[0]).getGroup() != null))
        {
            return true;
        }
        return false;
    }
}