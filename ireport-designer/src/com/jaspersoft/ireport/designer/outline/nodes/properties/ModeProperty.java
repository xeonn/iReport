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
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
 */
public final class ModeProperty extends PropertySupport.ReadWrite {

    JRDesignElement element = null;

    @SuppressWarnings("unchecked")
    public ModeProperty(JRDesignElement element)
    {
        super(JRBaseStyle.PROPERTY_MODE, Boolean.class,
              "Opaque",
              "Set if an element is opaque or transparent.");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return new Boolean( element.getMode() == JRDesignElement.MODE_OPAQUE);
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val == null || val instanceof Boolean)
        {
            Byte oldValue = element.getOwnMode();
            Byte newValue =   val == null ? null : ( ((Boolean)val).booleanValue()  ? JRDesignElement.MODE_OPAQUE : JRDesignElement.MODE_TRANSPARENT );
            element.setMode(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "Mode", 
                        Byte.TYPE,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);

        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.getOwnMode() == null;
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
