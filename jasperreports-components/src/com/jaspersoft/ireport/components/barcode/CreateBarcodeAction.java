/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode;


import com.jaspersoft.ireport.designer.palette.actions.*;
import com.jaspersoft.ireport.designer.utils.Misc;
import net.sf.jasperreports.components.barbecue.StandardBarbecueComponent;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class CreateBarcodeAction extends CreateReportElementAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jd) {

        BarcodeChooserDialog bcd = new BarcodeChooserDialog(Misc.getMainFrame(), true);
        bcd.setVisible(true);

        

        return bcd.getComponent();
    }



    
}
