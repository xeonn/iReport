/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.editors.JRFontPropertyEditor;
import java.beans.PropertyEditor;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractFontProperty extends AbstractProperty
{
    protected JasperDesign jasperDesign = null;
    protected PropertyEditor editor = null;
    
    @SuppressWarnings("unchecked")
    public AbstractFontProperty(Object object, JasperDesign jasperDesign)
    {
        super(JRFont.class, object);
        this.jasperDesign = jasperDesign;
        setValue( "canEditAsText", Boolean.FALSE );
    }

    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new JRFontPropertyEditor(jasperDesign);
        }
        return editor;
    }

    @Override
    public Object getValue()
    {
        return getPropertyValue();
    }

    @Override
    public Object getPropertyValue()
    {
        return getFont();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getFont();
    }

    @Override
    public Object getDefaultValue()
    {
        return null;
    }

    @Override
    public void validate(Object value)
    {
    }

    @Override
    public void setPropertyValue(Object value)
    {
        setFont((JRFont)value);
    }

    public abstract JRFont getFont();

    public abstract void setFont(JRFont font);
}
