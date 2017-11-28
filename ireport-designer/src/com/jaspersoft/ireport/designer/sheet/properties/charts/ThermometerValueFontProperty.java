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
import com.jaspersoft.ireport.designer.sheet.JRFontProperty;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDisplay;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JRDesignFont;
import net.sf.jasperreports.engine.design.JasperDesign;
    
    
/**
 *  Class to manage the JRDesignValueDisplay.PROPERTY_FONT property
 */
public final class ThermometerValueFontProperty extends JRFontProperty
{
        private final JasperDesign jd;
        private final JRDesignThermometerPlot element;
        
        
        public ThermometerValueFontProperty(JRDesignThermometerPlot element, JasperDesign jd)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( JRDesignValueDisplay.PROPERTY_FONT, I18n.getString("Value_Font"), I18n.getString("Value_Font"), jd);
            this.element = element;
            this.jd = jd;
        }

    @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (element.getValueDisplay() == null || element.getValueDisplay().getFont() == null) ? null : element.getValueDisplay().getFont();
         }

    @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val == null || val instanceof JRFont)
            {
                JRValueDisplay oldValue = element.getValueDisplay();
                JRDesignValueDisplay newValue = new JRDesignValueDisplay( element.getValueDisplay());
                newValue.setFont((JRDesignFont)val);

                element.setValueDisplay(newValue);
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            I18n.getString("ValueDisplay"), 
                            JRValueDisplay.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }
    
        @Override
        public boolean isDefaultValue() {
            return null == element.getValueDisplay() || null == element.getValueDisplay().getFont();
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
}
