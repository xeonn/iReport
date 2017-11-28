/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import java.awt.Rectangle;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
public class DecreaseVSpaceAction extends AbstractFormattingToolAction {

    
    public DecreaseVSpaceAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_add_vspace_min.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        
        List<JRDesignElement> sortedElements = sortYX( elements );
        
        for (int i=1; i<sortedElements.size(); ++i)
        {
            JRDesignElement element = sortedElements.get(i);
            Rectangle oldBounds = getElementBounds(element);
            if (  element.getY() - 5*i > 0)
            {
                element.setY( element.getY() - 5*i);
            }
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return "Decrease Vert. Space";
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
