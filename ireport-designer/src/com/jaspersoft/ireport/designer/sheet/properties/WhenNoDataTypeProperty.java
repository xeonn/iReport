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
        return "When No Data Type";
    }

    @Override
    public String getShortDescription()
    {
        return "What to display when there are no records in the data source.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL), "All Sections, No Detail"));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_BLANK_PAGE), "Blank Page"));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_NO_DATA_SECTION), "No Data Section"));
        tags.add(new Tag(new Byte(JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES), "No Pages"));
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
