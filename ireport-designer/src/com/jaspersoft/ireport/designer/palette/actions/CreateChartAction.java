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
import com.jaspersoft.ireport.designer.charts.ChartSelectionJDialog;
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Mutex;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class CreateChartAction extends CreateReportElementAction 
{

    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignElement element = null;

        if (getScene() instanceof CrosstabObjectScene)
        {
            Runnable r = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(), "You can not use a chart inside a crosstab","Error", JOptionPane.WARNING_MESSAGE);
                }
            };
            
            Mutex.EVENT.readAccess(r); 
            return null;
        }
        
        Object pWin = Misc.getMainWindow();
        ChartSelectionJDialog dialog = null;
        if (pWin instanceof Dialog) dialog = new ChartSelectionJDialog((Dialog)pWin, true);
        else dialog = new ChartSelectionJDialog((Frame)pWin, true);

        dialog.setJasperDesign(jd);
        dialog.setVisible(true);
        
        if (dialog.getDialogResult() == JOptionPane.OK_OPTION)
        {
            element = dialog.getChart();
            element.setWidth(200);
            element.setHeight(100);
            String s = IReportManager.getPreferences().get("DefaultTheme","");
            if (s.length() > 0)
            {
                if (getJasperDesign().getPropertiesMap().getProperty("net.sf.jasperreports.chart.theme") == null)
                {
                    getJasperDesign().getPropertiesMap().setProperty("net.sf.jasperreports.chart.theme", s);
                }
            }
        }

        return element;
    }


    @Override
    public void drop(DropTargetDropEvent dtde) {

        JRDesignChart element = (JRDesignChart)createReportElement(getJasperDesign());

        if (element == null) return;
        // Find location...
        dropFieldElementAt(getScene(), getJasperDesign(), element, dtde.getLocation());
    }

    public void dropFieldElementAt(Scene theScene, JasperDesign jasperDesign, JRDesignChart element, Point location)
    {
        if (theScene instanceof ReportObjectScene)
        {
            Point p = theScene.convertViewToScene( location );

            // find the band...
            JRDesignBand b = ModelUtils.getBandAt(jasperDesign, p);
            int yLocation = ModelUtils.getBandLocation(b, jasperDesign);
            Point pLocationInBand = new Point(p.x - jasperDesign.getLeftMargin(),
                                              p.y - yLocation);
            if (b != null)
            {

                // if the band is not a detail, propose to aggregate the value...
                if (b.getOrigin().getBandType() == JROrigin.TITLE)
                {
                    element.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
                }
            }

        }

        super.dropElementAt(theScene, jasperDesign, element, location);
    }
    
}
