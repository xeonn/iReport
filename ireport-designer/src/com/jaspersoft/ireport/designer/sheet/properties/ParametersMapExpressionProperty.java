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
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSubreport;

    
/**
 *  Class to manage the JRDesignSubreport.PROPERTY_PARAMETERS_MAP_EXPRESSION property
 */
public final class ParametersMapExpressionProperty extends ExpressionProperty 
{
    private final JRDesignSubreport subreport;

    public ParametersMapExpressionProperty(JRDesignSubreport subreport, JRDesignDataset dataset)
    {
        super(subreport, dataset);
        this.subreport = subreport;
    }

    @Override
    public String getName()
    {
        return JRDesignSubreport.PROPERTY_PARAMETERS_MAP_EXPRESSION;
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
        return (JRDesignExpression)subreport.getParametersMapExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        subreport.setParametersMapExpression(expression);
    }

}
