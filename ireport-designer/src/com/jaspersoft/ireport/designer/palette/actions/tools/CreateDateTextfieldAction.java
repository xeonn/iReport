/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions.tools;

import com.jaspersoft.ireport.designer.palette.actions.*;
import com.jaspersoft.ireport.designer.tools.FieldPatternDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class CreateDateTextfieldAction extends CreateTextFieldAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );

        ((JRDesignExpression)element.getExpression()).setText("new java.util.Date()");
        ((JRDesignExpression)element.getExpression()).setValueClassName("java.util.Date");

        FieldPatternDialog dialog = new FieldPatternDialog(Misc.getMainFrame(),true);
        dialog.setOnlyDate(true);

        dialog.setVisible(true);
        if (dialog.getDialogResult() == JOptionPane.OK_OPTION)
        {
            element.setPattern( dialog.getPattern() );
            setMatchingClassExpression(
            ((JRDesignExpression)element.getExpression()),
            ((JRDesignExpression)element.getExpression()).getValueClassName(),true);

            return element;
        }

        return null;
    }
}
