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
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JasperDesign.PROPERTY_PAGE_WIDTH property
 */
public final class MeterUnitsProperty extends PropertySupport
{
        private final JRDesignMeterPlot element;
    
        @SuppressWarnings("unchecked")
        public MeterUnitsProperty(JRDesignMeterPlot element)
        {
            super(JRDesignMeterPlot.PROPERTY_UNITS,String.class, "Units", "Units", true, true);
            this.element = element;
            this.setValue("oneline", Boolean.TRUE);
        }
        
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (element.getUnits() == null) ? "" : element.getUnits();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val instanceof String)
            {
                String oldValue = element.getUnits();
                String newValue = (val == null) ? null : val.toString();
                element.setUnits(newValue);
            
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Units", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
        
            }
        }
}
