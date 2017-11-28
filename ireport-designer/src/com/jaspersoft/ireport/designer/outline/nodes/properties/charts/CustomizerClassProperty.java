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
import net.sf.jasperreports.engine.design.JRDesignChart;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JasperDesign.PROPERTY_PAGE_WIDTH property
 */
public final class CustomizerClassProperty extends PropertySupport
{
        private final JRDesignChart element;
    
        @SuppressWarnings("unchecked")
        public CustomizerClassProperty(JRDesignChart element)
        {
            super(JRDesignChart.PROPERTY_CUSTOMIZER_CLASS,String.class, "Customizer Class", "Name of an optional class to customize the chart", true, true);
            this.element = element;
            this.setValue("oneline", Boolean.TRUE);
        }
        
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (element.getCustomizerClass() == null) ? "" : element.getCustomizerClass();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val instanceof String)
            {
                String oldValue = element.getCustomizerClass();
                String newValue = (val == null || ((String)val).trim().length() == 0) ? null : ((String)val).trim();
                element.setCustomizerClass(newValue);
            
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "CustomizerClass", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
        
            }
        }
}
