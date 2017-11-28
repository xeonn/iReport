package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.tools.PageFormatPanel;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class EditPageFormatAction extends NodeAction {

    @Override
    protected void performAction(Node[] nodes) {
        
        JasperDesign jd = IReportManager.getInstance().getActiveReport();
        if (jd != null)
        {

            PageFormatPanel pfp = new PageFormatPanel();
            pfp.setJasperDesign(jd);
            pfp.showDialog(null, true);
        }  
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public String getName() {
        return I18n.getString("action.pageFormat");
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return IReportManager.getInstance().getActiveReport() != null;
    }
}