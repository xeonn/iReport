/*
 * ParameterNameProperty.java
 * 
 * Created on Sep 20, 2007, 10:12:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import com.jaspersoft.ireport.designer.sheet.editors.JRPenPropertyEditor;
import com.jaspersoft.ireport.designer.sheet.properties.AbstractProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPenContainer;
import net.sf.jasperreports.engine.base.JRBasePen;

/**
 *
 * @author gtoffoli
 */
public class JRPenProperty extends AbstractProperty {

    JRPen pen = null;
    JRPenPropertyEditor editor = null;
    JRPenContainer container = null;
    
    @SuppressWarnings("unchecked")
    public JRPenProperty(JRPen pen, JRPenContainer container)
    {
       super(JRPen.class, pen);
       setName("pen");
       setDisplayName(I18n.getString("PenProperty.Property.Pen"));        
       setShortDescription(I18n.getString("JRPenProperty.Property.detail"));
       setValue( "canEditAsText", Boolean.FALSE );
       this.pen = pen;
       this.container = container;
    }

    public void setPen(JRPen mpen)
    {
        if (mpen != null)
        {
            pen.setLineColor( mpen.getOwnLineColor());
            pen.setLineWidth( mpen.getOwnLineWidth());
            pen.setLineStyle( mpen.getOwnLineStyle());
        }
        else
        {
            pen.setLineColor( null );
            pen.setLineWidth( null );
            pen.setLineStyle( null );
        }
    }
    
    @Override
    public boolean isDefaultValue() {
        
        if (pen == null) return true;
        
        if (pen.getOwnLineColor() != null) return false;
        if (pen.getOwnLineWidth() != null) return false;
        if (pen.getOwnLineStyle() != null) return false;
        
        return true;
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }
    
    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new JRPenPropertyEditor();
        }
        return editor;
    }

    @Override
    public Object getPropertyValue() {
        return pen.clone(container);
    }

    @Override
    public Object getOwnPropertyValue() {
        return pen.clone(container);
    }

    @Override
    public Object getDefaultValue() {
        return new JRBasePen(null);
    }

    @Override
    public void validate(Object value) {
        
    }

    @Override
    public void setPropertyValue(Object value) {
        
        if (value instanceof JRPen)
        {
            setPen((JRPen)value);
        }
        else
        {
           setPen(null);
        }
    }
    
}
