/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignGenericElement;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;

/**
 *
 * Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
 */
public class GenericElementEvaluationTimeProperty extends PropertySupport {
    
    // FIXME: refactorize this
    private final JRDesignDataset dataset;
    private final JRDesignGenericElement element;
    private ComboBoxPropertyEditor editor;

    @SuppressWarnings("unchecked")
    public GenericElementEvaluationTimeProperty(JRDesignGenericElement element, JRDesignDataset dataset)
    {
        // TODO: Replace WhenNoDataType with the right constant
        super( JRDesignGenericElement.PROPERTY_EVALUATION_TIME,Byte.class, I18n.getString("Global.Property.EvaluationTime"), I18n.getString("Global.Property.EvaluationTimedetail"), true, true);
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

            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_NOW), I18n.getString("Global.Property.Now")));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_REPORT), I18n.getString("Global.Property.Report")));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_PAGE), I18n.getString("Global.Property.Page")));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_COLUMN), I18n.getString("Global.Property.Column")));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_GROUP), I18n.getString("Global.Property.Group")));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_BAND), I18n.getString("Global.Property.Band")));
            l.add(new Tag(new Byte(JRExpression.EVALUATION_TIME_AUTO), I18n.getString("Global.Property.Auto")));

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



            String oldGroupValue = element.getEvaluationGroupName();
            String newGroupValue = null;
            if ( (val).byteValue() == JRExpression.EVALUATION_TIME_GROUP )
            {
                if (dataset.getGroupsList().size() == 0)
                {
                    IllegalArgumentException iae = annotateException(I18n.getString("Global.Property.NogroupsTextFielddetail")); 
                    throw iae; 
                }

                newGroupValue = ((JRGroup)dataset.getGroupsList().get(0)).getName();
            }
            element.setEvaluationTime(newValue);

            if ( (oldGroupValue == null || newGroupValue == null) && (oldGroupValue != newGroupValue) ||
                 (oldGroupValue != null && newGroupValue != null && !oldGroupValue.equals(newGroupValue)))
            {
                ObjectPropertyUndoableEdit urobGroup =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "EvaluationGroupName",
                            String.class,
                            oldGroupValue,newGroupValue);

                element.setEvaluationGroupName(newGroupValue);
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
