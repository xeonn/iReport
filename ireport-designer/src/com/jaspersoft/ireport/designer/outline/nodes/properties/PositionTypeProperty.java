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
public final class PositionTypeProperty extends PropertySupport
{
        private final JRDesignElement element;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public PositionTypeProperty(JRDesignElement element)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super(JRDesignElement.PROPERTY_POSITION_TYPE,Byte.class, "Position type", "How to anchor the element.", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP), "Fix Relative to Top"));
                l.add(new Tag(new Byte(JRDesignElement.POSITION_TYPE_FLOAT), "Float"));
                l.add(new Tag(new Byte(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_BOTTOM), "Fix Relative to Bottom"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Byte(element.getPositionType());
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

    @Override
    public boolean isDefaultValue() {
        return element.getPositionType() == JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setPropertyValue(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP);
    }

    @Override
    public boolean supportsDefaultValue() {
        return super.supportsDefaultValue();
    }

    private void setPropertyValue(Object val)
    {
        if (val instanceof Byte)
        {
            Byte oldValue = element.getPositionType();
            Byte newValue = (Byte)val;
            element.setPositionType(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "PositionType", 
                        Byte.TYPE,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }
}
