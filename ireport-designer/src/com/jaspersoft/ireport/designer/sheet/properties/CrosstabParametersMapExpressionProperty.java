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
import java.util.Map;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;

    
/**
 *  Class to manage the JRDesignSubreport.PROPERTY_PARAMETERS_MAP_EXPRESSION property
 */
public final class CrosstabParametersMapExpressionProperty extends ExpressionProperty 
{
    private final JRDesignCrosstab crosstab;

    public CrosstabParametersMapExpressionProperty(JRDesignCrosstab crosstab, JRDesignDataset dataset)
    {
        super(crosstab, dataset);
        this.crosstab = crosstab;
    }

    @Override
    public String getName()
    {
        return JRDesignCrosstab.PROPERTY_PARAMETERS_MAP_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ParametersMapExpression");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ParamMapExpdetail");
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Map.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)crosstab.getParametersMapExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        crosstab.setParametersMapExpression(expression);
    }

}
