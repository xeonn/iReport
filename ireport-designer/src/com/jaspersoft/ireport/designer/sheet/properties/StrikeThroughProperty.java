/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * Class to manage the JRBaseStyle.PROPERTY_STRIKE_THROUGH property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class StrikeThroughProperty extends BooleanProperty{

    private final JRDesignTextElement element;

    @SuppressWarnings("unchecked")
    public StrikeThroughProperty(JRDesignTextElement element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_STRIKE_THROUGH;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.StrikeThrough");
    }

    @Override
    public String getShortDescription()
    {
        return "";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isStrikeThrough();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isOwnStrikeThrough();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isStrikeThrough)
    {
    	element.setStrikeThrough(isStrikeThrough);
    }
    
}
