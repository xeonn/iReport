/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;

    
/**
 * Class to manage the JRDesignElement.PROPERTY_PRINT_WHEN_EXPRESSION property
 */
public final class PrintWhenExpressionProperty extends ExpressionProperty 
{
    private final JRDesignElement element;

    public PrintWhenExpressionProperty(JRDesignElement element, JRDesignDataset dataset)
    {
        super(element, dataset);
        this.element = element;
        
        if (ModelUtils.getTopElementGroup(element) instanceof JRDesignCellContents)
        {
            JRDesignCellContents contents = (JRDesignCellContents) ModelUtils.getTopElementGroup(element);
            this.setValue(ExpressionContext.ATTRIBUTE_EXPRESSION_CONTEXT, new ExpressionContext(contents.getOrigin().getCrosstab()));
        }
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_PRINT_WHEN_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Print When Expressio";
    }

    @Override
    public String getShortDescription()
    {
        return "Print this element only when this expression is blank or returns true.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Boolean.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)element.getPrintWhenExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        element.setPrintWhenExpression(expression);
    }

}
