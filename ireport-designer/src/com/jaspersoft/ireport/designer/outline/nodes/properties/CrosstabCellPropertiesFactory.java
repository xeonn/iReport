/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.sheet.properties.CellBackcolorProperty;
import com.jaspersoft.ireport.designer.sheet.properties.CellModeProperty;
import com.jaspersoft.ireport.designer.sheet.properties.CellStyleProperty;
import com.jaspersoft.ireport.designer.ModelUtils;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Sheet;

/**
 *
 * @author gtoffoli
 */
public class CrosstabCellPropertiesFactory {

    /**
     * Get the GraphicElement properties...
     */
    public static List<Sheet.Set> getCrosstabCellPropertySets(JRDesignCellContents cell, JasperDesign jd)
    {
        JRDesignDataset dataset = ModelUtils.getElementDataset(cell.getOrigin().getCrosstab(), jd);
        
        List<Sheet.Set> list = new ArrayList<Sheet.Set>();
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("CELL_PROPERTIES");
        propertySet.setDisplayName("Cell properties");
        propertySet.put(new CellModeProperty( cell ));
        propertySet.put(new CellBackcolorProperty( cell ));
        propertySet.put(new CellStyleProperty( cell, jd ));
        
        list.add(propertySet);
        
        return list;
    }
    
}
