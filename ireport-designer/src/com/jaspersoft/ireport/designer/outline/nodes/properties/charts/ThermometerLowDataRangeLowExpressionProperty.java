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
import net.sf.jasperreports.charts.JRDataRange;
import net.sf.jasperreports.charts.design.JRDesignDataRange;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
    
    
/**
 *  Class to manage the JRDesignDataRange.PROPERTY_LOW_EXPRESSION property
 */
public final class ThermometerLowDataRangeLowExpressionProperty extends ExpressionProperty {

     private final JRDesignDataset dataset;
     private final JRDesignThermometerPlot element;

    public ThermometerLowDataRangeLowExpressionProperty(JRDesignThermometerPlot element, JRDesignDataset dataset)
    {
        super("LOW_RANGE_" + JRDesignDataRange.PROPERTY_LOW_EXPRESSION,
              "Low Data Range Low Expression",
              "Low Data Range Low Expression");
        this.element = element;
        this.dataset = dataset;
        this.setValue("expressionContext", new ExpressionContext(dataset));
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        if (element.getLowRange() == null) return "";
        if (element.getLowRange().getLowExpression() == null) return "";
        if (element.getLowRange().getLowExpression().getText() == null) return "";
        return element.getLowRange().getLowExpression().getText();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        JRDataRange oldValue =  element.getLowRange();
        JRDesignDataRange newValue = new JRDesignDataRange(null);
        
        if (oldValue != null) 
        {
            try {
                newValue = (JRDesignDataRange)oldValue.clone();
            } catch (Exception ex) {}
        }
        
        //System.out.println("Setting as value: " + val);
        if (val == null || val.equals(""))
        {
            newValue.setLowExpression(null);
        }
        else
        {
            String s = (val != null) ? val+"" : "";
            
            JRDesignExpression newExp = new JRDesignExpression();
            newExp.setText(s);
            newExp.setValueClassName( "java.lang.Number" );
            newValue.setLowExpression(newExp);
        }
        
        try {
            element.setLowRange(newValue);

            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "LowRange", 
                            JRDataRange.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        } catch (Exception ex) { 
            // No exception should be never thrown...
        }
        //System.out.println("Done: " + val);
    }
}
