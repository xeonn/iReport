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
import com.jaspersoft.ireport.designer.outline.NewTypesUtils;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.DeleteStyleUndoableEdit;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.NewAction;
import org.openide.actions.PasteAction;
import org.openide.actions.RenameAction;
import org.openide.actions.ReorderAction;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.nodes.Sheet.Set;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * ParameterNode detects the events fired by the subtended parameter.
 * Implements the support for the property sheet of a parameter.
 * If a parameter is system defined, it can not be cut.
 * Actions of a parameter node include copy, paste, reorder, rename and delete.
 * 
 * @author gtoffoli
 */
public class StyleNode extends AbstractStyleNode  {

    public StyleNode(JasperDesign jd, JRDesignStyle style, Lookup doLkp)
    {
        super (jd, style, doLkp);
    }
    
    public JRDesignStyle getDesignStyle()
    {
        return (JRDesignStyle)getStyle();
    }

        
    /**
     *  This is the function to create the sheet...
     * 
     */
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        
        Set set = sheet.get(Sheet.PROPERTIES);
        Property[] props = set.getProperties();
        
        // Remove all the properties...
        for (int i=0; i<props.length; ++i)
        {
            set.remove(props[i].getName());
        }
        // Add the missing properties...
        set.put(new NameProperty( getDesignStyle(), jd));
        set.put(new DefaultStyleProperty( getDesignStyle(), jd));
        set.put(new ParentStyleProperty( getDesignStyle(), jd));
        
        for (int i=0; i<props.length; ++i)
        {
            set.put(props[i]);
        }
        
        return sheet;
    }
    
        
    @Override
    public boolean canRename() {
        return true;
    }
    
    
    @Override
    public void destroy() throws IOException {
       
          int index = jd.getStylesList().indexOf(getStyle());
          jd.removeStyle(getStyle());
          
          DeleteStyleUndoableEdit undo = new DeleteStyleUndoableEdit(getDesignStyle(), jd,index); //newIndex
          IReportManager.getInstance().addUndoableEdit(undo);
          
          super.destroy();
    }
        
    @Override
    public Action[] getActions(boolean popup) {
        return new Action[] {
            SystemAction.get(NewAction.class),
            SystemAction.get( CopyAction.class ),
            SystemAction.get( PasteAction.class),
            SystemAction.get( CutAction.class ),
            SystemAction.get( RenameAction.class ),
            SystemAction.get( ReorderAction.class ),
            null,
            SystemAction.get( DeleteAction.class ) };
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
    public void setName(String s) {
        
        if (s.equals(""))
        {
            throw new IllegalArgumentException(I18n.getString("StyleNode.Exception.NameNotValid"));
        }
        
        List<JRDesignStyle> currentStyles = null;
        currentStyles = (List<JRDesignStyle>)jd.getStylesList();
        for (JRDesignStyle p : currentStyles)
        {
            if (p != getStyle() && p.getName().equals(s))
            {
                throw new IllegalArgumentException(I18n.getString("StyleNode.Exception.NameInUse"));
            }
        }
        
        String oldName = getStyle().getName();
        getDesignStyle().setName(s);
        
        ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getStyle(), "Name", String.class, oldName, s);

        IReportManager.getInstance().addUndoableEdit(opue);
    }

    
    @Override
    public NewType[] getNewTypes()
    {
        return NewTypesUtils.getNewType(this, NewTypesUtils.CONDITIONAL_STYLE);
    }
    
    
    /***************  SHEET PROPERTIES DEFINITIONS **********************/
    
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_NAME property
     */
    public static final class NameProperty extends PropertySupport.ReadWrite {

        JRDesignStyle style = null;
        JasperDesign jd = null;

        @SuppressWarnings("unchecked")
        public NameProperty(JRDesignStyle style, JasperDesign jd)
        {
            super(JRDesignStyle.PROPERTY_NAME, String.class,
                  I18n.getString("StyleNode.Property.Name"),
                  I18n.getString("StyleNode.Property.NameStyle"));
            this.style = style;
            this.jd = jd;
            this.setValue("oneline", Boolean.TRUE);
        }

        @Override
        public boolean canWrite()
        {
            return true;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return getStyle().getName();
        }

        @SuppressWarnings("unchecked")
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {


            if (val == null || val.equals(""))
            {
                IllegalArgumentException iae = annotateException(I18n.getString("StyleNode.Exception.NameNotValid")); 
                throw iae; 
            }

            String s = val+"";

            List<JRDesignStyle> currentStyles = null;
            currentStyles = (List<JRDesignStyle>)getJasperDesign().getStylesList();
            for (JRDesignStyle st : currentStyles)
            {
                if (st != getStyle() && st.getName().equals(s))
                {
                    IllegalArgumentException iae = annotateException(I18n.getString("StyleNode.Exception.NameInUse")); 
                    throw iae; 
                }
            }
            String oldName = getStyle().getName();
            getStyle().setName(s);

            ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getStyle(), "Name", String.class, oldName, getStyle().getName());

            IReportManager.getInstance().addUndoableEdit(opue);

        }

        public JasperDesign getJasperDesign() {
            return jd;
        }

        public JRDesignStyle getStyle() {
            return style;
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
     *  Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
     */
    public static final class DefaultStyleProperty extends PropertySupport.ReadWrite {

        JRDesignStyle style = null;
        JasperDesign jd = null;

        @SuppressWarnings("unchecked")
        public DefaultStyleProperty(JRDesignStyle style, JasperDesign jd)
        {
            super(JRDesignStyle.PROPERTY_DEFAULT, Boolean.class,
                  "Default Style",
                  I18n.getString("StyleNode.Property.DefaultStyle"));
            this.jd = jd;
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Boolean( getStyle().isDefault() );
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val != null && val instanceof Boolean)
            {
                Boolean oldValue = getStyle().isDefault();
                Boolean newValue = (Boolean)val;
                ObjectPropertyUndoableEdit urob2 = null;
                
                // Find if there is another default style...
                if (newValue.booleanValue())
                {
                    List<JRDesignStyle> list = (List<JRDesignStyle>)getJasperDesign().getStylesList();
                    for (JRDesignStyle st : list)
                    {
                        if (st.isDefault())
                        {
                            st.setDefault(false);
                            urob2 = new ObjectPropertyUndoableEdit(
                                st,
                                "Default", 
                                Boolean.TYPE,
                                Boolean.TRUE,Boolean.FALSE);
                            break;
                        }
                    }
                }
                
                getStyle().setDefault(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getStyle(),
                            "Default", 
                            Boolean.TYPE,
                            oldValue,newValue);
                if (urob2 != null)
                {
                    urob.concatenate(urob2);
                }
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }

        @Override
        public boolean isDefaultValue() {
            return !getStyle().isDefault();
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            super.restoreDefaultValue();
            setValue(Boolean.FALSE);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
        
        public JasperDesign getJasperDesign() {
            return jd;
        }

        public JRDesignStyle getStyle() {
            return style;
        }

        
    }
    
    public static final class ParentStyleProperty extends PropertySupport implements PropertyChangeListener
    {
        JRDesignStyle style = null;
        JasperDesign jd = null;
        private ComboBoxPropertyEditor editor;

        @SuppressWarnings("unchecked")
        public ParentStyleProperty(JRDesignStyle style, JasperDesign jd)
        {
            // TODO: Replace WhenNoDataType with the right constant
            super( JRDesignStyle.PROPERTY_PARENT_STYLE, String.class, I18n.getString("StyleNode.Property.Style"), I18n.getString("StyleNode.Property.Message"), true, true);
            this.jd = jd;
            this.style = style;

            setValue("canEditAsText", Boolean.TRUE);
            setValue("oneline", Boolean.TRUE);
            setValue("suppressCustomEditor", Boolean.FALSE);
            
            jd.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, jd.getEventSupport()));
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

            if (getStyle().getStyle() != null)
            {
                return getStyle().getStyle();
            }
            else if (getStyle().getStyleNameReference() != null)
            {
                return getStyle().getStyleNameReference();
            }
            return "";
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

            String oldValue = getStyle().getStyleNameReference();
            JRStyle oldStyle = getStyle().getStyle();

            getStyle().setParentStyleNameReference(newValue);
            getStyle().setParentStyle(newStyle);

            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            getStyle(),
                            "ParentStyleNameReference", 
                            String.class,
                            oldValue,newValue);

            ObjectPropertyUndoableEdit urob_style =
                        new ObjectPropertyUndoableEdit(
                            getStyle(),
                            "ParentStyle", 
                            JRStyle.class,
                            oldStyle,newStyle);

            urob.concatenate(urob_style);

            // Find the undoRedo manager...
            IReportManager.getInstance().addUndoableEdit(urob);
        }

        public JasperDesign getJasperDesign() {
            return jd;
        }

        public JRDesignStyle getStyle() {
            return style;
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
        List styles = getJasperDesign().getStylesList();
        for (int i=0; i<styles.size(); ++i)
        {
            JRDesignStyle st = (JRDesignStyle)styles.get(i);
            l.add(new Tag( st , st.getName()));
            st.getEventSupport().addPropertyChangeListener(WeakListeners.propertyChange(this, st.getEventSupport()));
        }
        
        return l;
    }
    }

    

}
