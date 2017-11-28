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
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import org.openide.nodes.PropertySupport;

/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 * @author gtoffoli
 */
public final class PenProperty extends PropertySupport
{
        private final JRDesignGraphicElement element;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public PenProperty(JRDesignGraphicElement element)
        {
            // TODO: Replace WhenNoDataType with the right constant
//FIXME
            super(null,Byte.class, "Pen", "Pen.", true, true);
//                super(JRDesignGraphicElement.PROPERTY_PEN,Byte.class, "Pen", "Pen.", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignGraphicElement.PEN_THIN), "Thin"));
                l.add(new Tag(new Byte(JRDesignGraphicElement.PEN_1_POINT), "1 point"));
                l.add(new Tag(new Byte(JRDesignGraphicElement.PEN_2_POINT), "2 point"));
                l.add(new Tag(new Byte(JRDesignGraphicElement.PEN_4_POINT), "4 point"));
                l.add(new Tag(new Byte(JRDesignGraphicElement.PEN_DOTTED), "Dotted"));
                l.add(new Tag(new Byte(JRDesignGraphicElement.PEN_NONE), "None"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Byte(element.getPen());
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }


        private void setPropertyValue(Object val)
        {
            if (val == null || val instanceof Byte)
            {
                Byte oldValue = element.getOwnPen();
                Byte newValue = (Byte)val;
                element.setPen(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Pen", 
                            Byte.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOwnPen() == null;
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
