/*
 * NewReportElementPaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.30.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions;

import com.jaspersoft.ireport.designer.charts.ChartSelectionJDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class CreateChartAction extends CreateReportElementAction 
{

    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignElement element = null;

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
        }

        return element;
    }
    
}
