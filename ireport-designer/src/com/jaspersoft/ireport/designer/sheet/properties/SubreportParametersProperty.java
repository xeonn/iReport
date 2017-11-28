/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.editors.SubreportParametersPropertyEditor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.nodes.PropertySupport;

    
public final class SubreportParametersProperty  extends PropertySupport {

    PropertyEditor editor = null;
    private final JRDesignDataset dataset;
    private final JRDesignSubreport element;

    @SuppressWarnings("unchecked")
    public SubreportParametersProperty(JRDesignSubreport element, JRDesignDataset dataset)
    {
       super( JRDesignSubreport.PROPERTY_PARAMETERS, Map.class, "Parameters","Subreport parameters", true,true);

       setValue("canEditAsText", Boolean.FALSE);
       setValue("expressionContext", new ExpressionContext(dataset));
       //setValue("subreport", element);
       this.element = element;
       this.dataset = dataset;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getParametersMap();
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || !(val instanceof Map)) throw new IllegalArgumentException();

        // Fill this map with the content of the map we got here...
        // TODO: manage UNDO for a map object...
        Map parameters = (Map)val;
        element.getParametersMap().clear();
        element.getParametersMap().putAll(parameters);
        element.getEventSupport().firePropertyChange( JRDesignSubreport.PROPERTY_PARAMETERS , null, element.getParametersMap() );
    }

    @Override
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            editor = new SubreportParametersPropertyEditor();
        }
        return editor;
    }


}
