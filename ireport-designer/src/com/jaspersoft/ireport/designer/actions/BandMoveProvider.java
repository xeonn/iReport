/*
 * BandMoveProvider.java
 * 
 * Created on Aug 28, 2007, 2:21:08 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.widgets.*;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Point;
import java.util.List;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.fill.JRFillBand;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class BandMoveProvider implements MoveProvider {

    int startY = 0;
    boolean reversOrder = false;
    
    public BandMoveProvider()
    {
        this(false);
    }
    
    public BandMoveProvider( boolean reversOrder)
    {
        this.reversOrder =  reversOrder;
    }
    
    public void movementStarted(Widget w) {
        
        startY = w.getPreferredLocation().y;
        w.setForeground(ReportObjectScene.EDITING_DESIGN_LINE_COLOR);
    }

    public void movementFinished(Widget w) {
        // we have to update the whole visual things...
        w.setForeground(ReportObjectScene.DESIGN_LINE_COLOR);
        
        ReportObjectScene scene = (ReportObjectScene)w.getScene();
        JRBand b = ((BandSeparatorWidget)w).getBand();
        
        // If it is not reversOrder, find the first band that covers this band.
        // This makes sense only when this band is 0 hight
        if (!reversOrder && b.getHeight() == 0)
        {
            // Look for the right band...
             List<JRBand> bands = ModelUtils.getBands(scene.getJasperDesign());
             JRBand rightBand = bands.get(0);
             for (JRBand tmpBand : bands)
             {
                 if (tmpBand == b) break;
                 if (tmpBand.getHeight() == 0) continue;
                 rightBand = tmpBand;
             }
             b = rightBand;
        }
        
        int delta = w.getPreferredLocation().y - startY;

        // Update the main page...
        int originalHight = b.getHeight();
        int newValue = b.getHeight() + delta;
        ((JRDesignBand)b).setHeight( newValue );
                
        ObjectPropertyUndoableEdit edit = new ObjectPropertyUndoableEdit(
                b, "Height", Integer.TYPE, originalHight,  newValue);
        IReportManager.getInstance().addUndoableEdit(edit);
        
        ((PageWidget)scene.getPageLayer().getChildren().get(0)).updateBounds();
        
        // Update band separators widgets...
        List<Widget> list = scene.getBandSeparatorsLayer().getChildren();
        for (Widget separatorWidget : list)
        {
            ((BandSeparatorWidget)separatorWidget).updateBounds();
        }
        list = scene.getElementsLayer().getChildren();
        for (Widget elementWidget : list)
        {
            ((JRDesignElementWidget)elementWidget).updateBounds();
            ((JRDesignElementWidget)elementWidget).getSelectionWidget().updateBounds();
        }
        
        ((ReportObjectScene)w.getScene()).validate();
        
    }

    public Point getOriginalLocation(Widget widget) {
        return widget.getPreferredLocation();
    }

    public void setNewLocation(Widget widget, Point newLocation) {
        // JRBand b = ((BandSeparatorWidget)w).getBand();
        //((JRDesignBand)b).setHeight( b.getHeight() + (newLocation.y - originalLocation.y));
        // Updatre
        //((ReportObjectScene)w.getScene())
        widget.setPreferredLocation(newLocation);
    }

}
