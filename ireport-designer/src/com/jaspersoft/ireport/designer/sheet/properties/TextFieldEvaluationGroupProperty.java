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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.openide.nodes.PropertySupport;
import org.openide.util.WeakListeners;

/**
 *
 * Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
 */
public class TextFieldEvaluationGroupProperty  extends PropertySupport implements PropertyChangeListener {

    // FIXME: refactorize this
    private final JRDesignDataset dataset;
    private final JRDesignTextField element;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public TextFieldEvaluationGroupProperty(JRDesignTextField element, JRDesignDataset dataset)
    {
        // TODO: Replace WhenNoDataType with the right constant
        super( JRDesignTextField.PROPERTY_EVALUATION_GROUP,JRGroup.class, I18n.getString("Global.Property.Evaluationgroup"), I18n.getString("Global.Property.EvalGroupdeatail"), true, true);
        this.element = element;
        this.dataset = dataset;
        setValue("suppressCustomEditor", Boolean.TRUE);

        dataset.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, dataset.getEventSupport()));
    }

    @Override
    public boolean canWrite() {
        return element.getEvaluationTime() == JRExpression.EVALUATION_TIME_GROUP;
    }


    @Override
    @SuppressWarnings("unchecked")
    public PropertyEditor getPropertyEditor() {

        if (editor == null)
        {
            editor = new ComboBoxPropertyEditor(false, getListOfTags());
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return element.getEvaluationGroup() == null ? "" :  element.getEvaluationGroup();
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val instanceof JRGroup)
        {
            JRGroup oldValue = element.getEvaluationGroup();
            JRGroup newValue = (JRGroup)val;
            element.setEvaluationGroup(newValue);

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "EvaluationGroup", 
                        JRGroup.class,
                        oldValue,newValue);
            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (editor == null) return;
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignDataset.PROPERTY_GROUPS) ||
            evt.getPropertyName().equals( JRDesignGroup.PROPERTY_NAME))
        {
            editor.setTagValues(getListOfTags());
        }
    }

    private java.util.ArrayList getListOfTags()
    {
            java.util.ArrayList l = new java.util.ArrayList();
            List groups = dataset.getGroupsList();
            for (int i=0; i<groups.size(); ++i)
            {
                JRDesignGroup group = (JRDesignGroup)groups.get(i);
                l.add(new Tag( group , group.getName()));
                group.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, group.getEventSupport()));
            }
            return l;
     }

}
