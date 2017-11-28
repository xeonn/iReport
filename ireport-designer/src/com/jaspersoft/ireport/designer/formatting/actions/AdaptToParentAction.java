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
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
public class AdaptToParentAction extends AbstractFormattingToolAction {

    
    public AdaptToParentAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_maximise.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        boolean aggregate = false;
        for (JRDesignElement element : elements)
        {
            Rectangle oldBounds = getElementBounds(element);
            // 1. Find the parent...
            Rectangle rect = ModelUtils.getParentBounds(jd, element);
            
            element.setX(0);
            element.setY(0);
            element.setWidth( rect.width);
            element.setHeight( rect.height);
            
        
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.adaptToParent");
    }

}
