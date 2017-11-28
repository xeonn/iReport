/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
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
        return I18n.getString("Global.Property.Backcolor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Backcolordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getBackcolor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnBackcolor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setBackcolor(color);
    }

}
