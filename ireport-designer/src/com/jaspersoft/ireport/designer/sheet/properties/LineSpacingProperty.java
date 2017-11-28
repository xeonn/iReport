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
import com.jaspersoft.ireport.designer.sheet.properties.ByteProperty;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class LineSpacingProperty extends ByteProperty
{
    private final JRDesignTextElement element;

    @SuppressWarnings("unchecked")
    public LineSpacingProperty(JRDesignTextElement element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_LINE_SPACING;
    }

    @Override
    public String getDisplayName()
    {
        return "Line Spacing";
    }

    @Override
    public String getShortDescription()
    {
        return "The space to put between lines of text.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_SINGLE), "Single"));
        tags.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_1_1_2), "1.5"));
        tags.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_DOUBLE), "Double"));
        return tags;
    }

    @Override
    public Byte getByte()
    {
        return element.getLineSpacing();
    }

    @Override
    public Byte getOwnByte()
    {
        return element.getOwnLineSpacing();
    }

    @Override
    public Byte getDefaultByte()
    {
        return null;
    }

    @Override
    public void setByte(Byte lineSpacing)
    {
        element.setLineSpacing(lineSpacing);
    }

}
