/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.properties;

import com.jaspersoft.ireport.designer.jrctx.nodes.editors.PaintProviderPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.properties.*;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class PaintProviderProperty extends AbstractProperty
{
    private PaintProviderPropertyEditor editor = null;
    
    @SuppressWarnings("unchecked")
    public PaintProviderProperty(Object object)
    {
        super(PaintProvider.class, object);
        
        setValue("canEditAsText", Boolean.FALSE);
    }

    @Override
    public Object getPropertyValue()
    {
        return getPaintProvider();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getOwnPaintProvider();
    }

    @Override
    public Object getDefaultValue()
    {
        return getDefaultPaintProvider();
    }

    @Override
    public PropertyEditor getPropertyEditor()
    {
        if(editor == null)
        {
            editor = new PaintProviderPropertyEditor();
        }
        return editor;
    }

    @Override
    public void validate(Object value)
    {
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setPropertyValue(getDefaultPaintProvider());
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setPaintProvider((PaintProvider)value);
    }

    public abstract PaintProvider getPaintProvider();

    public abstract PaintProvider getOwnPaintProvider();

    public abstract PaintProvider getDefaultPaintProvider();

    public abstract void setPaintProvider(PaintProvider paintProvider);

}
