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
import net.sf.jasperreports.charts.design.JRDesignLinePlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
    
    
/**
 *  Class to manage the JRDesignLinePlot.PROPERTY_VALUE_AXIS_LABEL_EXPRESSION property
 */
public final class LineValueAxisLabelExpressionProperty extends ExpressionProperty 
{
    private final JRDesignLinePlot plot;

    public LineValueAxisLabelExpressionProperty(JRDesignLinePlot plot, JRDesignDataset dataset)
    {
        super(plot, dataset);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignLinePlot.PROPERTY_VALUE_AXIS_LABEL_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Value_Axis_Label_Expression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Value_Axis_Label_Expression.");
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
