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
public class JoinLeftAction extends AbstractFormattingToolAction {

    
    public JoinLeftAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/joinleft.png";
    }

    
    @Override
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        
        List<JRDesignElement> sortedElements = sortXY( elements );
        
        int actualX = sortedElements.get(0).getX();
        for (JRDesignElement element : sortedElements)
        {
            Rectangle oldBounds = getElementBounds(element);
            element.setX( actualX );
            actualX += element.getWidth();
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.joinLeft");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
