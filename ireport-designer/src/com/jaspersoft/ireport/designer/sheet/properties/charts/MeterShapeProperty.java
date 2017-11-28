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
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
    
    
/**
 *  Class to manage the JRDesignMeterPlot.PROPERTY_SHAPE property
 */
public final class MeterShapeProperty extends ByteProperty
{
    private final JRDesignMeterPlot element;

    
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
        return I18n.getString("Shape");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Shape.");
    }

    @Override
    public List getTagList() 
    {
        List tags = new ArrayList();
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_PIE), I18n.getString("Pie")));
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_CIRCLE), I18n.getString("Circle")));
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_CHORD), I18n.getString("Chord")));
        tags.add(new Tag(new Byte(JRDesignMeterPlot.SHAPE_DIAL), I18n.getString("Dial")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getShapeByte();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getShapeByte();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
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
