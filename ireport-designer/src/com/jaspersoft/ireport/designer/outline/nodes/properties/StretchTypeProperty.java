/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class StretchTypeProperty extends PropertySupport
{
        private final JRDesignElement element;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public StretchTypeProperty(JRDesignElement element)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super(JRDesignElement.PROPERTY_STRETCH_TYPE,Byte.class, "Stretch type", "How to stretch the element.", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_NO_STRETCH), "No stretch"));
                l.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_RELATIVE_TO_BAND_HEIGHT), "Relative to Band Height"));
                l.add(new Tag(new Byte(JRDesignElement.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT), "Relative to Tallest Object"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Byte(element.getStretchType());
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

        @Override
        public boolean isDefaultValue() {
            return element.getStretchType() == JRDesignElement.STRETCH_TYPE_NO_STRETCH;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setPropertyValue(JRDesignElement.STRETCH_TYPE_NO_STRETCH);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }

        private void setPropertyValue(Object val)
        {
            if (val instanceof Byte)
            {
                Byte oldValue = element.getStretchType();
                Byte newValue = (Byte)val;
                element.setStretchType(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "StretchType", 
                            Byte.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }
}
