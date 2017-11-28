package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignStyle.PROPERTY_BOLD property
 */
public final class BoldProperty extends PropertySupport.ReadWrite {

    private final JRBaseStyle style;

    @SuppressWarnings(value = "unchecked")
    public BoldProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_BOLD, Boolean.class, I18n.getString("AbstractStyleNode.Property.Bold"), "");
        this.style = style;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.isBold();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Boolean) {
            Boolean oldValue = style.isOwnBold();
            Boolean newValue = (Boolean) val;
            style.setBold(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "Bold", Boolean.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.isOwnBold() == null;
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
