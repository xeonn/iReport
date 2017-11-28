package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.AddBandUndoableEdit;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class AddAnotherDetailBandAction extends NodeAction {
   
    public String getName() {
        return I18n.getString("AddAnotherDetailBandAction.Name");
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

        JasperDesign jd = IReportManager.getInstance().getActiveReport();
        if (jd != null)
        {
            JRDesignBand band = new JRDesignBand();
            band.setHeight(50);
            ((JRDesignSection)jd.getDetailSection()).addBand(band);
            AddBandUndoableEdit undo = new AddBandUndoableEdit(band,jd);
            IReportManager.getInstance().addUndoableEdit(undo);
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {

        return IReportManager.getInstance().getActiveReport() != null;
    }
}