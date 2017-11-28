/*
 * ExpressionPropertyEditor.java
 * 
 * Created on Oct 12, 2007, 11:38:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.sheet.editors.JRPropertiesMapPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.editors.box.JRLineBoxPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.properties.AbstractProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRBoxContainer;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class JRLineBoxProperty  extends AbstractProperty {

    PropertyEditor editor = null;
    JRBoxContainer container = null;
    
    @SuppressWarnings("unchecked")
    public JRLineBoxProperty(JRBoxContainer container)
    {
       super(JRLineBox.class, container.getLineBox());
       setName("linebox");
       setDisplayName(I18n.getString("JRLineBoxProperty.Paddingandborders"));
       setShortDescription(I18n.getString("JRLineBoxProperty.Paddingandborders"));
       setValue("canEditAsText", Boolean.FALSE);
       this.container = container;
    }

    
    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new JRLineBoxPropertyEditor();
        }
        return editor;
    }

    @Override
    public Object getPropertyValue() {
        return container.getLineBox().clone(container);
    }

    @Override
    public Object getOwnPropertyValue() {
        return container.getLineBox().clone(container);
    }

    @Override
    public Object getDefaultValue() {
        return new JRBaseLineBox(container);
    }

    @Override
    public void validate(Object value) {
        
    }

    @Override
    public void setPropertyValue(Object value) {
        if (value != null && value instanceof JRLineBox)
        {
            ModelUtils.applyBoxProperties(container.getLineBox(), (JRLineBox)value);
        }
    }
    
    
}

