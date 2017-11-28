/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.*;
import java.beans.PropertyEditor;
import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.engine.JRChart;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractItemLabelProperty extends AbstractProperty
{
    protected JRChart chart = null;
    protected PropertyEditor editor = null;
    
    @SuppressWarnings("unchecked")
    public AbstractItemLabelProperty(Object object, JRChart chart)
    {
        super(JRItemLabel.class, object);
        this.chart = chart;
        setValue( "canEditAsText", Boolean.FALSE );
    }

    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new JRItemLabelPropertyEditor(chart);
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
        return getItemLabel();
    }

    @Override
    public Object getOwnPropertyValue()
    {
        return getItemLabel();
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
        setItemLabel((JRItemLabel)value);
    }

    public abstract JRItemLabel getItemLabel();

    public abstract void setItemLabel(JRItemLabel font);
}
