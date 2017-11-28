/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class StylePatternProperty extends PatternProperty
{
    private final JRBaseStyle style;
    
    @SuppressWarnings("unchecked")
    public StylePatternProperty(JRBaseStyle style)
    {
        super(style);
        this.style = style;
    }

    public String getPattern()
    {
        return style.getPattern();
    }

    public String getOwnPattern()
    {
        return style.getOwnPattern();
    }

    public void setPattern(String pattern)
    {
        style.setPattern(pattern);
    }
    
}
