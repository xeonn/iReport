/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import java.beans.PropertyEditor;
import java.util.List;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class StringListProperty extends StringProperty
{
    protected ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public StringListProperty(Object object)
    {
        super(object);
        setValue("oneline", Boolean.TRUE);
        setValue("canEditAsText", Boolean.TRUE);
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() 
    {
        if (editor == null)
        {
            editor = new ComboBoxPropertyEditor(true, getTagList());
        }
        return editor;
    }

    public abstract List getTagList();

}
