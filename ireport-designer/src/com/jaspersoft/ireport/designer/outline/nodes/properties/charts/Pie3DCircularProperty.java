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
import net.sf.jasperreports.charts.design.JRDesignPiePlot;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
 */
public final class Pie3DCircularProperty extends PropertySupport.ReadWrite {

    private final JRDesignPie3DPlot element;

    @SuppressWarnings("unchecked")
    public Pie3DCircularProperty(JRDesignPie3DPlot element)
    {
        super(JRDesignPiePlot.PROPERTY_CIRCULAR, Boolean.class,
              "Circular",
              "Circular");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.isCircular();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val instanceof Boolean)
        {
            boolean oldValue = element.isCircular();
            boolean newValue =   ((Boolean)val).booleanValue();
            element.setCircular(newValue);
            
            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "Circular", 
                        Boolean.TYPE,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);

        }
    }
}
