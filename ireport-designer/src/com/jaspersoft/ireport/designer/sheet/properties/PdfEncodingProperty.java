/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import org.openide.nodes.PropertySupport;

/**
 *
 * Class to manage the JRBaseStyle.PROPERTY_PDF_ENCODING property
 */
public class PdfEncodingProperty  extends PropertySupport.ReadWrite{

    // FIXME: refactorize this
    private final JRDesignTextElement element;
    PropertyEditor editor = null;

    @SuppressWarnings("unchecked")
    public PdfEncodingProperty(JRDesignTextElement element)
    {
        super(JRBaseStyle.PROPERTY_PDF_ENCODING, String.class,
              I18n.getString("Global.Property.PdfEncoding"),
              I18n.getString("Global.Property.PdfEncoding"));
        this.element = element;

        setValue("canEditAsText",true);
        setValue("oneline",true);
        setValue("suppressCustomEditor",false);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getPdfEncoding();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val == null || val instanceof String)
        {
            if ((val+"").trim().length() == 0) val = null;

            String oldValue = element.getOwnPdfEncoding();
            String newValue = (String)val;

            element.setPdfEncoding(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "PdfEncoding", 
                        String.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.getOwnPdfEncoding() == null;
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
            java.util.List encodings = new ArrayList();

            // Add regular PDF fonts...
            encodings.add(new Tag("Cp1250","CP1250 (Central European)"));
            encodings.add(new Tag("Cp1251","CP1251 (Cyrillic)"));
            encodings.add(new Tag("Cp1252","CP1252 (Western European ANSI aka WinAnsi)"));
            encodings.add(new Tag("Cp1253","CP1253 (Greek)"));
            encodings.add(new Tag("Cp1254","CP1254 (Turkish)"));
            encodings.add(new Tag("Cp1255","CP1255 (Hebrew)"));
            encodings.add(new Tag("Cp1256","CP1256 (Arabic)"));
            encodings.add(new Tag("Cp1257","CP1257 (Baltic)"));
            encodings.add(new Tag("Cp1258","CP1258 (Vietnamese)"));
            encodings.add(new Tag("UniGB-UCS2-H","UniGB-UCS2-H (Chinese Simplified)"));
            encodings.add(new Tag("UniGB-UCS2-V","UniGB-UCS2-V (Chinese Simplified)"));
            encodings.add(new Tag("UniCNS-UCS2-H","UniCNS-UCS2-H (Chinese traditional)"));
            encodings.add(new Tag("UniCNS-UCS2-V","UniCNS-UCS2-V (Chinese traditional)"));
            encodings.add(new Tag("UniJIS-UCS2-H","UniJIS-UCS2-H (Japanese)"));
            encodings.add(new Tag("UniJIS-UCS2-V","UniJIS-UCS2-V (Japanese)"));
            encodings.add(new Tag("UniJIS-UCS2-HW-H","UniJIS-UCS2-HW-H (Japanese)"));
            encodings.add(new Tag("UniJIS-UCS2-HW-V","UniJIS-UCS2-HW-V (Japanese)"));
            encodings.add(new Tag("UniKS-UCS2-H","UniKS-UCS2-H (Korean)"));
            encodings.add(new Tag("UniKS-UCS2-V","UniKS-UCS2-V (Korean)"));
            encodings.add(new Tag("Identity-H","Identity-H (Unicode with horizontal writing)"));
            encodings.add(new Tag("Identity-V","Identity-V (Unicode with vertical writing)"));

            editor = new ComboBoxPropertyEditor(true, encodings);
        }
        return editor;
    }
    
}
