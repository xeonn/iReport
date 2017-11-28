/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;


/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_EXPRESSION property
 */
public final class TitleExpressionProperty extends ExpressionProperty 
{
    private final JRDesignChart chart;

    public TitleExpressionProperty(JRDesignChart chart, JRDesignDataset dataset)
    {
        super(chart, dataset);
        this.chart = chart;
    }

    @Override
    public String getName()
    {
        return JRDesignChart.PROPERTY_TITLE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Title Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "Title Expression.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)chart.getTitleExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        chart.setTitleExpression(expression);
    }

}
