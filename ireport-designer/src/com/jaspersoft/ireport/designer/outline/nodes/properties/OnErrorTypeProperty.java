/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
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
import net.sf.jasperreports.engine.base.JRBaseImage;
import net.sf.jasperreports.engine.design.JRDesignImage;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class OnErrorTypeProperty extends PropertySupport
{
        private final JRDesignImage element;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public OnErrorTypeProperty(JRDesignImage element)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super(JRBaseImage.PROPERTY_ON_ERROR_TYPE,Byte.class, "On Error Type", "What to print or to do if an error occurs loading the image.", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignImage.ON_ERROR_TYPE_ERROR), "Error"));
                l.add(new Tag(new Byte(JRDesignImage.ON_ERROR_TYPE_BLANK), "Blank"));
                l.add(new Tag(new Byte(JRDesignImage.ON_ERROR_TYPE_ICON), "Icon"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Byte(element.getOnErrorType());
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

        private void setPropertyValue(Object val)
        {
            if (val instanceof Byte)
            {
                Byte oldValue = element.getOnErrorType();
                Byte newValue = (Byte)val;
                element.setOnErrorType(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "OnErrorType", 
                            Byte.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOnErrorType() == JRDesignImage.ON_ERROR_TYPE_ERROR;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue( JRDesignImage.ON_ERROR_TYPE_ERROR );
        }

        @Override
        public boolean supportsDefaultValue() {
           return true;
        }
}
