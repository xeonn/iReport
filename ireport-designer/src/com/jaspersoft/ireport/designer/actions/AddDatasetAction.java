package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.NullBandNode;
import com.jaspersoft.ireport.designer.outline.nodes.ReportNode;
import com.jaspersoft.ireport.designer.undo.AddDatasetUndoableEdit;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;

public final class AddDatasetAction extends NodeAction {

    private static AddDatasetAction instance = null;
    
    public static synchronized AddDatasetAction getInstance()
    {
        if (instance == null)
        {
            instance = new AddDatasetAction();
        }
        
        return instance;
    }
    
    private AddDatasetAction()
    {
        super();
    }
    
    
    public String getName() {
        return NbBundle.getMessage(AddDatasetAction.class, "CTL_AddDatasetAction");
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
        
        if (activatedNodes.length > 0 &&
            activatedNodes[0] instanceof ReportNode)
        {
            try {
                ReportNode node = (ReportNode) activatedNodes[0];
                JRDesignDataset newDataset = new JRDesignDataset(false);
                String name = "dataset";
                for (int i = 1;; i++) {
                    if (!node.getJasperDesign().getDatasetMap().containsKey(name + i)) {
                        newDataset.setName(name + i);
                        break;
                    }
                }

                node.getJasperDesign().addDataset(newDataset);
                AddDatasetUndoableEdit edit = new AddDatasetUndoableEdit(newDataset, node.getJasperDesign());
                IReportManager.getInstance().addUndoableEdit(edit);
            } catch (JRException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length == 0) return false;
        return (activatedNodes.length > 0 && activatedNodes[0] instanceof ReportNode);
    }
}