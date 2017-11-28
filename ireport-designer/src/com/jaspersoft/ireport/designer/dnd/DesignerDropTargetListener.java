/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.designer.dnd;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
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
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author gtoffoli
 */
class DesignerDropTargetListener implements DropTargetListener {

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
        Scene scene = OutlineTopComponent.getDefault().getCurrentJrxmlVisualView().getReportDesignerPanel().getActiveScene();
        Point p = scene.convertViewToScene(location);
        if (scene instanceof ReportObjectScene)
        {
             return ModelUtils.getBandAt(IReportManager.getInstance().getActiveReport(), p) != null;
        }
        else if (scene instanceof CrosstabObjectScene)
        {
            JRDesignCrosstab crosstab = ((CrosstabObjectScene)scene).getDesignCrosstab();
            return ModelUtils.getCellAt(crosstab,  p) != null;
        }
       return false;
    }
}
