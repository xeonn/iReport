/*
 * ExpressionPropertyEditor.java
 * 
 * Created on Oct 12, 2007, 11:38:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet;

import com.jaspersoft.ireport.designer.sheet.editors.JRImportsPropertyEditor;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author gtoffoli
 */
public class JRImportsProperty  extends PropertySupport {

    PropertyEditor editor = null;
    JasperDesign jd = null;
    
    @SuppressWarnings("unchecked")
    public JRImportsProperty(JasperDesign jd)
    {
       super( "imports", (new String[0]).getClass(), I18n.getString("Property.Imports"),I18n.getString("Property.Imports.detail"), true,true);
       setValue("canEditAsText", Boolean.FALSE);
       this.jd = jd;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return jd.getImports();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!(val != null && val.getClass().isArray() && val.getClass().getComponentType().equals(String.class) )) throw new IllegalArgumentException();

        if (jd.getImports() == val) return;

        // Fill this map with the content of the map we got here...
        String[] originalImports = jd.getImports();
        for (int i=0; originalImports != null && i<originalImports.length; ++i )
        {
            jd.removeImport(originalImports[i]);
        }

        String[] newImports = (String[])val;
        for (int i=0; newImports != null && i<newImports.length; ++i )
        {
            jd.addImport(newImports[i]);
        }


        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
    }
    
    @Override
    public PropertyEditor getPropertyEditor() {
        
        if (editor == null)
        {
            editor = new JRImportsPropertyEditor();
        }
        return editor;
    }
    
    
}

