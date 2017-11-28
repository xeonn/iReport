/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseChart;
import net.sf.jasperreports.engine.design.JRDesignChart;


/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_POSITION property
 */
public final class TitlePositionProperty extends ByteProperty
{
    private final JRDesignChart chart;

    @SuppressWarnings("unchecked")
    public TitlePositionProperty(JRDesignChart chart)
    {
        super(chart);
        this.chart = chart;
    }

    @Override
    public String getName()
    {
        return JRBaseChart.PROPERTY_TITLE_POSITION;
    }

    @Override
    public String getDisplayName()
    {
        return "Title Position";
    }

    @Override
    public String getShortDescription()
    {
        return "Title Position.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_TOP), "Top"));
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_BOTTOM), "Bottom"));
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_LEFT), "Left"));
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_RIGHT), "Right"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return chart.getTitlePosition();
    }

    @Override
    public Byte getOwnByte()
    {
        return chart.getTitlePosition();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignChart.EDGE_TOP;
    }

    @Override
    public void setByte(Byte position)
    {
        chart.setTitlePosition(position);
    }

}
