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
public final class ImageUsingCacheProperty extends PropertySupport.ReadWrite {

    private final JRDesignImage element;

    @SuppressWarnings("unchecked")
    public ImageUsingCacheProperty(JRDesignImage element)
    {
        super(JRBaseImage.PROPERTY_USING_CACHE, Boolean.class,
              "Using Cache",
              "Set if an the image should be cached filling the report.");
        this.element = element;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.isUsingCache();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val == null || val instanceof Boolean)
        {
            Boolean oldValue = element.isOwnUsingCache();
            Boolean newValue =   (Boolean)val;
            element.setUsingCache(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "UsingCache", 
                        Boolean.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);

        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.isOwnUsingCache() == null;
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
