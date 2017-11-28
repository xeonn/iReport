/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import java.awt.Rectangle;
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
public class OrganizeAsTableAction extends AbstractFormattingToolAction {

    
    public OrganizeAsTableAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/organize.png";
    }

    
    @Override
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);

        IReportManager.getInstance().setForceAggregateUndo(true);
        SystemAction.get(AlignMarginTopAction.class).performAction(nodes);
        
        int currentX = 0;
        for (JRDesignElement element : elements)
        {
            // 1. Find the parent...
            Rectangle oldBounds = getElementBounds(element);
            element.setX( currentX  );
            currentX += element.getWidth();
            addTransformationUndo( element, oldBounds, true);
        }
        
        SystemAction.get(SameHeightMinAction.class).performAction(nodes);
        SystemAction.get(IncreaseHSpaceAction.class).performAction(nodes);
        IReportManager.getInstance().setForceAggregateUndo(false);
    }

    @Override
    public String getName() {
        return "Organize As Table";
    }

}
