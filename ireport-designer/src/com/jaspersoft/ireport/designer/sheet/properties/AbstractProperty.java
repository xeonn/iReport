/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.PropertyUndoableEdit;
import java.lang.reflect.InvocationTargetException;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractProperty extends PropertySupport.ReadWrite
{
    protected final Object object;



    @SuppressWarnings("unchecked")
    public AbstractProperty(Class clazz, Object object)
    {
        super(null, clazz, null, null);
        this.object = object;
    }

    @Override
    public Object getValue()
    {
        Object value = getPropertyValue();
        return value == null ? "" : value;
    }

    @Override
    public void setValue(Object newValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        Object oldValue = getOwnPropertyValue();

        validate(newValue);
        
        setPropertyValue(newValue);

        PropertyUndoableEdit undo =
            new PropertyUndoableEdit(
                this,
                oldValue,
                newValue
                );
        IReportManager.getInstance().addUndoableEdit(undo);
    }

    @Override
    public boolean isDefaultValue() 
    {
        Object value = getPropertyValue();

        return
            (getDefaultValue() == null && value == null)
            || (getDefaultValue() != null && getDefaultValue().equals(value));
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException 
    {
        //FIXMETD check this super.restoreDefaultValue();
        setValue(getDefaultValue());
    }

    @Override
    public boolean supportsDefaultValue() 
    {
        return true;
    }

    public IllegalArgumentException annotateException(String msg)
    {
        IllegalArgumentException iae = new IllegalArgumentException(msg); 
        ErrorManager.getDefault().annotate(
            iae, 
            ErrorManager.EXCEPTION,
            msg,
            msg, null, null
            ); 
        return iae;
    }

    public abstract Object getPropertyValue();

    public abstract Object getOwnPropertyValue();

    public abstract Object getDefaultValue();

    public abstract void validate(Object value);

    public abstract void setPropertyValue(Object value);

}
