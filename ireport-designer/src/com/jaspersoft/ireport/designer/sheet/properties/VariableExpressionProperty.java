/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JRDesignVariable;


/**
 * @author gtoffoli
 */
public final class VariableExpressionProperty extends ExpressionProperty 
{
    private final JRDesignVariable variable;
    private final JRDesignDataset dataset;

    public VariableExpressionProperty(JRDesignVariable variable, JRDesignDataset dataset)
    {
        super(variable, dataset);
        this.variable = variable;
        this.dataset = dataset;
    }

    @Override
    public String getName()
    {
        return JRDesignVariable.PROPERTY_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("VariableExpressionProperty.Property.VariableExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("VariableExpressionProperty.Property.VariableExpression");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Object.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)variable.getExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        // This avoid incompatibilities with the
        // variable...
        if (expression != null)
        {
            if (variable.getCalculation() == JRDesignVariable.CALCULATION_COUNT ||
                variable.getCalculation() == JRDesignVariable.CALCULATION_DISTINCT_COUNT)
            {
                expression.setValueClassName("java.lang.Object");
            }
            else
            {
                expression.setValueClassName(variable.getValueClassName());
            }
        }
        variable.setExpression(expression);
    }

    @Override
    public boolean canWrite()
    {
        return !variable.isSystemDefined();
    }

}
