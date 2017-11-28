package com.jaspersoft.ireport.components.list;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.utils.Misc;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;
import net.sf.jasperreports.components.list.ListComponent;
import net.sf.jasperreports.components.list.StandardListComponent;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDatasetRun;

public final class EditDatasetRunAction extends NodeAction {

        
    public String getName() {
        return I18n.getString("EditDatasetRunAction.name");
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

        ElementNode node = (ElementNode)activatedNodes[0];
        if (node.getElement() instanceof JRDesignComponentElement &&
            ((JRDesignComponentElement)node.getElement()).getComponent() instanceof StandardListComponent)
        {
            ListDatasetRunPanel panel = new ListDatasetRunPanel();
            panel.setJasperDesign( node.getJasperDesign() );
            StandardListComponent component = (StandardListComponent)((JRDesignComponentElement)node.getElement()).getComponent();
            panel.setDatasetRun( (JRDesignDatasetRun)component.getDatasetRun()   );

            if (panel.showDialog(Misc.getMainFrame(), true) == JOptionPane.OK_OPTION)
            {
                component.setDatasetRun( panel.getDatasetRun() );
                IReportManager.getInstance().notifyReportChange();
            }
        }
    }

    private String getExpressionClassName(JRDesignTextField element)
    {
        if (element.getExpression() == null) return "java.lang.String";
        if (element.getExpression().getValueClassName() == null) return "java.lang.String";
        return element.getExpression().getValueClassName();
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if (!(activatedNodes[0] instanceof ElementNode)) return false;
        ElementNode node = (ElementNode)activatedNodes[0];
        if (node.getElement() instanceof JRDesignComponentElement &&
            ((JRDesignComponentElement)node.getElement()).getComponent() instanceof ListComponent)
        {
            return true;
        }
        return false;
    }
}