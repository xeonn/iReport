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
public class SameWidthMinAction extends AbstractFormattingToolAction {

    
    public SameWidthMinAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_same_hsize_min.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        int width = elements.get(0).getWidth();
        boolean aggregate = false;
        
        // Find the smallest one...
        for (int i=1; i<elements.size(); ++i)
        {
            if (width > elements.get(i).getWidth())
            {
                width = elements.get(i).getWidth();
            }
        }
        
        for (int i=0; i<elements.size(); ++i)
        {
            JRDesignElement element = elements.get(i);
            Rectangle oldBounds = getElementBounds(element);
            element.setWidth( width );
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return "Same Width (Min)";
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
