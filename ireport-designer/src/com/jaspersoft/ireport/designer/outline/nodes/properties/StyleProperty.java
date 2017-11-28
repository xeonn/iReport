/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.WeakListeners;

    
/**
 *  Class to manage the JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE property
 */
public final class StyleProperty extends PropertySupport implements PropertyChangeListener
{
        final JRDesignElement element;
        final JasperDesign jasperDesign;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public StyleProperty(JRDesignElement element, JasperDesign jasperDesign)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE,String.class, "Style", "The optional style to use for this element. Can be the name of a locally defined style or the name of a style defined in an external style template file.", true, true);
            this.element = element;
            this.jasperDesign = jasperDesign;

            setValue("canEditAsText", Boolean.TRUE);
            setValue("oneline", Boolean.TRUE);
            setValue("suppressCustomEditor", Boolean.FALSE);
        
            jasperDesign.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, jasperDesign.getEventSupport()));
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                editor = new ComboBoxPropertyEditor( true, getListOfTags());
            }
            return editor;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {

            String name = element.getStyleNameReference();
            if (element.getStyle() != null )
            {
                name = element.getStyle().getName();
            }

            return (name == null) ? "" : name;
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            String newValue = ""+val;
            JRStyle newStyle = null;
            if (val instanceof JRStyle)
            {
                newValue = ((JRStyle)val).getName();
                newStyle = (JRStyle)val;
            }
            else
            {
                if (val == null || (newValue.length() == 0))
                {
                    newValue = null;
                }
            }

            String oldValue = element.getStyleNameReference();
            JRStyle oldStyle = element.getStyle();

            element.setStyleNameReference(newValue);
            element.setStyle(newStyle);

            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "StyleNameReference", 
                            String.class,
                            oldValue,newValue);

            ObjectPropertyUndoableEdit urob_style =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Style", 
                            JRStyle.class,
                            oldStyle,newStyle);

            urob.concatenate(urob_style);

            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }

    public void propertyChange(PropertyChangeEvent evt) {
        if (editor == null) return;
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JasperDesign.PROPERTY_STYLES) ||
            evt.getPropertyName().equals( JRDesignStyle.PROPERTY_NAME))
        {
            editor.setTagValues(getListOfTags());
        }
    }
    
    private java.util.ArrayList getListOfTags()
    {
        java.util.ArrayList l = new java.util.ArrayList();
        l.add(new Tag( null , ""));
        List styles = jasperDesign.getStylesList();
        for (int i=0; i<styles.size(); ++i)
        {
            JRDesignStyle style = (JRDesignStyle)styles.get(i);
            l.add(new Tag( style , style.getName()));
            style.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, style.getEventSupport()));
        }
        
        return l;
    }
}

