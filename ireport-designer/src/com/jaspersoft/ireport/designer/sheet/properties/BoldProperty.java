/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * Class to manage the JRBaseStyle.PROPERTY_BOLD property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class BoldProperty extends BooleanProperty{

    private final JRDesignTextElement element;

    @SuppressWarnings("unchecked")
    public BoldProperty(JRDesignTextElement element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_BOLD;
    }

    @Override
    public String getDisplayName()
    {
        return "Bold";
    }

    @Override
    public String getShortDescription()
    {
        return "";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isBold();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isOwnBold();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isBold)
    {
    	element.setBold(isBold);
    }
    
}
