/*
 * NewReportElementPaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.30.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.undo.AddElementUndoableEdit;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class CreateReportElementAction extends PaletteItemAction 
{

    public void drop(DropTargetDropEvent dtde) {
        
        JRDesignElement element = createReportElement(getJasperDesign());
        
        if (element == null) return;
        // Find location...
        if (getScene() instanceof ReportObjectScene)
        {
            Point p = getScene().convertViewToScene( dtde.getLocation() );
            p.x -= 10;
            p.y -= 10;
            // find the band...
            JRDesignBand b = ModelUtils.getBandAt(getJasperDesign(), p);
            
            if (b != null)
            {
                element.setX( p.x - getJasperDesign().getLeftMargin());
                element.setY( p.y - ModelUtils.getBandLocation(b, getJasperDesign()));
                b.addElement(element);
                
                AddElementUndoableEdit edit = new AddElementUndoableEdit(element,b);
                IReportManager.getInstance().addUndoableEdit(edit);
            }
        }
    }
    
    public abstract JRDesignElement createReportElement(JasperDesign jd);
    
}
