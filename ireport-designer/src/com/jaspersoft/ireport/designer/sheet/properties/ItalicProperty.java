/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * Class to manage the JRBaseStyle.PROPERTY_ITALIC property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class ItalicProperty extends BooleanProperty{

    private final JRDesignTextElement element;

    @SuppressWarnings("unchecked")
    public ItalicProperty(JRDesignTextElement element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_ITALIC;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.Italic");
    }

    @Override
    public String getShortDescription()
    {
        return "";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isItalic();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isOwnItalic();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isItalic)
    {
    	element.setItalic(isItalic);
    }
    
}
