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
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class FilterExpressionProperty extends ExpressionProperty
{
    private final JRDesignDataset dataset;

    @SuppressWarnings("unchecked")
    public FilterExpressionProperty(JRDesignDataset dataset)
    {
        super(dataset, dataset);
        this.dataset = dataset;
    }

    @Override
    public String getName()
    {
        return JRDesignDataset.PROPERTY_FILTER_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Filter Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "Expression used to filter the records. It must return a Boolean object.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Boolean.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)dataset.getFilterExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        dataset.setFilterExpression(expression);
    }

}
