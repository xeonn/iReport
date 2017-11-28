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
import net.sf.jasperreports.engine.design.JRDesignParameter;


/**
 *  Class to manage the JRDesignParameter.PROPERTY_DEFAULT_VALUE_EXPRESSION property
 */
public final class DefaultValueExpressionProperty extends ExpressionProperty 
{
    private final JRDesignParameter parameter;
    private final JRDesignDataset dataset;

    public DefaultValueExpressionProperty(JRDesignParameter parameter, JRDesignDataset dataset)
    {
        super(parameter, dataset);
        this.parameter = parameter;
        this.dataset = dataset;
    }

    @Override
    public String getName()
    {
        return JRDesignParameter.PROPERTY_DEFAULT_VALUE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("DefaultValueExpressionProperty.Property.DVE");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("DefaultValueExpressionProperty.Property.DVE");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Object.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)parameter.getDefaultValueExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        expression.setValueClassName(parameter.getValueClassName());
        parameter.setDefaultValueExpression(expression);
    }

    @Override
    public boolean canWrite()
    {
        return !parameter.isSystemDefined();
    }

}
