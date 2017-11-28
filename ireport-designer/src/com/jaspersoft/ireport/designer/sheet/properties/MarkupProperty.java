/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import java.util.List;
import net.sf.jasperreports.engine.JRCommonText;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class MarkupProperty extends StringListProperty
{
    private final JRDesignTextElement textElement;

    @SuppressWarnings("unchecked")
    public MarkupProperty(JRDesignTextElement textElement)
    {
        super(textElement);
        this.textElement = textElement;
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_MARKUP;
    }

    @Override
    public String getDisplayName()
    {
        return "Markup";
    }

    @Override
    public String getShortDescription()
    {
        return "The markup language used inside the text element.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(JRCommonText.MARKUP_NONE, JRCommonText.MARKUP_NONE));
        tags.add(new Tag(JRCommonText.MARKUP_STYLED_TEXT, JRCommonText.MARKUP_STYLED_TEXT));
        tags.add(new Tag(JRCommonText.MARKUP_RTF, JRCommonText.MARKUP_RTF));
        tags.add(new Tag(JRCommonText.MARKUP_HTML, JRCommonText.MARKUP_HTML));
        return tags;
    }

    @Override
    public String getString()
    {
        return textElement.getMarkup();
    }

    @Override
    public String getOwnString()
    {
        return textElement.getOwnMarkup();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String markup)
    {
        textElement.setMarkup(markup);
    }

}
