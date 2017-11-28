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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.ErrorManager;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class DataSourceExpressionProperty extends ExpressionProperty 
{
    private final JRDesignSubreport subreport;

    public DataSourceExpressionProperty(JRDesignSubreport subreport, JRDesignDataset dataset)
    {
        super(subreport, dataset);
        this.subreport = subreport;
    }

    @Override
    public String getName()
    {
        return JRDesignSubreport.PROPERTY_DATASOURCE_EXPRESSION;
    }

    @Override
    public String getDisplayName()
    {
        return "Data Source Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "The expression must return a JRDataSource object to fill the subreport.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return JRDataSource.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        return (JRDesignExpression)subreport.getDataSourceExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        subreport.setDataSourceExpression(expression);
    }

    @Override
    public boolean canWrite() //FIXMETD is this needed? check all
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
