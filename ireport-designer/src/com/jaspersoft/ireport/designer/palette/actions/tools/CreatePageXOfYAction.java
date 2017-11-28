/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions.tools;

import com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldAction;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Point;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author gtoffoli
 */
public class CreatePageXOfYAction extends CreateTextFieldAction {

    @Override
    public JRDesignElement[] createReportElements(JasperDesign jd) {
        JRDesignTextField[] elements = new JRDesignTextField[2];

        elements[0] = (JRDesignTextField)super.createReportElement( jd );

        ((JRDesignExpression)elements[0].getExpression()).setText("\"" + I18n.getString("Page_X_Of_Y.page", "\"+$V{PAGE_NUMBER}+\"") + "\"");
        ((JRDesignExpression)elements[0].getExpression()).setValueClassName("java.lang.String");

        elements[0].setHorizontalAlignment( JRDesignTextField.HORIZONTAL_ALIGN_RIGHT);
        setMatchingClassExpression(
            ((JRDesignExpression)elements[0].getExpression()),
            "java.lang.String",
            true
            );


        elements[1] = (JRDesignTextField)super.createReportElement( jd );

        ((JRDesignExpression)elements[1].getExpression()).setText("\" \" + $V{PAGE_NUMBER}");
        ((JRDesignExpression)elements[1].getExpression()).setValueClassName("java.lang.String");

        setMatchingClassExpression(
            ((JRDesignExpression)elements[1].getExpression()),
            "java.lang.String",
            true
            );

        elements[1].setEvaluationTime( JRExpression.EVALUATION_TIME_REPORT);

        return elements;
    }

    @Override
    public void adjustElement(JRDesignElement[] elements, int index, Scene theScene, JasperDesign jasperDesign, Object parent, Point dropLocation) {

        if (index == 0)
        {
            elements[0].setWidth(80);
        }
        if (index == 1)
        {
            elements[1].setWidth(40);
            elements[1].setX( elements[0].getX() + elements[0].getWidth());
        }


    }





}
