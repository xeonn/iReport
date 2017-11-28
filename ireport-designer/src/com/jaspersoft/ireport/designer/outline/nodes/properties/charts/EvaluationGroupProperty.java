/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties.charts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import org.openide.nodes.PropertySupport;

    
/**
 *  Class to manage the JRDesignChart.PROPERTY_EVALUATION_TIME property
 */
public final class EvaluationGroupProperty extends PropertySupport
{
        private final JRDesignDataset dataset;
        private final JRDesignChart element;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public EvaluationGroupProperty(JRDesignChart element, JRDesignDataset dataset)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( JRDesignChart.PROPERTY_EVALUATION_GROUP,JRGroup.class, "Evaluation group", "Evaluate the image expression when the specified group changes", true, true);
            this.element = element;
            this.dataset = dataset;
            setValue("suppressCustomEditor", Boolean.TRUE);
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
                java.util.ArrayList l = new java.util.ArrayList();

                List groups = dataset.getGroupsList();
                for (int i=0; i<groups.size(); ++i)
                {
                    JRGroup group = (JRGroup)groups.get(i);
                    l.add(new Tag( group , group.getName()));
                }
                editor = new ComboBoxPropertyEditor(false, l);
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
}
