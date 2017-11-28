/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.sheet.properties.*;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.locale.I18n;
import java.util.List;
import net.sf.jasperreports.charts.design.JRDesignChartAxis;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class AxisPositionTypeProperty extends ByteProperty
{
    private final JRDesignChartAxis element;

    @SuppressWarnings("unchecked")
    public AxisPositionTypeProperty(JRDesignChartAxis element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignChartAxis.PROPERTY_POSITION;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.AxisPositionType");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.AxisPositionType.detail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignChartAxis.POSITION_LEFT_OR_TOP), I18n.getString("Global.Property.AxisPositionType.LeftTop")));
        tags.add(new Tag(new Byte(JRDesignChartAxis.POSITION_RIGHT_OR_BOTTOM), I18n.getString("Global.Property.AxisPositionType.RightBottom")));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getPosition();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getPosition();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignChartAxis.POSITION_LEFT_OR_TOP;
    }

    @Override
    public void setByte(Byte positionType)
    {
        element.setPosition(positionType);
    }

}
