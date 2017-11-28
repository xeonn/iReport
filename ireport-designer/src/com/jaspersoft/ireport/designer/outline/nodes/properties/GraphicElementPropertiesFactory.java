/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.outline.properties.EvaluationTimeProperty;
import com.jaspersoft.ireport.designer.outline.properties.RadiusProperty;
import com.jaspersoft.ireport.designer.outline.properties.DirectionProperty;
import com.jaspersoft.ireport.designer.ModelUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignEllipse;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignRectangle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;

/**
 *
 * @author gtoffoli
 */
public class GraphicElementPropertiesFactory {

    /**
     * Get the GraphicElement properties...
     */
    public static List<Sheet.Set> getGraphicPropertySets(JRDesignGraphicElement element, JasperDesign jd)
    {
        JRDesignDataset dataset = ModelUtils.getElementDataset(element, jd);
        
        List<Sheet.Set> list = new ArrayList<Sheet.Set>();
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("GRAPHIC_ELEMENT_PROPERTIES");
        propertySet.setDisplayName("Graphic properties");
        //propertySet.put(new PenProperty( element ));
        propertySet.put(new FillProperty( element ));
        list.add(propertySet);
        
        if (element instanceof JRDesignImage)
        {
            Sheet.Set imagePropertySet = Sheet.createPropertiesSet();
            imagePropertySet.setName("IMAGE_ELEMENT_PROPERTIES");
            imagePropertySet.setDisplayName("Image properties");
            imagePropertySet.put(new ImageExpressionProperty((JRDesignImage)element, dataset));
            imagePropertySet.put(new ImageExpressionClassNameProperty((JRDesignImage)element) );
            imagePropertySet.put(new ScaleImageProperty( (JRDesignImage)element ));
            imagePropertySet.put(new HorizontalAlignmentProperty( (JRDesignImage)element ));
            imagePropertySet.put(new VerticalAlignmentProperty( (JRDesignImage)element ));
            imagePropertySet.put(new ImageUsingCacheProperty( (JRDesignImage)element ));
            imagePropertySet.put(new LazyProperty( (JRDesignImage)element ));
            imagePropertySet.put(new OnErrorTypeProperty( (JRDesignImage)element ));
            imagePropertySet.put(new EvaluationTimeProperty((JRDesignImage)element));//, dataset));
            imagePropertySet.put(new EvaluationGroupProperty((JRDesignImage)element, dataset));
            list.add(imagePropertySet);
        }
        else if (element instanceof JRDesignLine)
        {
            Sheet.Set linePropertySet = Sheet.createPropertiesSet();
            linePropertySet.setName("LINE_ELEMENT_PROPERTIES");
            linePropertySet.setDisplayName("Line properties");
            linePropertySet.put(new DirectionProperty( (JRDesignLine)element ));
            list.add(linePropertySet);
        }
        else if (element instanceof JRDesignRectangle)
        {
            Sheet.Set rectanglePropertySet = Sheet.createPropertiesSet();
            rectanglePropertySet.setName("RECTANGLE_ELEMENT_PROPERTIES");
            rectanglePropertySet.setDisplayName("Rectangle properties");
            rectanglePropertySet.put(new RadiusProperty( (JRDesignRectangle)element ));
            list.add(rectanglePropertySet);
        }
        else if (element instanceof JRDesignEllipse)
        {
            // Nothing to do...
        }
        
        return list;
    }
    
    
}
