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
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
    
    
/**
 *  Class to manage the JRDesignMeterPlot.PROPERTY_SHAPE property
 */
public final class MeterShapeProperty extends ByteProperty
{
    private final JRDesignMeterPlot element;

    @SuppressWarnings("unchecked")
    public MeterShapeProperty(JRDesignMeterPlot element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignMeterPlot.PROPERTY_SHAPE;
    }

    @Override
    public String getDisplayName()
    {
        return "Shape";
    }

    @Override
    public String getShortDescription()
    {
        return "Shape.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_PIE), "Pie"));
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_CIRCLE), "Circle"));
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_CHORD), "Chord"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getShape();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getShape();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignMeterPlot.SHAPE_PIE;
    }

    @Override
    public void setByte(Byte position)
    {
        try
        {
            element.setShape(position);
        }
        catch(JRException e)
        {
            throw new JRRuntimeException(e);
        }
    }
    
}
