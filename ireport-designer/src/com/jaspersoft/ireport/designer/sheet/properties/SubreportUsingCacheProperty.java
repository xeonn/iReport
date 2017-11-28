/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.base.JRBaseSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreport;

/**
 *  Class to manage the JRDesignTextElement.PROPERTY_ITALIC property
 * @author gtoffoli
 */
public final class SubreportUsingCacheProperty extends BooleanProperty
{
    private final JRDesignSubreport subreport;

    @SuppressWarnings("unchecked")
    public SubreportUsingCacheProperty(JRDesignSubreport subreport)
    {
        super(subreport);
        this.subreport = subreport;
    }
    @Override
    public String getName()
    {
        return JRBaseSubreport.PROPERTY_USING_CACHE;
    }

    @Override
    public String getDisplayName()
    {
        return "Using Cache";
    }

    @Override
    public String getShortDescription()
    {
        return "Set if the subreport must be cached when loaded.";
    }

    @Override
    public Boolean getBoolean()
    {
        return subreport.isUsingCache();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return subreport.isOwnUsingCache();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isUsingCache)
    {
        subreport.setUsingCache(isUsingCache);
    }

}
