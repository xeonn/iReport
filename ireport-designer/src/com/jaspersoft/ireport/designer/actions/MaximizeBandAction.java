package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import com.jaspersoft.ireport.designer.outline.nodes.BandNode;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.designer.undo.PropertyUndoableEdit;

public final class MaximizeBandAction extends NodeAction {

    public String getName() {
        return I18n.getString("MaximizeBandAction.Name");
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

        JRDesignBand band = ((BandNode)activatedNodes[0]).getBand();
        int height = ModelUtils.getMaxBandHeight(band, ((BandNode)activatedNodes[0]).getJasperDesign() );
        int oldValue = band.getHeight();
        if (oldValue < height)
        {
            band.setHeight(height);
            ObjectPropertyUndoableEdit undo = new ObjectPropertyUndoableEdit(band,"Height",Integer.TYPE,oldValue,height);
            IReportManager.getInstance().addUndoableEdit(undo);
            IReportManager.getInstance().notifyReportChange();
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        return ( activatedNodes != null &&
                 activatedNodes.length == 1 &&
                 activatedNodes[0] instanceof BandNode);
    }
}