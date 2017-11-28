/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editorˆ.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
    
    
/**
 *  Class to manage the JRDesignBarPlot.PROPERTY_VALUE_AXIS_LABEL_EXPRESSION property
 */
public final class Bar3DRangeAxisMaxValueExpressionProperty extends ExpressionProperty 
{
    private final JRDesignBar3DPlot plot;

    public Bar3DRangeAxisMaxValueExpressionProperty(JRDesignBar3DPlot plot, JRDesignDataset dataset)
    {
        super(plot, dataset);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_RANGE_AXIS_MAXVALUE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.RangeAxisMaxValueExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.RangeAxisMaxValueExpression");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Comparable.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)plot.getRangeAxisMaxValueExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        plot.setRangeAxisMaxValueExpression(expression);
    }

}