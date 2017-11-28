package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.GroupNode;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class MoveGroupDownAction extends NodeAction {

    private static MoveGroupDownAction instance = null;
    
    private MoveGroupDownAction()
    {
        super();
    }
    
    
    public String getName() {
        return I18n.getString("MoveGroupDownAction.Name.CTL_MoveGroupDownAction");
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
        List groups = dataset.getGroupsList();
        int index = groups.indexOf(grp);
        if (index < groups.size())
        {
            groups.remove(grp);
            groups.add(index+1, grp);
        }

        dataset.getEventSupport().firePropertyChange( JRDesignDataset.PROPERTY_GROUPS, null, null);

        // We should add an undo here...
        IReportManager.getInstance().notifyReportChange();
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if ( activatedNodes[0] instanceof GroupNode &&
            ( ((GroupNode)activatedNodes[0]).getGroup() != null))
        {
            GroupNode groupNode = (GroupNode)activatedNodes[0];
            JRDesignGroup grp = groupNode.getGroup();
            JRDesignDataset dataset = groupNode.getDataset();
            List groups = dataset.getGroupsList();
            int index = groups.indexOf(grp);
            if (index == groups.size()-1) return false;
            return true;
        }
        return false;
    }

    
}