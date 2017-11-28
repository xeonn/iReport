/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class PositionTypeProperty extends ByteProperty
{
    private final JRDesignElement element;

    @SuppressWarnings("unchecked")
    public PositionTypeProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_POSITION_TYPE;
    }

    @Override
    public String getDisplayName()
    {
        return "Position Type";
    }

    @Override
    public String getShortDescription()
    {
        return "How to anchor the element.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP), "Fix Relative to Top"));
        tags.add(new Tag(new Byte(JRDesignElement.POSITION_TYPE_FLOAT), "Float"));
        tags.add(new Tag(new Byte(JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_BOTTOM), "Fix Relative to Bottom"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getPositionType();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getPositionType();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRDesignElement.POSITION_TYPE_FIX_RELATIVE_TO_TOP;
    }

    @Override
    public void setByte(Byte positionType)
    {
        element.setPositionType(positionType);
    }

}
