/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.ExpressionProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.ErrorManager;

    
/**
 *  Class to manage the JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION property
 */
public final class DataSourceExpressionProperty extends ExpressionProperty {

     private final JRDesignDataset dataset;
     private final JRDesignSubreport element;

    public DataSourceExpressionProperty(JRDesignSubreport element, JRDesignDataset dataset)
    {
        super(JRDesignSubreport.PROPERTY_DATASOURCE_EXPRESSION,
              "DataSource Expression",
              "The expression must return a JRDataSource object to fill the subreport.");
        this.element = element;
        this.dataset = dataset;
        this.setValue("expressionContext", new ExpressionContext(dataset));
    }

    @Override
    public boolean canWrite() {
        return element.getDataSourceExpression() != null;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        if (element.getDataSourceExpression() == null) return "";
        if (element.getDataSourceExpression().getText() == null) return "";
        return element.getDataSourceExpression().getText();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        JRDesignExpression oldExp =  (JRDesignExpression)element.getDataSourceExpression();
        JRDesignExpression newExp = null;
        //System.out.println("Setting as value: " + val);
        if ( val == null)
        {
            element.setDataSourceExpression(null);
        }
        else
        {
            String s = (val != null) ? val+"" : "";

            newExp = new JRDesignExpression();
            newExp.setText(s);
            element.setDataSourceExpression(newExp);
        }

        ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "DataSource", 
                        JRExpression.class,
                        oldExp,newExp);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);

        //System.out.println("Done: " + val);
    }

    @Override
    public String getHtmlDisplayName() {
        if (!canWrite())
        {
            return "<font color=\"#CCCCCC\">" + getDisplayName() + "</font>";
        }
        return super.getHtmlDisplayName();
    }



    public IllegalArgumentException annotateException(String msg)
    {
        IllegalArgumentException iae = new IllegalArgumentException(msg); 
        ErrorManager.getDefault().annotate(iae, 
                                ErrorManager.EXCEPTION,
                                msg,
                                msg, null, null); 
        return iae;
    }
}
