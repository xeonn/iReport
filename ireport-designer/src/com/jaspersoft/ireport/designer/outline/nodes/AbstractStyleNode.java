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
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.sheet.JRLineBoxProperty;
import com.jaspersoft.ireport.designer.sheet.JRPenProperty;
import com.jaspersoft.ireport.designer.sheet.properties.StylePatternProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.locale.I18n;
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
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBasePen;
import net.sf.jasperreports.engine.base.JRBaseStyle;
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
public class AbstractStyleNode extends IRAbstractNode implements PropertyChangeListener {

    JasperDesign jd = null;
    private JRBaseStyle style = null;

    public AbstractStyleNode(JasperDesign jd, JRDesignStyle style, Lookup doLkp)
    {
        super (new StyleChildren(jd, style, doLkp) , new ProxyLookup(doLkp, Lookups.fixed(jd, style)));
        this.jd = jd;
        this.style = style;
        
        init();
    }
    
    public AbstractStyleNode(JasperDesign jd, JRBaseStyle style, Lookup doLkp)
    {
        super (Children.LEAF, new ProxyLookup(doLkp, Lookups.fixed(jd, style)));
        this.jd = jd;
        this.style = style;
        
        init();
    }
    
    private void init()
    {
        setDisplayName ( style.getName());
        super.setName( style.getName() );
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/style-16.png");
        
        style.getEventSupport().addPropertyChangeListener(this);
        ((JRBasePen)style.getLinePen()).getEventSupport().addPropertyChangeListener(this);
        
        JRBaseLineBox baseBox = (JRBaseLineBox)style.getLineBox();
        baseBox.getEventSupport().addPropertyChangeListener(this);
        ((JRBasePen)baseBox.getPen()).getEventSupport().addPropertyChangeListener(this);
        ((JRBasePen)baseBox.getTopPen()).getEventSupport().addPropertyChangeListener(this);
        ((JRBasePen)baseBox.getBottomPen()).getEventSupport().addPropertyChangeListener(this);
        ((JRBasePen)baseBox.getLeftPen()).getEventSupport().addPropertyChangeListener(this);
        ((JRBasePen)baseBox.getRightPen()).getEventSupport().addPropertyChangeListener(this);
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
        
        //set.put(new NameProperty( getStyle(), jd));
        //set.put(new DefaultStyleProperty( getStyle(), jd));
        //set.put(new ParentStyleProperty( getStyle(), jd));
        set.put(new ModeProperty( getStyle()));
        set.put(new ForecolorProperty( getStyle()));
        set.put(new BackcolorProperty( getStyle()));
        // Pen....
        
        set.put(new PaddingAndBordersProperty( getStyle()));
        JRPenProperty penProp = new JRPenProperty(getStyle().getLinePen(), getStyle());
        
        set.put(penProp);
        
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
        
        set.put(new StylePatternProperty( getStyle() ));
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
        return false;
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
        else if (evt.getPropertyName().equals(JRBasePen.PROPERTY_LINE_COLOR) ||
                 evt.getPropertyName().equals(JRBasePen.PROPERTY_LINE_STYLE) ||
                 evt.getPropertyName().equals(JRBasePen.PROPERTY_LINE_WIDTH))
        {
            
            if (ModelUtils.containsProperty(this.getPropertySets(),"pen"))
            {
                this.firePropertyChange("pen", evt.getOldValue(), evt.getNewValue() );
            }
            
            if (ModelUtils.containsProperty(this.getPropertySets(),"linebox"))
            {
                this.firePropertyChange("linebox", evt.getOldValue(), evt.getNewValue() );
            }
        }
        else if (evt.getPropertyName().equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING) ||
                 evt.getPropertyName().equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING) ||
                 evt.getPropertyName().equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING) ||
                 evt.getPropertyName().equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING))
        {
            if (ModelUtils.containsProperty(this.getPropertySets(),"linebox"))
            {
                this.firePropertyChange("linebox", evt.getOldValue(), evt.getNewValue() );
            }
        }
        
        // Update the sheet
        this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
    }

    public JRBaseStyle getStyle() {
        return style;
    }

    public void setStyle(JRBaseStyle style) {
        this.style = style;
    }
    
    
    /***************  SHEET PROPERTIES DEFINITIONS **********************/
    
    public final class ModeProperty extends PropertySupport.ReadWrite {

        JRBaseStyle style = null;

        @SuppressWarnings("unchecked")
        public ModeProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_MODE, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Opaque"),
                  I18n.getString("AbstractStyleNode.Property.Set"));
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

        JRBaseStyle style = null;

        @SuppressWarnings("unchecked")
        public ForecolorProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_FORECOLOR, java.awt.Color.class,
                  I18n.getString("AbstractStyleNode.Property.Forecolor"),
                  I18n.getString("AbstractStyleNode.Property.Forecolordetail"));
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

        JRBaseStyle style = null;

        @SuppressWarnings("unchecked")
        public BackcolorProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_BACKCOLOR, java.awt.Color.class,
                  I18n.getString("AbstractStyleNode.Property.Backcolor"),
                  I18n.getString("AbstractStyleNode.Property.Backcolordetail"));
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
            private final JRBaseStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public FillProperty(JRBaseStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_FILL,Byte.class, I18n.getString("AbstractStyleNode.Property.Fill"),I18n.getString("AbstractStyleNode.Property.Fill"), true, true);
                this.style = style;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JRDesignGraphicElement.FILL_SOLID), I18n.getString("AbstractStyleNode.Property.Solid")));
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
            private final JRBaseStyle style;

            @SuppressWarnings("unchecked")
            public RadiusProperty(JRBaseStyle style)
            {
                super(JRBaseStyle.PROPERTY_RADIUS,Integer.class, I18n.getString("RadiusPropertyRadius.Property.Radius"),  I18n.getString("RadiusPropertyRadius.Property.Radiusdetail"), true, true);
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
                        IllegalArgumentException iae = annotateException(I18n.getString("RadiusPropertyRadius.Property.Message"));
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
            private final JRBaseStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public ScaleImageProperty(JRBaseStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_SCALE_IMAGE,Byte.class, I18n.getString("AbstractStyleNode.Property.Scale"), I18n.getString("AbstractStyleNode.Property.Scaledetail"), true, true);
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
                    l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_CLIP), I18n.getString("AbstractStyleNode.Property.Clip")));
                    l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_FILL_FRAME), I18n.getString("AbstractStyleNode.Property.Fill_Frame")));
                    l.add(new Tag(new Byte(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE), I18n.getString("AbstractStyleNode.Property.Retain_Shape")));
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
            private final JRBaseStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public HorizontalAlignmentProperty(JRBaseStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT,Byte.class, I18n.getString("AbstractStyleNode.Property.Horizontal_Alignment"), I18n.getString("AbstractStyleNode.Property.HorizDetail"), true, true);
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
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_LEFT), I18n.getString("AbstractStyleNode.Property.Left")));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_CENTER), I18n.getString("AbstractStyleNode.Property.Center")));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_RIGHT), I18n.getString("AbstractStyleNode.Property.Right")));
                    l.add(new Tag(new Byte(JRDesignImage.HORIZONTAL_ALIGN_JUSTIFIED), I18n.getString("AbstractStyleNode.Property.Justified")));
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
            private final JRBaseStyle style;
            private ComboBoxPropertyEditor editor;

            @SuppressWarnings("unchecked")
            public VerticalAlignmentProperty(JRBaseStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT,Byte.class, I18n.getString("AbstractStyleNode.Property.Vertical_Alignment"), I18n.getString("AbstractStyleNode.Property.VerticalDetail"), true, true);
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
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_TOP), I18n.getString("AbstractStyleNode.Property.Left")));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_MIDDLE), I18n.getString("AbstractStyleNode.Property.Center")));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_BOTTOM), I18n.getString("AbstractStyleNode.Property.Right")));
                    l.add(new Tag(new Byte(JRDesignImage.VERTICAL_ALIGN_JUSTIFIED), I18n.getString("AbstractStyleNode.Property.Justified")));
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
            private final JRBaseStyle style;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public RotationProperty(JRBaseStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_ROTATION,Byte.class, I18n.getString("AbstractStyleNode.Property.Rotation"), I18n.getString("AbstractStyleNode.Property.RotationDetail"), true, true);
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
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_NONE), I18n.getString("AbstractStyleNode.Property.None")));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_LEFT), I18n.getString("AbstractStyleNode.Property.Left")));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_RIGHT), I18n.getString("AbstractStyleNode.Property.Right")));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_UPSIDE_DOWN), I18n.getString("AbstractStyleNode.Property.Upside_Down")));
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
            private final JRBaseStyle style;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public LineSpacingProperty(JRBaseStyle style)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_LINE_SPACING,Byte.class, I18n.getString("AbstractStyleNode.Property.Line_Spacing"), I18n.getString("AbstractStyleNode.Property.LSDetail"), true, true);
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
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_SINGLE), I18n.getString("AbstractStyleNode.Property.Single")));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_1_1_2), I18n.getString("AbstractStyleNode.Property.1-5")));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_DOUBLE), I18n.getString("AbstractStyleNode.Property.Double")));
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

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public StyledTextProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_IS_STYLED_TEXT, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Is_Styled_Text"),
                  I18n.getString("AbstractStyleNode.Property.StyleTextDetail"));
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

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public BoldProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_BOLD, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Bold"),
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

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public ItalicProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_ITALIC, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Italic"),
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

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public StrikeThroughProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_STRIKE_THROUGH, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Strike_Through"),
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

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public UnderlineProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_UNDERLINE, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Underline"),
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

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public PdfEmbeddedProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_PDF_EMBEDDED, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Pdf_Embedded"),
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

        private final JRBaseStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public FontNameProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_FONT_NAME, String.class,
                  I18n.getString("AbstractStyleNode.Property.Font_name"),
                  I18n.getString("Font_name"));
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

        private final JRBaseStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public FontSizeProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_FONT_SIZE, Integer.class,
                  I18n.getString("AbstractStyleNode.Property.Size"),
                  I18n.getString("AbstractStyleNode.Property.Size"));
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

        private final JRBaseStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public PdfFontNameProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_PDF_FONT_NAME, String.class,
                  I18n.getString("AbstractStyleNode.Property.Pdf_Font_name"),
                  I18n.getString("AbstractStyleNode.Property.Pdf_Font_name"));
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

        private final JRBaseStyle style;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public PdfEncodingProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_PDF_ENCODING, String.class,
                  I18n.getString("AbstractStyleNode.Property.Pdf_Encoding"),
                  I18n.getString("AbstractStyleNode.Property.Pdf_Encoding"));
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
     *  Class to manage the JRDesignStyle.PROPERTY_ITALIC property
     */
    public static final class BlankWhenNullProperty extends PropertySupport.ReadWrite {

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public BlankWhenNullProperty(JRBaseStyle style)
        {
            super(JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, Boolean.class,
                  I18n.getString("AbstractStyleNode.Property.Blank_When_Null"),
                  I18n.getString("AbstractStyleNode.Property.Blankdetail"));
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
    
    
    /**
     *  Class to manage the JRDesignStyle.PROPERTY_ITALIC property
     */
    public static final class PaddingAndBordersProperty extends JRLineBoxProperty {

        private final JRBaseStyle style;

        @SuppressWarnings("unchecked")
        public PaddingAndBordersProperty(JRBaseStyle style)
        {
            super(style);
            this.style = style;
        }

//        @Override
//        public boolean isDefaultValue() {
//            return style.isOwnBlankWhenNull() == null;
//        }
//
//        @Override
//        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
//            setValue(null);
//        }
//
//        @Override
//        public boolean supportsDefaultValue() {
//            return true;
//        }
    }


}
