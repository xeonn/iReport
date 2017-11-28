package com.jaspersoft.ireport.designer.sheet.properties.style;

import com.jaspersoft.ireport.designer.IRFont;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRDesignStyle.PROPERTY_PDF_FONT_NAME property
 */
final public class PdfFontNameProperty extends PropertySupport.ReadWrite {

    private final JRBaseStyle style;
    PropertyEditor editor = null;

    @SuppressWarnings(value = "unchecked")
    public PdfFontNameProperty(JRBaseStyle style) {
        super(JRBaseStyle.PROPERTY_PDF_FONT_NAME, String.class, I18n.getString("AbstractStyleNode.Property.Pdf_Font_name"), I18n.getString("AbstractStyleNode.Property.Pdf_Font_name"));
        this.style = style;

        setValue("canEditAsText", true);
        setValue("oneline", true);
        setValue("suppressCustomEditor", true);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return style.getPdfFontName();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val == null || val instanceof String) {
            String oldValue = style.getOwnPdfFontName();
            String newValue = (String) val;

            style.setPdfFontName(newValue);
            ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(style, "PdfFontName", String.class, oldValue, newValue);
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return style.getOwnPdfFontName() == null;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setValue(null);
    }

    @Override
    public boolean supportsDefaultValue() {
        return true;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public PropertyEditor getPropertyEditor() {
        if (editor == null) {
            List classes = new ArrayList();
            classes.add(new Tag("Helvetica"));
            classes.add(new Tag("Helvetica-Bold"));
            classes.add(new Tag("Helvetica-BoldOblique"));
            classes.add(new Tag("Helvetica-Oblique"));
            classes.add(new Tag("Courier"));
            classes.add(new Tag("Courier-Bold"));
            classes.add(new Tag("Courier-BoldOblique"));
            classes.add(new Tag("Courier-Oblique"));
            classes.add(new Tag("Symbol"));
            classes.add(new Tag("Times-Roman"));
            classes.add(new Tag("Times-Bold"));
            classes.add(new Tag("Times-BoldItalic"));
            classes.add(new Tag("Times-Italic"));
            classes.add(new Tag("ZapfDingbats"));
            classes.add(new Tag("STSong-Light"));
            classes.add(new Tag("MHei-Medium"));
            classes.add(new Tag("MSung-Light"));
            classes.add(new Tag("HeiseiKakuGo-W5"));
            classes.add(new Tag("HeiseiMin-W3"));
            classes.add(new Tag("HYGoThic-Medium"));
            classes.add(new Tag("HYSMyeongJo-Medium"));
            List<IRFont> fonts = IReportManager.getInstance().getIRFonts();
            for (IRFont f : fonts) {
                classes.add(new Tag(f.getFile(), f.toString()));
            }
            editor = new ComboBoxPropertyEditor(true, classes);
        }
        return editor;
    }
}
