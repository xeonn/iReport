/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSubreport;

    
/**
 *  Class to manage the JRDesignImage.PROPERTY_EXPRESSION property
 */
public final class SubreportExpressionProperty extends ExpressionProperty 
{
    private final JRDesignSubreport subreport;

    public SubreportExpressionProperty(JRDesignSubreport subreport, JRDesignDataset dataset)
    {
        super(subreport, dataset);
        this.subreport = subreport;
    }

    @Override
    public String getName()
    {
        return JRDesignSubreport.PROPERTY_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.SubreportExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.SubreportExpressiondetail");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return String.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)subreport.getExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        subreport.setExpression(expression);
    }

}
