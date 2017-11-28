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
import java.util.List;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.PropertySupport;

/**
 * Class to manage the JRBaseFont.PROPERTY_REPORT_FONT property
 */
public class ReportFontProperty extends PropertySupport.ReadWrite {

    // FIXME: refactorize this
    private final JRDesignTextElement element;
    private final JasperDesign jd;
    PropertyEditor editor = null;

    @SuppressWarnings("unchecked")
    public ReportFontProperty(JRDesignTextElement element, JasperDesign jd)
    {
        super(JRBaseFont.PROPERTY_REPORT_FONT, JRReportFont.class,
              I18n.getString("Global.Property.Reportfont"),
              I18n.getString("Global.Property.Reportfont"));
        this.element = element;
        this.jd = jd;

        setValue("canEditAsText",false);
        setValue("oneline",true);
        setValue("suppressCustomEditor",true);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getReportFont() == null ? "" : element.getReportFont();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (val == null || val instanceof JRReportFont)
        {
            JRReportFont oldValue = element.getReportFont();
            JRReportFont newValue =   (JRReportFont)val;
            element.setReportFont(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "ReportFont", 
                        JRReportFont.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    @Override
    public boolean isDefaultValue() {
        return element.getReportFont() == null;
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
            @SuppressWarnings("deprecation")
            List fonts = jd.getFontsList();
            for (int i=0; i<fonts.size(); ++i)
            {
                JRReportFont font = (JRReportFont)fonts.get(i);
                classes.add(new Tag(font, font.getName() ));
            }

            editor = new ComboBoxPropertyEditor(false, classes);
        }
        return editor;
    }

    @Override
    public String getHtmlDisplayName() {
        return "<s>" + super.getDisplayName() + "</s>";
    }
    
}
