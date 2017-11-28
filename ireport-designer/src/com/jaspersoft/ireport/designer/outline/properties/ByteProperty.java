/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.openide.nodes.PropertySupport;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class ByteProperty extends PropertySupport.ReadWrite
{
    private final Object object;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public ByteProperty(Object object)
    {
        super(
            null,
            Byte.class, 
            null, 
            null
            );
        this.object = object;
        setValue("suppressCustomEditor", Boolean.TRUE);
        editor = new ComboBoxPropertyEditor(false, getTagList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() 
    {
        return editor;
    }

    @Override
    public void setValue(Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        if (value instanceof Byte)
        {
            Byte oldValue = (Byte)getValue();
            Byte newValue = (Byte)value;

            setValue(newValue);

            ObjectPropertyUndoableEdit urob =
                new ObjectPropertyUndoableEdit(
                    object,
                    getDisplayName(), 
                    Byte.TYPE,
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

    public abstract List getTagList();

    public abstract Byte getDefaultValue();

    public abstract void setValue(Byte value);
}
