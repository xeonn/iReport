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
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.charts.JRValueDisplay;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDisplay;
import org.openide.nodes.PropertySupport;
    
    
/**
 *  Class to manage the JasperDesign.PROPERTY_PAGE_WIDTH property
 */
public final class MeterValueMaskProperty extends PropertySupport
{
        private final JRDesignMeterPlot element;
    
        
        public MeterValueMaskProperty(JRDesignMeterPlot element)
        {
            super(JRDesignValueDisplay.PROPERTY_MASK,String.class, I18n.getString("Mask"), I18n.getString("Mask"), true, true);
            this.element = element;
            this.setValue(I18n.getString("oneline"), Boolean.TRUE);
        }
        
        @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return (element.getValueDisplay() == null || element.getValueDisplay().getMask() == null) ? I18n.getString("") : element.getValueDisplay().getMask();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val)
    {
        if (val == null || val instanceof String)
        {
            JRValueDisplay oldValue = element.getValueDisplay();
            JRDesignValueDisplay newValue = new JRDesignValueDisplay( element.getValueDisplay(), element.getChart());
            newValue.setMask((String)val);
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
        return null == element.getValueDisplay() || null == element.getValueDisplay().getMask();
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
