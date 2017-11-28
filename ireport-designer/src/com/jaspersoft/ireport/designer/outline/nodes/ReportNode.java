/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.properties.LanguageProperty;
import com.jaspersoft.ireport.designer.sheet.properties.WhenNoDataTypeProperty;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.actions.AddDatasetAction;
import com.jaspersoft.ireport.designer.menu.EditQueryAction;
import com.jaspersoft.ireport.designer.sheet.JRPropertiesMapProperty;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.sheet.editors.ComboBoxPropertyEditor;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.designer.wizards.ReportGroupWizardAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.Action;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.ErrorManager;
import org.openide.cookies.SaveCookie;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.actions.NodeAction;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class ReportNode extends IRAbstractNode implements PropertyChangeListener, ExpressionHolder {

    JasperDesign jd = null;
    
    public static final Action testPropertiesAction = new NodeAction() {

        protected void performAction(Node[] activatedNodes) {
                JasperDesign jd = activatedNodes[0].getLookup().lookup(JasperDesign.class);
                jd.setName("test name");
                jd.setPageWidth(700);
                jd.setPageHeight(400);
                jd.setOrientation( JasperDesign.ORIENTATION_LANDSCAPE);
                jd.setTopMargin(10);
                jd.setBottomMargin(20);
                jd.setLeftMargin(35);
                jd.setRightMargin(5);
        }

        protected boolean enable(Node[] activatedNodes) {
            return (activatedNodes.length > 0);
        }

        public String getName() {
            return "Test document properties changes";
        }

        public HelpCtx getHelpCtx() {
            return null;
        }
    };
    
    public ReportNode(JasperDesign jd, Lookup doLkp)
    {
        super (new ReportChildren(jd,doLkp), new ProxyLookup(Lookups.singleton(jd), doLkp) );
        this.jd = jd;
        jd.getEventSupport().addPropertyChangeListener(this);
        for (int i=0; i<this.jd.getGroupsList().size(); ++i)
        {
            JRDesignGroup grp = (JRDesignGroup)this.jd.getGroupsList().get(i);
            grp.getEventSupport().addPropertyChangeListener(this);
        }
        setIconBaseWithExtension("com/jaspersoft/ireport/designer/resources/report-16.png");
    }

    public JasperDesign getJasperDesign() {
        return jd;
    }

    
    @Override
    public String getDisplayName() {
        //return "Report " + jd.getName();
        return "" + jd.getName();
    }
    

    @Override
    protected Sheet createSheet() {
        //System.out.println("Requested createSheet!!!");
        //.out.flush();
        
        Sheet sheet = super.createSheet();
        Sheet.Set reportPropertiesSet = Sheet.createPropertiesSet();
        reportPropertiesSet.setName("REPORT_PROPERTIES");
        reportPropertiesSet.setDisplayName("Report properties");
        reportPropertiesSet.put(new NameProperty( jd ));
        sheet.put(reportPropertiesSet);
        
        Sheet.Set pageSizeSet = Sheet.createPropertiesSet();
        pageSizeSet.setName("PAGE_SIZE");
        pageSizeSet.setDisplayName("Page size");
        pageSizeSet.put(new PageWidthProperty( jd ));
        pageSizeSet.put(new PageHeightProperty( jd ));
        pageSizeSet.put(new OrientationProperty( jd ));
        sheet.put(pageSizeSet);
        
        Sheet.Set marginsSet = Sheet.createPropertiesSet();
        marginsSet.setName("PAGE_MARGINS");
        marginsSet.setDisplayName("Margins");
        marginsSet.put(new LeftMarginProperty( jd ));
        marginsSet.put(new RightMarginProperty( jd ));
        marginsSet.put(new TopMarginProperty( jd ));
        marginsSet.put(new BottomMarginProperty( jd ));
        sheet.put(marginsSet);
        
        Sheet.Set columnsSet = Sheet.createPropertiesSet();
        columnsSet.setName("PAGE_COLUMNS");
        columnsSet.setDisplayName("Columns");
        columnsSet.put(new ColumnCountProperty( jd ));
        columnsSet.put(new ColumnWidthProperty( jd ));
        columnsSet.put(new ColumnSpacingProperty( jd ));
        sheet.put(columnsSet);
        
        Sheet.Set moreSet = Sheet.createPropertiesSet();
        moreSet.setName("PAGE_MORE");
        moreSet.setDisplayName("More...");
        DatasetNode.fillDatasetPropertySet(moreSet, jd.getMainDesignDataset(), jd );
        moreSet.put(new JRPropertiesMapProperty( jd ));
        moreSet.put(new TitleNewPageProperty( jd ));
        moreSet.put(new SummaryNewPageProperty( jd ));
        moreSet.put(new FloatColumnFooterProperty( jd ));
        moreSet.put(new IgnorePaginationProperty( jd ));
        moreSet.put(new PrintOrderProperty( jd ));
        moreSet.put(new WhenNoDataTypeProperty( jd ));
        moreSet.put(new LanguageProperty( jd ));
        moreSet.put(new FormatFactoryClassProperty( jd ));
        sheet.put(moreSet);
        
        return sheet;
    }
    
    @Override
    public Action[] getActions(boolean context) {
        
        Action[] actions = super.getActions(context);
        java.util.ArrayList<Action> myactions = new java.util.ArrayList<Action>();
        for (int i=0; i<actions.length; ++i)
        {
            myactions.add(actions[i]);
        }
        myactions.add(null);
        
        myactions.add(SystemAction.get(EditQueryAction.class));
        myactions.add(null);
        myactions.add(SystemAction.get(ReportGroupWizardAction.class));
        myactions.add(SystemAction.get(AddDatasetAction.class));
        //testPropertiesAction);
        
        return myactions.toArray(new Action[myactions.size()]);
    }
    
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_PAGE_WIDTH property
     */
    private static final class NameProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public NameProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_NAME,String.class, "Report name", "The name of this report", true, true);
                this.jasperDesign = jd;
                this.setValue("oneline", Boolean.TRUE);
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getName();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof String)
                {
                    String oldValue = jasperDesign.getName();
                    String newValue = (String)val;
                    jasperDesign.setName(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "Name", 
                                String.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_PAGE_WIDTH property
     */
    private static final class PageWidthProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public PageWidthProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_PAGE_WIDTH,Integer.class, "Page width", "This is the page width", true, true);
                this.jasperDesign = jd;
            }
            
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getPageWidth();
            }

            // TODO: check page width consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getPageWidth();
                    Integer newValue = (Integer)val;
                    jasperDesign.setPageWidth(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "PageWidth", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_PAGE_HEIGHT property
     */
    private static final class PageHeightProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public PageHeightProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_PAGE_HEIGHT,Integer.class, "Page height", "This is the page height", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getPageHeight();
            }

            // TODO: check page height consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getPageHeight();
                    Integer newValue = (Integer)val;
                    jasperDesign.setPageHeight(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "PageHeight", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_LEFT_MARGIN property
     */
    private static final class LeftMarginProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public LeftMarginProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_LEFT_MARGIN,Integer.class, "Left margin", "This is the left margin", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getLeftMargin();
            }

            // TODO: check page width with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getLeftMargin();
                    Integer newValue = (Integer)val;
                    jasperDesign.setLeftMargin(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "LeftMargin", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
     /**
     *  Class to manage the JasperDesign.PROPERTY_RIGHT_MARGIN property
     */
    private static final class RightMarginProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public RightMarginProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_RIGHT_MARGIN,Integer.class, "Right margin", "This is the right margin", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getRightMargin();
            }

            // TODO: check page width with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getRightMargin();
                    Integer newValue = (Integer)val;
                    jasperDesign.setRightMargin(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "RightMargin", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_TOP_MARGIN property
     */
    private static final class TopMarginProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public TopMarginProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_TOP_MARGIN,Integer.class, "Top margin", "This is the top margin", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getTopMargin();
            }

            // TODO: check page height with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getTopMargin();
                    Integer newValue = (Integer)val;
                    jasperDesign.setTopMargin(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "TopMargin", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
            
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_BOTTOM_MARGIN property
     */
    private static final class BottomMarginProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public BottomMarginProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_BOTTOM_MARGIN,Integer.class, "Bottom margin", "This is the bottom margin", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getBottomMargin();
            }

            // TODO: check page height with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getBottomMargin();
                    Integer newValue = (Integer)val;
                    jasperDesign.setBottomMargin(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "BottomMargin", 
                                Integer.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_BOTTOM_MARGIN property
     */
    private static final class ColumnCountProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public ColumnCountProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_COLUMN_COUNT,Integer.class, "Columns", "Number of columns", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getColumnCount();
            }

            // TODO: check page height with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getColumnCount();
                    Integer newValue = (Integer)val;
                    
                    if (newValue <= 0)
                    {
                        IllegalArgumentException iae = annotateException("You need at least one column!");
                        throw iae; 
                    }
                    
                    jasperDesign.setColumnCount(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "ColumnCount", 
                                Integer.TYPE,
                                oldValue,newValue);
                    
                    // Recalculate the column width...
                    int total = jasperDesign.getPageWidth();
                    total -= jasperDesign.getLeftMargin();
                    total -= jasperDesign.getRightMargin();
                    if (jasperDesign.getColumnCount() > 1)
                    {
                        total -= jasperDesign.getColumnSpacing()*jasperDesign.getColumnCount()-1;
                    }
                    
                    total /= jasperDesign.getColumnCount();
                    
                    ObjectPropertyUndoableEdit urob2 =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "ColumnWidth", 
                                Integer.TYPE,
                                jasperDesign.getColumnWidth(),total);
                    
                    jasperDesign.setColumnWidth(total);
                    urob.concatenate(urob2);
                    
                    if (jasperDesign.getColumnCount() == 1 &&
                        jasperDesign.getColumnSpacing() != 0)
                    {
                        ObjectPropertyUndoableEdit urob3 =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "ColumnSpacing", 
                                Integer.TYPE,
                                jasperDesign.getColumnSpacing(),0);
                    
                        jasperDesign.setColumnSpacing(0);
                        urob.concatenate(urob3);
                    }
                    
                    
                    
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
    }
    
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_BOTTOM_MARGIN property
     */
    private static final class ColumnWidthProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public ColumnWidthProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_COLUMN_WIDTH,Integer.class, "Column Width", "Width of each column", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getColumnWidth();
            }

            // TODO: check page height with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getColumnWidth();
                    Integer newValue = (Integer)val;
                    

                    int available = jasperDesign.getPageWidth();
                    available -= jasperDesign.getLeftMargin();
                    available -= jasperDesign.getRightMargin();
                    available /= jasperDesign.getColumnCount();
                    //available -= jasperDesign.getColumnSpacing()*jasperDesign.getColumnCount()-1;
                    
                    if (newValue > available)
                    {
                        IllegalArgumentException iae = annotateException("You don't have enought space (max column width: " + available + " pixels)");
                        throw iae; 
                    }
                    
                    jasperDesign.setColumnWidth(newValue);
                    
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "ColumnWidth", 
                                Integer.TYPE,
                                oldValue,newValue);
                    
                    if (jasperDesign.getColumnCount() > 1)
                    {
                        available = jasperDesign.getPageWidth();
                        available -= jasperDesign.getLeftMargin();
                        available -= jasperDesign.getRightMargin();
                        available -= jasperDesign.getColumnCount()*newValue;
                        available /= jasperDesign.getColumnCount()-1;
                        // Recalculate the column spacing...
                        ObjectPropertyUndoableEdit urob2 =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "ColumnWidth", 
                                Integer.TYPE,
                                jasperDesign.getColumnSpacing(),available);
                        
                        jasperDesign.setColumnSpacing(available);
                        urob.concatenate(urob2);
                    }
                    
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
    }
    
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_BOTTOM_MARGIN property
     */
    private static final class ColumnSpacingProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public ColumnSpacingProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_COLUMN_SPACING,Integer.class, "Column Spacing", "The space between two columns", true, true);
                this.jasperDesign = jd;
            }

            @Override
            public boolean canWrite() {
                return jasperDesign.getColumnCount() > 1;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.getColumnSpacing();
            }

            // TODO: check page height with this margin consistency
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Integer)
                {
                    Integer oldValue = jasperDesign.getColumnSpacing();
                    Integer newValue = (Integer)val;
                    

                    int available = jasperDesign.getPageWidth();
                    available -= jasperDesign.getLeftMargin();
                    available -= jasperDesign.getRightMargin();
                    //available -= jasperDesign.getColumnCount()*jasperDesign.getColumnWidth();
                    available /= jasperDesign.getColumnCount()-1;
                    
                    if (newValue > available)
                    {
                        IllegalArgumentException iae = annotateException("You don't have enought space (max available column space: " + available + " pixels)");
                        throw iae; 
                    }
                    
                    jasperDesign.setColumnSpacing(newValue);
                    
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "ColumnSpacing", 
                                Integer.TYPE,
                                oldValue,newValue);
                    
                    // Adjust column width...
                    available = jasperDesign.getPageWidth();
                    available -= jasperDesign.getLeftMargin();
                    available -= jasperDesign.getRightMargin();
                    available -= (jasperDesign.getColumnCount()-1) * newValue;
                    available /= jasperDesign.getColumnCount();

                    // Recalculate the column spacing...
                    ObjectPropertyUndoableEdit urob2 =
                        new ObjectPropertyUndoableEdit(
                            jasperDesign,
                            "ColumnWidth", 
                            Integer.TYPE,
                            jasperDesign.getColumnWidth(),available);

                    jasperDesign.setColumnWidth(available);
                    urob.concatenate(urob2);

                    
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
    }
    
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_ORIENTATION property
     */
    private static final class OrientationProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public OrientationProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_ORIENTATION,Byte.class, "Orientation", "This is the page orientation", true, true);
                this.jasperDesign = jd;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JasperDesign.ORIENTATION_PORTRAIT), "Portrait"));
                    l.add(new Tag(new Byte(JasperDesign.ORIENTATION_LANDSCAPE), "Landscape"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(jasperDesign.getOrientation());
            }

            // TODO: what to do with page width/height ?
            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Byte)
                {
                    Byte oldValue = jasperDesign.getOrientation();
                    Byte newValue = (Byte)val;
                    jasperDesign.setOrientation(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "Orientation", 
                                Byte.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_TITLE_NEW_PAGE property
     */
    private static final class TitleNewPageProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public TitleNewPageProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_TITLE_NEW_PAGE,Boolean.class, "Title on a new page", "Print the title band on a separate page", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.isTitleNewPage();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Boolean)
                {
                    Boolean oldValue = jasperDesign.isTitleNewPage();
                    Boolean newValue = (Boolean)val;
                    jasperDesign.setTitleNewPage(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "TitleNewPage", 
                                Boolean.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_SUMMARY_NEW_PAGE property
     */
    private static final class SummaryNewPageProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public SummaryNewPageProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_SUMMARY_NEW_PAGE,Boolean.class, "Summary on a new page", "Print the summary band on a separate page", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.isSummaryNewPage();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Boolean)
                {
                    Boolean oldValue = jasperDesign.isSummaryNewPage();
                    Boolean newValue = (Boolean)val;
                    jasperDesign.setSummaryNewPage(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "SummaryNewPage", 
                                Boolean.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_FLOAT_COLUMN_FOOTER property
     */
    private static final class FloatColumnFooterProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public FloatColumnFooterProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_FLOAT_COLUMN_FOOTER,Boolean.class, "Float column footer", "Let the column footer to float", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.isFloatColumnFooter();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Boolean)
                {
                    Boolean oldValue = jasperDesign.isFloatColumnFooter();
                    Boolean newValue = (Boolean)val;
                    jasperDesign.setFloatColumnFooter(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "FloatColumnFooter", 
                                Boolean.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_IGNORE_PAGINATION property
     */
    private static final class IgnorePaginationProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public IgnorePaginationProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_IGNORE_PAGINATION,Boolean.class, "Ignore pagination", "Print the whole document as a single page", true, true);
                this.jasperDesign = jd;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return jasperDesign.isIgnorePagination();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Boolean)
                {
                    Boolean oldValue = jasperDesign.isIgnorePagination();
                    Boolean newValue = (Boolean)val;
                    jasperDesign.setIgnorePagination(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "IgnorePagination", 
                                Boolean.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_ORIENTATION property
     */
    private static final class PrintOrderProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
            private ComboBoxPropertyEditor editor;
            
            @SuppressWarnings("unchecked")
            public PrintOrderProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_PRINT_ORDER,Byte.class, "Print order", "The way to print the records", true, true);
                this.jasperDesign = jd;
                setValue("suppressCustomEditor", Boolean.TRUE);
            }

            @Override
            @SuppressWarnings("unchecked")
            public PropertyEditor getPropertyEditor() {

                if (editor == null)
                {
                    java.util.ArrayList l = new java.util.ArrayList();
                    l.add(new Tag(new Byte(JasperDesign.PRINT_ORDER_VERTICAL), "Vertical"));
                    l.add(new Tag(new Byte(JasperDesign.PRINT_ORDER_HORIZONTAL), "Horizontal"));
                    editor = new ComboBoxPropertyEditor(false, l);
                }
                return editor;
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return new Byte(jasperDesign.getPrintOrder());
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof Byte)
                {
                    Byte oldValue = jasperDesign.getPrintOrder();
                    Byte newValue = (Byte)val;
                    jasperDesign.setPrintOrder(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "PrintOrder", 
                                Byte.TYPE,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
                }
            }
    }
    
    /**
     *  Class to manage the JasperDesign.PROPERTY_PAGE_WIDTH property
     */
    private static final class FormatFactoryClassProperty extends PropertySupport
    {
            private final JasperDesign jasperDesign;
        
            @SuppressWarnings("unchecked")
            public FormatFactoryClassProperty(JasperDesign jd)
            {
                super(JasperDesign.PROPERTY_FORMAT_FACTORY_CLASS,String.class, "Format Factory Class", "Name of an optional class to format numbers, dates, etc...", true, true);
                this.jasperDesign = jd;
                this.setValue("oneline", Boolean.TRUE);
            }
            
            public Object getValue() throws IllegalAccessException, InvocationTargetException {
                return (jasperDesign.getFormatFactoryClass() == null) ? "" : jasperDesign.getFormatFactoryClass();
            }

            public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (val instanceof String)
                {
                    String oldValue = jasperDesign.getFormatFactoryClass();
                    String newValue = (val == null || ((String)val).trim().length() == 0) ? null : ((String)val).trim();
                    jasperDesign.setFormatFactoryClass(newValue);
                
                    ObjectPropertyUndoableEdit urob =
                            new ObjectPropertyUndoableEdit(
                                jasperDesign,
                                "FormatFactoryClass", 
                                String.class,
                                oldValue,newValue);
                    // Find the undoRedo manager...
                    IReportManager.getInstance().addUndoableEdit(urob);
            
                }
            }
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName() == null) return;
        
        if (evt.getPropertyName().equals(JasperDesign.PROPERTY_BACKGROUND) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_TITLE) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_PAGE_HEADER) ||    
            evt.getPropertyName().equals(JasperDesign.PROPERTY_COLUMN_HEADER) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_DETAIL) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_COLUMN_FOOTER) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_PAGE_FOOTER) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_LAST_PAGE_FOOTER) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_SUMMARY) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_NO_DATA) ||
            evt.getPropertyName().equals(JRDesignDataset.PROPERTY_GROUPS) ||
            evt.getPropertyName().equals(JRDesignGroup.PROPERTY_GROUP_HEADER) ||
            evt.getPropertyName().equals(JRDesignGroup.PROPERTY_GROUP_FOOTER) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_DATASETS))
        {
            ((ReportChildren)getChildren()).updateChildren();
        }
        
        if (evt.getPropertyName().equals( JasperDesign.PROPERTY_NAME))
        {
            this.fireDisplayNameChange(null, jd.getName());
        }
        
        if (evt.getPropertyName().equals( JasperDesign.PROPERTY_COLUMN_COUNT) ||
            evt.getPropertyName().equals( JasperDesign.PROPERTY_COLUMN_SPACING) ||
            evt.getPropertyName().equals( JasperDesign.PROPERTY_COLUMN_WIDTH))
        {
            this.firePropertyChange(JasperDesign.PROPERTY_COLUMN_COUNT, null, jd.getColumnCount() );
            this.firePropertyChange(JasperDesign.PROPERTY_COLUMN_SPACING, null, jd.getColumnSpacing() );
            this.firePropertyChange(JasperDesign.PROPERTY_COLUMN_WIDTH, null, jd.getColumnWidth() );
            
            
        }
        else if (ModelUtils.containsProperty(  this.getPropertySets(), evt.getPropertyName()))
        {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
        }
        
        if (evt.getPropertyName().equals(JRDesignDataset.PROPERTY_GROUPS))
        {
            // refresh group listening...
            for (int i=0; i<this.jd.getGroupsList().size(); ++i)
            {
                JRDesignGroup grp = (JRDesignGroup)this.jd.getGroupsList().get(i);
                grp.getEventSupport().removePropertyChangeListener(this);
                grp.getEventSupport().addPropertyChangeListener(this);
            }
        }
        
        /*
        else if (evt.getPropertyName().equals( JasperDesign.PROPERTY_PAGE_HEIGHT) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_PAGE_WIDTH) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_ORIENTATION) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_RIGHT_MARGIN) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_LEFT_MARGIN) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_TOP_MARGIN) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_BOTTOM_MARGIN) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_TITLE_NEW_PAGE) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_SUMMARY_NEW_PAGE) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_FLOAT_COLUMN_FOOTER) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_PRINT_ORDER) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_IGNORE_PAGINATION) ||
                 evt.getPropertyName().equals(  "WhenNoDataType") ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_FORMAT_FACTORY_CLASS) ||
                 evt.getPropertyName().equals( JasperDesign.PROPERTY_LANGUAGE) ||
                 DatasetNode.acceptProperty(evt))
        {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
        }
        */
    }

    
    @Override
    public <T extends Node.Cookie> T getCookie(Class<T> type) {

        Object o = getLookup().lookup(type);
        if (o == null && SaveCookie.class.isAssignableFrom(type))
        {
            o = IReportManager.getInstance().getActiveVisualView().getEditorSupport().getDataObject().getLookup().lookup(SaveCookie.class);
        }
        
        if (o == null && Node.Cookie.class.isAssignableFrom(type)) // try to look in the super cookie...
        {
           o = super.getCookie(type); 
        }
        
        return o instanceof Node.Cookie ? (T)o : null;
    }

    public boolean hasExpression(JRDesignExpression ex) {
        if (jd.getFilterExpression() == ex) return true;
        return false;
    }

    public ExpressionContext getExpressionContext(JRDesignExpression ex) {
        return new ExpressionContext( jd.getMainDesignDataset() );
    }
    
}
