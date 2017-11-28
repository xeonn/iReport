/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline.nodes;

import com.jaspersoft.ireport.designer.ElementDecorator;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.outline.nodes.properties.ElementPropertiesFactory;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.actions.OpenSubreportAction;
import com.jaspersoft.ireport.designer.actions.PaddingAndBordersAction;
import com.jaspersoft.ireport.designer.charts.ChartDataAction;
import com.jaspersoft.ireport.designer.charts.multiaxis.AddAxisChartAction;
import com.jaspersoft.ireport.designer.menu.HyperlinkAction;
import com.jaspersoft.ireport.designer.undo.DeleteElementUndoableEdit;
import com.jaspersoft.ireport.designer.utils.WeakPreferenceChangeListener;
import java.awt.datatransfer.Transferable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.Action;
import net.sf.jasperreports.charts.design.JRDesignDataRange;
import net.sf.jasperreports.charts.design.JRDesignMeterPlot;
import net.sf.jasperreports.charts.design.JRDesignThermometerPlot;
import net.sf.jasperreports.charts.design.JRDesignValueDisplay;
import net.sf.jasperreports.engine.JRBoxContainer;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBasePen;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.MoveDownAction;
import org.openide.actions.MoveUpAction;
import org.openide.actions.PasteAction;
import org.openide.actions.ReorderAction;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.NodeTransfer;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author gtoffoli
 */
public class ElementNode extends IRIndexedNode implements PropertyChangeListener, ExpressionHolder, PreferenceChangeListener {

    JasperDesign jd = null;
    JRDesignElement element = null;
    private ElementNameVisitor elemenNameVisitor = null;

    public JRDesignElement getElement() {
        return element;
    }
        
    public JasperDesign getJasperDesign() {
        return jd;
    }

    public void setElement(JRDesignElement element) {
        this.element = element;
    }

    public void preferenceChange(PreferenceChangeEvent evt) {
          fireDisplayNameChange(null, getDisplayName());
    }

    public ElementNode(JasperDesign jd, JRDesignElement element, Children children, Index index, Lookup doLkp)
    {
        super (children, index, new ProxyLookup( doLkp, Lookups.fixed(jd,element)));
        elemenNameVisitor = new ElementNameVisitor(jd);
        this.jd = jd;
        this.element = element;
        
        element.getEventSupport().addPropertyChangeListener(this);

        IReportManager.getInstance().getPreferences().addPreferenceChangeListener(new WeakPreferenceChangeListener(this,IReportManager.getInstance().getPreferences()));
        
        if (element instanceof JRDesignGraphicElement)
        {
            JRDesignGraphicElement gele = (JRDesignGraphicElement)element;
            ((JRBasePen)gele.getLinePen()).getEventSupport().addPropertyChangeListener(this);
        }
        
        if (element instanceof JRBoxContainer)
        {
            JRBoxContainer boxcontainer = (JRBoxContainer)element;
            JRBaseLineBox baseBox = (JRBaseLineBox)boxcontainer.getLineBox();
            baseBox.getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getTopPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getBottomPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getLeftPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getRightPen()).getEventSupport().addPropertyChangeListener(this);
        }
        
    }
    
    public ElementNode(JasperDesign jd, JRDesignElement element, Lookup doLkp)
    {
        this(jd,element, Children.LEAF, null,doLkp);
    }
    
    public ElementNode(JasperDesign jd, JRDesignElement element, ElementContainerChildren children, Lookup doLkp)
    {
        this(jd,element, children, children.getIndex(),doLkp);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        
        // adding common properties...
        List<Sheet.Set> propertiesSets = ElementPropertiesFactory.getPropertySets(element, jd);
                
        for (int i=0; i<propertiesSets.size(); ++i)
        {
                sheet.put( propertiesSets.get(i));
        }
        
        return sheet;
    }
    
//    @Override
//    public void setDisplayName(String name) {
//        super.setDisplayName(name 
//            + ((element.getKey() != null &&  element.getKey().length() > 0) ? " (" +  element.getKey() + ")" : ""));
//    }
    
    public String getDisplayName()
    {
        if (elemenNameVisitor != null && getElement() != null)
        {
            return elemenNameVisitor.getName(getElement());
        }
        else
        {
            return super.getDisplayName();
        }
    }
    
    /**
     * Elements can be cut, so canCut returns always true.
     */
    @Override
    public boolean canCut() {
        return true;
    }
    
    /**
     *  This is used internally to understand if the element can accept other elements...
     */
    public boolean canPaste() {
        return false;
    }
    
    /**
     * Elements can not be renamed, so canRename returns always false.
     */
    @Override
    public boolean canRename() {
        return false;
    }
    
    /**
     * Elements can not be destroied , so canDestroy returns always true.
     */
    @Override
    public boolean canDestroy() {
        return true;
    }
    
    /**
     * When this node is destroied, we have to remove this element from his parent container.
     */
    @Override
    public void destroy() throws IOException {
       
       Object container = element.getElementGroup();
       int index = 0;
       
       if (container instanceof JRDesignElementGroup)
       {
           index = ((JRDesignElementGroup)container).getChildren().indexOf(element);
           ((JRDesignElementGroup)container).removeElement(element);
       
       }
       if (container instanceof JRDesignFrame)
       {
           index = ((JRDesignFrame)container).getChildren().indexOf(element);
           ((JRDesignFrame)container).removeElement(element);
       }
       DeleteElementUndoableEdit edit = new DeleteElementUndoableEdit(element,container,index);
       IReportManager.getInstance().addUndoableEdit(edit, true);
       // TODO: add Unduable edit here
       super.destroy();
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
    public Transferable drag() throws IOException {
        return clipboardCut();
    }
    
    @Override
    public Action[] getActions(boolean popup) {
        java.util.List<Action> list = new ArrayList<Action>();

        if (getElement() instanceof JRDesignSubreport)
        {

            list.add( SystemAction.get( OpenSubreportAction.class ) );
            list.add( null );
        }


        if (getElement() instanceof JRDesignChart &&
            ((JRDesignChart)getElement()).getChartType() != JRDesignChart.CHART_TYPE_MULTI_AXIS)
        {
            list.add( ChartDataAction.getInstance() );
            list.add( null );
        }

        if (getElement() instanceof JRDesignChart &&
            ((JRDesignChart)getElement()).getChartType() == JRDesignChart.CHART_TYPE_MULTI_AXIS)
        {
            list.add( AddAxisChartAction.getInstance() );
            list.add( null );
        }
        
        if (getElement() instanceof JRBoxContainer)
        {
            
            list.add( SystemAction.get( PaddingAndBordersAction.class ));
            
        }
        
        if (getElement() instanceof JRHyperlink)
        {
            list.add( HyperlinkAction.getInstance() );
            list.add( null );
        }
        
        list.add( SystemAction.get( CopyAction.class ) );
        list.add( SystemAction.get( CutAction.class ) );
        if (canPaste()) list.add( SystemAction.get( PasteAction.class ) );
        list.add( SystemAction.get( ReorderAction.class ) );
        list.add( SystemAction.get(MoveUpAction.class) );
        list.add( SystemAction.get(MoveDownAction.class) );
        list.add( null  );
        
        
        
        list.add( SystemAction.get( DeleteAction.class ) );

        // Looks for decorators supporting this element type...
        List<ElementDecorator> decorators = IReportManager.getElementDecorators( getElement() );

        for (ElementDecorator decorator : decorators)
        {
            list.addAll(Arrays.asList(decorator.getActions(this)));
        }
        //Pdf508TagDecorator pdf508TagDecorator = new Pdf508TagDecorator();
        

        return list.toArray(new Action[list.size()]);
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        
        com.jaspersoft.ireport.designer.IReportManager.getInstance().notifyReportChange();
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignElement.PROPERTY_KEY) ||
            evt.getPropertyName().equals(JRDesignImage.PROPERTY_EXPRESSION) ||
            evt.getPropertyName().equals(JRDesignTextField.PROPERTY_EXPRESSION))
        {
            fireNameChange(null, getName());
        }
        
        if (evt.getPropertyName().equals(JRDesignElement.PROPERTY_PARENT_STYLE))
        {
            this.firePropertyChange(JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE, evt.getOldValue(), evt.getNewValue() );
        }
        if (evt.getPropertyName().equals(JRDesignImage.PROPERTY_EXPRESSION))
        {
            this.firePropertyChange(JRDesignExpression.PROPERTY_VALUE_CLASS_NAME, evt.getOldValue(), evt.getNewValue() );
        }
//FIXME
//        if (evt.getPropertyName().equals(JRBaseStyle.PROPERTY_BORDER))
//        {
//                this.firePropertyChange(JRBaseStyle.PROPERTY_TOP_BORDER, evt.getOldValue(), evt.getNewValue() );
//                this.firePropertyChange(JRBaseStyle.PROPERTY_LEFT_BORDER, evt.getOldValue(), evt.getNewValue() );
//                this.firePropertyChange(JRBaseStyle.PROPERTY_RIGHT_BORDER, evt.getOldValue(), evt.getNewValue() );
//        }
//        
//        if (evt.getPropertyName().equals(JRBaseStyle.PROPERTY_BORDER_COLOR))
//        {
//                this.firePropertyChange(JRBaseStyle.PROPERTY_TOP_BORDER_COLOR, evt.getOldValue(), evt.getNewValue() );
//                this.firePropertyChange(JRBaseStyle.PROPERTY_LEFT_BORDER_COLOR, evt.getOldValue(), evt.getNewValue() );
//                this.firePropertyChange(JRBaseStyle.PROPERTY_RIGHT_BORDER_COLOR, evt.getOldValue(), evt.getNewValue() );
//        }
//        
//        if (evt.getPropertyName().equals(JRBaseStyle.PROPERTY_PADDING))
//        {
//                this.firePropertyChange(JRBaseStyle.PROPERTY_TOP_PADDING, evt.getOldValue(), evt.getNewValue() );
//                this.firePropertyChange(JRBaseStyle.PROPERTY_LEFT_PADDING, evt.getOldValue(), evt.getNewValue() );
//                this.firePropertyChange(JRBaseStyle.PROPERTY_RIGHT_PADDING, evt.getOldValue(), evt.getNewValue() );
//        }
        
        if (evt.getPropertyName().equals(JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION))
        {
                this.firePropertyChange(JRDesignSubreport.PROPERTY_DATASOURCE_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange("PROPERTY_CONNECTION_TYPE", evt.getOldValue(), evt.getNewValue() );
        }
        if (evt.getPropertyName().equals(JRDesignSubreport.PROPERTY_DATASOURCE_EXPRESSION))
        {
                this.firePropertyChange(JRDesignSubreport.PROPERTY_CONNECTION_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange("PROPERTY_CONNECTION_TYPE", evt.getOldValue(), evt.getNewValue() );
        }
        
        if (evt.getPropertyName().equals(JRDesignMeterPlot.PROPERTY_DATA_RANGE) ||
            evt.getPropertyName().equals(JRDesignThermometerPlot.PROPERTY_DATA_RANGE))
        {
                this.firePropertyChange( JRDesignDataRange.PROPERTY_HIGH_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange( JRDesignDataRange.PROPERTY_LOW_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
        }
        else if (evt.getPropertyName().equals(JRDesignThermometerPlot.PROPERTY_LOW_RANGE))
        {
                this.firePropertyChange( "LOW_RANGE_" + JRDesignDataRange.PROPERTY_HIGH_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange( "LOW_RANGE_" + JRDesignDataRange.PROPERTY_LOW_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
        }
        else if (evt.getPropertyName().equals(JRDesignThermometerPlot.PROPERTY_MEDIUM_RANGE))
        {
                this.firePropertyChange( "MEDIUM_RANGE_" + JRDesignDataRange.PROPERTY_HIGH_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange( "MEDIUM_RANGE_" + JRDesignDataRange.PROPERTY_LOW_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
        }
        else if (evt.getPropertyName().equals(JRDesignThermometerPlot.PROPERTY_HIGH_RANGE))
        {
                this.firePropertyChange( "HIGH_RANGE_" + JRDesignDataRange.PROPERTY_HIGH_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange( "HIGH_RANGE_" + JRDesignDataRange.PROPERTY_LOW_EXPRESSION, evt.getOldValue(), evt.getNewValue() );
        }
        else if (evt.getPropertyName().equals(JRDesignMeterPlot.PROPERTY_VALUE_DISPLAY) ||
                 evt.getPropertyName().equals(JRDesignThermometerPlot.PROPERTY_VALUE_DISPLAY))
        {
                this.firePropertyChange( JRDesignValueDisplay.PROPERTY_FONT, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange( JRDesignValueDisplay.PROPERTY_MASK, evt.getOldValue(), evt.getNewValue() );
                this.firePropertyChange( JRDesignValueDisplay.PROPERTY_COLOR, evt.getOldValue(), evt.getNewValue() );
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
        
        if (ModelUtils.containsProperty(  this.getPropertySets(), evt.getPropertyName()))
        {
            this.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue() );
        }
    }

    public boolean hasExpression(JRDesignExpression ex) {
        
        HasExpressionVisitor hasExpressionVisitor = new HasExpressionVisitor(jd,ex);
        return hasExpressionVisitor.hasExpression(element);
    }

    public ExpressionContext getExpressionContext(JRDesignExpression ex) {
        
        
        // The chart is a bit hard to handle. There are some expressions that
        // always refer to the main dataset (in general all the one used in
        // the dataset run, others depending by the dataset set in the dataset run.
        // So if the dataset run uses a particular dataset, the expression context
        // should use it.
        if (element instanceof JRDesignChart)
        {
            JRDesignChart chart = (JRDesignChart)element;
            if (chart.getDataset().getDatasetRun() != null &&
                chart.getDataset().getDatasetRun().getDatasetName() != null)
            {
                // The dataset run uses a different dataset.
                // so let's check if the expression is one of the dataset run or if it
                // belongs to the chart.
                if (chart.getDataset().getDatasetRun().getConnectionExpression() != ex &&
                     chart.getDataset().getDatasetRun().getDataSourceExpression() != ex &&
                     chart.getDataset().getDatasetRun().getParametersMapExpression() != ex)
                {
                    boolean found = false;
                    JRDatasetParameter[] params = chart.getDataset().getDatasetRun().getParameters();
                    for (int i=0; i<params.length; ++i)
                    {
                        if (params[i].getExpression() == ex)
                        {
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found)
                    {
                        return new ExpressionContext( (JRDesignDataset)jd.getDatasetMap().get(chart.getDataset().getDatasetRun().getDatasetName()));
                    }
                }
            }
        }
        
        // For crosstabs the discussion is similar...
        
        // Usually this is the element expression context...
        return new ExpressionContext(ModelUtils.getElementDataset(element, jd));
        
        
    }
    
}
