package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.nodes.PropertySupport;

public final class ForecolorProperty extends PropertySupport.ReadWrite {

    JRBaseStyle style = null;

    @SuppressWarnings(value = "unchecked")
    public ForecolorProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_FORECOLOR, Color.class, I18n.getString("AbstractStyleNode.Property.Forecolor"), I18n.getString("AbstractStyleNode.Property.Forecolordetail"));
        this.style = style;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getForecolor();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        setPropertyValue(val);
    }

    private void setPropertyValue(Object val) {
        if (val == null || val instanceof Color) {
            Color oldValue = style.getOwnForecolor();
            Color newValue = (Color) val;
            style.setForecolor(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "Forecolor", Color.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return null == style.getOwnForecolor();
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
