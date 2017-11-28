/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.editors.FieldPatternPropertyEditor;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class PatternProperty extends StringProperty
{
    private PropertyEditor editor = null;

    @Override
    public PropertyEditor getPropertyEditor() {
        if (editor == null)
        {
            editor = new FieldPatternPropertyEditor();
        }
        return editor;
    }
    
    @SuppressWarnings("unchecked")
    public PatternProperty(Object object)
    {
        super(object);
        setValue("suppressCustomEditor", Boolean.FALSE);
    }

    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_PATTERN;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("PatternProperty.Property.Pattern");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("PatternProperty.Property.Patterndetail");
    }

    @Override
    public String getString()
    {
        return getPattern();
    }

    @Override
    public String getOwnString()
    {
        return getOwnPattern();
    }

    @Override
    public String getDefaultString()
    {
        return null;
    }

    @Override
    public void setString(String pattern)
    {
        setPattern(pattern);
    }

    public abstract String getPattern();

    public abstract String getOwnPattern();

    public abstract void setPattern(String pattern);
}
