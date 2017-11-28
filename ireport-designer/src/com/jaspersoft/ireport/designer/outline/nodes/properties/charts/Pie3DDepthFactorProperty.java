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
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRBaseChartPlot.PROPERTY_BACKGROUND_ALPHA property
 */
public final class Pie3DDepthFactorProperty extends PropertySupport.ReadWrite {

    JRDesignPie3DPlot element = null;

    @SuppressWarnings("unchecked")
    public Pie3DDepthFactorProperty(JRDesignPie3DPlot element)
    {
        super(JRDesignPie3DPlot.PROPERTY_DEPTH_FACTOR, Double.class,
              "Depth Factor",
              "Depth Factor");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getDepthFactor();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val)
    {
        if (val instanceof Double)
        {
            double oldValue = element.getDepthFactor();
            double newValue =  ((Double)val).doubleValue();
            
            if (newValue < 0)
            {
                IllegalArgumentException iae = annotateException("The depth factor must be a positive value.");
                throw iae; 
            }
            
            element.setDepthFactor(newValue);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "DepthFactor", 
                        Double.TYPE,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
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
