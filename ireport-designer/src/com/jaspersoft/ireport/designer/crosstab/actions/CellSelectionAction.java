/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.crosstab.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.outline.OutlineTopComponent;
import java.awt.event.MouseEvent;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
public class CellSelectionAction extends WidgetAction.Adapter {

    public WidgetAction.State mousePressed(Widget widget,   WidgetAction.WidgetMouseEvent event)
    {
        
        if (event.getButton() == MouseEvent.BUTTON1 && widget instanceof CrosstabObjectScene)
        {
            // find the correct band...
            JasperDesign jd = ((CrosstabObjectScene)widget).getJasperDesign();
            JRDesignCrosstab crosstab = ((CrosstabObjectScene)widget).getDesignCrosstab();
            if (jd != null && crosstab != null)
            {
                    JRDesignCellContents cellContent = ModelUtils.getCellAt(crosstab, event.getPoint());
                    // If the cell is null, the document root is selected.
                    Node node = IReportManager.getInstance().findNodeOf(cellContent == null ? crosstab : cellContent, OutlineTopComponent.getDefault().getExplorerManager().getRootContext());
                try {
                    IReportManager.getInstance().getActiveVisualView().getExplorerManager().setSelectedNodes(new Node[]{node});
                    //IReportManager.getInstance().setSelectedObject(cellContent == null ? crosstab : cellContent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                    //IReportManager.getInstance().setSelectedObject(cellContent == null ? crosstab : cellContent);
            }
        }
        
        return WidgetAction.State.REJECTED; // let someone use it...
    }
    
}
