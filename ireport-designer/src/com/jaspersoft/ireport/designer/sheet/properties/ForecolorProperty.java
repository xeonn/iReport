/*
 * ForecolorProperty.java
 * 
 * Created on 20-feb-2008, 23.03.43
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
 * Class to manage the JRDesignElement.PROPERTY_FORECOLOR property
 * 
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public final class ForecolorProperty extends ColorProperty 
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public ForecolorProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_FORECOLOR;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.Forecolor");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Forecolordetail");
    }

    @Override
    public Color getColor() 
    {
        return element.getForecolor();
    }

    @Override
    public Color getOwnColor()
    {
        return element.getOwnForecolor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        element.setForecolor(color);
    }

}
