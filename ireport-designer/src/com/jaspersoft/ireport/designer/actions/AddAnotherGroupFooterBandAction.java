package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.BandNode;
import com.jaspersoft.ireport.designer.undo.AddBandUndoableEdit;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignSection;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class AddAnotherGroupFooterBandAction extends NodeAction {
    
    public String getName() {
        return I18n.getString("AddAnotherGroupFooterBandAction.Name");
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

        JRDesignGroup group = ((BandNode)activatedNodes[0]).getGroup();
        JRDesignBand band = new JRDesignBand();
        band.setHeight(50);
        ((JRDesignSection)group.getGroupFooterSection()).addBand(band);
        AddBandUndoableEdit undo = new AddBandUndoableEdit(band,((BandNode)activatedNodes[0]).getJasperDesign());
        IReportManager.getInstance().addUndoableEdit(undo);
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {

        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if (!(activatedNodes[0] instanceof BandNode)) return false;
        return ((BandNode)activatedNodes[0]).getGroup() != null;
    }
}