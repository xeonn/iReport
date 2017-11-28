/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author gtoffoli
 */
public class SameSizeAction extends AbstractFormattingToolAction {

    
    public SameSizeAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_same_size.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);

        IReportManager.getInstance().setForceAggregateUndo(true);
        
        SystemAction.get(SameWidthAction.class).performAction(nodes);
        SystemAction.get(SameHeightAction.class).performAction(nodes);

        IReportManager.getInstance().setForceAggregateUndo(false);
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.sameSize");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
