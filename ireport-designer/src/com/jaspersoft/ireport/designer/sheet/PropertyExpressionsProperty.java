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
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.editors.JRPropertiesMapPropertyEditor;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import org.openide.nodes.PropertySupport;

/**
 * This class should be able to handle both common properties
 * and expression based properties.
 * 
 * @author gtoffoli
 */
public class PropertyExpressionsProperty  extends PropertySupport {

    PropertyEditor editor = null;
    JRDesignElement element = null;
    
    @SuppressWarnings("unchecked")
    public PropertyExpressionsProperty(JRDesignElement element, JRDesignDataset dataset)
    {
       super( "properties", List.class, "Properties expressions","List of property expressions for this element", true,true);
       setValue("canEditAsText", Boolean.FALSE);
       setValue("useList", Boolean.TRUE);
       setValue("canUseExpression", Boolean.TRUE);
       this.setValue(ExpressionContext.ATTRIBUTE_EXPRESSION_CONTEXT, new ExpressionContext(dataset));
       
       this.element = element;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        
        JRPropertiesMap map = element.getPropertiesMap();
        List properties = new ArrayList();
        String[] names = map.getPropertyNames();
        
        for (int i=0; i<names.length; ++i)
        {
            properties.add(new GenericProperty(names[i], map.getProperty(names[i])));
        }
        
        // add to the list the expression properties...
        JRPropertyExpression[] expProperties = element.getPropertyExpressions();
        for (int i=0; expProperties != null &&  i<expProperties.length; ++i)
        {
            properties.add(new GenericProperty(expProperties[i].getName(), (JRDesignExpression)expProperties[i].getValueExpression()));
        }
        
        return properties;
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //if (!(val instanceof JRPropertiesMap)) throw new IllegalArgumentException();
        
        if (!(val instanceof List)) throw new IllegalArgumentException();
        
        // Fill this map with the content of the map we got here...
        
        // 1. Create the map...
        JRPropertiesMap map = new JRPropertiesMap();
        List values = (List)val;
        for (int i=0; i <values.size(); ++i)
        {
            GenericProperty prop = (GenericProperty)values.get(i);
            if (!prop.isUseExpression())
            {
                map.setProperty(prop.getKey(), (String)prop.getValue());
            }
        }
        
        ModelUtils.replacePropertiesMap(map, element.getPropertiesMap());
        ModelUtils.replaceExpressionProperties(element, values);
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

