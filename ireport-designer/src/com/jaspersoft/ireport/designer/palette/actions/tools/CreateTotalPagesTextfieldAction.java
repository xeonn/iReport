/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions.tools;

import com.jaspersoft.ireport.designer.palette.actions.*;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class CreateTotalPagesTextfieldAction extends CreateTextFieldAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jd)
    {
         JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );

        ((JRDesignExpression)element.getExpression()).setText("$V{PAGE_NUMBER}");
        ((JRDesignExpression)element.getExpression()).setValueClassName("java.lang.Integer");

        element.setEvaluationTime( JRExpression.EVALUATION_TIME_REPORT);
        setMatchingClassExpression(
            ((JRDesignExpression)element.getExpression()),
            "java.lang.Integer",
            true
            );

        return element;
    }
}
