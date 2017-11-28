/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.properties.StringProperty;
import net.sf.jasperreports.engine.base.JRBaseStaticText;
import net.sf.jasperreports.engine.design.JRDesignStaticText;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class TextProperty extends StringProperty
{
    private final JRDesignStaticText staticText;

    @SuppressWarnings("unchecked")
    public TextProperty(JRDesignStaticText staticText)
    {
        super(staticText);
        this.staticText = staticText;
        setValue("oneline", Boolean.FALSE);
        setValue("suppressCustomEditor", Boolean.FALSE);
    }

    @Override
    public String getName()
    {
        return JRBaseStaticText.PROPERTY_TEXT;
    }

    @Override
    public String getDisplayName()
    {
        return "Text";
    }

    @Override
    public String getShortDescription()
    {
        return "The text to show in this label.";
    }

    @Override
    public String getString()
    {
        return staticText.getText();
    }

    @Override
    public String getOwnString()
    {
        return staticText.getText();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String text)
    {
        staticText.setText(text);
    }

}
