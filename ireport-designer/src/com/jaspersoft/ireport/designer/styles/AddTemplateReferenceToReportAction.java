/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.styles;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRSimpleTemplate;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRTemplateReference;
import net.sf.jasperreports.engine.design.JRDesignReportTemplate;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 *
 * @author gtoffoli
 */
public class AddTemplateReferenceToReportAction extends NodeAction {

    @Override
    protected void performAction(Node[] nodes) {

            // Copy the style in the report...
        JasperDesign jd = IReportManager.getInstance().getActiveReport();
        for (int i=0; i<nodes.length; ++i)
        {
            JRTemplateReference reference = nodes[i].getLookup().lookup(JRTemplateReference.class);
            if (reference != null)
            {
                    // Copy the reference...
                    JRDesignReportTemplate reportTemplate = new JRDesignReportTemplate();
                    reportTemplate.setSourceExpression(Misc.createExpression("java.lang.String", "\""+ Misc.string_replace("\\\\","\\",reference.getLocation()) +"\""));
                    jd.addTemplate(reportTemplate);
                    IReportManager.getInstance().notifyReportChange();
            }
        }

    }

    @Override
    protected boolean enable(Node[] nodes) {

        if (IReportManager.getInstance().getActiveReport() == null) return false;
        
        for (int i=0; i<nodes.length; ++i)
        {
            if (nodes[i].getLookup().lookup(JRTemplateReference.class) == null) return false;
        }
        return nodes.length > 0;

    }

    @Override
    public String getName() {
        return I18n.getString("AddTemplateReferenceToReportAction.addTemplateReferenceToReport");
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
