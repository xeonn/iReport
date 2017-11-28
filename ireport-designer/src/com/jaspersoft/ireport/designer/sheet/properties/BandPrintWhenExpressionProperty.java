/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class BandPrintWhenExpressionProperty extends ExpressionProperty 
{

    private final JRDesignBand band;

    public BandPrintWhenExpressionProperty(JRDesignBand band, JRDesignDataset dataset)
    {
        super(band, dataset);
        this.band = band;
    }

    @Override
    public String getName()
    {
        return JRDesignBand.PROPERTY_PRINT_WHEN_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Print When Expression";
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
        return (JRDesignExpression)band.getPrintWhenExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        band.setPrintWhenExpression(expression);
    }

}