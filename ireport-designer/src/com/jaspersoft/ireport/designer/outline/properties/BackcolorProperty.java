/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.properties;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class BackcolorProperty extends ColorProperty 
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public BackcolorProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_BACKCOLOR;
    }

    @Override
    public String getDisplayName()
    {
        return "Backcolor";
    }

    @Override
    public String getShortDescription()
    {
        return "The background color.";
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException 
    {
        return element.getBackcolor();
    }

    @Override
    public Color getOwnValue()
    {
        return element.getOwnBackcolor();
    }

    @Override
    public Color getDefaultValue()
    {
        return null;
    }

    @Override
    public void setValue(Color color)
    {
        element.setBackcolor(color);
    }

}
