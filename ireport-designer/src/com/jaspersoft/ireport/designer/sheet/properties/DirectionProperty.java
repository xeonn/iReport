/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import java.util.List;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.base.JRBaseLine;
import net.sf.jasperreports.engine.design.JRDesignLine;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class DirectionProperty extends ByteProperty
{
    private final JRDesignLine line;

    @SuppressWarnings("unchecked")
    public DirectionProperty(JRDesignLine line)
    {
        super(line);
        this.line = line;
    }

    @Override
    public String getName()
    {
        return JRBaseLine.PROPERTY_DIRECTION;
    }

    @Override
    public String getDisplayName()
    {
        return "Direction";
    }

    @Override
    public String getShortDescription()
    {
        return "The line direction.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignLine.DIRECTION_TOP_DOWN), "Top Down (\\)"));
        tags.add(new Tag(new Byte(JRDesignLine.DIRECTION_BOTTOM_UP), "Bottom Up (/)"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return line.getDirection();
    }

    @Override
    public Byte getOwnByte()
    {
        return line.getDirection();
    }

    @Override
    public Byte getDefaultByte()
    {
        return JRLine.DIRECTION_TOP_DOWN;
    }

    @Override
    public void setByte(Byte direction)
    {
        line.setDirection(direction);
    }

}
