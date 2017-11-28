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
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JRDesignBarPlot.PROPERTY_CATEGORY_AXIS_LABEL_EXPRESSION property
 */
public final class BarCategoryAxisTickLabelMaskProperty extends PropertySupport
{
        private final JRDesignBarPlot element;
    
        @SuppressWarnings("unchecked")
        public BarCategoryAxisTickLabelMaskProperty(JRDesignBarPlot element)
        {
            super(JRDesignBarPlot.PROPERTY_CATEGORY_AXIS_TICK_LABEL_MASK,
                    String.class, 
                    "Category Axis Tick Label Mask", "Category Axis Tick Label Mask", true, true);
            this.element = element;
            this.setValue("oneline", Boolean.TRUE);
        }
        
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (element.getCategoryAxisTickLabelMask() == null) ? "" : element.getCategoryAxisTickLabelMask();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val instanceof String)
            {
                String oldValue = element.getCategoryAxisTickLabelMask();
                String newValue = (val == null || ((String)val).trim().length() == 0) ? null : ((String)val).trim();
                element.setCategoryAxisTickLabelMask(newValue);
            
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "CategoryAxisTickLabelMask", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
        
            }
        }
}
