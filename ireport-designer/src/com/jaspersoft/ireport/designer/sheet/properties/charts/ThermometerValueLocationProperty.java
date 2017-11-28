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
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
    
    
/**
 *  Class to manage the JRDesignThermometerPlot.PROPERTY_VALUE_LOCATION property
 */
public final class ThermometerValueLocationProperty extends ByteProperty
{
    private final JRDesignThermometerPlot plot;

    
    public ThermometerValueLocationProperty(JRDesignThermometerPlot plot)
    {
        super(plot);
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignThermometerPlot.PROPERTY_VALUE_LOCATION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ValueLocation");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ValueLocation");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_BULB), I18n.getString("Global.Property.Bulb")));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_LEFT), I18n.getString("Global.Property.Left")));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_RIGHT), I18n.getString("Global.Property.Right")));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_NONE), I18n.getString("Global.Property.None")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return plot.getValueLocation();
    }

    @Override
    public Byte getOwnByte()
    {
        return plot.getValueLocation();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignThermometerPlot.LOCATION_BULB;
    }

    @Override
    public void setByte(Byte position)
    {
        plot.setValueLocation(position);
    }
    
}
