/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class TextFieldPatternProperty extends PatternProperty
{
    private final JRDesignTextField textField;
    
    @SuppressWarnings("unchecked")
    public TextFieldPatternProperty(JRDesignTextField textField)
    {
        super(textField);
        this.textField = textField;
    }

    public String getPattern()
    {
        return textField.getPattern();
    }

    public String getOwnPattern()
    {
        return textField.getOwnPattern();
    }

    public void setPattern(String pattern)
    {
        textField.setPattern(pattern);
    }
    
}
