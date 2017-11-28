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
import net.sf.jasperreports.engine.base.JRBaseSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreport;

    
/**
 *
 */
public final class RunToBottomProperty extends BooleanProperty
{
    private final JRDesignSubreport subreport;

    @SuppressWarnings("unchecked")
    public RunToBottomProperty(JRDesignSubreport subreport)
    {
        super(subreport);
        this.subreport = subreport;
    }

    @Override
    public String getName()
    {
        return JRBaseSubreport.PROPERTY_RUN_TO_BOTTOM;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.RunToBottom");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.RunToBottom.");
    }

    @Override
    public Boolean getBoolean()
    {
        // showing always a default value which is false for this property.
        Boolean isRunToBottom=subreport.isRunToBottom();
        return isRunToBottom==null?false:isRunToBottom;
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return subreport.isRunToBottom();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean runToBottom)
    {
        subreport.setRunToBottom(runToBottom);
    }

}
