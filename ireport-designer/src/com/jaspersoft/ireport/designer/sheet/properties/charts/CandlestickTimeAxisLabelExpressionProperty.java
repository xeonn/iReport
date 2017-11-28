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
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignCandlestickPlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
    
    
/**
 *  Class to manage the JRDesignCandlestickPlot.PROPERTY_TIME_AXIS_LABEL_EXPRESSION property
 */
public final class CandlestickTimeAxisLabelExpressionProperty extends ExpressionProperty 
{
    private final JRDesignCandlestickPlot plot;

    public CandlestickTimeAxisLabelExpressionProperty(JRDesignCandlestickPlot plot, JRDesignDataset dataset)
    {
        super(plot, dataset);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignCandlestickPlot.PROPERTY_TIME_AXIS_LABEL_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.TimeAxisLabelExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.TimeAxisLabelExpressiondetail");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)plot.getTimeAxisLabelExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        plot.setTimeAxisLabelExpression(expression);
    }

}
