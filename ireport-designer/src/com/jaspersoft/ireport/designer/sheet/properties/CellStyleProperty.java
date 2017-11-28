/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.AbstractStyleProperty;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class CellStyleProperty extends AbstractStyleProperty
{
    private final JRDesignCellContents cell;

    @SuppressWarnings("unchecked")
    public CellStyleProperty(JRDesignCellContents cell, JasperDesign jd)
    {
        super(cell, jd);
        setName( JRDesignCellContents.PROPERTY_STYLE_NAME_REFERENCE);
        this.cell = cell;
    }

    @Override
    public String getStyleNameReference() 
    {
        return cell.getStyleNameReference();
    }

    @Override
    public void setStyleNameReference(String s) 
    {
        cell.setStyleNameReference(s);
    }

    @Override
    public JRStyle getStyle() 
    {
        return cell.getStyle();
    }

    @Override
    public void setStyle(JRStyle s) 
    {
        cell.setStyle(s);
    }

}
