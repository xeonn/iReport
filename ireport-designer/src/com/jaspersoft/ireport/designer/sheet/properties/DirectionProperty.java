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
import com.jaspersoft.ireport.locale.I18n;
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
        return I18n.getString("Global.Property.Direction");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.Directiondetail");
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignLine.DIRECTION_TOP_DOWN), I18n.getString("Global.Property.TopDown")));
        tags.add(new Tag(new Byte(JRDesignLine.DIRECTION_BOTTOM_UP), I18n.getString("Global.Property.BottomUp")));
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
