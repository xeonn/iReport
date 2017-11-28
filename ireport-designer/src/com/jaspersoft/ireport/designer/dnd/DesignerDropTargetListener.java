/*
 * DesignerDropTargetListener.java
 * 
 * Created on 12-nov-2007, 19.07.58
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.dnd;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.outline.OutlineTopComponent;
import com.jaspersoft.ireport.designer.palette.PaletteItem;
import com.jaspersoft.ireport.designer.palette.PaletteUtils;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author gtoffoli
 */
class DesignerDropTargetListener implements DropTargetListener {

    public DesignerDropTargetListener(ReportObjectScene scene) {
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        if (!acceptDataFlavor( dtde.getCurrentDataFlavors()) )
        {
            dtde.rejectDrag();
            return;
        }
        
        if (!isInDocument(dtde.getLocation())){
            dtde.rejectDrag();
            return;
        }
        
        dtde.acceptDrag( DnDConstants.ACTION_COPY_OR_MOVE );
        
    }

    public void dragOver(DropTargetDragEvent dtde) {
        // Check if we can accept that stuff...
        
        if (!acceptDataFlavor( dtde.getCurrentDataFlavors()) )
        {
            dtde.rejectDrag();
            return;
        }
        
        if (!isInDocument(dtde.getLocation())){
            dtde.rejectDrag();
            return;
        }
        
        
        PaletteItem paletteItem = null;
        try {
                paletteItem = (PaletteItem) dtde.getTransferable().getTransferData(PaletteUtils.PALETTE_ITEM_DATA_FLAVOR);
        
                // Check if it is in...
                dtde.acceptDrag( DnDConstants.ACTION_COPY_OR_MOVE );
                
        } catch (Exception ex) {
                ex.printStackTrace();
                dtde.rejectDrag();
        }
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent dtde) {
        
        PaletteItem paletteItem = null;
        try {
                paletteItem = (PaletteItem) dtde.getTransferable().getTransferData(PaletteUtils.PALETTE_ITEM_DATA_FLAVOR);
        
                dtde.acceptDrop( DnDConstants.ACTION_COPY_OR_MOVE );
                
                paletteItem.drop(dtde);
                
        } catch (IOException ex) {
                ex.printStackTrace();
        } catch (UnsupportedFlavorException ex) {
                ex.printStackTrace();
        }
        
    }

    private void doDragOver( DropTargetDragEvent dtde ) {
        dtde.acceptDrag( DnDConstants.ACTION_COPY_OR_MOVE );
    }
    
    private void doDrop( DropTargetDropEvent dtde ) {
        dtde.acceptDrop( DnDConstants.ACTION_COPY_OR_MOVE );
    }
    
    private boolean acceptDataFlavor(DataFlavor[] falvors)
    {
        for (int i = 0; i < falvors.length; i++) {
            DataFlavor dataFlavor = falvors[i];
            if (dataFlavor.match( PaletteUtils.PALETTE_ITEM_DATA_FLAVOR ))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isInDocument(Point location)
    {
        Scene scene = OutlineTopComponent.getDefault().getCurrentJrxmlVisualView().getReportDesignerPanel().getScene();
        Point p = scene.convertViewToScene(location);
        return ModelUtils.getBandAt(IReportManager.getInstance().getActiveReport(), p) != null;
    }
}
