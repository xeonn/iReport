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
public class EqualsHSpaceAction extends AbstractFormattingToolAction {

    
    public EqualsHSpaceAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_add_hspace.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        
        List<JRDesignElement> sortedElements = sortXY( elements );
        
        
        int gap = 0;
        int usedSpace = 0;
        int minX = sortedElements.get(0).getX();
        int maxX = minX + sortedElements.get(0).getWidth();
        for (JRDesignElement element : sortedElements)
        {
            if (minX > element.getX()) minX = element.getX();
            if (maxX < element.getX()+element.getWidth()) maxX = element.getX()+element.getWidth();
            usedSpace += element.getWidth();
        }
        
        gap = (maxX - minX - usedSpace)/(elements.size()-1);
        
        int actualX = minX;
        
        for (int i=0; i<sortedElements.size(); ++i)
        {
            JRDesignElement element = sortedElements.get(i);
            if (i == 0) {
                actualX = element.getX() + element.getWidth() + gap;
                continue;
            }
            
            Rectangle oldBounds = getElementBounds(element);
            if (i == sortedElements.size() - 1)
            {
                // Trick to avoid calculations errors.
                element.setX( maxX - element.getWidth() );
            }
            else
            {
                element.setX( actualX );
            }
            actualX = element.getX() + element.getWidth() + gap;
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.equalHSpace");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
