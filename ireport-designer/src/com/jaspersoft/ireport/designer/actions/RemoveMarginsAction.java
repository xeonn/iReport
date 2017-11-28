package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class RemoveMarginsAction extends NodeAction {

    public String getName() {
        return I18n.getString("RemoveMarginsAction.Name");
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
            int documentWidth = jd.getPageWidth() - jd.getLeftMargin() - jd.getRightMargin();
            int documentHeight = jd.getPageHeight() - jd.getTopMargin() - jd.getBottomMargin();

            ObjectPropertyUndoableEdit undo = new ObjectPropertyUndoableEdit(jd,"LeftMargin", Integer.TYPE, jd.getLeftMargin(), 0);
            undo.concatenate(new ObjectPropertyUndoableEdit(jd,"RightMargin", Integer.TYPE, jd.getRightMargin(), 0));
            undo.concatenate(new ObjectPropertyUndoableEdit(jd,"TopMargin", Integer.TYPE, jd.getTopMargin(), 0));
            undo.concatenate(new ObjectPropertyUndoableEdit(jd,"BottomMargin", Integer.TYPE, jd.getBottomMargin(), 0));
            undo.concatenate(new ObjectPropertyUndoableEdit(jd,"PageWidth", Integer.TYPE, jd.getPageWidth(), documentWidth));
            undo.concatenate(new ObjectPropertyUndoableEdit(jd,"PageHeight", Integer.TYPE, jd.getPageHeight(), documentHeight));

            jd.setLeftMargin(0);
            jd.setRightMargin(0);
            jd.setTopMargin(0);
            jd.setBottomMargin(0);
            jd.setPageWidth(documentWidth);
            jd.setPageHeight(documentHeight);
            
            undo.setPresentationName(I18n.getString("RemoveMarginsAction.Name"));
            
            IReportManager.getInstance().addUndoableEdit(undo);
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        
        return IReportManager.getInstance().getActiveReport() != null;
    }
}