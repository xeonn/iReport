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
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JRDesignVariable;
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
 *
 * @author gtoffoli
 */
public class VariableNode extends IRAbstractNode implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRDesignVariable variable = null;

    public VariableNode(JasperDesign jd, JRDesignVariable variable, Lookup doLkp)
    {
        super (Children.LEAF, new ProxyLookup(doLkp, Lookups.fixed(jd, variable)));
        this.jd = jd;
        this.variable = variable;
        setDisplayName ( variable.getName());
        super.setName( variable.getName() );
        if (variable.isSystemDefined())
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/variable-16.png");
        }
        else
        {
            setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/variable-16.png");
        }
        
        variable.getEventSupport().addPropertyChangeListener(this);
    }

    @Override
    public String getDisplayName() {
        return variable.getName();
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
        
        set.put(new NameProperty( getVariable(),dataset));
        set.put(new ValueClassNameProperty( getVariable()));
        if (!getVariable().isSystemDefined())
        {
            set.put(new CalculationProperty(getVariable()));
            set.put(new ResetTypeProperty(getVariable(), dataset));
            set.put(new ResetGroupProperty(getVariable(), dataset));
            set.put(new IncrementTypeProperty(getVariable(), dataset));
            set.put(new IncrementGroupProperty(getVariable(), dataset));
            set.put(new IncrementerFactoryClassNameProperty(getVariable()));
            set.put(new VariableExpressionProperty(getVariable(),dataset));
            set.put(new InitialValueExpressionProperty(getVariable(),dataset));
        }
        
        sheet.put(set);
        return sheet;
    }
    
    @Override
    public boolean canCut() {
        return !variable.isSystemDefined();
    }
    
    @Override
    public boolean canRename() {
        return !variable.isSystemDefined();
    }
    
    @Override
    public boolean canDestroy() {
        return !variable.isSystemDefined();
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
       
       if (!getVariable().isSystemDefined())
       {
           
          JRDesignDataset dataset = getParentNode().getLookup().lookup(JRDesignDataset.class);
          dataset.removeVariable(getVariable());
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
                    "com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldFromVariableAction",
                    getVariable()));
        
        return tras;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setName(String s) {
        
        if (s.equals(""))
        {
            throw new IllegalArgumentException("Variable name not valid.");
        }
        
        List<JRDesignVariable> currentVariables = null;
        JRDesignDataset dataset = getParentNode().getLookup().lookup(JRDesignDataset.class);
        currentVariables = (List<JRDesignVariable>)dataset.getVariablesList();
        for (JRDesignVariable p : currentVariables)
        {
            if (p != getVariable() && p.getName().equals(s))
            {
                throw new IllegalArgumentException("Variable name already in use.");
            }
        }
        
        String oldName = getVariable().getName();
        getVariable().setName(s);
        
        ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getVariable(), "Name", String.class, oldName, s);

        IReportManager.getInstance().addUndoableEdit(opue);
    }

    public JRDesignVariable getVariable() {
        return variable;
    }

    public void setVariable(JRDesignVariable variable) {
        this.variable = variable;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignVariable.PROPERTY_NAME ))
        {
            super.setName(getVariable().getName());
            this.setDisplayName(getVariable().getName());
        }
        
        // Update the sheet
        this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
    }
    
    
    
    
    
    
    
    
    
    
    /***************  SHEET PROPERTIES DEFINITIONS **********************/
    
    
    /**
     *  Class to manage the JRDesignVariable.PROPERTY_NAME property
     */
    public static final class NameProperty extends PropertySupport.ReadWrite {

        JRDesignVariable variable = null;
        JRDesignDataset dataset = null;

        @SuppressWarnings("unchecked")
        public NameProperty(JRDesignVariable variable, JRDesignDataset dataset)
        {
            super(JRDesignVariable.PROPERTY_NAME, String.class,
                  "Name",
                  "Name of the variable");
            this.variable = variable;
            this.dataset = dataset;
            this.setValue("oneline", Boolean.TRUE);
        }

        @Override
        public boolean canWrite()
        {
            return !getVariable().isSystemDefined();
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return getVariable().getName();
        }

        @SuppressWarnings("unchecked")
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {


            if (val == null || val.equals(""))
            {
                IllegalArgumentException iae = annotateException("Variable name not valid."); 
                throw iae; 
            }

            String s = val+"";

            List<JRDesignVariable> currentVariables = null;
            currentVariables = (List<JRDesignVariable>)getDataset().getVariablesList();
            for (JRDesignVariable p : currentVariables)
            {
                if (p != getVariable() && p.getName().equals(s))
                {
                    IllegalArgumentException iae = annotateException("Variable name already in use."); 
                    throw iae; 
                }
            }
            String oldName = getVariable().getName();
            getVariable().setName(s);

            ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getVariable(), "Name", String.class, oldName, getVariable().getName());

            IReportManager.getInstance().addUndoableEdit(opue);

        }

        public JRDesignDataset getDataset() {
            return dataset;
        }

        public void setDataset(JRDesignDataset dataset) {
            this.dataset = dataset;
        }

        public JRDesignVariable getVariable() {
            return variable;
        }

        public void setVariable(JRDesignVariable variable) {
            this.variable = variable;
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
     *  Class to manage the JRDesignVariable.PROPERTY_VALUE_CLASS_NAME property
     */
    private static final class ValueClassNameProperty extends PropertySupport.ReadWrite {

        JRDesignVariable variable = null;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public ValueClassNameProperty(JRDesignVariable variable)
        {
            super(JRDesignVariable.PROPERTY_VALUE_CLASS_NAME, String.class,
                  "Variable Class",
                  "Variable Class");
            this.variable = variable;
        }

        @Override
        public boolean canWrite()
        {
            return !getVariable().isSystemDefined();
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return getVariable().getValueClassName();
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

                String oldValue = getVariable().getValueClassName();
                String newValue = s;
                getVariable().setValueClassName( s);

                ObjectPropertyUndoableEdit urob = new ObjectPropertyUndoableEdit(getVariable(),"ValueClassName", String.class ,oldValue,newValue );
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return getVariable().getValueClassName().equals("java.lang.String");
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

        public JRDesignVariable getVariable() {
            return variable;
        }

        @Override
        @SuppressWarnings("unchecked")
        public PropertyEditor getPropertyEditor() {

            if (editor == null)
            {
                if (getVariable().isSystemDefined()){
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
     *  Class to manage the JRDesignVariable.PROPERTY_INITIAL_VALUE_EXPRESSION property
     */
    public static final class InitialValueExpressionProperty extends ExpressionProperty {

        JRDesignVariable variable = null;
        JRDesignDataset dataset = null;

        public InitialValueExpressionProperty(JRDesignVariable variable, JRDesignDataset dataset)
        {
            super(JRDesignVariable.PROPERTY_INITIAL_VALUE_EXPRESSION,
                  "Initial value expression",
                  "Initial value expression");
            this.variable = variable;
            this.dataset = dataset;
            this.setValue("expressionContext", new ExpressionContext(dataset));
        }

        @Override
        public boolean canWrite()
        {
            return !getVariable().isSystemDefined();
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            if (variable.getInitialValueExpression() == null) return "";
            return variable.getInitialValueExpression().getText();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            JRDesignExpression oldExp =  (JRDesignExpression) variable.getInitialValueExpression();
            JRDesignExpression newExp = null;
            //System.out.println("Setting as value: " + val);
            if (val == null || val.equals(""))
            {
                variable.setInitialValueExpression(null);
            }
            else
            {
                String s = val+"";

                newExp = new JRDesignExpression();
                newExp.setText(s);
                variable.setInitialValueExpression(newExp);
            }
            
            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getVariable(),
                            "InitialValueExpression", 
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

        public JRDesignVariable getVariable() {
            return variable;
        }

        public void setVariable(JRDesignVariable variable) {
            this.variable = variable;
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
            return getVariable().getInitialValueExpression() == null;
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
     *  Class to manage the JRDesignVariable.PROPERTY_INITIAL_VALUE_EXPRESSION property
     */
    public static final class VariableExpressionProperty extends ExpressionProperty {

        JRDesignVariable variable = null;
        JRDesignDataset dataset = null;

        public VariableExpressionProperty(JRDesignVariable variable, JRDesignDataset dataset)
        {
            super(JRDesignVariable.PROPERTY_EXPRESSION,
                  "Expression",
                  "Expression");
            this.variable = variable;
            this.dataset = dataset;
            this.setValue("expressionContext", new ExpressionContext(dataset));
        }

        @Override
        public boolean canWrite()
        {
            return !getVariable().isSystemDefined();
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            if (variable.getExpression() == null) return "";
            return variable.getExpression().getText();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            JRDesignExpression oldExp =  (JRDesignExpression) variable.getExpression();
            JRDesignExpression newExp = null;
            //System.out.println("Setting as value: " + val);
            if (val == null || val.equals(""))
            {
                variable.setExpression(null);
            }
            else
            {
                String s = val+"";

                newExp = new JRDesignExpression();
                newExp.setText(s);
                variable.setExpression(newExp);
            }
            
            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getVariable(),
                            "Expression", 
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

        public JRDesignVariable getVariable() {
            return variable;
        }

        public void setVariable(JRDesignVariable variable) {
            this.variable = variable;
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
            return getVariable().getExpression() == null;
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
     *  Class to manage the JRDesignVariable.PROPERTY_CALCULATION property
     */
    private static final class CalculationProperty extends PropertySupport
    {
            //private JRDesignDataset dataset = null;
            private JRDesignVariable variable = null;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public CalculationProperty(JRDesignVariable variable)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRDesignVariable.PROPERTY_CALCULATION,Byte.class, "Calculation", "Set the calculation performed by this variable", true, true);
                //this.dataset = dataset;
                this.variable = variable;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            public boolean isDefaultValue() {
                return variable.getCalculation() == JRDesignVariable.CALCULATION_NOTHING;
            }

            @Override
            public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {

                Byte oldValue = variable.getCalculation();
                Byte newValue = JRDesignVariable.CALCULATION_NOTHING;
                variable.setCalculation(newValue);

                ObjectPropertyUndoableEdit urob =
                                new ObjectPropertyUndoableEdit(
                                    variable,
                                    "Calculation", 
                                    Byte.TYPE,
                                    oldValue,newValue);
               // Find the undoRedo manager...
               IReportManager.getInstance().addUndoableEdit(urob);         
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
                    
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_NOTHING), "Nothing"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_COUNT), "Count"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_DISTINCT_COUNT), "Distinct Count"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_SUM), "Sum"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_AVERAGE), "Average"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_LOWEST), "Lowest"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_HIGHEST), "Highest"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_STANDARD_DEVIATION), "Standard Deviation"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_VARIANCE), "Variance"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_SYSTEM), "System"));
                    l.add(new Tag(new Byte(JRDesignVariable.CALCULATION_FIRST), "First"));
                    
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(variable.getCalculation());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Byte)
                {
                    Byte oldValue = variable.getCalculation();
                    Byte newValue = (Byte)val;
                    variable.setCalculation(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                variable,
                                "Calculation", 
                                Byte.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    /**
     *  Class to manage the JRDesignVariable.PROPERTY_CALCULATION property
     */
    private static final class ResetTypeProperty extends PropertySupport
    {
            private JRDesignDataset dataset = null;
            private JRDesignVariable variable = null;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public ResetTypeProperty(JRDesignVariable variable, JRDesignDataset dataset)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRDesignVariable.PROPERTY_RESET_TYPE,Byte.class, "Reset type", "When the variable should be reset to his initial value", true, true);
                this.variable = variable;
                this.dataset = dataset;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            public boolean isDefaultValue() {
                return variable.getResetType() == JRDesignVariable.RESET_TYPE_REPORT;
            }

            @Override
            public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
                setPropertyValue(JRDesignVariable.RESET_TYPE_REPORT);
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
                    
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_REPORT), "Report"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_COLUMN), "Column"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_GROUP), "Group"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_NONE), "None"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_PAGE), "Page"));
                    
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(variable.getResetType());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Byte)
                {
                    setPropertyValue((Byte)val);
                }
            }
            
            private void setPropertyValue(Byte val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
            {
                    Byte oldValue = variable.getResetType();
                    Byte newValue = val;
                    
                    variable.setResetType(newValue);
                    
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                variable,
                                "ResetType", 
                                Byte.TYPE,
                                oldValue,newValue);
                    System.out.println("Added undo: " + oldValue + " --> " + newValue);
                    
                    JRGroup oldGroupValue = variable.getResetGroup();
                    JRGroup newGroupValue = null;
                    if ( (val).byteValue() == JRDesignVariable.RESET_TYPE_GROUP )
                    {
                        if (dataset.getGroupsList().size() == 0)
                        {
                            IllegalArgumentException iae = annotateException("No groups are defined to be used with this variable"); 
                            throw iae; 
                        }
                    
                        newGroupValue = (JRGroup)dataset.getGroupsList().get(0);
                    }
                    
                    if (oldGroupValue != newGroupValue)
                    {
                        ObjectPropertyUndoableEdit urobGroup =
                                new ObjectPropertyUndoableEdit(
                                    variable,
                                    "ResetGroup", 
                                    JRGroup.class,
                                    oldGroupValue,newGroupValue);
                        variable.setResetGroup(newGroupValue);
                        urob.concatenate(urobGroup);
                        System.out.println("Added undo->groupUndo: " + urob.getPresentationName());
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
    
    /**
     *  Class to manage the JRDesignVariable.PROPERTY_CALCULATION property
     */
    private static final class ResetGroupProperty extends PropertySupport
    {
            private JRDesignDataset dataset = null;
            private JRDesignVariable variable = null;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public ResetGroupProperty(JRDesignVariable variable, JRDesignDataset dataset)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRDesignVariable.PROPERTY_RESET_GROUP,JRGroup.class, "Reset group", "Reset the variable when the specified group changes", true, true);
                this.variable = variable;
                this.dataset = dataset;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            public boolean canWrite() {
                return !variable.isSystemDefined() && variable.getResetType() == JRDesignVariable.RESET_TYPE_GROUP;
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
                return variable.getResetGroup() == null ? "" : variable.getResetGroup();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof JRGroup)
                {
                    JRGroup oldValue = variable.getResetGroup();
                    JRGroup newValue = (JRGroup)val;
                    variable.setResetGroup(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                variable,
                                "ResetGroup", 
                                JRGroup.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    
    /**
     *  Class to manage the JRDesignVariable.PROPERTY_INCREMENT_TYPE property
     */
    private static final class IncrementTypeProperty extends PropertySupport
    {
            private JRDesignDataset dataset = null;
            private JRDesignVariable variable = null;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public IncrementTypeProperty(JRDesignVariable variable, JRDesignDataset dataset)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRDesignVariable.PROPERTY_INCREMENT_TYPE,Byte.class, "Increment type", "When the variable expression should be evaluated to get a new value", true, true);
                this.variable = variable;
                this.dataset = dataset;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            public boolean isDefaultValue() {
                return variable.getIncrementType() == JRDesignVariable.RESET_TYPE_NONE;
            }

            @Override
            public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
                setPropertyValue(JRDesignVariable.RESET_TYPE_NONE);
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
                    
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_REPORT), "Report"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_COLUMN), "Column"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_GROUP), "Group"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_NONE), "None"));
                    l.add(new Tag(new Byte(JRDesignVariable.RESET_TYPE_PAGE), "Page"));
                    
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(variable.getIncrementType());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Byte)
                {
                     setPropertyValue((Byte)val);
                }
            }
            
            private void setPropertyValue(Byte val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
            {
                    Byte oldValue = variable.getIncrementType();
                    Byte newValue = val;
                    
                    variable.setIncrementType(newValue);
                    
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                variable,
                                "IncrementType", 
                                Byte.TYPE,
                                oldValue,newValue);
                    
                    JRGroup oldGroupValue = variable.getIncrementGroup();
                    JRGroup newGroupValue = null;
                    if ( (val).byteValue() == JRDesignVariable.RESET_TYPE_GROUP )
                    {
                        if (dataset.getGroupsList().size() == 0)
                        {
                            IllegalArgumentException iae = annotateException("No groups are defined to be used with this variable"); 
                            throw iae; 
                        }
                    
                        newGroupValue = (JRGroup)dataset.getGroupsList().get(0);
                    }
                    
                    if (oldGroupValue != newGroupValue)
                    {
                        ObjectPropertyUndoableEdit urobGroup =
                                new ObjectPropertyUndoableEdit(
                                    variable,
                                    "IncrementGroup", 
                                    JRGroup.class,
                                    oldGroupValue,newGroupValue);
                        variable.setIncrementGroup(newGroupValue);
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
    
    /**
     *  Class to manage the JRDesignVariable.PROPERTY_INCREMENT_GROUP property
     */
    private static final class IncrementGroupProperty extends PropertySupport
    {
            private JRDesignDataset dataset = null;
            private JRDesignVariable variable = null;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public IncrementGroupProperty(JRDesignVariable variable, JRDesignDataset dataset)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRDesignVariable.PROPERTY_INCREMENT_GROUP,JRGroup.class, "Increment group", "Evaluate the variable expression when the specified group changes", true, true);
                this.variable = variable;
                this.dataset = dataset;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            public boolean canWrite() {
                return !variable.isSystemDefined() && variable.getIncrementType() == JRDesignVariable.RESET_TYPE_GROUP;
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
                return variable.getIncrementGroup() == null ? "" : variable.getIncrementGroup();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof JRGroup)
                {
                    JRGroup oldValue = variable.getIncrementGroup();
                    JRGroup newValue = (JRGroup)val;
                    variable.setIncrementGroup(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                variable,
                                "IncrementGroup", 
                                JRGroup.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    
    /**
     *  Class to manage the JRDesignVariable.PROPERTY_NAME property
     */
    public static final class IncrementerFactoryClassNameProperty extends PropertySupport.ReadWrite {

        JRDesignVariable variable = null;

        @SuppressWarnings("unchecked")
        public IncrementerFactoryClassNameProperty(JRDesignVariable variable)
        {
            super(JRDesignVariable.PROPERTY_INCREMENTER_FACTORY_CLASS_NAME, String.class,
                  "Incrementer Factory Class",
                  "Name of the optional Incrementer Factory Class to use with this variable");
            this.variable = variable;
            this.setValue("oneline", Boolean.TRUE);
        }

        @Override
        public boolean canWrite()
        {
            return !getVariable().isSystemDefined();
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (getVariable().getIncrementerFactoryClassName() == null) ? "" : getVariable().getIncrementerFactoryClassName();
        }

        @SuppressWarnings("unchecked")
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            String s = val == null ?  null : val+"";
            if (s != null && (s.trim().length() == 0 ||
                s.equals("null"))) s = null;

            String oldName = getVariable().getIncrementerFactoryClassName();
            getVariable().setIncrementerFactoryClassName(s);

            ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getVariable(), "IncrementerFactoryClassName", String.class, oldName, getVariable().getName());

            IReportManager.getInstance().addUndoableEdit(opue);

        }

        public JRDesignVariable getVariable() {
            return variable;
        }

        public void setVariable(JRDesignVariable variable) {
            this.variable = variable;
        }
    }
}
