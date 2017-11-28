package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.PropertySupport;

/**
 * SHEET PROPERTIES DEFINITIONS
 */
public final class ModeProperty extends PropertySupport.ReadWrite {

    JRBaseStyle style = null;

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return new Boolean(style.getMode() != null && style.getMode() == JRDesignElement.MODE_OPAQUE);
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Boolean) {
            Byte oldValue = style.getOwnMode();
            Byte newValue = val == null ? null : (((Boolean) val).booleanValue() ? JRDesignElement.MODE_OPAQUE : JRDesignElement.MODE_TRANSPARENT);
            style.setMode(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "Mode", Byte.TYPE, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnMode() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public ModeProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_MODE, Boolean.class, I18n.getString("AbstractStyleNode.Property.Opaque"), I18n.getString("AbstractStyleNode.Property.Set"));
        this.style = style;
    }
}
