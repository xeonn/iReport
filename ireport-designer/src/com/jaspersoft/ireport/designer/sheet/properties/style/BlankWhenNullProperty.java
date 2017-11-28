package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignStyle.PROPERTY_ITALIC property
 */
public final class BlankWhenNullProperty extends PropertySupport.ReadWrite {

    private final JRBaseStyle style;

    @SuppressWarnings(value = "unchecked")
    public BlankWhenNullProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, Boolean.class, I18n.getString("AbstractStyleNode.Property.Blank_When_Null"), I18n.getString("AbstractStyleNode.Property.Blankdetail"));
        this.style = style;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.isBlankWhenNull();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Boolean) {
            Boolean oldValue = style.isOwnBlankWhenNull();
            Boolean newValue = (Boolean) val;
            style.setBlankWhenNull(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "BlankWhenNull", Boolean.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.isOwnBlankWhenNull() == null;
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
