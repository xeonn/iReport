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
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabParameter;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignParameter;


/**
 *  Class to manage the JRDesignParameter.PROPERTY_DEFAULT_VALUE_EXPRESSION property
 */
public final class CrosstabParameterValueExpressionProperty extends ExpressionProperty 
{
    private final JRDesignCrosstabParameter parameter;
    private final JRDesignDataset dataset;

    public CrosstabParameterValueExpressionProperty(JRDesignCrosstabParameter parameter, JRDesignDataset dataset)
    {
        super(parameter, dataset);
        this.parameter = parameter;
        this.dataset = dataset;
    }

    @Override
    public String getName()
    {
        return JRDesignCrosstabParameter.PROPERTY_VALUE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("ValueExpressionProperty.Property.Name");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("ValueExpressionProperty.Property.Description");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Object.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)parameter.getExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        expression.setValueClassName(parameter.getValueClassName());
        parameter.setExpression(expression);
    }

    @Override
    public boolean canWrite()
    {
        return !parameter.isSystemDefined();
    }

}
