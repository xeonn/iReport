/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.SubreportUsingCacheProperty;
import com.jaspersoft.ireport.designer.sheet.properties.SubreportParametersProperty;
import com.jaspersoft.ireport.designer.sheet.properties.SubreportExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.ParametersMapExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.DataSourceExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.properties.ConnectionExpressionProperty;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.sheet.properties.RunToBottomProperty;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;

/**
 *
 * @author gtoffoli
 */
public class SubreportPropertiesFactory {

    
    
    /**
     * Get the static text properties...
     */
    public static Sheet.Set getSubreportPropertySet(JRDesignSubreport element, JasperDesign jd)
    {
        
        JRDesignDataset dataset = ModelUtils.getElementDataset(element, jd);
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("SUBREPORT_ELEMENT_PROPERTIES");
        propertySet.setDisplayName("Subreport properties");
        propertySet.put(new SubreportExpressionProperty(element, dataset));
        propertySet.put(new SubreportExpressionClassNameProperty(element));
        propertySet.put(new SubreportUsingCacheProperty(element));
        propertySet.put(new RunToBottomProperty(element));
        propertySet.put(new ParametersMapExpressionProperty(element, dataset));
        propertySet.put(new ConnectionTypeProperty(element) );
        propertySet.put(new ConnectionExpressionProperty(element, dataset));
        propertySet.put(new DataSourceExpressionProperty(element, dataset));
        propertySet.put(new SubreportParametersProperty(element, dataset));
        propertySet.put(new SubreportReturnValuesProperty(element, dataset));
        
        //propertySet.put(new LeftProperty( element ));
        return propertySet;
    }
    
    /**
     * Convenient way to get all the properties of an element.
     * Properties positions could be reordered to have a better order.
     */
    public static List<Sheet.Set> getPropertySets(JRDesignElement element, JasperDesign jd)
    {
        List<Sheet.Set> sets = new ArrayList<Sheet.Set>();
        
        if (element instanceof  JRDesignSubreport)
        {
            sets.add( getSubreportPropertySet((JRDesignSubreport)element, jd ));
        }
        
        return sets;
    }
    
    
}
