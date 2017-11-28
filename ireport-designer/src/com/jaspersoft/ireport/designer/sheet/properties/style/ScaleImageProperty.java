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
public final class ScaleImageProperty extends PropertySupport {

    private final JRBaseStyle style;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings(value = "unchecked")
    public ScaleImageProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_SCALE_IMAGE, Byte.class, I18n.getString("AbstractStyleNode.Property.Scale"), I18n.getString("AbstractStyleNode.Property.Scaledetail"), true, true);
        this.style = style;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            ArrayList l = new ArrayList();
            l.add(new Tag(null, "<Default>"));
            l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_CLIP), I18n.getString("AbstractStyleNode.Property.Clip")));
            l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_FILL_FRAME), I18n.getString("AbstractStyleNode.Property.Fill_Frame")));
            l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE), I18n.getString("AbstractStyleNode.Property.Retain_Shape")));
            editor = new ComboBoxPropertyEditor(false, l);
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getScaleImage();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Byte) {
            Byte oldValue = style.getOwnScaleImage();
            Byte newValue = (Byte) val;
            style.setScaleImage(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "ScaleImage", Byte.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnScaleImage() == null;
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
