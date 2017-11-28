/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public class AddStyleToReportAction extends NodeAction {

    @Override
    protected void performAction(Node[] nodes) {

            // Copy the style in the report...
        JasperDesign jd = IReportManager.getInstance().getActiveReport();
        for (int i=0; i<nodes.length; ++i)
        {
            JRStyle style = nodes[i].getLookup().lookup(JRStyle.class);
            if (style != null)
            {
                try {
                    // Copy the style...
                    jd.addStyle(style);
                } catch (JRException ex) {
                    ex.printStackTrace();
                    IReportManager.getInstance().notifyReportChange();
                }
            }
        }

    }

    @Override
    protected boolean enable(Node[] nodes) {

        if (IReportManager.getInstance().getActiveReport() == null) return false;
        
        for (int i=0; i<nodes.length; ++i)
        {
            if (nodes[i].getLookup().lookup(JRStyle.class) == null) return false; 
        }
        return nodes.length > 0;

    }

    @Override
    public String getName() {
        return I18n.getString("AddStyleToReportAction.addSyleToReport");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

}
