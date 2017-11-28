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
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class JRPropertiesMapProperty  extends PropertySupport {

    PropertyEditor editor = null;
    JRPropertiesHolder propertiesHolder = null;
    
    @SuppressWarnings("unchecked")
    public JRPropertiesMapProperty(JRPropertiesHolder holder)
    {
       super( "properties", JRPropertiesMap.class, I18n.getString("JRPropertiesMapProperty.Property.Properties"),I18n.getString("JRPropertiesMapProperty.Property.Propertiesdetail"), true,true);
       setValue("canEditAsText", Boolean.FALSE);
       this.propertiesHolder = holder;
       if (holder instanceof JasperDesign)
       {
           setValue("reportProperties", Boolean.TRUE);
       }
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return propertiesHolder.getPropertiesMap().cloneProperties();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!(val instanceof JRPropertiesMap)) throw new IllegalArgumentException();
        
        // Fill this map with the content of the map we got here...
        ModelUtils.replacePropertiesMap((JRPropertiesMap)val, propertiesHolder.getPropertiesMap());
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
    }
    
    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new JRPropertiesMapPropertyEditor();
        }
        return editor;
    }
    
    
}

