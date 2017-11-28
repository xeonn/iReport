/*
 * GraphicElementPropertiesFactory.java
 * 
 * Created on 5-nov-2007, 19.43.44
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.components.barcode.barcode4j;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.sf.jasperreports.components.barcode4j.BarcodeComponent;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import org.openide.nodes.PropertySupport;
import org.openide.util.WeakListeners;

    
/**
 *  Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
 */
public final class Barcode4JEvaluationGroupProperty extends PropertySupport implements PropertyChangeListener
{
        private final JRDesignDataset dataset;
        private final BarcodeComponent component;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public Barcode4JEvaluationGroupProperty(BarcodeComponent component, JRDesignDataset dataset)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( BarcodeComponent.PROPERTY_EVALUATION_GROUP,JRGroup.class, "Evaluation group", "Evaluate the expression when the specified group changes", true, true);
            this.component = component;
            this.dataset = dataset;
            setValue("suppressCustomEditor", Boolean.TRUE);
            
            dataset.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, dataset.getEventSupport()));
        }

        @Override
        public boolean canWrite() {
            return component.getEvaluationTime() == JRExpression.EVALUATION_TIME_GROUP;
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
            return component.getEvaluationGroup() == null ? "" :  component.getEvaluationGroup();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (val instanceof String)
            {
                String oldValue = component.getEvaluationGroup();
                String newValue = (String)val;
                component.setEvaluationGroup(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            component,
                            "EvaluationGroup", 
                            String.class,
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
            l.add(new Tag( group.getName() , group.getName()));
            group.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, group.getEventSupport()));
        }
        return l;
    }
}
