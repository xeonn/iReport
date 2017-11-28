/*
 * ElementPropertiesFactory.java
 * 
 * Created on 31-ott-2007, 23.03.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes.properties;

import com.jaspersoft.ireport.designer.IRFont;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.ExpressionProperty;
import com.jaspersoft.ireport.designer.sheet.FieldPatternProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.WeakListeners;

/**
 *
 * @author gtoffoli
 */
public class TextElementPropertiesFactory {

    /**
     * Get the common properties...
     */
    public static Sheet.Set getTextPropertySet(JRDesignTextElement element, JasperDesign jd)
    {
        
        JRDesignDataset dataset = ModelUtils.getElementDataset(element, jd);
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("TEXT_ELEMENT_PROPERTIES");
        propertySet.setDisplayName("Text properties");
        
        propertySet.put(new FontNameProperty( element ));
        propertySet.put(new FontSizeProperty( element ));
        
        propertySet.put(new BoldProperty( element ));
        propertySet.put(new ItalicProperty( element ));
        propertySet.put(new UnderlineProperty( element ));
        propertySet.put(new StrikeThroughProperty( element ));
        
        propertySet.put(new PdfFontNameProperty( element ));
        propertySet.put(new PdfEmbeddedProperty( element ));
        propertySet.put(new PdfEncodingProperty( element ));
        
        propertySet.put(new HorizontalAlignmentProperty( element ));
        propertySet.put(new VerticalAlignmentProperty( element ));
        propertySet.put(new RotationProperty( element ));
        propertySet.put(new LineSpacingProperty( element ));
        propertySet.put(new StyledTextProperty( element ));
        
        propertySet.put(new ReportFontProperty( element, jd ));
        
        
        //propertySet.put(new LeftProperty( element ));
        return propertySet;
    }
    
    /**
     * Get the static text properties...
     */
    public static Sheet.Set getStaticTextPropertySet(JRDesignStaticText element, JasperDesign jd)
    {
        
        //JRDesignDataset dataset = ModelUtils.getElementDataset(element, jd);
        
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("STATIC_TEXT_ELEMENT_PROPERTIES");
        propertySet.setDisplayName("Static text properties");
        
        propertySet.put(new TextProperty( element ));
        return propertySet;
    }
    
    /**
     * Get the static text properties...
     */
    public static Sheet.Set getTextFieldPropertySet(JRDesignTextField element, JasperDesign jd)
    {
        
        JRDesignDataset dataset = ModelUtils.getElementDataset(element, jd);
        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("TEXTFIELD_ELEMENT_PROPERTIES");
        propertySet.setDisplayName("Text field properties");
        propertySet.put(new TextFieldExpressionProperty(element, dataset));
        propertySet.put(new TextFieldExpressionClassNameProperty(element));
        propertySet.put(new BlankWhenNullProperty(element));
        propertySet.put(new PatternProperty(element));
        propertySet.put(new StretchWithOverflowProperty( element ));
        propertySet.put(new EvaluationTimeProperty(element, dataset));
        propertySet.put(new EvaluationGroupProperty(element, dataset));
        
        //propertySet.put(new LeftProperty( element ));
        return propertySet;
    }
    
    /**
     * Convenient way to get all the properties of an element.
     * Properties positions could be reordered to have a better order.
     */
    public static List<Sheet.Set> getPropertySets(JRDesignElement element, JasperDesign jd)
    {
        List<Sheet.Set> sets = new ArrayList<Sheet.Set>();
        
        if (element instanceof  JRDesignStaticText)
        {
            sets.add( getStaticTextPropertySet((JRDesignStaticText)element, jd ));
        }
        else if (element instanceof  JRDesignTextField)
        {
            sets.add( getTextFieldPropertySet((JRDesignTextField)element, jd ));
        }
        
        if (element instanceof  JRDesignTextElement)
        {
            sets.add( getTextPropertySet((JRDesignTextElement)element, jd) );
        }
        
        return sets;
    }
    
    
    /**
     *  Class to manage the JRDesignParameter.PROPERTY_NAME property
     */
    public static final class TextProperty extends PropertySupport.ReadWrite {

        final JRDesignStaticText element;

        @SuppressWarnings("unchecked")
        public TextProperty(JRDesignStaticText element)
        {
            super(JRDesignElement.PROPERTY_KEY, String.class,
                  "Text",
                  "The text to show in this label.");
            this.element = element;
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return (element.getText()  != null) ? element.getText() : ""; 
        }
        
        @SuppressWarnings("unchecked")
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            String oldValue = element.getText();
            
            String newValue = val+"";
            if (val == null)
            {
                newValue = "";
            }
            
            element.setText(newValue);
            
            ObjectPropertyUndoableEdit opue = new ObjectPropertyUndoableEdit(
                    element, "Text", String.class, oldValue, newValue);

            IReportManager.getInstance().addUndoableEdit(opue);

        }
    }
    
    /**
     *  Class to manage the JRDesignElement.PROPERTY_POSITION_TYPE property
     */
    private static final class HorizontalAlignmentProperty extends PropertySupport
    {
            private final JRDesignTextElement element;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public HorizontalAlignmentProperty(JRDesignTextElement element)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT,Byte.class, "Horizontal Alignment", "How to align the text.", true, true);
                this.element = element;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JRDesignTextElement.HORIZONTAL_ALIGN_LEFT), "Left"));
                    l.add(new Tag(new Byte(JRDesignTextElement.HORIZONTAL_ALIGN_CENTER), "Center"));
                    l.add(new Tag(new Byte(JRDesignTextElement.HORIZONTAL_ALIGN_RIGHT), "Right"));
                    l.add(new Tag(new Byte(JRDesignTextElement.HORIZONTAL_ALIGN_JUSTIFIED), "Justified"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(element.getHorizontalAlignment());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }

            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = element.getOwnHorizontalAlignment();
                    Byte newValue = (Byte)val;
                    element.setHorizontalAlignment(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                element,
                                "HorizontalAlignment", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
            
            @Override
            public boolean isDefaultValue() {
                return element.getOwnHorizontalAlignment() == null;
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
    private static final class VerticalAlignmentProperty extends PropertySupport
    {
            private final JRDesignTextElement element;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public VerticalAlignmentProperty(JRDesignTextElement element)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT,Byte.class, "Vertical Alignment", "How to align the text.", true, true);
                this.element = element;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JRDesignTextElement.VERTICAL_ALIGN_TOP), "Top"));
                    l.add(new Tag(new Byte(JRDesignTextElement.VERTICAL_ALIGN_MIDDLE), "Middle"));
                    l.add(new Tag(new Byte(JRDesignTextElement.VERTICAL_ALIGN_BOTTOM), "Bottom"));
                    l.add(new Tag(new Byte(JRDesignTextElement.VERTICAL_ALIGN_JUSTIFIED), "Justified"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(element.getVerticalAlignment());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }

            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = element.getOwnVerticalAlignment();
                    Byte newValue = (Byte)val;
                    element.setVerticalAlignment(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                element,
                                "VerticalAlignment", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
            
            @Override
            public boolean isDefaultValue() {
                return element.getOwnVerticalAlignment() == null;
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
            private final JRDesignTextElement element;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public RotationProperty(JRDesignTextElement element)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_ROTATION,Byte.class, "Rotation", "How to rotate the text.", true, true);
                this.element = element;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_NONE), "None"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_LEFT), "Left"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_RIGHT), "Right"));
                    l.add(new Tag(new Byte(JRDesignTextElement.ROTATION_UPSIDE_DOWN), "Upside Down"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(element.getRotation());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }

            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = element.getOwnRotation();
                    Byte newValue = (Byte)val;
                    element.setRotation(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                element,
                                "Rotation", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
                
                
            }

            @Override
            public boolean isDefaultValue() {
                return element.getOwnRotation() == null;
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
            private final JRDesignTextElement element;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public LineSpacingProperty(JRDesignTextElement element)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super(JRBaseStyle.PROPERTY_LINE_SPACING,Byte.class, "Line Spacing", "The space to put between lines of text.", true, true);
                this.element = element;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_SINGLE), "Single"));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_1_1_2), "1.5"));
                    l.add(new Tag(new Byte(JRDesignTextElement.LINE_SPACING_DOUBLE), "Double"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(element.getLineSpacing());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                setPropertyValue(val);
            }

            private void setPropertyValue(Object val)
            {
                if (val == null || val instanceof Byte)
                {
                    Byte oldValue = element.getOwnLineSpacing();
                    Byte newValue = (Byte)val;
                    element.setLineSpacing(newValue);

                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                element,
                                "LineSpacing", 
                                Byte.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }

            @Override
            public boolean isDefaultValue() {
                return element.getOwnLineSpacing() == null;
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

        private final JRDesignTextElement element;

        @SuppressWarnings("unchecked")
        public StyledTextProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_IS_STYLED_TEXT, Boolean.class,
                  "Is Styled Text",
                  "Set if the value should be parsed as styled text.");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isStyledText();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnStyledText();
                Boolean newValue =   (Boolean)val;
                element.setStyledText(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "StyledText", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnStyledText() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_BOLD property
     */
    public static final class BoldProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;

        @SuppressWarnings("unchecked")
        public BoldProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_BOLD, Boolean.class,
                  "Bold",
                  "");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isBold();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnBold();
                Boolean newValue =   (Boolean)val;
                element.setBold(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Bold", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnBold() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_ITALIC property
     */
    public static final class ItalicProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;

        @SuppressWarnings("unchecked")
        public ItalicProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_ITALIC, Boolean.class,
                  "Italic",
                  "");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isItalic();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnItalic();
                Boolean newValue =   (Boolean)val;
                element.setItalic(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Italic", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnItalic() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_STRIKE_THROUGH property
     */
    public static final class StrikeThroughProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;

        @SuppressWarnings("unchecked")
        public StrikeThroughProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_STRIKE_THROUGH, Boolean.class,
                  "Strike Through",
                  "");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isStrikeThrough();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnStrikeThrough();
                Boolean newValue =   (Boolean)val;
                element.setStrikeThrough(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "StrikeThrough", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnStrikeThrough() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_UNDERLINE property
     */
    public static final class UnderlineProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;

        @SuppressWarnings("unchecked")
        public UnderlineProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_UNDERLINE, Boolean.class,
                  "Underline",
                  "");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isUnderline();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnUnderline();
                Boolean newValue =   (Boolean)val;
                element.setUnderline(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Underline", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnUnderline() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_PDF_EMBEDDED property
     */
    public static final class PdfEmbeddedProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;

        @SuppressWarnings("unchecked")
        public PdfEmbeddedProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_PDF_EMBEDDED, Boolean.class,
                  "Pdf Embedded",
                  "");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isPdfEmbedded();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnPdfEmbedded();
                Boolean newValue =   (Boolean)val;
                element.setPdfEmbedded(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "PdfEmbedded", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnPdfEmbedded() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_VALUE_CLASS_NAME property
     */
    private static final class FontNameProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public FontNameProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_FONT_NAME, String.class,
                  "Font name",
                  "Font name");
            this.element = element;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getFontName();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof String)
            {
                String oldValue = element.getOwnFontName();
                String newValue =   (String)val;
                element.setFontName(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "FontName", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOwnFontName() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_FONT_SIZE property
     */
    private static final class FontSizeProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public FontSizeProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_FONT_SIZE, Integer.class,
                  "Size",
                  "Size");
            this.element = element;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getFontSize();
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
                Integer oldValue = element.getOwnFontSize();
                Integer newValue =   (Integer)val;
                element.setFontSize(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "FontSize", 
                            Integer.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOwnFontSize() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_FONT_SIZE property
     */
    private static final class ReportFontProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;
        private final JasperDesign jd;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public ReportFontProperty(JRDesignTextElement element, JasperDesign jd)
        {
            super(JRBaseFont.PROPERTY_REPORT_FONT, JRReportFont.class,
                  "Report font",
                  "Report font");
            this.element = element;
            this.jd = jd;
        
            setValue("canEditAsText",false);
            setValue("oneline",true);
            setValue("suppressCustomEditor",true);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getReportFont() == null ? "" : element.getReportFont();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof JRReportFont)
            {
                JRReportFont oldValue = element.getReportFont();
                JRReportFont newValue =   (JRReportFont)val;
                element.setReportFont(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "ReportFont", 
                            JRReportFont.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getReportFont() == null;
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
                @SuppressWarnings("deprecation")
                List fonts = jd.getFontsList();
                for (int i=0; i<fonts.size(); ++i)
                {
                    JRReportFont font = (JRReportFont)fonts.get(i);
                    classes.add(new Tag(font, font.getName() ));
                }
                    
                editor = new ComboBoxPropertyEditor(false, classes);
            }
            return editor;
        }

        @Override
        public String getHtmlDisplayName() {
            return "<s>" + super.getDisplayName() + "</s>";
        }
    }
    
    
    /**
     *  Class to manage the JRDesignTextElement.PROPERTY_PDF_FONT_NAME property
     */
    private static final class PdfFontNameProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public PdfFontNameProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_PDF_FONT_NAME, String.class,
                  "Pdf Font name",
                  "Pdf Font name");
            this.element = element;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",true);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getPdfFontName();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof String)
            {
                String oldValue = element.getOwnPdfFontName();
                String newValue =   (String)val;
                
                element.setPdfFontName(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "PdfFontName", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOwnPdfFontName() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_PDF_ENCODING property
     */
    private static final class PdfEncodingProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextElement element;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public PdfEncodingProperty(JRDesignTextElement element)
        {
            super(JRBaseStyle.PROPERTY_PDF_ENCODING, String.class,
                  "Pdf Encoding",
                  "Pdf Encoding");
            this.element = element;
        
            setValue("canEditAsText",true);
            setValue("oneline",true);
            setValue("suppressCustomEditor",false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getPdfEncoding();
        }

        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof String)
            {
                if ((val+"").trim().length() == 0) val = null;
                
                String oldValue = element.getOwnPdfEncoding();
                String newValue = (String)val;
                
                element.setPdfEncoding(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "PdfEncoding", 
                            String.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);
            }
        }

        @Override
        public boolean isDefaultValue() {
            return element.getOwnPdfEncoding() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_ITALIC property
     */
    public static final class StretchWithOverflowProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextField element;

        @SuppressWarnings("unchecked")
        public StretchWithOverflowProperty(JRDesignTextField element)
        {
            super(JRBaseTextField.PROPERTY_STRETCH_WITH_OVERFLOW, Boolean.class,
                  "Stretch With Overflow",
                  "Stretch the field vertically it the text does not fit in the element.");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isStretchWithOverflow();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val instanceof Boolean)
            {
                boolean oldValue = element.isStretchWithOverflow();
                boolean newValue = (Boolean)val;
                element.setStretchWithOverflow(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "StretchWithOverflow", 
                            Boolean.TYPE,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isStretchWithOverflow() == false;
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(false);
        }

        @Override
        public boolean supportsDefaultValue() {
            return true;
        }
    }
    
    
    
    /**
     *  Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
     */
    private static final class EvaluationTimeProperty extends PropertySupport
    {
            private final JRDesignDataset dataset;
            private final JRDesignTextField element;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public EvaluationTimeProperty(JRDesignTextField element, JRDesignDataset dataset)
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
    
    /**
     *  Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
     */
    private static final class EvaluationGroupProperty extends PropertySupport implements PropertyChangeListener
    {
            private final JRDesignDataset dataset;
            private final JRDesignTextField element;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public EvaluationGroupProperty(JRDesignTextField element, JRDesignDataset dataset)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRDesignTextField.PROPERTY_EVALUATION_GROUP,JRGroup.class, "Evaluation group", "Evaluate the image expression when the specified group changes", true, true);
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
    
    
    
            
            /**
     *  Class to manage the JRDesignImage.PROPERTY_EVALUATION_TIME property
     */
    private static final class PatternProperty extends FieldPatternProperty
    {
            private final JRDesignTextField element;
            
            @SuppressWarnings("unchecked")
            public PatternProperty(JRDesignTextField element)
            {
                // TODO: Replace WhenNoDataType with the right constant
                super( JRBaseStyle.PROPERTY_PATTERN,
                        "Pattern", "Patter to use to format numbers and dates.");
                this.element = element;
            }

            @Override
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return element.getPattern() == null ? "" :  element.getPattern();
            }

            @Override
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val == null || val instanceof String)
                {
                    String oldValue = element.getOwnPattern();
                    String newValue = (String)val;
                    if (newValue != null && newValue.length() == 0) newValue = null;
                    
                    element.setPattern(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                element,
                                "Pattern", 
                                String.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
            
            @Override
            public boolean isDefaultValue() {
                return element.getPattern() == null;
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
     *  Class to manage the JRDesignTextElement.PROPERTY_ITALIC property
     */
    public static final class BlankWhenNullProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextField element;

        @SuppressWarnings("unchecked")
        public BlankWhenNullProperty(JRDesignTextField element)
        {
            super(JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, Boolean.class,
                  "Blank When Null",
                  "Print a blank string instead of null.");
            this.element = element;
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            return element.isBlankWhenNull();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            if (val == null || val instanceof Boolean)
            {
                Boolean oldValue = element.isOwnBlankWhenNull();
                Boolean newValue = (Boolean)val;
                element.setBlankWhenNull(newValue);
                
                ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "BlankWhenNull", 
                            Boolean.class,
                            oldValue,newValue);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            }
        }
        
        @Override
        public boolean isDefaultValue() {
            return element.isOwnBlankWhenNull() == null;
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
     *  Class to manage the JRDesignImage.PROPERTY_EXPRESSION property
     */
    public static final class TextFieldExpressionProperty extends ExpressionProperty {

         private final JRDesignDataset dataset;
         private final JRDesignTextField element;

        public TextFieldExpressionProperty(JRDesignTextField element, JRDesignDataset dataset)
        {
            super(JRDesignTextField.PROPERTY_EXPRESSION,
                  "Expression",
                  "Expression");
            this.element = element;
            this.dataset = dataset;
            this.setValue("expressionContext", new ExpressionContext(dataset));
        }

        @Override
        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            if (element.getExpression() == null) return "";
            return element.getExpression().getText();
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            JRDesignExpression oldExp =  (JRDesignExpression)element.getExpression();
            JRDesignExpression newExp = null;
            //System.out.println("Setting as value: " + val);
            if ( (val == null || val.equals("")) && 
                 (oldExp == null || oldExp.getValueClassName() == null || oldExp.getValueClassName().equals("java.lang.String")) )
            {
                element.setExpression(null);
            }
            else
            {
                String s = (val != null) ? val+"" : "";

                newExp = new JRDesignExpression();
                newExp.setText(s);
                newExp.setValueClassName( oldExp != null ? oldExp.getValueClassName() : null );
                element.setExpression(newExp);
            }
            
            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Expression", 
                            JRExpression.class,
                            oldExp,newExp);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            //System.out.println("Done: " + val);
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
            return element.getExpression() == null || 
                   element.getExpression().getText() == null ||
                   element.getExpression().getText().length() == 0;
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
     *  Class to manage the JRDesignVariable.PROPERTY_VALUE_CLASS_NAME property
     */
    private static final class TextFieldExpressionClassNameProperty extends PropertySupport.ReadWrite {

        private final JRDesignTextField element;
        PropertyEditor editor = null;

        @SuppressWarnings("unchecked")
        public TextFieldExpressionClassNameProperty(JRDesignTextField element)
        {
            super(JRDesignExpression.PROPERTY_VALUE_CLASS_NAME, String.class,
                  "Expression Class",
                  "Expression Class");
            this.element = element;
            
            setValue("canEditAsText", true);
            setValue("oneline", true);
            setValue("suppressCustomEditor", false);
        }

        public Object getValue() throws IllegalAccessException, InvocationTargetException {
            
            if (element.getExpression() == null) return "java.lang.String";
            if (element.getExpression().getValueClassName() == null) return "java.lang.String";
            return element.getExpression().getValueClassName();
        }

        
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

            JRDesignExpression oldExp =  (JRDesignExpression)element.getExpression();
            JRDesignExpression newExp = null;
            //System.out.println("Setting as value: " + val);
            
            String newVal = (val != null) ? val+"" : "";
            newVal = newVal.trim();
            
            if ( newVal.equals("") )
            {
                newVal = null;
            }
            
            newExp = new JRDesignExpression();
            newExp.setText( (oldExp != null) ? oldExp.getText() : null );
            newExp.setValueClassName( newVal );
            element.setExpression(newExp);
            
            ObjectPropertyUndoableEdit urob =
                        new ObjectPropertyUndoableEdit(
                            element,
                            "Expression", 
                            JRExpression.class,
                            oldExp,newExp);
                // Find the undoRedo manager...
                IReportManager.getInstance().addUndoableEdit(urob);

            //System.out.println("Done: " + val);
        }

        @Override
        public boolean isDefaultValue() {
            return element.getExpression() == null ||
                   element.getExpression().getValueClassName() == null ||
                   element.getExpression().getValueClassName().equals("java.lang.String");
        }

        @Override
        public void restoreDefaultValue() throws IllegalAccessException, InvocationTargetException {
            setValue(null);
            editor.setValue("java.lang.String");
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
                classes.add(new Tag("java.lang.String"));
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
                
                editor = new ComboBoxPropertyEditor(false, classes);
            }
            return editor;
        }
    }
    
}
