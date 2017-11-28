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
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;

    
/**
 *  Class to manage the JRDesignParameter.PROPERTY_NAME property
 */
public final class KeyProperty extends PropertySupport.ReadWrite {

    final JRDesignElement element;
    final JasperDesign jasperDesign;

    @SuppressWarnings("unchecked")
    public KeyProperty(JRDesignElement element, JasperDesign jasperDesign)
    {
        super(JRDesignElement.PROPERTY_KEY, String.class,
              "Key",
              "Optional identifier for this element. If set, it must be unique within the overall band/cell.");
        this.element = element;
        this.jasperDesign = jasperDesign;
        this.setValue("oneline", Boolean.TRUE);
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return (getElement().getKey() == null) ? "" : getElement().getKey();
    }

    @SuppressWarnings("unchecked")
    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        String oldValue = getElement().getKey();

        String newValue = val+"";
        if (val == null || val.equals(""))
        {
            newValue = null;
        }
        else
        {
            // Find the top container of this element (band or cell)
            JRElementGroup group = ModelUtils.getTopElementGroup(getElement());
            if (group.getElementByKey(newValue) != null)
            {
                IllegalArgumentException iae = annotateException("Key already in use in this band/cell.");
                throw iae; 
            }
        }

        getElement().setKey(newValue);
        ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                getElement(), "Key", String.class, oldValue, newValue);

        IReportManager.getInstance().addUndoableEdit(opue);

    }

   public JRDesignElement getElement() {
        return element;
    }

    public IllegalArgumentException annotateException(String msg)
    {
        IllegalArgumentException iae = new IllegalArgumentException(msg); 
        ErrorManager.getDefault().annotate(iae, 
                                ErrorManager.EXCEPTION,
                                msg,
                                msg, null, null); 
        return iae;
    }
}
