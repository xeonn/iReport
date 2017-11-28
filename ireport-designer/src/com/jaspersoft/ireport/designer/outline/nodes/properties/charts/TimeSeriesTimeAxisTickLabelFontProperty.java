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
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesPlot;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JRDesignFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_FONT property
 */
public final class TimeSeriesTimeAxisTickLabelFontProperty extends JRFontProperty
{
        private final JasperDesign jd;
        private final JRDesignTimeSeriesPlot element;
        
        @SuppressWarnings("unchecked")
        public TimeSeriesTimeAxisTickLabelFontProperty(JRDesignTimeSeriesPlot element, JasperDesign jd)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( JRDesignTimeSeriesPlot.PROPERTY_TIME_AXIS_TICK_LABEL_FONT, "Time Axis Tick Label Font", "Time Axis Tick Label Font", jd);
            this.element = element;
            this.jd = jd;
        }

    @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getTimeAxisTickLabelFont();
        }

    @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val == null || val instanceof JRFont)
            {
                JRDesignFont oldValue = (JRDesignFont)element.getTimeAxisTickLabelFont();
                JRDesignFont newValue = (JRDesignFont)val;
                element.setTimeAxisTickLabelFont(newValue);
            
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "TimeAxisTickLabelFont", 
                            JRFont.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }
    
        @Override
        public boolean isDefaultValue() {
            return null == element.getTimeAxisTickLabelFont();
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
