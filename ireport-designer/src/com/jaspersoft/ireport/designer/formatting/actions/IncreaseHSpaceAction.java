/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.formatting.actions;

import com.jaspersoft.ireport.locale.I18n;
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
public class IncreaseHSpaceAction extends AbstractFormattingToolAction {

    
    public IncreaseHSpaceAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_add_hspace_plus.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        
        List<JRDesignElement> sortedElements = sortXY( elements );
        
        for (int i=1; i<sortedElements.size(); ++i)
        {
            JRDesignElement element = sortedElements.get(i);
            Rectangle oldBounds = getElementBounds(element);
            element.setX( element.getX() + 5*i);
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.increaseHSpace");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
