/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import java.awt.event.MouseEvent;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class BandSelectionAction extends WidgetAction.Adapter {

    public WidgetAction.State mousePressed(Widget widget,   WidgetAction.WidgetMouseEvent event)
    {
        
        if (event.getButton() == MouseEvent.BUTTON1 && widget instanceof ReportObjectScene)
        {
            // find the correct band...
            JasperDesign jd = ((ReportObjectScene)widget).getJasperDesign();
            if (jd != null)
            {
                    JRBand band = ModelUtils.getBandAt( jd , event.getPoint());
                    // If the band is null, the document root is selected.
                    IReportManager.getInstance().setSelectedObject(band);
            }
        }
        
        return WidgetAction.State.REJECTED; // let someone use it...
    }
    
}
