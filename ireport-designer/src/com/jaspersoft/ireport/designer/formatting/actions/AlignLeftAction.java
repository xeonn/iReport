/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.Action;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
public class AlignLeftAction extends AbstractFormattingToolAction {

    
    public AlignLeftAction()
    {
        super();
        putValue(Action.NAME, getName());
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/align_left.png";
    }

    
    @Override
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        JRDesignElement masterElement = elements.get(0);
        for (JRDesignElement element : elements)
        {
            Rectangle oldBounds = getElementBounds(element);
            
            // 1. Find the parent...
            Rectangle rect = ModelUtils.getParentBounds(jd, element);
            element.setX( masterElement.getX() );
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.alignLeft");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
