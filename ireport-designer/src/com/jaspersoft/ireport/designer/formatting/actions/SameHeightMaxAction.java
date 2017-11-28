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
public class SameHeightMaxAction extends AbstractFormattingToolAction {

    
    public SameHeightMaxAction()
    {
        super();
        putValue(Action.NAME, getName());
    }
    
    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/resources/formatting/elem_same_vsize_plus.png";
    }

    
    protected void performAction(Node[] nodes) {
        
        if (nodes.length == 0) return;
        JasperDesign jd = nodes[0].getLookup().lookup(JasperDesign.class);
        if (jd == null) return;
        
        List<JRDesignElement> elements = getSelectedElements(nodes);
    
        int height = elements.get(0).getHeight();
        boolean aggregate = false;
        
        // Find the smallest one...
        for (int i=1; i<elements.size(); ++i)
        {
            if (height < elements.get(i).getHeight())
            {
                height = elements.get(i).getHeight();
            }
        }
        
        for (int i=0; i<elements.size(); ++i)
        {
            JRDesignElement element = elements.get(i);
            Rectangle oldBounds = getElementBounds(element);
            element.setHeight( height );
            aggregate = addTransformationUndo( element, oldBounds, aggregate);
        }
    }

    @Override
    public String getName() {
        return I18n.getString("formatting.tools.sameHeightMax");
    }

    @Override
    public boolean requiresMultipleObjects() {
        return true;
    }


}
