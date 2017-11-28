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
import com.jaspersoft.ireport.designer.charts.ChartSelectionJDialog;
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
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
    
}
