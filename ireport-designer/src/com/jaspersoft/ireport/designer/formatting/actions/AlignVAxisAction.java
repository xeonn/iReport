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
public class AlignVAxisAction extends AbstractFormattingToolAction {

    
    public AlignVAxisAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/center_v.png";
    }

    
    @Override
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        JRDesignElement masterElement = elements.get(0);
        int axis = masterElement.getX()+masterElement.getWidth()/2;
        for (JRDesignElement element : elements)
        {
            Rectangle oldBounds = getElementBounds(element);
            
            // 1. Find the parent...
            element.setX( axis - (element.getWidth()/2) );
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.alignVerticalAxes");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
