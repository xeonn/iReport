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
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;

    
/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class FillProperty extends ByteProperty
{
    private final JRDesignGraphicElement element;

    @SuppressWarnings("unchecked")
    public FillProperty(JRDesignGraphicElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_FILL;
    }

    @Override
    public String getDisplayName()
    {
        return "Fill";
    }

    @Override
    public String getShortDescription()
    {
        return "The fill mode.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRGraphicElement.FILL_SOLID), "Solid"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getFill();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnFill();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte fill)
    {
        element.setFill(fill);
    }

}
