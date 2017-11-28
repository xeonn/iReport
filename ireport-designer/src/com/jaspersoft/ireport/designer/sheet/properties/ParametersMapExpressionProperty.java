/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.ErrorManager;

    
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
        return "Parameters Map Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "Optional expression to set a Map of parameters for this subreport.";
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
