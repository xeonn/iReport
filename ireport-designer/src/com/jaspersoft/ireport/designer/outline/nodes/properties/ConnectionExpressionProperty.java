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
public final class ConnectionExpressionProperty extends ExpressionProperty {

     private final JRDesignDataset dataset;
     private final JRDesignSubreport element;

    public ConnectionExpressionProperty(JRDesignSubreport element, JRDesignDataset dataset)
    {
        super(JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION,
              "Connection Expression",
              "The expression must return a java.sql.Connection object to fill the subreport.");
        this.element = element;
        this.dataset = dataset;
        this.setValue("expressionContext", new ExpressionContext(dataset));
    }

    @Override
    public boolean canWrite() {
        return element.getConnectionExpression() != null;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        if (element.getConnectionExpression() == null) return "";
        if (element.getConnectionExpression().getText() == null) return "";
        return element.getConnectionExpression().getText();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        JRDesignExpression oldExp =  (JRDesignExpression)element.getConnectionExpression();
        JRDesignExpression newExp = null;
        //System.out.println("Setting as value: " + val);
        if ( val == null)
        {
            element.setConnectionExpression(null);
        }
        else
        {
            String s = (val != null) ? val+"" : "";

            newExp = new JRDesignExpression();
            newExp.setText(s);
            element.setConnectionExpression(newExp);
        }

        ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "ConnectionExpression", 
                        JRExpression.class,
                        oldExp,newExp);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);

        //System.out.println("Done: " + val);
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

    @Override
    public String getHtmlDisplayName() {
        if (!canWrite())
        {
            return "<font color=\"#CCCCCC\">" + getDisplayName() + "</font>";
        }
        return super.getHtmlDisplayName();
    }
}
