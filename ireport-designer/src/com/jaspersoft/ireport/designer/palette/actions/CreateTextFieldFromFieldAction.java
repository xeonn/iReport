/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.designer.palette.actions;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.tools.AggregationFunctionDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Exceptions;

/**
 *
 * @author gtoffoli
 */
@SuppressWarnings("unchecked")
public class CreateTextFieldFromFieldAction extends CreateTextFieldAction {

    @Override
    public JRDesignElement createReportElement(JasperDesign jasperDesign)
    {
        JRDesignTextField element = (JRDesignTextField)super.createReportElement( jasperDesign );

        JRDesignField field = (JRDesignField)getPaletteItem().getData();

        ((JRDesignExpression)element.getExpression()).setText("$F{"+ field.getName() + "}");
        setMatchingClassExpression(
            ((JRDesignExpression)element.getExpression()), 
            field.getValueClassName(), 
            true
            );

        return element;
    }

    public boolean isNumeric(String type)
    {
        return type!=null && (type.equals("java.lang.Byte") ||
            type.equals("java.lang.Short") ||
            type.equals("java.lang.Integer") ||
            type.equals("java.lang.Long") ||
            type.equals("java.lang.Float") ||
            type.equals("java.lang.Double") ||
            type.equals("java.lang.Number") ||
            type.equals("java.math.BigDecimal"));
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {

        JRDesignTextField element = (JRDesignTextField)createReportElement(getJasperDesign());

        if (element == null) return;
        // Find location...
        dropFieldElementAt(getScene(), getJasperDesign(), element, dtde.getLocation());
    }

    public void dropFieldElementAt(Scene theScene, JasperDesign jasperDesign, JRDesignTextField element, Point location)
    {
        JRDesignField newField = (JRDesignField)getPaletteItem().getData();

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
                if (b.getOrigin().getBandType() == JROrigin.GROUP_FOOTER ||
                    b.getOrigin().getBandType() == JROrigin.GROUP_HEADER ||
                    b.getOrigin().getBandType() == JROrigin.COLUMN_FOOTER ||
                    b.getOrigin().getBandType() == JROrigin.COLUMN_HEADER ||
                    b.getOrigin().getBandType() == JROrigin.PAGE_FOOTER ||
                    b.getOrigin().getBandType() == JROrigin.PAGE_HEADER ||
                    b.getOrigin().getBandType() == JROrigin.TITLE ||
                    b.getOrigin().getBandType() == JROrigin.SUMMARY)
                {
                    AggregationFunctionDialog dialog = new AggregationFunctionDialog(Misc.getMainFrame(), true);
                    dialog.setFunctionSet( ( isNumeric( newField.getValueClassName())) ? AggregationFunctionDialog.NUMERIC_SET : AggregationFunctionDialog.STRING_SET );

                    dialog.setDefaultSelection(AggregationFunctionDialog.DEFAULT_AS_VALUE);
                    dialog.setVisible(true);

                    Byte aggFunc = dialog.getSelectedFunction();
                    if (aggFunc != null)
                    {
                        // create the variable...
                        JRDesignVariable var = new JRDesignVariable();
                        var.setName(newField.getName());
                        var.setCalculation(aggFunc);
                        var.setExpression(Misc.createExpression( newField.getValueClassName() , "$F{" + newField.getName() + "}"));

                        if (aggFunc.equals(JRVariable.CALCULATION_COUNT) ||
                            aggFunc.equals(JRVariable.CALCULATION_DISTINCT_COUNT))
                        {
                            var.setValueClassName("java.lang.Integer");
                            element.setPattern(null);
                        }
                        else
                        {
                            var.setValueClassName(newField.getValueClassName());
                        }

                        if (b.getOrigin().getBandType() == JROrigin.SUMMARY ||
                            b.getOrigin().getBandType() == JROrigin.TITLE)
                        {
                            var.setResetType(JRVariable.RESET_TYPE_REPORT);
                        }
                        else if (b.getOrigin().getBandType() == JROrigin.GROUP_FOOTER ||
                            b.getOrigin().getBandType() == JROrigin.GROUP_HEADER)
                        {
                            var.setResetType(JRVariable.RESET_TYPE_GROUP);
                            var.setResetGroup( (JRGroup) jasperDesign.getGroupsMap().get( b.getOrigin().getGroupName() ) );
                        }
                        else if (b.getOrigin().getBandType() == JROrigin.COLUMN_FOOTER ||
                                 b.getOrigin().getBandType() == JROrigin.COLUMN_HEADER)
                        {
                            var.setResetType(JRVariable.RESET_TYPE_COLUMN);
                        }
                        else if (b.getOrigin().getBandType() == JROrigin.PAGE_HEADER ||
                                 b.getOrigin().getBandType() == JROrigin.PAGE_FOOTER)
                        {
                            var.setResetType(JRVariable.RESET_TYPE_PAGE);
                        }

                        try {
                            int i=1;
                            // Find the first available var...
                            while (jasperDesign.getVariablesMap().containsKey(var.getName()+"_"+i))
                            {
                                ++i;
                            }
                            var.setName(var.getName()+"_"+i);
                            jasperDesign.addVariable(var);
                        } catch (JRException ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        JRDesignExpression exp = new JRDesignExpression();
                        exp.setText("$V{" + var.getName() + "}");
                        element.setExpression(exp);
                        CreateTextFieldFromFieldAction.setMatchingClassExpression(
                        ((JRDesignExpression)element.getExpression()),
                        var.getValueClassName(),
                        true);

                        if (b.getOrigin().getBandType() == JROrigin.GROUP_HEADER)
                        {
                            element.setEvaluationTime(JRExpression.EVALUATION_TIME_GROUP);
                            element.setEvaluationGroup( (JRGroup) jasperDesign.getGroupsMap().get( b.getOrigin().getGroupName() ) );
                        }
                        else if (b.getOrigin().getBandType() == JROrigin.COLUMN_HEADER)
                        {
                            element.setEvaluationTime(JRExpression.EVALUATION_TIME_COLUMN);
                        }
                        else if (b.getOrigin().getBandType() == JROrigin.PAGE_HEADER)
                        {
                            element.setEvaluationTime(JRExpression.EVALUATION_TIME_PAGE);
                        }
                        else if (b.getOrigin().getBandType() == JROrigin.TITLE)
                        {
                            element.setEvaluationTime(JRExpression.EVALUATION_TIME_REPORT);
                        }
                    }
                }
            }
        }

        super.dropElementAt(theScene, jasperDesign, element, location);
    }
}
