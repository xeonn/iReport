/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.properties.ExpressionProperty;
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
public final class ThermometerMediumDataRangeLowExpressionProperty extends ExpressionProperty 
{
    private final JRDesignThermometerPlot plot;

    public ThermometerMediumDataRangeLowExpressionProperty(JRDesignThermometerPlot plot, JRDesignDataset dataset)
    {
        super(plot, dataset);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return "MEDIUM_RANGE_" + JRDesignDataRange.PROPERTY_LOW_EXPRESSION;//FIXMETD concatenation?
    }

    @Override
    public String getDisplayName()
    {
        return "Medium Data Range Low Expression";
    }

    @Override
    public String getShortDescription()
    {
        return "Medium Data Range Low Expression.";
    }

    @Override
    public String getDefaultExpressionClassName()
    {
        return Number.class.getName();
    }

    @Override
    public JRDesignExpression getExpression()
    {
        JRDataRange dataRange = plot.getDataRange();
        return dataRange == null ? null : (JRDesignExpression) dataRange.getLowExpression();
    }

    @Override
    public void setExpression(JRDesignExpression expression)
    {
        //FIXMETD plot.setCategoryAxisLabelExpression(expression);
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        JRDataRange oldValue =  plot.getMediumRange();
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
            plot.setMediumRange(newValue);

            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            plot,
                            JRDesignThermometerPlot.PROPERTY_MEDIUM_RANGE, 
                            JRDataRange.class,
                            oldValue,
                            newValue
                            );
                // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        } catch (Exception ex) { 
            // No exception should be never thrown...
        }
        //System.out.println("Done: " + val);
    }
}
