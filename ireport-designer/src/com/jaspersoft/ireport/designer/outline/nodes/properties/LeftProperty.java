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
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

/**
 *  Class to manage the JRDesignElement.PROPERTY_X property
 * @author gtoffoli
 */
public final class LeftProperty extends PropertySupport
{
        private final JRDesignElement element;

        @SuppressWarnings("unchecked")
        public LeftProperty(JRDesignElement element)
        {
            super(JRDesignElement.PROPERTY_X,Integer.class, "Left", "Left position of this element", true, true);
            this.element = element;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getX();
        }

        // TODO: check page width with this margin consistency
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val instanceof Integer)
            {
                Integer oldValue = element.getX();
                Integer newValue = (Integer)val;

                if (newValue < 0)
                {
                    IllegalArgumentException iae = annotateException("This property requires a positive number.");
                    throw iae; 
                }

                element.setX(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "X", 
                            Integer.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }


        public IllegalArgumentException annotateException(String msg)
        {
            IllegalArgumentException iae = new IllegalArgumentException(msg); 
            ErrorManager.getDefault().annotate(iae, 
                                    ErrorManager.EXCEPTION,
                                    msg,
                                    msg, null, null); 
            return iae;
        }
}
