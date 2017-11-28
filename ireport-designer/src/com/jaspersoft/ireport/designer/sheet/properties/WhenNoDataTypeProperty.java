/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *  Class to manage the WhenNoDataType property
 */
public final class WhenNoDataTypeProperty extends ByteProperty
{
    private final JasperDesign jasperDesign;

    @SuppressWarnings("unchecked")
    public WhenNoDataTypeProperty(JasperDesign jasperDesign)
    {
        super(jasperDesign);
        this.jasperDesign = jasperDesign;
    }

    @Override
    public String getName()
    {
        return JasperDesign.PROPERTY_WHEN_NO_DATA_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("WhenNoDataTypeProperty.Property");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("WhenNoDataTypeProperty.Property.Message");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL), I18n.getString("WhenNoDataTypeProperty.Property.All")));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_BLANK_PAGE), I18n.getString("WhenNoDataTypeProperty.Property.Blank")));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_NO_DATA_SECTION), I18n.getString("WhenNoDataTypeProperty.Property.Section")));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES), I18n.getString("WhenNoDataTypeProperty.Property.Pages")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return jasperDesign.getWhenNoDataType();
    }

    @Override
    public Byte getOwnByte()
    {
        return jasperDesign.getWhenNoDataType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES;
    }

    @Override
    public void setByte(Byte type)
    {
        jasperDesign.setWhenNoDataType(type);
    }

}
