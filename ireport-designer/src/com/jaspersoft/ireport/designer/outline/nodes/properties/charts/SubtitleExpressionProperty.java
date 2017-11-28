/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.ExpressionProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import org.openide.ErrorManager;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_EXPRESSION property
 */
public final class SubtitleExpressionProperty extends ExpressionProperty {

     private final JRDesignDataset dataset;
     private final JRDesignChart element;

    public SubtitleExpressionProperty(JRDesignChart element, JRDesignDataset dataset)
    {
        super(JRDesignChart.PROPERTY_SUBTITLE_EXPRESSION,
              "Subtitle Expression",
              "Subtitle Expression");
        this.element = element;
        this.dataset = dataset;
        this.setValue("expressionContext", new ExpressionContext(dataset));
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        if (element.getSubtitleExpression() == null) return "";
        return element.getSubtitleExpression().getText();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        JRDesignExpression oldExp =  (JRDesignExpression)element.getSubtitleExpression();
        JRDesignExpression newExp = null;
        //System.out.println("Setting as value: " + val);
        if (val == null || val.equals(""))
        {
            element.setSubtitleExpression(null);
        }
        else
        {
            String s = (val != null) ? val+"" : "";

            newExp = new JRDesignExpression();
            newExp.setText(s);
            newExp.setValueClassName( "java.lang.String" );
            element.setSubtitleExpression(newExp);
        }
        
        ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "SubtitleExpression", 
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
        return element.getSubtitleExpression() == null || 
               element.getSubtitleExpression().getText() == null ||
               element.getSubtitleExpression().getText().length() == 0;
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
