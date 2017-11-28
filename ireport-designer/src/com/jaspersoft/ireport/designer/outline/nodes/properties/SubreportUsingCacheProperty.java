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
import net.sf.jasperreports.engine.base.JRBaseSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.nodes.PropertySupport;

/**
 *  Class to manage the JRDesignTextElement.PROPERTY_ITALIC property
 * @author gtoffoli
 */
public final class SubreportUsingCacheProperty extends PropertySupport.ReadWrite {

    private final JRDesignSubreport element;

    @SuppressWarnings("unchecked")
    public SubreportUsingCacheProperty(JRDesignSubreport element)
    {
        super(JRBaseSubreport.PROPERTY_USING_CACHE, Boolean.class,
              "Use Cache",
              "Set if the subreport must be cached when loaded.");
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
            Boolean newValue = (Boolean)val;
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
