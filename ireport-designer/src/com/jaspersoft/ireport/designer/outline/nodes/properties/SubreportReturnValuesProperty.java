/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.editors.SubreportReturnValuesPropertyEditor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import org.openide.nodes.PropertySupport;

    
public final class SubreportReturnValuesProperty  extends PropertySupport {

    PropertyEditor editor = null;
    private final JRDesignDataset dataset;
    private final JRDesignSubreport element;

    @SuppressWarnings("unchecked")
    public SubreportReturnValuesProperty(JRDesignSubreport element, JRDesignDataset dataset)
    {
       super( JRDesignSubreport.PROPERTY_RETURN_VALUES, List.class, "Return Values","Subreport return values.", true,true);

       setValue("canEditAsText", Boolean.FALSE);
       setValue("expressionContext", new ExpressionContext(dataset));
       //setValue("subreport", element);
       this.element = element;
       this.dataset = dataset;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getReturnValuesList();
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || !(val instanceof List)) throw new IllegalArgumentException();

        // Fill this map with the content of the map we got here...
        // TODO: manage UNDO for a map object...
        List returnValues = (List)val;
        element.getReturnValuesList().clear();
        element.getReturnValuesList().addAll(returnValues);
        element.getEventSupport().firePropertyChange( JRDesignSubreport.PROPERTY_RETURN_VALUES , null, element.getReturnValuesList() );
    }

    @Override
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            editor = new SubreportReturnValuesPropertyEditor();
        }
        return editor;
    }


}
