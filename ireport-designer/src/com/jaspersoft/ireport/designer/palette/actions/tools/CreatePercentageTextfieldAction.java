/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions.tools;

import com.jaspersoft.ireport.designer.palette.actions.*;
import com.jaspersoft.ireport.designer.utils.Misc;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.util.Exceptions;

/**
 *
 * @author gtoffoli
 */
public class CreatePercentageTextfieldAction extends CreateTextFieldAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jd)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jd );

        FieldPercentageDialog dialog = new FieldPercentageDialog(Misc.getMainFrame());
        dialog.setJasperDesign(jd);

        dialog.setVisible(true);
        if (dialog.getDialogResult() == JOptionPane.OK_OPTION)
        {
            JRField f = dialog.getSelectedField();
            Byte resetType = dialog.getSelectedResetType();
            JRGroup group = null;
            if (resetType == JRVariable.RESET_TYPE_GROUP)
            {
                group = dialog.getSelectedGroup();
            }

            // Let's create the variable...

            JRDesignVariable variable = new JRDesignVariable();
            for (int i = 0; ; ++i)
            {
                String vname = f.getName() + "_SUM";
                if (i > 0) vname += "_" + i;

                if (jd.getVariablesMap().containsKey(vname))
                {
                    continue;
                }

                variable.setName(vname);
                break;
            }

            variable.setExpression( Misc.createExpression( f.getValueClassName(), "$F{" + f.getName() + "}" ));
            variable.setValueClassName( f.getValueClassName() );
            variable.setCalculation( JRVariable.CALCULATION_SUM);
            variable.setResetType(resetType);
            if (resetType == JRVariable.RESET_TYPE_GROUP)
            {
                variable.setResetGroup(group);
            }
            try {
                jd.addVariable(variable);
            } catch (JRException ex) {
                ex.printStackTrace();
            }

            ((JRDesignExpression)element.getExpression()).setText("new Double( $F{" + f.getName() + "}.doubleValue() / $V{"+ variable.getName() + "}.doubleValue() )");
            ((JRDesignExpression)element.getExpression()).setValueClassName("java.lang.Double");

            element.setPattern( "#,##0.00%" );
            setMatchingClassExpression(
            ((JRDesignExpression)element.getExpression()),
            ((JRDesignExpression)element.getExpression()).getValueClassName(),true);

            element.setEvaluationTime(JRExpression.EVALUATION_TIME_AUTO);

            return element;
        }

        return null;
    }
}
