/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractStyleProperty;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE property
 */
public final class StyleProperty extends AbstractStyleProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public StyleProperty(JRDesignElement element, JasperDesign jasperDesign)
    {
        super(element, jasperDesign);
        this.element = element;
    }

    @Override
    public String getStyleNameReference() 
    {
        return element.getStyleNameReference();
    }

    @Override
    public void setStyleNameReference(String s) 
    {
        element.setStyleNameReference(s);
    }

    @Override
    public JRStyle getStyle() 
    {
        return element.getStyle();
    }

    @Override
    public void setStyle(JRStyle s) 
    {
        element.setStyle(s);
    }
        
}

