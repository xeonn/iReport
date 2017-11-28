/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.design.JRDesignElement;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_PRINT_IN_FIRST_WHOLE_BAND property
 */
public final class PrintInFirstWholeBandProperty extends BooleanProperty
{
    private JRDesignElement element = null;

    @SuppressWarnings("unchecked")
    public PrintInFirstWholeBandProperty(JRDesignElement element)
    {
        super(element);
        this.element = element;
    }

    @Override
    public String getName()
    {
        return JRDesignElement.PROPERTY_PRINT_IN_FIRST_WHOLE_BAND;
    }

    @Override
    public String getDisplayName()
    {
        return "Print In First Whole Band";
    }

    @Override
    public String getShortDescription()
    {
        return "Print In First Whole Band.";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isPrintInFirstWholeBand();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isPrintInFirstWholeBand();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return false;
    }

    @Override
    public void setBoolean(Boolean isPrint)
    {
        element.setPrintInFirstWholeBand(isPrint);
    }

}
