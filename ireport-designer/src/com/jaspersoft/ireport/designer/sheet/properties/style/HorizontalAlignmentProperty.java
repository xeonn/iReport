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
public final class HorizontalAlignmentProperty extends PropertySupport {

    private final JRBaseStyle style;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings(value = "unchecked")
    public HorizontalAlignmentProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT, Byte.class, I18n.getString("AbstractStyleNode.Property.Horizontal_Alignment"), I18n.getString("AbstractStyleNode.Property.HorizDetail"), true, true);
        this.style = style;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            ArrayList l = new ArrayList();
            l.add(new Tag(null, "<Default>"));
            l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_LEFT), I18n.getString("AbstractStyleNode.Property.Left")));
            l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_CENTER), I18n.getString("AbstractStyleNode.Property.Center")));
            l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_RIGHT), I18n.getString("AbstractStyleNode.Property.Right")));
            l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_JUSTIFIED), I18n.getString("AbstractStyleNode.Property.Justified")));
            editor = new ComboBoxPropertyEditor(false, l);
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getHorizontalAlignment();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Byte) {
            Byte oldValue = style.getOwnHorizontalAlignment();
            Byte newValue = (Byte) val;
            style.setHorizontalAlignment(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "HorizontalAlignment", Byte.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnHorizontalAlignment() == null;
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
