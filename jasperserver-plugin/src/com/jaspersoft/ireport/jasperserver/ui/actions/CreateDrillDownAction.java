/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.actions;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class CreateDrillDownAction extends PaletteItemAction  {

    @Override
    public void drop(DropTargetDropEvent dtde) {
        
        
        LayerWidget elementsLayer = ((AbstractReportObjectScene)this.getScene()).getElementsLayer();
        
        Point p = getScene().convertViewToScene( dtde.getLocation() );
            
        List<Widget> children = elementsLayer.getChildren();
        for (Widget w : children)
        {
            if (w instanceof JRDesignElementWidget)
            {
                if (w.isHitAt(p))
                {
                    JRDesignElement element = ((JRDesignElementWidget)w).getElement();
                    if (element instanceof JRHyperlink)
                    {
                        // 1. Set the element
                        
                        // 2. Set 
                        
                    }
                }
            }
        }
        
    }

}
