package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
 */
public final class StyledTextProperty extends PropertySupport.ReadWrite {

    private final JRBaseStyle style;

    @SuppressWarnings(value = "unchecked")
    public StyledTextProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_IS_STYLED_TEXT, Boolean.class, I18n.getString("AbstractStyleNode.Property.Is_Styled_Text"), I18n.getString("AbstractStyleNode.Property.StyleTextDetail"));
        this.style = style;
    }

    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.isStyledText();
    }

    @Override
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof Boolean) {
            Boolean oldValue = style.isOwnStyledText();
            Boolean newValue = (Boolean) val;
            style.setStyledText(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "StyledText", Boolean.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.isOwnStyledText() == null;
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
