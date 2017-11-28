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
public class EqualsVSpaceAction extends AbstractFormattingToolAction {

    
    public EqualsVSpaceAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_add_vspace.png";
    }

    
    @Override
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        
        List<JRDesignElement> sortedElements = sortYX( elements );
        
        
        int gap = 0;
        int usedSpace = 0;
        int minY = sortedElements.get(0).getY();
        int maxY = minY + sortedElements.get(0).getHeight();
        for (JRDesignElement element : sortedElements)
        {
            if (minY > element.getY()) minY = element.getY();
            if (maxY < element.getY()+element.getHeight()) maxY = element.getY()+element.getHeight();
            usedSpace += element.getHeight();
        }
        
        gap = (maxY - minY - usedSpace)/(elements.size()-1);
        
        int actualY = minY;
        
        for (int i=0; i<sortedElements.size(); ++i)
        {
            JRDesignElement element = sortedElements.get(i);
            if (i == 0) {
                actualY = element.getY() + element.getHeight() + gap;
                continue;
            }
            
            Rectangle oldBounds = getElementBounds(element);
            if (i == sortedElements.size() - 1)
            {
                // Trick to avoid calculations errors.
                element.setY( maxY - element.getHeight() );
            }
            else
            {
                element.setY( actualY );
            }
            actualY = element.getY() + element.getHeight() + gap;
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.equalVSpace");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
