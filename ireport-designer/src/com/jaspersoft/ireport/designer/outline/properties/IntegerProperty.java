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
import java.lang.reflect.InvocationTargetException;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class IntegerProperty extends PropertySupport.ReadWrite
{
    private final Object object;

    @SuppressWarnings("unchecked")
    public IntegerProperty(Object object)
    {
        super(
            null,
            Integer.class, 
            null, 
            null
            );
        this.object = object;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public void setValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        if (value instanceof Integer)//FIXMETD accept null too?
        {
            Integer oldValue = (Integer)getOwnValue();
            Integer newValue = (Integer)value;

            validate(newValue);

            setValue(newValue);

            ObjectPropertyUndoableEdit urob =
                new ObjectPropertyUndoableEdit(
                    object,
                    getDisplayName(), 
                    Integer.TYPE,
                    oldValue,
                    newValue
                    );
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    public IllegalArgumentException annotateException(String msg)
    {
        IllegalArgumentException e = new IllegalArgumentException(msg); 
        ErrorManager.getDefault().annotate(e, ErrorManager.EXCEPTION, msg, msg, null, null); 
        return e;
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

    public abstract Integer getOwnValue();

    public abstract Integer getDefaultValue();

    public abstract void setValue(Integer value);

    public abstract void validate(Integer value);
}
