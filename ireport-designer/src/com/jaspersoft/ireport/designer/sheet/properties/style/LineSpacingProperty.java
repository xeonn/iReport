package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
final public class LineSpacingProperty extends PropertySupport {

    private final JRBaseStyle style;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings(value = "unchecked")
    public LineSpacingProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_LINE_SPACING, Byte.class, I18n.getString("AbstractStyleNode.Property.Line_Spacing"), I18n.getString("AbstractStyleNode.Property.LSDetail"), true, true);
        this.style = style;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            ArrayList l = new ArrayList();
            l.add(new Tag(null, "<Default>"));
            l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_SINGLE), I18n.getString("AbstractStyleNode.Property.Single")));
            l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_1_1_2), I18n.getString("AbstractStyleNode.Property.1-5")));
            l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_DOUBLE), I18n.getString("AbstractStyleNode.Property.Double")));
            editor = new ComboBoxPropertyEditor(false, l);
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getLineSpacing();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val) {
        if (val == null || val instanceof Byte) {
            Byte oldValue = style.getOwnLineSpacing();
            Byte newValue = (Byte) val;
            style.setLineSpacing(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "LineSpacing", Byte.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnLineSpacing() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }
}
