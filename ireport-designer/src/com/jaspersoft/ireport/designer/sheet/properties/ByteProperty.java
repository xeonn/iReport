/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import java.beans.PropertyEditor;
import java.util.List;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class ByteProperty extends AbstractProperty
{
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public ByteProperty(Object object)
    {
        super(Byte.class, object);
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() 
    {
        if (editor == null)
        {
            editor = new ComboBoxPropertyEditor(false, getTagList());
        }
        return editor;
    }

    @Override
    public Object getPropertyValue()
    {
        return getByte();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnByte();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultByte();
    }

    @Override
    public void validate(Object value)
    {
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setByte((Byte)value);
    }

    public abstract List getTagList();

    public abstract Byte getByte();

    public abstract Byte getOwnByte();

    public abstract Byte getDefaultByte();

    public abstract void setByte(Byte value);
}
