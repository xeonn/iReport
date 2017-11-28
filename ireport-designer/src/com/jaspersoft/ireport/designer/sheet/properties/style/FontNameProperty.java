package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.util.JRFontUtil;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignStyle.PROPERTY_VALUE_CLASS_NAME property
 */
final public class FontNameProperty extends PropertySupport.ReadWrite {

    private final JRBaseStyle style;
    PropertyEditor editor = null;

    @SuppressWarnings(value = "unchecked")
    public FontNameProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_FONT_NAME, String.class, I18n.getString("AbstractStyleNode.Property.Font_name"), I18n.getString("Font_name"));
        this.style = style;

        setValue("canEditAsText", true);
        setValue("oneline", true);
        setValue("suppressCustomEditor", false);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getFontName();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof String) {
            String oldValue = style.getOwnFontName();
            String newValue = (String) val;
            style.setFontName(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "FontName", String.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnFontName() == null;
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
    @SuppressWarnings(value = "unchecked")
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            List classes = new ArrayList();

            Collection extensionFonts = JRFontUtil.getFontFamilyNames();
            for(Iterator it = extensionFonts.iterator(); it.hasNext();)
            {
                classes.add(new Tag((String)it.next()));
            }
            
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
