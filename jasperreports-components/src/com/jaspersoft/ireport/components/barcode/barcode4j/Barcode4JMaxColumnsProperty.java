/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import net.sf.jasperreports.components.barcode4j.PDF417Component;
import org.openide.nodes.PropertySupport;


/**
 *  Class to manage the JRDesignChart.PROPERTY_TITLE_POSITION property
 */
public final class Barcode4JMaxColumnsProperty extends PropertySupport
{

    private final PDF417Component component;
    private ComboBoxPropertyEditor editor;
    
    @SuppressWarnings("unchecked")
    public Barcode4JMaxColumnsProperty(PDF417Component component)
    {
        // TODO: Replace WhenNoDataType with the right constant
        super( PDF417Component.PROPERTY_MAX_COLUMNS,Integer.class,
                I18n.getString("barcode4j.property.maxColumns.name"),
                I18n.getString("barcode4j.property.maxColumns.description"), true, true);
        this.component = component;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            editor = new ComboBoxPropertyEditor(false, getListOfTags());
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {

        return component.getMaxColumns();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Integer)
        {
            Integer oldValue = component.getMaxColumns();

            Integer newValue = (Integer)val;
            component.setMaxColumns(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        component,
                        "MaxColumns",
                        Integer.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    private java.util.ArrayList getListOfTags()
    {
        ArrayList tags = new java.util.ArrayList();
        tags.add(new Tag(null, "<Default>"));
        for (int i=1; i<=30; ++i)
        {
            tags.add(new Tag(new Integer(i), "" + i));
        }
        return tags;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }

    @Override
    public boolean isDefaultValue() {
        return component.getMaxColumns() == null;
    }

}
