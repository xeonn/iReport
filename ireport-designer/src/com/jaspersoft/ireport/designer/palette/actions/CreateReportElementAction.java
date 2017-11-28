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
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.palette.PaletteItemAction;
import com.jaspersoft.ireport.designer.undo.AddElementUndoableEdit;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.animator.AnimatorEvent;
import org.netbeans.api.visual.animator.AnimatorListener;
import org.netbeans.api.visual.animator.SceneAnimator;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Mutex;

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
        else if (getScene() instanceof CrosstabObjectScene)
        {
            Point p = getScene().convertViewToScene( dtde.getLocation() );
            p.x -= 10;
            p.y -= 10;
            
            // This if should be always false since the check is done
            // in the specific implementations of createReportElement...
            if (element instanceof JRCrosstab ||
                element instanceof JRChart ||
                element instanceof JRBreak ||
                element instanceof JRSubreport)
            {
                Runnable r = new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(Misc.getMainFrame(), "You can use this kind of element inside a crosstab.","Error", JOptionPane.WARNING_MESSAGE);
                    }
                };

                Mutex.EVENT.readAccess(r); 
                return;
             }
            
             JRDesignCrosstab crosstab = ((CrosstabObjectScene)getScene()).getDesignCrosstab();
             final JRDesignCellContents cell = ModelUtils.getCellAt(crosstab, p, true);
             if (cell != null)
             {
                 Point base = ModelUtils.getCellLocation(crosstab, cell);
                 element.setX( p.x - base.x );
                 element.setY( p.y - base.y );
                 
                 String styleName = "Crosstab Data Text";
                 if (getJasperDesign().getStylesMap().containsKey(styleName))
                 {
                     element.setStyle((JRStyle)getJasperDesign().getStylesMap().get(styleName));
                 }
                 cell.addElement(element);
                 
                 AddElementUndoableEdit edit = new AddElementUndoableEdit(element,cell);
                 IReportManager.getInstance().addUndoableEdit(edit);
                 
             }
        }
    }
    
    public abstract JRDesignElement createReportElement(JasperDesign jd);

    // The main idea is to optimize the space of each element...
    
    
}
