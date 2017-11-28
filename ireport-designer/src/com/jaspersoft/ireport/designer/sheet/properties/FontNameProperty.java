/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRBaseStyle.PROPERTY_FONT_NAME property
 */
public class FontNameProperty extends PropertySupport.ReadWrite {
    
    // FIXME: refactorize this
    private final JRDesignTextElement element;
    PropertyEditor editor = null;

    @SuppressWarnings("unchecked")
    public FontNameProperty(JRDesignTextElement element)
    {
        super(JRBaseStyle.PROPERTY_FONT_NAME, String.class,
              "Font name",
              "Font name");
        this.element = element;

        setValue("canEditAsText",true);
        setValue("oneline",true);
        setValue("suppressCustomEditor",false);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getFontName();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val == null || val instanceof String)
        {
            String oldValue = element.getOwnFontName();
            String newValue =   (String)val;
            element.setFontName(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "FontName", 
                        String.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.getOwnFontName() == null;
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
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            java.util.List classes = new ArrayList();
            //List<Font> fonts = IReportManager.getInstance().getFonts();
            //
            //for (Font f : fonts)
            //{
            //    classes.add(new Tag(f.getFontName()));
            //}

            String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    classes.add(new Tag(name));
            }
            editor = new ComboBoxPropertyEditor(true, classes);
        }
        return editor;
    }

}
