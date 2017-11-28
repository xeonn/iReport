/*
 * NewReportElementPaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.30.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions;

import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
@SuppressWarnings("unchecked")
public class CreateTextFieldFromFieldAction extends CreateTextFieldAction {

    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );
        
        JRDesignField field = (JRDesignField)getPaletteItem().getData();

        ((JRDesignExpression)element.getExpression()).setText("$F{"+ field.getName() + "}");
        setMatchingClassExpression(
            ((JRDesignExpression)element.getExpression()), 
            field.getValueClassName(), 
            true
            );
        
        return element;
    }

}
