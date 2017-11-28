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
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRDesignAreaPlot.PROPERTY_VALUE_AXIS_LABEL_EXPRESSION property
 */
public final class AreaValueAxisTickLabelMaskProperty extends PropertySupport
{
        private final JRDesignAreaPlot element;
    
        @SuppressWarnings("unchecked")
        public AreaValueAxisTickLabelMaskProperty(JRDesignAreaPlot element)
        {
            super(JRDesignAreaPlot.PROPERTY_VALUE_AXIS_TICK_LABEL_MASK,
                    String.class, 
                    "Value Axis Tick Label Mask", "Value Axis Tick Label Mask", true, true);
            this.element = element;
            this.setValue("oneline", Boolean.TRUE);
        }
        
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (element.getValueAxisTickLabelMask() == null) ? "" : element.getValueAxisTickLabelMask();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val instanceof String)
            {
                String oldValue = element.getValueAxisTickLabelMask();
                String newValue = (val == null || ((String)val).trim().length() == 0) ? null : ((String)val).trim();
                element.setValueAxisTickLabelMask(newValue);
            
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "ValueAxisTickLabelMask", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
        
            }
        }
}
