/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.ColorProperty;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class CellBackcolorProperty extends ColorProperty
{
    private final JRDesignCellContents cellContents;

    @SuppressWarnings("unchecked")
    public CellBackcolorProperty(JRDesignCellContents cellContents)
    {
        super(cellContents);
        this.cellContents = cellContents;
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
        return I18n.getString("Global.Property.backgrounddetail");
    }

    @Override
    public Color getColor() 
    {
        return cellContents.getBackcolor();
    }

    @Override
    public Color getOwnColor()
    {
        return getColor();
    }

    @Override
    public Color getDefaultColor()
    {
        return null;
    }

    @Override
    public void setColor(Color color)
    {
        cellContents.setBackcolor(color);
    }

}
