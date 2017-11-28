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
 *  Class to manage the JRDesignSubreport.PROPERTY_PARAMETERS_MAP_EXPRESSION property
 */
public final class ParametersMapExpressionProperty extends ExpressionProperty {

     private final JRDesignDataset dataset;
     private final JRDesignSubreport element;

    public ParametersMapExpressionProperty(JRDesignSubreport element, JRDesignDataset dataset)
    {
        super(JRDesignSubreport.PROPERTY_PARAMETERS_MAP_EXPRESSION,
              "Parameters Map Expression",
              "Optional expression to set a Map of parameters for this subreport.");
        this.element = element;
        this.dataset = dataset;
        this.setValue("expressionContext", new ExpressionContext(dataset));
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        if (element.getParametersMapExpression() == null) return "";
        return element.getParametersMapExpression().getText();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        JRDesignExpression oldExp =  (JRDesignExpression)element.getParametersMapExpression();
        JRDesignExpression newExp = null;
        //System.out.println("Setting as value: " + val);
        if ( val == null || val.equals(""))
        {
            element.setParametersMapExpression(null);
        }
        else
        {
            String s = (val != null) ? val+"" : "";

            newExp = new JRDesignExpression();
            newExp.setText(s);
            element.setParametersMapExpression(newExp);
        }

        ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "ParametersMapExpression", 
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
    public boolean isDefaultValue() {
        return element.getParametersMapExpression() == null || 
               element.getParametersMapExpression().getText() == null ||
               element.getParametersMapExpression().getText().length() == 0;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }
}
