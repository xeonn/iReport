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
import net.sf.jasperreports.charts.design.JRDesignBubblePlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
    
    
/**
 *  Class to manage the JRDesignBubblePlot.PROPERTY_X_AXIS_LABEL_EXPRESSION property
 */
public final class BubbleXAxisLabelExpressionProperty extends ExpressionProperty 
{
    private final JRDesignBubblePlot plot;

    public BubbleXAxisLabelExpressionProperty(JRDesignBubblePlot plot, JRDesignDataset dataset)
    {
        super(plot, dataset);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBubblePlot.PROPERTY_X_AXIS_LABEL_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "X Axis Label Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "X Axis Label Expression.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)plot.getXAxisLabelExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        plot.setXAxisLabelExpression(expression);
    }

}
