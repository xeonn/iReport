/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.BooleanProperty;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class CellModeProperty extends BooleanProperty
{
    private final JRDesignCellContents cellContents;

    @SuppressWarnings("unchecked")
    public CellModeProperty(JRDesignCellContents cellContents)
    {
        super(cellContents);
        this.cellContents = cellContents;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_MODE;
    }

    @Override
    public String getDisplayName()
    {
        return "Opaque";
    }

    @Override
    public String getShortDescription()
    {
        return "Set if the cell is opaque or transparent.";
    }

    @Override
    public Boolean getBoolean()
    {
        return cellContents.getMode() != null && cellContents.getMode() == JRElement.MODE_OPAQUE;
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return getBoolean();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isPrint)
    {
        cellContents.setMode(isPrint == null ? null : (isPrint ? JRElement.MODE_OPAQUE : JRElement.MODE_TRANSPARENT));
    }

}
