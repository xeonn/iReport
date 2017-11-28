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
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
    
    
/**
 *  Class to manage the JRDesignTimeSeriesPlot.PROPERTY_VALUE_AXIS_LABEL_EXPRESSION property
 */
public final class TimeSeriesValueAxisLabelExpressionProperty extends ExpressionProperty 
{
    private final JRDesignTimeSeriesPlot plot;

    public TimeSeriesValueAxisLabelExpressionProperty(JRDesignTimeSeriesPlot plot, JRDesignDataset dataset)
    {
        super(plot, dataset);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignTimeSeriesPlot.PROPERTY_VALUE_AXIS_LABEL_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Value Axis Label Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "Value Axis Label Expression.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)plot.getValueAxisLabelExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        plot.setValueAxisLabelExpression(expression);
    }

}
