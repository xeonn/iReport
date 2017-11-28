/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

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
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRBaseStyle.PROPERTY_FONT_NAME property
 */
public class PdfFontNameProperty extends PropertySupport.ReadWrite {
    
    // FIXME: refactorize this
    private final JRDesignTextElement element;
    PropertyEditor editor = null;

    @SuppressWarnings("unchecked")
    public PdfFontNameProperty(JRDesignTextElement element)
    {
        super(JRBaseStyle.PROPERTY_PDF_FONT_NAME, String.class,
              I18n.getString("Global.Property.PdfFontname"),
              I18n.getString("Global.Property.PdfFontname"));
        this.element = element;

        setValue("canEditAsText",true);
        setValue("oneline",true);
        setValue("suppressCustomEditor",true);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getPdfFontName();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val == null || val instanceof String)
        {
            String oldValue = element.getOwnPdfFontName();
            String newValue =   (String)val;

            element.setPdfFontName(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "PdfFontName", 
                        String.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.getOwnPdfFontName() == null;
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
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            java.util.List classes = new ArrayList();

            // Add regular PDF fonts...
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

            for (IRFont f : fonts)
            {
                classes.add(new Tag(f.getFile(), f.toString()));
            }

            editor = new ComboBoxPropertyEditor(true, classes);
        }
        return editor;
    }

}
