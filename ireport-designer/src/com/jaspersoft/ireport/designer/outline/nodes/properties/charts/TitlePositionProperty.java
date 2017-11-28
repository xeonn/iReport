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
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.design.JRDesignChart;
import org.openide.nodes.PropertySupport;


/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_POSITION property
 */
public final class TitlePositionProperty extends PropertySupport
{
        private final JRDesignChart element;
        private ComboBoxPropertyEditor editor;
        
        @SuppressWarnings("unchecked")
        public TitlePositionProperty(JRDesignChart element)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super(JRBaseChart.PROPERTY_TITLE_POSITION,Byte.class, "Title Position", "Title Position", true, true);
            this.element = element;
            setValue("suppressCustomEditor", Boolean.TRUE);
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                java.util.ArrayList l = new java.util.ArrayList();
                l.add(new Tag(new Byte(JRDesignChart.EDGE_TOP), "Top"));
                l.add(new Tag(new Byte(JRDesignChart.EDGE_BOTTOM), "Bottom"));
                l.add(new Tag(new Byte(JRDesignChart.EDGE_LEFT), "Left"));
                l.add(new Tag(new Byte(JRDesignChart.EDGE_RIGHT), "Right"));
                editor = new ComboBoxPropertyEditor(false, l);
            }
            return editor;
        }
        
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Byte(element.getTitlePosition());
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

    
        private void setPropertyValue(Object val)
        {
            if (val instanceof Byte)
            {
                byte oldValue = element.getTitlePosition();
                byte newValue = ((Byte)val).byteValue();
                element.setTitlePosition(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "TitlePosition", 
                            Byte.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.getTitlePosition() == JRDesignChart.EDGE_TOP;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(new Byte( JRDesignChart.EDGE_TOP) );
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
}
