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
import net.sf.jasperreports.engine.design.JRDesignImage;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
 */
public final class VerticalAlignmentProperty extends PropertySupport {

    private final JRBaseStyle style;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings(value = "unchecked")
    public VerticalAlignmentProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT, Byte.class, I18n.getString("AbstractStyleNode.Property.Vertical_Alignment"), I18n.getString("AbstractStyleNode.Property.VerticalDetail"), true, true);
        this.style = style;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            ArrayList l = new ArrayList();
            l.add(new Tag(null, "<Default>"));
            l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_TOP), I18n.getString("Global.Property.Top")));
            l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_MIDDLE), I18n.getString("Global.Property.Middle")));
            l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_BOTTOM), I18n.getString("Global.Property.Bottom")));
            //l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_JUSTIFIED), I18n.getString("AbstractStyleNode.Property.Justified")));
            editor = new ComboBoxPropertyEditor(false, l);
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getVerticalAlignment();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Byte) {
            Byte oldValue = style.getOwnVerticalAlignment();
            Byte newValue = (Byte) val;
            style.setVerticalAlignment(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "VerticalAlignment", Byte.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnVerticalAlignment() == null;
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
