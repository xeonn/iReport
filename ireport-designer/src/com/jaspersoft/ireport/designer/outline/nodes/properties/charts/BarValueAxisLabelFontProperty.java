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
import com.jaspersoft.ireport.designer.sheet.JRFontProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JRDesignFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_FONT property
 */
public final class BarValueAxisLabelFontProperty extends JRFontProperty
{
        private final JasperDesign jd;
        private final JRDesignBarPlot element;
        
        @SuppressWarnings("unchecked")
        public BarValueAxisLabelFontProperty(JRDesignBarPlot element, JasperDesign jd)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( JRDesignBarPlot.PROPERTY_VALUE_AXIS_LABEL_FONT, "Value Axis Label Font", "Value Axis Label Font", jd);
            this.element = element;
            this.jd = jd;
        }

    @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getValueAxisLabelFont();
        }

    @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val == null || val instanceof JRFont)
            {
                JRDesignFont oldValue = (JRDesignFont)element.getValueAxisLabelFont();
                JRDesignFont newValue = (JRDesignFont)val;
                element.setValueAxisLabelFont(newValue);
            
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "ValueAxisLabelFont", 
                            JRFont.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }
    
        @Override
        public boolean isDefaultValue() {
            return null == element.getValueAxisLabelFont();
        }
    
        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
        
        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
        }
}
