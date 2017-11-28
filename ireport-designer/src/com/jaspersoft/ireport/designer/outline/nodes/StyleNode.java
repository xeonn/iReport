/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.IRFont;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.sheet.FieldPatternProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.DeleteStyleUndoableEdit;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.datatransfer.Transferable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
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
import org.openide.util.WeakListeners;
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
public class StyleNode extends IRAbstractNode implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRDesignStyle style = null;

    public StyleNode(JasperDesign jd, JRDesignStyle style, Lookup doLkp)
    {
        super (Children.LEAF, new ProxyLookup(doLkp, Lookups.fixed(jd, style)));
        this.jd = jd;
        this.style = style;
        setDisplayName ( style.getName());
        super.setName( style.getName() );
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/style-16.png");
        
        style.getEventSupport().addPropertyChangeListener(this);
    }

    @Override
    public String getDisplayName() {
        return getStyle().getName();
    }
    
    /**
     *  This is the function to create the sheet...
     * 
     */
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        
        Sheet.Set set = Sheet.createPropertiesSet();
        
        set.put(new NameProperty( getStyle(), jd));
        set.put(new DefaultStyleProperty( getStyle(), jd));
        set.put(new ParentStyleProperty( getStyle(), jd));
        set.put(new ModeProperty( getStyle()));
        set.put(new ForecolorProperty( getStyle()));
        set.put(new BackcolorProperty( getStyle()));
        // Pen....
        // Width...
        set.put(new FillProperty( getStyle()));
        set.put(new RadiusProperty( getStyle()));
        set.put(new ScaleImageProperty( getStyle()));
        set.put(new HorizontalAlignmentProperty( getStyle()));
        set.put(new VerticalAlignmentProperty( getStyle()));
        
        set.put(new RotationProperty( getStyle() ));
        set.put(new LineSpacingProperty( getStyle() ));
        set.put(new StyledTextProperty( getStyle() ));
        
        set.put(new FontNameProperty( getStyle() ));
        set.put(new FontSizeProperty( getStyle() ));
        
        set.put(new BoldProperty( getStyle() ));
        set.put(new ItalicProperty( getStyle() ));
        set.put(new UnderlineProperty( getStyle() ));
        set.put(new StrikeThroughProperty( getStyle() ));
        
        set.put(new PdfFontNameProperty( getStyle() ));
        set.put(new PdfEmbeddedProperty( getStyle() ));
        set.put(new PdfEncodingProperty( getStyle() ));
        
        set.put(new PatternProperty( getStyle() ));
        set.put(new BlankWhenNullProperty( getStyle() ));
        
                
        sheet.put(set);
        return sheet;
    }
    
    @Override
    public boolean canCut() {
        return true;
    }
    
    @Override
    public boolean canRename() {
        return true;
    }
    
    @Override
    public boolean canDestroy() {
        return true;
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
       
          int index = jd.getStylesList().indexOf(getStyle());
          jd.removeStyle(getStyle());
          
          DeleteStyleUndoableEdit undo = new DeleteStyleUndoableEdit(getStyle(), jd,index); //newIndex
          IReportManager.getInstance().addUndoableEdit(undo);
          
          super.destroy();
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
        //tras.put(new ReportObjectPaletteTransferable( 
        //            "com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldFromParameterAction",
        //            getParameter()));
        
        return tras;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignStyle.PROPERTY_NAME ))
        {
            super.setName(getStyle().getName());
            this.setDisplayName(getStyle().getName());
        }
        
        // Update the sheet
        this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setName(String s) {
        
        if (s.equals(""))
        {
            throw new IllegalArgumentException("Style name not valid.");
        }
        
        List<JRDesignStyle> currentStyles = null;
        JRDesignDataset dataset = getParentNode().getLookup().lookup(JRDesignDataset.class);
        currentStyles = (List<JRDesignStyle>)jd.getStylesList();
        for (JRDesignStyle p : currentStyles)
        {
            if (p != getStyle() && p.getName().equals(s))
            {
                throw new IllegalArgumentException("Style name already in use.");
            }
        }
        
        String oldName = getStyle().getName();
        getStyle().setName(s);
        
        ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    getStyle(), "Name", String.class, oldName, s);

        IReportManager.getInstance().addUndoableEdit(opue);
    }

    
    public JRDesignStyle getStyle() {
        return style;
    }

    public void setStyle(JRDesignStyle style) {
        this.style = style;
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
                  "Name",
                  "Name of the style");
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
                IllegalArgumentException iae = annotateException("Style name not valid."); 
                throw iae; 
            }

            String s = val+"";

            List<JRDesignStyle> currentStyles = null;
            currentStyles = (List<JRDesignStyle>)getJasperDesign().getStylesList();
            for (JRDesignStyle st : currentStyles)
            {
                if (st != getStyle() && st.getName().equals(s))
                {
                    IllegalArgumentException iae = annotateException("Style name already in use."); 
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
                  "Default Style");
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
            super( JRDesignStyle.PROPERTY_PARENT_STYLE, String.class, "Style", "The optional style to use as parent. Can be the name of a locally defined style or the name of a style defined in an external style template file.", true, true);
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

    
    public final class ModeProperty extends PropertySupport.ReadWrite {

        JRDesignStyle style = null;

        @SuppressWarnings("unchecked")
        public ModeProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_MODE, Boolean.class,
                  "Opaque",
                  "Set if the style is opaque or transparent.");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return new Boolean( style.getMode() != null && style.getMode() == JRDesignElement.MODE_OPAQUE);
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Byte oldValue = style.getOwnMode();
                Byte newValue =   val == null ? null : ( ((Boolean)val).booleanValue()  ? JRDesignElement.MODE_OPAQUE : JRDesignElement.MODE_TRANSPARENT );
                style.setMode(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "Mode", 
                            Byte.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }

        @Override
        public boolean isDefaultValue() {
            return style.getOwnMode() == null;
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
    
    
    public final class ForecolorProperty extends PropertySupport.ReadWrite {

        JRDesignStyle style = null;

        @SuppressWarnings("unchecked")
        public ForecolorProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_FORECOLOR, java.awt.Color.class,
                  "Forecolor",
                  "The foreground color.");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.getForecolor();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

        private void setPropertyValue(Object val)
        {
            if (val == null || val instanceof Color)
            {
                Color oldValue = style.getOwnForecolor();
                Color newValue =  (Color)val;
                style.setForecolor(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "Forecolor", 
                            Color.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return null == style.getOwnForecolor();
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
    
    
    public final class BackcolorProperty extends PropertySupport.ReadWrite {

        JRDesignStyle style = null;

        @SuppressWarnings("unchecked")
        public BackcolorProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_BACKCOLOR, java.awt.Color.class,
                  "Backcolor",
                  "The background color.");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.getBackcolor();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            setPropertyValue(val);
        }

        private void setPropertyValue(Object val)
        {
            if (val == null || val instanceof Color)
            {
                Color oldValue = style.getOwnBackcolor();
                Color newValue =  (Color)val;
                style.setBackcolor(newValue);

                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "Backcolor", 
                            Color.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return null == style.getOwnBackcolor();
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
 
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    public final class FillProperty extends PropertySupport
    {
            private final JRDesignStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public FillProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_FILL,Byte.class, "Fill", "Fill.", true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JRDesignGraphicElement.FILL_SOLID), "Solid"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }

            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getFill();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }


            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = style.getOwnFill();
                    Byte newValue = (Byte)val;
                    style.setFill(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "Fill", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }

            @Override
            public boolean isDefaultValue() {
                return style.getOwnFill() == null;
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
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_X property
     */
    public final class RadiusProperty extends PropertySupport
    {
            private final JRDesignStyle style;

            @SuppressWarnings("unchecked")
            public RadiusProperty(JRDesignStyle style)
            {
                super(JRBaseStyle.PROPERTY_RADIUS,Integer.class, "Radius", "The radius used to paint the corners.", true, true);
                this.style = style;
            }

            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getRadius();
            }

            // TODO: check page width with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val == null || val instanceof Integer)
                {
                    Integer oldValue = style.getOwnRadius();
                    Integer newValue = (Integer)val;

                    if (newValue < 0)
                    {
                        IllegalArgumentException iae = annotateException("This property requires a positive number.");
                        throw iae; 
                    }

                    style.setRadius(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "Radius", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
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
                return style.getOwnRadius() == null;
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
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    public final class ScaleImageProperty extends PropertySupport
    {
            private final JRDesignStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public ScaleImageProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_SCALE_IMAGE,Byte.class, "Scale", "How to scale the image.", true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(null, "<Default>"));
                    l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_CLIP), "Clip"));
                    l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_FILL_FRAME), "Fill Frame"));
                    l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE), "Retain Shape"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }

            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getScaleImage();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = style.getOwnScaleImage();
                    Byte newValue = (Byte)val;
                    style.setScaleImage(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "ScaleImage", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }

            @Override
            public boolean isDefaultValue() {
                return style.getOwnScaleImage() == null;
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

    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    public final class HorizontalAlignmentProperty extends PropertySupport
    {
            private final JRDesignStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public HorizontalAlignmentProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT,Byte.class, "Horizontal Alignment", "How to align the image.", true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(null, "<Default>"));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_LEFT), "Left"));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_CENTER), "Center"));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_RIGHT), "Right"));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_JUSTIFIED), "Justified"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }

            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getHorizontalAlignment();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = style.getOwnHorizontalAlignment();
                    Byte newValue = (Byte)val;
                    style.setHorizontalAlignment(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "HorizontalAlignment", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }

            @Override
            public boolean isDefaultValue() {
                return style.getOwnHorizontalAlignment() == null;
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
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    public final class VerticalAlignmentProperty extends PropertySupport
    {
            private final JRDesignStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public VerticalAlignmentProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT,Byte.class, "Vertical Alignment", "How to align the image.", true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(null, "<Default>"));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_TOP), "Left"));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_MIDDLE), "Center"));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_BOTTOM), "Right"));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_JUSTIFIED), "Justified"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }

            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getVerticalAlignment();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = style.getOwnVerticalAlignment();
                    Byte newValue = (Byte)val;
                    style.setVerticalAlignment(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "VerticalAlignment", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }

            @Override
            public boolean isDefaultValue() {
                return style.getOwnVerticalAlignment() == null;
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
    
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    private static final class RotationProperty extends PropertySupport
    {
            private final JRDesignStyle style;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public RotationProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_ROTATION,Byte.class, "Rotation", "How to rotate the text.", true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(null, "<Default>"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_NONE), "None"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_LEFT), "Left"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_RIGHT), "Right"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_UPSIDE_DOWN), "Upside Down"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getRotation();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }

            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = style.getOwnRotation();
                    Byte newValue = (Byte)val;
                    style.setRotation(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "Rotation", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
                
                
            }

            @Override
            public boolean isDefaultValue() {
                return style.getOwnRotation() == null;
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
    
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    private static final class LineSpacingProperty extends PropertySupport
    {
            private final JRDesignStyle style;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public LineSpacingProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_LINE_SPACING,Byte.class, "Line Spacing", "The space to put between lines of text.", true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(null, "<Default>"));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_SINGLE), "Single"));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_1_1_2), "1.5"));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_DOUBLE), "Double"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getLineSpacing();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }

            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = style.getOwnLineSpacing();
                    Byte newValue = (Byte)val;
                    style.setLineSpacing(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "LineSpacing", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }

            @Override
            public boolean isDefaultValue() {
                return style.getOwnLineSpacing() == null;
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
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_FOR_PROMPTING property
     */
    public static final class StyledTextProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public StyledTextProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_IS_STYLED_TEXT, Boolean.class,
                  "Is Styled Text",
                  "Set if the value should be parsed as styled text.");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isStyledText();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnStyledText();
                Boolean newValue =   (Boolean)val;
                style.setStyledText(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "StyledText", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnStyledText() == null;
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
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_BOLD property
     */
    public static final class BoldProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public BoldProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_BOLD, Boolean.class,
                  "Bold",
                  "");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isBold();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnBold();
                Boolean newValue =   (Boolean)val;
                style.setBold(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "Bold", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnBold() == null;
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
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_ITALIC property
     */
    public static final class ItalicProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public ItalicProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_ITALIC, Boolean.class,
                  "Italic",
                  "");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isItalic();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnItalic();
                Boolean newValue =   (Boolean)val;
                style.setItalic(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "Italic", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnItalic() == null;
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
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_STRIKE_THROUGH property
     */
    public static final class StrikeThroughProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public StrikeThroughProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_STRIKE_THROUGH, Boolean.class,
                  "Strike Through",
                  "");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isStrikeThrough();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnStrikeThrough();
                Boolean newValue =   (Boolean)val;
                style.setStrikeThrough(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "StrikeThrough", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnStrikeThrough() == null;
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
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_UNDERLINE property
     */
    public static final class UnderlineProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public UnderlineProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_UNDERLINE, Boolean.class,
                  "Underline",
                  "");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isUnderline();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnUnderline();
                Boolean newValue =   (Boolean)val;
                style.setUnderline(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "Underline", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnUnderline() == null;
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
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_PDF_EMBEDDED property
     */
    public static final class PdfEmbeddedProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public PdfEmbeddedProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_PDF_EMBEDDED, Boolean.class,
                  "Pdf Embedded",
                  "");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isPdfEmbedded();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnPdfEmbedded();
                Boolean newValue =   (Boolean)val;
                style.setPdfEmbedded(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "PdfEmbedded", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnPdfEmbedded() == null;
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
    
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_VALUE_CLASS_NAME property
     */
    private static final class FontNameProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public FontNameProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_FONT_NAME, String.class,
                  "Font name",
                  "Font name");
            this.style = style;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.getFontName();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof String)
            {
                String oldValue = style.getOwnFontName();
                String newValue =   (String)val;
                style.setFontName(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "FontName", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return style.getOwnFontName() == null;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
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
                java.util.List classes = new ArrayList();
                //List<Font> fonts = IReportManager.getInstance().getFonts();
                //
                //for (Font f : fonts)
                //{
                //    classes.add(new Tag(f.getFontName()));
                //}
                
                String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
                for (int i = 0; i < names.length; i++) {
                        String name = names[i];
                        classes.add(new Tag(name));
                }
                editor = new ComboBoxPropertyEditor(true, classes);
            }
            return editor;
        }

    }
    
    
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_FONT_SIZE property
     */
    private static final class FontSizeProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public FontSizeProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_FONT_SIZE, Integer.class,
                  "Size",
                  "Size");
            this.style = style;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.getFontSize();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val != null && ! (val instanceof Integer))
            {
                // Try to convert the value in an integer...
                try {
                    
                    val = new Integer(val+"");
                } catch (Exception ex) {
                    // no way...
                    return;
                }
            }
            
            if (val == null || val instanceof Integer)
            {
                Integer oldValue = style.getOwnFontSize();
                Integer newValue =   (Integer)val;
                style.setFontSize(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "FontSize", 
                            Integer.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return style.getOwnFontSize() == null;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
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
                java.util.List classes = new ArrayList();
                for (int i=6; i<100; )
                {
                    classes.add(new Tag(new Integer(i), ""+i));
                    
                    if (i<16) i++;
                    else if (i<32) i+=2;
                    else if (i<48) i+=4;
                    else if (i<72) i+=6;
                    else i+=8;
                }
                    
                editor = new ComboBoxPropertyEditor(true, classes);
            }
            return editor;
        }

    }
    
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_PDF_FONT_NAME property
     */
    private static final class PdfFontNameProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public PdfFontNameProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_PDF_FONT_NAME, String.class,
                  "Pdf Font name",
                  "Pdf Font name");
            this.style = style;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",true);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.getPdfFontName();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof String)
            {
                String oldValue = style.getOwnPdfFontName();
                String newValue =   (String)val;
                
                style.setPdfFontName(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "PdfFontName", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return style.getOwnPdfFontName() == null;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
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
                java.util.List classes = new ArrayList();
                
                // Add regular PDF fonts...
                classes.add(new Tag("Helvetica"));
                classes.add(new Tag("Helvetica-Bold"));
                classes.add(new Tag("Helvetica-BoldOblique"));
                classes.add(new Tag("Helvetica-Oblique"));
                classes.add(new Tag("Courier"));
                classes.add(new Tag("Courier-Bold"));
                classes.add(new Tag("Courier-BoldOblique"));
                classes.add(new Tag("Courier-Oblique"));
                classes.add(new Tag("Symbol"));
                classes.add(new Tag("Times-Roman"));
                classes.add(new Tag("Times-Bold"));
                classes.add(new Tag("Times-BoldItalic"));
                classes.add(new Tag("Times-Italic"));
                classes.add(new Tag("ZapfDingbats"));
                classes.add(new Tag("STSong-Light"));
                classes.add(new Tag("MHei-Medium"));
                classes.add(new Tag("MSung-Light"));
                classes.add(new Tag("HeiseiKakuGo-W5"));
                classes.add(new Tag("HeiseiMin-W3"));
                classes.add(new Tag("HYGoThic-Medium"));
                classes.add(new Tag("HYSMyeongJo-Medium"));
                
                List<IRFont> fonts = IReportManager.getInstance().getIRFonts();
                
                for (IRFont f : fonts)
                {
                    classes.add(new Tag(f.getFile(), f.toString()));
                }
                
                editor = new ComboBoxPropertyEditor(true, classes);
            }
            return editor;
        }
    }
    
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_PDF_ENCODING property
     */
    private static final class PdfEncodingProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public PdfEncodingProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_PDF_ENCODING, String.class,
                  "Pdf Encoding",
                  "Pdf Encoding");
            this.style = style;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.getPdfEncoding();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof String)
            {
                if ((val+"").trim().length() == 0) val = null;
                
                String oldValue = style.getOwnPdfEncoding();
                String newValue = (String)val;
                
                style.setPdfEncoding(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "PdfEncoding", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return style.getOwnPdfEncoding() == null;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
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
                java.util.List encodings = new ArrayList();
                
                // Add regular PDF fonts...
        	encodings.add(new Tag("Cp1250","CP1250 (Central European)"));
                encodings.add(new Tag("Cp1251","CP1251 (Cyrillic)"));
                encodings.add(new Tag("Cp1252","CP1252 (Western European ANSI aka WinAnsi)"));
                encodings.add(new Tag("Cp1253","CP1253 (Greek)"));
                encodings.add(new Tag("Cp1254","CP1254 (Turkish)"));
                encodings.add(new Tag("Cp1255","CP1255 (Hebrew)"));
                encodings.add(new Tag("Cp1256","CP1256 (Arabic)"));
                encodings.add(new Tag("Cp1257","CP1257 (Baltic)"));
                encodings.add(new Tag("Cp1258","CP1258 (Vietnamese)"));
                encodings.add(new Tag("UniGB-UCS2-H","UniGB-UCS2-H (Chinese Simplified)"));
                encodings.add(new Tag("UniGB-UCS2-V","UniGB-UCS2-V (Chinese Simplified)"));
                encodings.add(new Tag("UniCNS-UCS2-H","UniCNS-UCS2-H (Chinese traditional)"));
                encodings.add(new Tag("UniCNS-UCS2-V","UniCNS-UCS2-V (Chinese traditional)"));
                encodings.add(new Tag("UniJIS-UCS2-H","UniJIS-UCS2-H (Japanese)"));
                encodings.add(new Tag("UniJIS-UCS2-V","UniJIS-UCS2-V (Japanese)"));
                encodings.add(new Tag("UniJIS-UCS2-HW-H","UniJIS-UCS2-HW-H (Japanese)"));
                encodings.add(new Tag("UniJIS-UCS2-HW-V","UniJIS-UCS2-HW-V (Japanese)"));
                encodings.add(new Tag("UniKS-UCS2-H","UniKS-UCS2-H (Korean)"));
                encodings.add(new Tag("UniKS-UCS2-V","UniKS-UCS2-V (Korean)"));
                encodings.add(new Tag("Identity-H","Identity-H (Unicode with horizontal writing)"));
                encodings.add(new Tag("Identity-V","Identity-V (Unicode with vertical writing)"));
                
                editor = new ComboBoxPropertyEditor(true, encodings);
            }
            return editor;
        }

    }
    
    
              /**
     *  Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
     */
    private static final class PatternProperty extends FieldPatternProperty
    {
            private final JRDesignStyle style;
            
            @SuppressWarnings("unchecked")
            public PatternProperty(JRDesignStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRBaseStyle.PROPERTY_PATTERN,
                        "Pattern", "Patter to use to format numbers and dates.");
                this.style = style;
            }

            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return style.getPattern() == null ? "" :  style.getPattern();
            }

            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val == null || val instanceof String)
                {
                    String oldValue = style.getOwnPattern();
                    String newValue = (String)val;
                    if (newValue != null && newValue.length() == 0) newValue = null;
                    
                    style.setPattern(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                style,
                                "Pattern", 
                                String.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
            
            @Override
            public boolean isDefaultValue() {
                return style.getPattern() == null;
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
    
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_ITALIC property
     */
    public static final class BlankWhenNullProperty extends PropertySupport.ReadWrite {

        private final JRDesignStyle style;

        @SuppressWarnings("unchecked")
        public BlankWhenNullProperty(JRDesignStyle style)
        {
            super(JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, Boolean.class,
                  "Blank When Null",
                  "Print a blank string instead of null.");
            this.style = style;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return style.isBlankWhenNull();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = style.isOwnBlankWhenNull();
                Boolean newValue = (Boolean)val;
                style.setBlankWhenNull(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            style,
                            "BlankWhenNull", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return style.isOwnBlankWhenNull() == null;
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
