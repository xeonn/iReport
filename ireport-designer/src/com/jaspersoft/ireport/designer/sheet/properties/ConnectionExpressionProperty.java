/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import java.sql.Connection;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSubreport;

    
/**
 * Class to manage the JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION property.
 */
public final class ConnectionExpressionProperty extends ExpressionProperty 
{
    private final JRDesignSubreport subreport;

    public ConnectionExpressionProperty(JRDesignSubreport subreport, JRDesignDataset dataset)
    {
        super(subreport, dataset);
        this.subreport = subreport;
    }

    @Override
    public String getName()
    {
        return JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Connection Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "The expression must return a java.sql.Connection object to fill the subreport.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Connection.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)subreport.getConnectionExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        subreport.setConnectionExpression(expression);
    }

    @Override
    public boolean canWrite() 
    {
        return getExpression() != null;
    }

    @Override
    public String getHtmlDisplayName() {
        if (!canWrite())
        {
            return "<font color=\"#CCCCCC\">" + getDisplayName() + "</font>";
        }
        return super.getHtmlDisplayName();
    }
}
