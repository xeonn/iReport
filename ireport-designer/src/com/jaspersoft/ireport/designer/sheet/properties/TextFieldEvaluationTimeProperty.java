/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

/**
 *
 * Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
 */
public class TextFieldEvaluationTimeProperty extends PropertySupport {
    
    // FIXME: refactorize this
    private final JRDesignDataset dataset;
    private final JRDesignTextField element;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public TextFieldEvaluationTimeProperty(JRDesignTextField element, JRDesignDataset dataset)
    {
        // TODO: Replace WhenNoDataType with the right constant
        super( JRDesignTextField.PROPERTY_EVALUATION_TIME,Byte.class, "Evaluation Time", "When the image expression should be evaluated.", true, true);
        this.element = element;
        this.dataset = dataset;
        setValue("suppressCustomEditor", Boolean.TRUE);
    }

    @Override
    public boolean isDefaultValue() {
        return element.getEvaluationTime() == JRExpression.EVALUATION_TIME_NOW;
    }

    @Override
    public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
        setPropertyValue(JRExpression.EVALUATION_TIME_NOW);
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
            java.util.ArrayList l = new java.util.ArrayList();

            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_NOW), "Now"));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_REPORT), "Report"));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_PAGE), "Page"));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_COLUMN), "Column"));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_GROUP), "Group"));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_BAND), "Band"));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_AUTO), "Auto"));

            editor = new ComboBoxPropertyEditor(false, l);
        }
        return editor;
    }

    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return new Byte(element.getEvaluationTime());
    }

    public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (val instanceof Byte)
        {
             setPropertyValue((Byte)val);
        }
    }

    private void setPropertyValue(Byte val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
            Byte oldValue = element.getEvaluationTime();
            Byte newValue = val;

            ObjectPropertyUndoableEdit urob =
                    new ObjectPropertyUndoableEdit(
                        element,
                        "EvaluationTime", 
                        Byte.TYPE,
                        oldValue,newValue);

            JRGroup oldGroupValue = element.getEvaluationGroup();
            JRGroup newGroupValue = null;
            if ( (val).byteValue() == JRExpression.EVALUATION_TIME_GROUP )
            {
                if (dataset.getGroupsList().size() == 0)
                {
                    IllegalArgumentException iae = annotateException("No groups are defined to be used with this element"); 
                    throw iae; 
                }

                newGroupValue = (JRGroup)dataset.getGroupsList().get(0);
            }
            element.setEvaluationTime(newValue);

            if (oldGroupValue != newGroupValue)
            {
                ObjectPropertyUndoableEdit urobGroup =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "EvaluationGroup", 
                            JRGroup.class,
                            oldGroupValue,newGroupValue);
                element.setEvaluationGroup(newGroupValue);
                urob.concatenate(urobGroup);
            }

            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
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
