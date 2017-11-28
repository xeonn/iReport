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
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseImage;
import net.sf.jasperreports.engine.design.JRDesignImage;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
 */
public final class LazyProperty extends PropertySupport.ReadWrite {

    private final JRDesignImage element;

    @SuppressWarnings("unchecked")
    public LazyProperty(JRDesignImage element)
    {
        super(JRBaseImage.PROPERTY_LAZY, Boolean.class,
              "Is Lazy",
              "If set to true, this property avoid the load of the image at fill time.");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return new Boolean( element.isLazy());
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val != null && val instanceof Boolean)
        {
            Boolean oldValue = element.isLazy();
            Boolean newValue =   (Boolean)val;
            element.setLazy(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "Lazy", 
                        Boolean.TYPE,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);

        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.isLazy() == false;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue( false );
    }

    @Override
    public boolean supportsDefaultValue() {
       return true;
    }

}
