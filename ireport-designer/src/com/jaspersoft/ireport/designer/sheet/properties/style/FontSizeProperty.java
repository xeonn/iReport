package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignStyle.PROPERTY_FONT_SIZE property
 */
final public class FontSizeProperty extends PropertySupport.ReadWrite {

    private final JRBaseStyle style;
    PropertyEditor editor = null;

    @SuppressWarnings(value = "unchecked")
    public FontSizeProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_FONT_SIZE, Integer.class, I18n.getString("AbstractStyleNode.Property.Size"), I18n.getString("AbstractStyleNode.Property.Size"));
        this.style = style;

        setValue("canEditAsText", true);
        setValue("oneline", true);
        setValue("suppressCustomEditor", false);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getFontSize();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val != null && !(val instanceof Integer)) {
            try {
                val = new Integer(val + "");
            } catch (Exception ex) {
                // no way...
                return;
            }
        }
        if (val == null || val instanceof Integer) {
            Integer oldValue = style.getOwnFontSize();
            Integer newValue = (Integer) val;
            style.setFontSize(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "FontSize", Integer.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnFontSize() == null;
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
            for (int i = 6; i < 100;) {
                classes.add(new Tag(new Integer(i), "" + i));

                if (i < 16) {
                    i++;
                } else if (i < 32) {
                    i += 2;
                } else if (i < 48) {
                    i += 4;
                } else if (i < 72) {
                    i += 6;
                } else {
                    i += 8;
                }
            }
            editor = new ComboBoxPropertyEditor(true, classes);
        }
        return editor;
    }
}
