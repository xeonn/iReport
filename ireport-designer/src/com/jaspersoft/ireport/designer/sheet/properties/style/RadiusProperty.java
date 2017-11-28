package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignElement.PROPERTY_X property
 */
public final class RadiusProperty extends PropertySupport {

    private final JRBaseStyle style;

    @SuppressWarnings(value = "unchecked")
    public RadiusProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_RADIUS, Integer.class, I18n.getString("RadiusPropertyRadius.Property.Radius"), I18n.getString("RadiusPropertyRadius.Property.Radiusdetail"), true, true);
        this.style = style;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getRadius();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Integer) {
            Integer oldValue = style.getOwnRadius();
            Integer newValue = (Integer) val;
            if (newValue < 0) {
                IllegalArgumentException iae = annotateException(I18n.getString("RadiusPropertyRadius.Property.Message"));
                throw iae;
            }

            style.setRadius(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "Radius", Integer.TYPE, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    public IllegalArgumentException annotateException(String msg) {
        IllegalArgumentException iae = new IllegalArgumentException(msg);
        ErrorManager.getDefault().annotate(iae, ErrorManager.EXCEPTION, msg, msg, null, null);
        return iae;
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnRadius() == null;
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
