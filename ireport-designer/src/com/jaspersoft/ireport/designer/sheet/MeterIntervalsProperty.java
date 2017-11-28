/*
 * ParameterNameProperty.java
 * 
 * Created on Sep 20, 2007, 10:12:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import com.jaspersoft.ireport.designer.sheet.editors.MeterIntervalsPropertyEditor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class MeterIntervalsProperty extends PropertySupport {

    PropertyEditor editor = null;
    
    @SuppressWarnings("unchecked")
    public MeterIntervalsProperty(String name,
                       String displayName,
                       String shortDescription)
    {
       super( name, List.class, displayName,shortDescription, true,true);
       setValue( "canEditAsText", Boolean.FALSE );
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return "";
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    }

    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new MeterIntervalsPropertyEditor();
        }
        return editor;
    }
    
}
