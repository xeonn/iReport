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
 * Class to manage the JRDesignVariable.PROPERTY_INITIAL_VALUE_EXPRESSION property
 * @author gtoffoli
 */
public final class InitialValueExpressionProperty extends ExpressionProperty 
{
    private final JRDesignVariable variable;
    private final JRDesignDataset dataset;

    public InitialValueExpressionProperty(JRDesignVariable variable, JRDesignDataset dataset)
    {
        super(variable, dataset);
        this.variable = variable;
        this.dataset = dataset;
    }

    @Override
    public String getName()
    {
        return JRDesignVariable.PROPERTY_INITIAL_VALUE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("InitialValueExpressionProperty.Property.InitialValueExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("InitialValueExpressionProperty.Property.InitialValueExpression");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Object.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)variable.getInitialValueExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        variable.setInitialValueExpression(expression);
    }

    @Override
    public boolean canWrite()
    {
        return !variable.isSystemDefined();
    }

}
