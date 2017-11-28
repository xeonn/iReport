/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.dnd.ReportObjectPaletteTransferable;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.JRPropertiesMapProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.datatransfer.Transferable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.RenameAction;
import org.openide.actions.ReorderAction;
import org.openide.nodes.Children;
import org.openide.nodes.NodeTransfer;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 * ParameterNode detects the events fired by the subtended parameter.
 * Implements the support for the property sheet of a parameter.
 * If a parameter is system defined, it can not be cut.
 * Actions of a parameter node include copy, paste, reorder, rename and delete.
 * 
 * @author gtoffoli
 */
public class ParameterNode extends IRAbstractNode implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRDesignParameter parameter = null;

    public ParameterNode(JasperDesign jd, JRDesignParameter parameter, Lookup doLkp)
    {
        super (Children.LEAF, new ProxyLookup(doLkp, Lookups.fixed(jd, parameter)));
        this.jd = jd;
        this.parameter = parameter;
        setDisplayName ( parameter.getName());
        super.setName( parameter.getName() );
        if (parameter.isSystemDefined())
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/parameter-16.png");
        }
        else
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/parameter-16.png");
        }
        
        parameter.getEventSupport().addPropertyChangeListener(this);
    }

    @Override
    public String getDisplayName() {
        return parameter.getName();
    }
    
    /**
     *  This is the function to create the sheet...
     * 
     */
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        
        Sheet.Set set = Sheet.createPropertiesSet();
        
        JRDesignDataset dataset = getParentNode().getLookup().lookup(JRDesignDataset.class);
        
        set.put(new NameProperty( getParameter(),dataset));
        set.put(new ValueClassNameProperty( getParameter()));
        if (!getParameter().isSystemDefined())
        {
            set.put(new ForPromptingProperty( getParameter()));
            set.put(new DefaultValueExpressionProperty(getParameter(),dataset));
            set.put(new DescriptionProperty(getParameter()));
            set.put(new JRPropertiesMapProperty( getParameter()) );
        }
        
        
        sheet.put(set);
        return sheet;
    }
    
    @Override
    public boolean canCut() {
        return !parameter.isSystemDefined();
    }
    
    @Override
    public boolean canRename() {
        return !parameter.isSystemDefined();
    }
    
    @Override
    public boolean canDestroy() {
        return !parameter.isSystemDefined();
    }
    
    @Override
    public Transferable clipboardCut() throws IOException {
        return NodeTransfer.transferable(this, NodeTransfer.CLIPBOARD_CUT);
    }
    
    @Override
    public Transferable clipboardCopy() throws IOException {
        return NodeTransfer.transferable(this, NodeTransfer.CLIPBOARD_COPY);
    }
    
    @Override
    public void destroy() throws IOException {
       
       if (!getParameter().isSystemDefined())
       {
           
          JRDesignDataset dataset = getParentNode().getLookup().lookup(JRDesignDataset.class);
          dataset.removeParameter(getParameter());
          super.destroy();
       } // otherwise the component was likely already removed with a parent component
    }
        
    @Override
    public Action[] getActions(boolean popup) {
        return new Action[] {
            SystemAction.get( CopyAction.class ),
            SystemAction.get( CutAction.class ),
            SystemAction.get( RenameAction.class ),
            SystemAction.get( ReorderAction.class ),
            null,
            SystemAction.get( DeleteAction.class ) };
    }
    
    @Override
    public Transferable drag() throws IOException {
        ExTransferable tras = ExTransferable.create(clipboardCut());
        tras.put(new ReportObjectPaletteTransferable( 
                    "com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldFromParameterAction",
                    getParameter()));
        
        return tras;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setName(String s) {
        
        if (s.equals(""))
        {
            throw new IllegalArgumentException("Parameter name not valid.");
        }
        
        List<JRDesignParameter> currentParameters = null;
        JRDesignDataset dataset = getParentNode().getLookup().lookup(JRDesignDataset.class);
        currentParameters = (List<JRDesignParameter>)dataset.getParametersList();
        for (JRDesignParameter p : currentParameters)
        {
            if (p != getParameter() && p.getName().equals(s))
            {
                throw new IllegalArgumentException("Parameter name already in use.");
            }
        }
        
        String oldName = getParameter().getName();
        getParameter().setName(s);
        
        ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getParameter(), "Name", String.class, oldName, s);

        IReportManager.getInstance().addUndoableEdit(opue);
    }

    public JRDesignParameter getParameter() {
        return parameter;
    }

    public void setParameter(JRDesignParameter parameter) {
        this.parameter = parameter;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignParameter.PROPERTY_NAME ))
        {
            super.setName(getParameter().getName());
            this.setDisplayName(getParameter().getName());
        }
        
        // Update the sheet
        this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
    }
    
    
    
    
    
    
    
    
    
    
    /***************  SHEET PROPERTIES DEFINITIONS **********************/
    
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_NAME property
     */
    public static final class NameProperty extends PropertySupport.ReadWrite {

        JRDesignParameter parameter = null;
        JRDesignDataset dataset = null;

        @SuppressWarnings("unchecked")
        public NameProperty(JRDesignParameter parameter, JRDesignDataset dataset)
        {
            super(JRDesignParameter.PROPERTY_NAME, String.class,
                  "Name",
                  "Name of the parameter");
            this.parameter = parameter;
            this.dataset = dataset;
            this.setValue("oneline", Boolean.TRUE);
        }

        @Override
        public boolean canWrite()
        {
            return !getParameter().isSystemDefined();
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return getParameter().getName();
        }

        @SuppressWarnings("unchecked")
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {


            if (val == null || val.equals(""))
            {
                IllegalArgumentException iae = annotateException("Parameter name not valid."); 
                throw iae; 
            }

            String s = val+"";

            List<JRDesignParameter> currentParameters = null;
            currentParameters = (List<JRDesignParameter>)getDataset().getParametersList();
            for (JRDesignParameter p : currentParameters)
            {
                if (p != getParameter() && p.getName().equals(s))
                {
                    IllegalArgumentException iae = annotateException("Parameter name already in use."); 
                    throw iae; 
                }
            }
            String oldName = getParameter().getName();
            getParameter().setName(s);

            ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getParameter(), "Name", String.class, oldName, getParameter().getName());

            IReportManager.getInstance().addUndoableEdit(opue);

        }

        public JRDesignDataset getDataset() {
            return dataset;
        }

        public void setDataset(JRDesignDataset dataset) {
            this.dataset = dataset;
        }

        public JRDesignParameter getParameter() {
            return parameter;
        }

        public void setParameter(JRDesignParameter parameter) {
            this.parameter = parameter;
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
    
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_VALUE_CLASS_NAME property
     */
    public class ValueClassNameProperty extends PropertySupport.ReadWrite {

        JRDesignParameter parameter = null;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public ValueClassNameProperty(JRDesignParameter parameter)
        {
            super(JRDesignParameter.PROPERTY_VALUE_CLASS_NAME, String.class,
                  "Parameter Class",
                  "Parameter Class");
            this.parameter = parameter;
        }

        @Override
        public boolean canWrite()
        {
            return !getParameter().isSystemDefined();
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return getParameter().getValueClassName();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null) 
            {
                return;
            }
            if (val instanceof String)
            {
                String s = ((String)val).trim();
                if (s.length() == 0) s = "java.lang.String";

                String oldValue = getParameter().getValueClassName();
                String newValue = s;
                getParameter().setValueClassName( s);

                ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(getParameter(),"ValueClassName", String.class ,oldValue,newValue );
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return getParameter().getValueClassName().equals("java.lang.String");
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            super.restoreDefaultValue();
            setValue("java.lang.String");
            editor.setValue("java.lang.String");
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }

        public JRDesignParameter getParameter() {
            return parameter;
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                if (getParameter().isSystemDefined()){
                    editor = super.getPropertyEditor();
                }
                else
                {
                    java.util.List classes = new ArrayList();
                    classes.add(new Tag("java.lang.Boolean"));
                    classes.add(new Tag("java.lang.Byte"));
                    classes.add(new Tag("java.util.Date"));
                    classes.add(new Tag("java.sql.Timestamp"));
                    classes.add(new Tag("java.sql.Time"));
                    classes.add(new Tag("java.lang.Double"));
                    classes.add(new Tag("java.lang.Float"));
                    classes.add(new Tag("java.lang.Integer"));
                    classes.add(new Tag("java.lang.Long"));
                    classes.add(new Tag("java.lang.Short"));
                    classes.add(new Tag("java.math.BigDecimal"));
                    classes.add(new Tag("java.lang.Number"));
                    classes.add(new Tag("java.lang.String"));
                    classes.add(new Tag("java.util.Collection"));
                    classes.add(new Tag("java.util.List"));
                    classes.add(new Tag("java.lang.Object"));
                    classes.add(new Tag("java.io.InputStream"));
                    classes.add(new Tag("net.sf.jasperreports.engine.JREmptyDataSource"));
                    editor = new ComboBoxPropertyEditor(true, classes);
                }
            }
            return editor;
        }

        @Override
        public Object getValue(String attributeName) {
            if ("canEditAsText".equals(attributeName)) return true;
            if ("oneline".equals(attributeName)) return true;
            if ("suppressCustomEditor".equals(attributeName)) return false;
            return super.getValue(attributeName);
        }
    }
    
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_DEFAULT_VALUE_EXPRESSION property
     */
    public static final class DefaultValueExpressionProperty extends ExpressionProperty {

        JRDesignParameter parameter = null;
        JRDesignDataset dataset = null;

        public DefaultValueExpressionProperty(JRDesignParameter parameter, JRDesignDataset dataset)
        {
            super(JRDesignParameter.PROPERTY_DEFAULT_VALUE_EXPRESSION,
                  "Default value expression",
                  "Default value expression");
            this.parameter = parameter;
            this.dataset = dataset;
            this.setValue("expressionContext", new ExpressionContext(dataset));
        }

        @Override
        public boolean canWrite()
        {
            return !getParameter().isSystemDefined();
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            if (parameter.getDefaultValueExpression() == null) return "";
            return parameter.getDefaultValueExpression().getText();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            JRDesignExpression oldExp =  (JRDesignExpression) parameter.getDefaultValueExpression();
            JRDesignExpression newExp = null;
            //System.out.println("Setting as value: " + val);
            if (val == null || val.equals(""))
            {
                parameter.setDefaultValueExpression(null);
            }
            else
            {
                String s = val+"";

                newExp = new JRDesignExpression();
                newExp.setText(s);
                parameter.setDefaultValueExpression(newExp);
            }
            
            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getParameter(),
                            "DefaultValueExpression", 
                            JRExpression.class,
                            oldExp,newExp);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            //System.out.println("Done: " + val);
        }

        public JRDesignDataset getDataset() {
            return dataset;
        }

        public void setDataset(JRDesignDataset dataset) {
            this.dataset = dataset;
        }

        public JRDesignParameter getParameter() {
            return parameter;
        }

        public void setParameter(JRDesignParameter parameter) {
            this.parameter = parameter;
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

         @Override
        public boolean isDefaultValue() {
            return getParameter().getDefaultValueExpression() == null;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            super.restoreDefaultValue();
            setValue(null);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
    }
    
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
     */
    public static final class ForPromptingProperty extends PropertySupport.ReadWrite {

        JRDesignParameter parameter = null;

        @SuppressWarnings("unchecked")
        public ForPromptingProperty(JRDesignParameter parameter)
        {
            super(JRDesignParameter.PROPERTY_FOR_PROMPTING, Boolean.class,
                  "Use as a prompt",
                  "Use as a prompt");
            this.parameter = parameter;
        }

        @Override
        public boolean canWrite()
        {
            return !getParameter().isSystemDefined();
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Boolean( getParameter().isForPrompting());
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val != null && val instanceof Boolean)
            {
                Boolean oldValue = getParameter().isForPrompting();
                Boolean newValue = (Boolean)val;
                getParameter().setForPrompting(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getParameter(),
                            "ForPrompting", 
                            Boolean.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }

        @Override
        public boolean isDefaultValue() {
            return getParameter().isForPrompting();
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            super.restoreDefaultValue();
            setValue(Boolean.TRUE);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }

        public JRDesignParameter getParameter() {
            return parameter;
        }

        public void setParameter(JRDesignParameter parameter) {
            this.parameter = parameter;
        }
    }
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_DESCRIPTION property
     */
    public class DescriptionProperty extends PropertySupport.ReadWrite {

        JRDesignParameter parameter = null;


        @SuppressWarnings("unchecked")
        public DescriptionProperty(JRDesignParameter parameter)
        {
            super(JRDesignParameter.PROPERTY_DESCRIPTION, String.class,
                  "Description",
                  "Description");
            this.parameter = parameter;
        }

        @Override
        public boolean canWrite()
        {
            return !getParameter().isSystemDefined();
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return parameter.getDescription() == null ? "" : parameter.getDescription();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            String oldValue = getParameter().getDescription();
            String newValue = val == null ? null : ""+val.toString();
            
            getParameter().setDescription(newValue);
            
            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getParameter(),
                            "Description", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
        }

        public JRDesignParameter getParameter() {
            return parameter;
        }

        public void setParameter(JRDesignParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public boolean isDefaultValue() {
            return getParameter().getDescription() == null;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
    }
 
}
