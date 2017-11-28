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
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignImage;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class VerticalAlignmentProperty extends PropertySupport
{
        private final JRDesignImage element;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public VerticalAlignmentProperty(JRDesignImage element)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT,Byte.class, "Vertical Alignment", "How to align the image.", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_TOP), "Left"));
                l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_MIDDLE), "Center"));
                l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_BOTTOM), "Right"));
                l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_JUSTIFIED), "Justified"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getVerticalAlignment();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val == null || val instanceof Byte)
            {
                Byte oldValue = element.getOwnVerticalAlignment();
                Byte newValue = (Byte)val;
                element.setVerticalAlignment(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "VerticalAlignment", 
                            Byte.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOwnVerticalAlignment() == null;
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
