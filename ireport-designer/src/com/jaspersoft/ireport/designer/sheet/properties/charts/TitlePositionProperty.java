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
import com.jaspersoft.ireport.locale.I18n;
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
        return I18n.getString("Global.Property.TitlePosition");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.TitlePosition");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_TOP), I18n.getString("Global.Property.Top")));
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_BOTTOM), I18n.getString("Global.Property.Bottom")));
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Byte(JRDesignChart.EDGE_RIGHT), I18n.getString("Global.Property.Right")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return chart.getTitlePositionByte();
    }

    @Override
    public Byte getOwnByte()
    {
        return chart.getTitlePositionByte();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte position)
    {
        chart.setTitlePosition(position);
    }

}
