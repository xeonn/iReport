/*
 * ParameterNameProperty.java
 * 
 * Created on Sep 20, 2007, 10:12:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import com.jaspersoft.ireport.designer.sheet.editors.JRFontPropertyEditor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class JRFontProperty extends PropertySupport {

    PropertyEditor editor = null;
    JasperDesign jasperDesign = null;
    
    @SuppressWarnings("unchecked")
    public JRFontProperty(String name,
                       String displayName,
                       String shortDescription,
                       JasperDesign jd)
    {
       super( name, JRFont.class, displayName,shortDescription, true,true);
       
       setValue( "canEditAsText", Boolean.FALSE );
       this.jasperDesign = jd;
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
            editor = new JRFontPropertyEditor(jasperDesign);
        }
        return editor;
    }
    
}
