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
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Mutex;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class CreateReportElementAction extends PaletteItemAction 
{

    private static JRDesignFrame findTopMostFrameAt(ReportObjectScene theScene, Point p) {

         LayerWidget layer = theScene.getElementsLayer();
         List<Widget> widgets = layer.getChildren();

         // Use revers order to find the top most...
         for (int i= widgets.size()-1; i>=0; --i)
         {
             Widget w = widgets.get(i);
             Point p2 = w.convertSceneToLocal(p);
             if (w.isHitAt(p2))
             {
                 if (w instanceof JRDesignElementWidget)
                 {
                     JRDesignElement de =((JRDesignElementWidget)w).getElement();
                     if (de instanceof JRDesignFrame)
                     {
                         return (JRDesignFrame)de;
                     }
                 }
             }
         }
         return null;
    }

    public void drop(DropTargetDropEvent dtde) {
        
        JRDesignElement element = createReportElement(getJasperDesign());
        
        if (element == null) return;
        // Find location...
        dropElementAt(getScene(), getJasperDesign(), element, dtde.getLocation());
    }
    
    public abstract JRDesignElement createReportElement(JasperDesign jd);

    // The main idea is to optimize the space of each element...
    
    public static void dropElementAt(Scene theScene, JasperDesign jasperDesign, JRDesignElement element, Point location)
    {
        if (theScene instanceof ReportObjectScene)
        {
            Point p = theScene.convertViewToScene( location );
            //p.x -= 10; removing a location transformation
            //p.y -= 10;
            // find the band...
            JRDesignBand b = ModelUtils.getBandAt(jasperDesign, p);
            int yLocation = ModelUtils.getBandLocation(b, jasperDesign);
            Point pLocationInBand = new Point(p.x - jasperDesign.getLeftMargin(),
                                              p.y - yLocation);
            if (b != null)
            {
                JRDesignFrame frame = findTopMostFrameAt((ReportObjectScene)theScene, p);

                if (frame != null)
                {
                    frame.addElement(element);
                    Point parentLocation = ModelUtils.getParentLocation(jasperDesign, element);
                    element.setX( p.x - parentLocation.x);
                    element.setY( p.y - parentLocation.y);
                }
                else
                {
                    element.setX(pLocationInBand.x);
                    element.setY(pLocationInBand.y);
                    b.addElement(element);
                }
                AddElementUndoableEdit edit = new AddElementUndoableEdit(element,b);
                IReportManager.getInstance().addUndoableEdit(edit);
            }
        }
        else if (theScene instanceof CrosstabObjectScene)
        {
            Point p = theScene.convertViewToScene( location );
            //p.x -= 10; removing a location transformation
            //p.y -= 10;
            
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
            
             JRDesignCrosstab crosstab = ((CrosstabObjectScene)theScene).getDesignCrosstab();
             final JRDesignCellContents cell = ModelUtils.getCellAt(crosstab, p, true);
             if (cell != null)
             {
                 Point base = ModelUtils.getCellLocation(crosstab, cell);
                 element.setX( p.x - base.x );
                 element.setY( p.y - base.y );
                 
                 String styleName = "Crosstab Data Text";
                 if (jasperDesign.getStylesMap().containsKey(styleName))
                 {
                     element.setStyle((JRStyle)jasperDesign.getStylesMap().get(styleName));
                 }
                 cell.addElement(element);
                 
                 AddElementUndoableEdit edit = new AddElementUndoableEdit(element,cell);
                 IReportManager.getInstance().addUndoableEdit(edit);
                 
             }
        }
    }
    
}
