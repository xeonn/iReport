/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.properties;

import com.jaspersoft.ireport.designer.outline.nodes.properties.*;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.PropertySupport;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class ColorProperty extends PropertySupport.ReadWrite 
{
    private final Object object;

    @SuppressWarnings("unchecked")
    public ColorProperty(Object object)
    {
        super(
            null,
            java.awt.Color.class,
            null,
            null
            );
        this.object = object;
    }

    @Override
    public void setValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        if (value == null || value instanceof Color)
        {
            Color oldValue = (Color)getOwnValue();
            Color newValue =  (Color)value;
            
            setValue(newValue);

            ObjectPropertyUndoableEdit urob =
                new ObjectPropertyUndoableEdit(
                    object,
                    getDisplayName(), 
                    Color.class,
                    oldValue,
                    newValue
                    );
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() 
    {
        Object value = null;

        try
        {
            value = getValue();
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }

        return
            (getDefaultValue() == null && value == null)
            || (getDefaultValue() != null && getDefaultValue().equals(value));
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException 
    {
        setValue(getDefaultValue());
    }

    @Override
    public boolean supportsDefaultValue() 
    {
        return true;
    }

    public abstract Color getOwnValue();

    public abstract Color getDefaultValue();

    public abstract void setValue(Color value);

}
