/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_PRINT_REPEATED_VALUES property
 */
public final class PrintRepeatedValuesProperty extends BooleanProperty 
{
    private JRDesignElement element = null;

    @SuppressWarnings("unchecked")
    public PrintRepeatedValuesProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_PRINT_REPEATED_VALUES;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.PrintRepeatedValues");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.PrintRepeatedValues.");
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isPrintRepeatedValues();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isPrintRepeatedValues();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return true;
    }

    @Override
    public void setBoolean(Boolean isPrint)
    {
        element.setPrintRepeatedValues(isPrint);
    }

}
