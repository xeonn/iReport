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
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
    
    
/**
 *  Class to manage the JRDesignThermometerPlot.PROPERTY_VALUE_LOCATION property
 */
public final class ThermometerValueLocationProperty extends ByteProperty
{
    private final JRDesignThermometerPlot plot;

    @SuppressWarnings("unchecked")
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
        return "Value Location";
    }

    @Override
    public String getShortDescription()
    {
        return "Value Location.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_BULB), "Bulb"));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_LEFT), "Left"));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_RIGHT), "Right"));
        tags.add(new Tag(new Byte(JRDesignThermometerPlot.LOCATION_NONE), "None"));
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
