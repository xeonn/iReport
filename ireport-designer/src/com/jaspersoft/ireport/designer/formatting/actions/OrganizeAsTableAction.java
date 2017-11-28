/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
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
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
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


        elements = this.sortXY(elements);
        
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
        return I18n.getString("formatting.tools.organizeAsTable");
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem item = super.getPopupPresenter();
        item.setIcon(getIcon());
        return item;
    }

    

}
