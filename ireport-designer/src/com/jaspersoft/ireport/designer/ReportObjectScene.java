/*
 * ReportObjectScene.java
 * 
 * Created on Aug 28, 2007, 12:50:55 AM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 */

package com.jaspersoft.ireport.designer;

/**
 *
 * @author gtoffoli
 */
import com.jaspersoft.ireport.designer.actions.BandMoveAction;
import com.jaspersoft.ireport.designer.actions.BandSelectionAction;
import com.jaspersoft.ireport.designer.actions.KeyboardElementMoveAction;
import com.jaspersoft.ireport.designer.actions.ReportAlignWithMoveStrategyProvider;
import com.jaspersoft.ireport.designer.actions.ReportAlignWithResizeStrategyProvider;
import com.jaspersoft.ireport.designer.actions.ReportAlignWithWidgetCollector;
import com.jaspersoft.ireport.designer.actions.TranslucentRectangularSelectDecorator;
import com.jaspersoft.ireport.designer.ruler.GuideLine;
import com.jaspersoft.ireport.designer.widgets.BandSeparatorWidget;
import com.jaspersoft.ireport.designer.widgets.BandWidget;
import com.jaspersoft.ireport.designer.widgets.GuideLineWidget;
import com.jaspersoft.ireport.designer.widgets.JRDesignChartWidget;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.jaspersoft.ireport.designer.widgets.JRDesignImageWidget;
import com.jaspersoft.ireport.designer.widgets.PageWidget;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.UIManager;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.convert.ReportConverter;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.draw.DrawVisitor;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class ReportObjectScene extends AbstractReportObjectScene implements PropertyChangeListener {

    private List<JRDesignElement> selectedElements = new ArrayList<JRDesignElement>();
    
    private ReportAlignWithWidgetCollector reportAlignWithWidgetCollector = null;
    private ReportAlignWithMoveStrategyProvider reportAlignWithMoveStrategyProvider = null;
    private ReportAlignWithResizeStrategyProvider reportAlignWithResizeStrategyProvider = null;
    private KeyboardElementMoveAction keyboardElementMoveAction = null;
    private BandSelectionAction bandSelectionAction = null;
    
    
    
    @Override
    public void setSnapToGrid(boolean snapToGrid) {
        if (this.snapToGrid != snapToGrid)
        {
            this.snapToGrid = snapToGrid;
            this.reportAlignWithMoveStrategyProvider.setSnapToGrid(snapToGrid);
            this.reportAlignWithResizeStrategyProvider.setSnapToGrid(snapToGrid);
        }
    }

    @Override
    public void setGridVisible(boolean gridVisible) {
        if (this.gridVisible != gridVisible)
        {
            this.gridVisible = gridVisible;
            pageLayer.revalidate(true);
            validate();
        }
    }
    
    public ReportAlignWithWidgetCollector getReportAlignWithWidgetCollector() {
        return reportAlignWithWidgetCollector;
    }

    public void setReportAlignWithWidgetCollector(ReportAlignWithWidgetCollector reportAlignWithWidgetCollector) {
        this.reportAlignWithWidgetCollector = reportAlignWithWidgetCollector;
    }
    
    private JasperDesign jasperDesign = null;
    private DrawVisitor drawVisitor = null;

    public DrawVisitor getDrawVisitor() {
        return drawVisitor;
    }

    public void setDrawVisitor(DrawVisitor drawVisitor) {
        this.drawVisitor = drawVisitor;
    }
    

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void setJasperDesign(JasperDesign jasperDesign) {

            this.jasperDesign = jasperDesign;
            if (jasperDesign == null) {
                
                this.drawVisitor = null;
                this.rebuildDocument();
                return;
            }

            
            jasperDesign.getEventSupport().addPropertyChangeListener(this);
            
            // Adding listeners for groups...
            for (int i=0; i<this.jasperDesign.getGroupsList().size(); ++i)
            {
                JRDesignGroup grp = (JRDesignGroup)this.jasperDesign.getGroupsList().get(i);
                grp.getEventSupport().addPropertyChangeListener(this);
            }
            
            this.drawVisitor = 
                    new DrawVisitor(new ReportConverter(jasperDesign, true, false), null);
            ThreadUtils.invokeInAWTThread(new Runnable() {

                public void run() {
                    rebuildDocument();
                }
            });
        
        
    }
    
    public void addBandSeparatorWidget(JRBand b, int yLocation)
    {
        if (b == null) return;
        BandSeparatorWidget bbw = new BandSeparatorWidget(this, b);
        bbw.getActions().addAction( new BandMoveAction(true, InputEvent.SHIFT_DOWN_MASK) );
        bbw.getActions().addAction( new BandMoveAction() );
        bandSeparatorsLayer.addChild(bbw);
        
        bandLayer.addChild(new BandWidget(this, b));
    }
    
    public void addElementWidget(JRDesignElement de)
    {
        if (de == null) return;
        JRDesignElementWidget widget = null;
        
        if (de instanceof JRDesignImage)
        {
            widget = new JRDesignImageWidget(this, (JRDesignImage)de);
        }
        else if (de instanceof JRDesignChart)
        {
            widget = new JRDesignChartWidget(this, (JRDesignChart)de);
        }
        else
        {
            widget = new JRDesignElementWidget(this, de);
        }
        //widget.getSelectionWidget().setLayout(new ResizeHandleLayout());
        //widget.getSelectionWidget().getActions().addAction(createSelectAction());
        
        widget.getActions().addAction (createSelectAction());
        
        widget.getSelectionWidget().getActions().addAction( keyboardElementMoveAction );
        
        widget.getSelectionWidget().getActions().addAction(createObjectHoverAction());
        
        
        widget.getSelectionWidget().getActions().addAction( ActionFactory.createResizeAction(
                        reportAlignWithResizeStrategyProvider, 
                        reportAlignWithResizeStrategyProvider) );
        
        widget.getSelectionWidget().getActions().addAction( ActionFactory.createMoveAction(
                        reportAlignWithMoveStrategyProvider, 
                        reportAlignWithMoveStrategyProvider) );
        
        widget.getActions().addAction( ActionFactory.createResizeAction(
                        reportAlignWithResizeStrategyProvider, 
                        reportAlignWithResizeStrategyProvider) );
        
        widget.getActions().addAction( ActionFactory.createMoveAction(
                        reportAlignWithMoveStrategyProvider, 
                        reportAlignWithMoveStrategyProvider) );
        
        widget.getActions().addAction(inplaceEditorAction);
        widget.getSelectionWidget().getActions().addAction(inplaceEditorAction);
        
        widget.getActions ().addAction(elementPopupMenuAction);
        widget.getSelectionWidget().getActions().addAction(elementPopupMenuAction);
        
        elementsLayer.addChild(widget);
        selectionLayer.addChild(widget.getSelectionWidget());
        
        addObject(de, widget);
    }
    
    public void rebuildDocument()
    {
        
        boolean oldUpdateingStatus = isUpdatingView();
        try {
            setUpdatingView(true);

            pageLayer.removeChildren();
            elementsLayer.removeChildren();
            bandLayer.removeChildren();
            bandSeparatorsLayer.removeChildren();
            selectionLayer.removeChildren();
            backgroundLayer.removeChildren();
            interractionLayer.removeChildren();

            // Remove all the objects...
            while (getObjects().size() > 0)
            {

                removeObject(getObjects().iterator().next());
            }

            if (jasperDesign == null) return;

            PageWidget pageWidget = new PageWidget(this);


            pageLayer.addChild(pageWidget);

            refreshBands();
            
        } finally {
        
            setUpdatingView(oldUpdateingStatus);
        }
    }
    
    
    public void  refreshDocument()
    {
        if (pageLayer.getChildren().size() != 0)
        {
            PageWidget pageWidget = (PageWidget)pageLayer.getChildren().get(0);
            pageWidget.updateBounds();
            pageWidget.repaint();
            refreshBands();
        }
    }
    /**
     * Refresh the bands, adding the missing elements if required.
     * Elements no longer referenced in the model are not removed by this method.
     * 
     */
    public void refreshBands()
    {
        boolean oldUpdateingStatus = isUpdatingView();
        try {
            setUpdatingView(true);
            
            int yLocation = getJasperDesign().getTopMargin();

            // Remove all the band separators, and remove the listening too...
            List<Widget> bWidgets = bandSeparatorsLayer.getChildren();
            for (Widget w : bWidgets)
            {
                ((JRDesignBand)((BandSeparatorWidget)w).getBand()).getEventSupport().removePropertyChangeListener((BandSeparatorWidget)w);
            }

            // Remove all the band BandWidget, and remove the listening too...
            bWidgets = bandLayer.getChildren();
            for (Widget w : bWidgets)
            {
                ((JRDesignBand)((BandWidget)w).getBand()).getEventSupport().removePropertyChangeListener((BandWidget)w);
            }

            bandSeparatorsLayer.removeChildren();
            bandLayer.removeChildren();

            // Reset selection (just in case...)
            if (getSelectedObjects().size() > 0)
            {
                setSelectedObjects( Collections.EMPTY_SET );
            }

            List<JRBand> bands = ModelUtils.getBands(getJasperDesign());

            for (JRBand b : bands)
            {
                yLocation += b.getHeight();
                addBandSeparatorWidget(b, yLocation);

                // Add the elements inside this band...
                //JRElement[] elements = b.getElements();
                addElements( b.getChildren());
            }

            // Remove the elements belonging to bands that are no longer
            // in the model...
            List<Widget> widgets = elementsLayer.getChildren();
            List<Widget> toBeRemoved = new ArrayList<Widget>();
            List<Widget> toBeRemovedSelection = new ArrayList<Widget>();
            List<Widget> toBeRemovedObject = new ArrayList<Widget>();
            for (Widget widget : widgets)
            {
                if (widget instanceof JRDesignElementWidget)
                {
                    JRDesignElementWidget dew = (JRDesignElementWidget)widget;
                    JRElementGroup grp = ModelUtils.getTopElementGroup(dew.getElement());
                    if (!bands.contains(grp))
                    {
                        // This band (top group) is not longer in the mode...
                        this.removeObject(dew.getElement());
                        toBeRemoved.add(widget);
                        toBeRemovedSelection.add(dew.getSelectionWidget());
                    }
                }
            }
            if (toBeRemoved.size() > 0)
            {
                elementsLayer.removeChildren(toBeRemoved);
                selectionLayer.removeChildren(toBeRemovedSelection);

            }
            validate();
        
        } finally {
            setUpdatingView(oldUpdateingStatus);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void addElements(List children)
    {
        for (int i=0; i<children.size(); ++i)
            {
                Object obj = children.get(i);
                if (obj instanceof JRDesignElementGroup)
                {
                    if (!elementGroupListeners.containsKey(obj))
                    {
                        GroupChangeListener gcl = new GroupChangeListener((JRDesignElementGroup)obj);
                        ((JRDesignElementGroup)obj).getEventSupport().addPropertyChangeListener(gcl);
                        elementGroupListeners.put(obj,gcl);
                    }
                    
                    addElements( ((JRDesignElementGroup)obj).getChildren() );
                }
            
                if (obj instanceof JRDesignElement)
                {
                    JRDesignElement de = (JRDesignElement)obj;
                    JRDesignElementWidget w = (JRDesignElementWidget)findElementWidget(de);
                    if (w != null)
                    {
                        w.updateBounds();
                    }
                    else
                    {
                        addElementWidget(de);
                    }

                    if (de instanceof JRDesignFrame)
                    {
                        addElements( ((JRDesignFrame)de).getChildren() );
                    }
                }
            }
    }
    
    /**
     * This method replace the findWidget method. It does not any assumption
     * on the content of the objects and their relations in the object scene.
     * 
     * @param de
     * @return
     */
    public JRDesignElementWidget findElementWidget(JRDesignElement de)
    {
        List<Widget> widgets = elementsLayer.getChildren();
        
        for (Widget w : widgets)
        {
            if (w instanceof JRDesignElementWidget)
            {
                JRDesignElementWidget dew = (JRDesignElementWidget)w;
                if (dew.getElement() == de) return dew;
            }
        }
        return null;
    }
    
    /**
     * This methods update the order and all the elements of a band, removing
     * elements no longer in the band...
     * The selection is preserved.
     */
    @SuppressWarnings("unchecked")
    public void refreshElementGroup(JRElementGroup group)
    {
        boolean oldUpdateingStatus = isUpdatingView();
        try {
            setUpdatingView(true);
            
            HashSet selectedObjects = new HashSet();
            selectedObjects.addAll(  getSelectedObjects() );
            if (selectedObjects.size() == 0) selectedObjects = null;

            List<Widget> children = getElementsLayer().getChildren();

            // 1. remove all the widget referencing this band
            List<JRDesignElementWidget> toRemove = new java.util.ArrayList<JRDesignElementWidget>();
            for (Widget w : children)
            {
                if (w instanceof JRDesignElementWidget)
                {
                    JRDesignElementWidget dw = (JRDesignElementWidget)w;

                    // Please note that the element can belong to a sub group ...
                    if (ModelUtils.isOrphan(dw.getElement()) ) 
                    {
                        // Fix Giulio (it does not make sense to remove all the group elements
                        // all the time. Add elements will take care to correctly discover
                        // if an element is already present in the band and will update it...
                        //ModelUtils.isElementChildOf(dw.getElement(),group) )
                    
                        toRemove.add(dw);
                        JRDesignElement element = dw.getElement();
                        // check if the element is still valid...
                        if (selectedObjects != null && selectedObjects.contains(element))
                        {
                            // Check if the element is still there...
                            boolean found = false;
                            JRElement[] ele = group.getElements();
                            for (int i=0; i<ele.length; ++i)
                            {
                                if (ele[i] == element)
                                {
                                    found = true;
                                    break;
                                }
                            }
                            // The element is no longer in the model, remove it.
                            if (!found)
                            {
                                selectedObjects.remove(element);
                            }
                        }
                    }
                }
            }

            for (JRDesignElementWidget dw : toRemove)
            {
                dw.getSelectionWidget().removeFromParent();
                dw.removeFromParent();
                if (getObjects().contains(dw.getElement()))
                {
                    removeObject(dw.getElement());
                }
            }

            // 2. add the new children...
            //JRElement[] elements = group.getElements();
            addElements( group.getChildren() );
            
            // Remove the widgets..

            // Update all the report elements children...
            if (selectedObjects != null)
                setSelectedObjects( selectedObjects);
            validate();
        
        
        } finally {
            setUpdatingView(oldUpdateingStatus);
        }

    }
    
    
    /**
     *  Layer used for the background (in the future this layer can be used to place
     *  watermarks to help the user to design the report.
     */
    LayerWidget backgroundLayer = null;

    public LayerWidget getBackgroundLayer() {
        return backgroundLayer;
    }

    public LayerWidget getPageLayer() {
        return pageLayer;
    }

    
    public LayerWidget getInterractionLayer() {
        return interractionLayer;
    }
    
    /**
     * This layer is used for the main page widget and the bands.
     */
    LayerWidget pageLayer = null;
    
    /**
     * This layer is used for interactions
     */
    LayerWidget interractionLayer  = null;

    /**
     * This layer is used for bandBorders widgets
     */
    LayerWidget bandSeparatorsLayer  = null;
    
    /**
     * This layer is used for band widgets (tooltips)
     */
    LayerWidget bandLayer  = null;
    
    public LayerWidget getBandSeparatorsLayer() {
        return bandSeparatorsLayer;
    }

    public void setBandSeparatorsLayer(LayerWidget bandSeparatorsLayer) {
        this.bandSeparatorsLayer = bandSeparatorsLayer;
    }
    
    public LayerWidget getBandLayer() {
        return bandLayer;
    }

    public void setBandLayer(LayerWidget bandLayer) {
        this.bandLayer = bandLayer;
    }
    
    public ReportObjectScene() {
        super();
        initScene();
    }
    
    private void initScene()
    {
        // I like to see a gray background. The default background for a JComponent is fine.
        this.setBackground(   UIManager.getColor("Panel.background")  );
        setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);
        
        backgroundLayer = new LayerWidget(this);
        addChild(backgroundLayer);
        
        pageLayer = new LayerWidget(this);
        addChild(pageLayer);
        
        elementsLayer = new LayerWidget(this);
        addChild(elementsLayer);
        
        bandLayer = new LayerWidget(this);
        addChild(bandLayer);
        
        bandSeparatorsLayer = new LayerWidget(this);
        addChild(bandSeparatorsLayer);
        
        selectionLayer  = new LayerWidget(this);
        addChild(selectionLayer );
        
        guideLinesLayer  = new LayerWidget(this);
        addChild(guideLinesLayer );
        
        interractionLayer  = new LayerWidget(this);
        addChild(interractionLayer );
        
        reportAlignWithWidgetCollector = new ReportAlignWithWidgetCollector(this);
        reportAlignWithMoveStrategyProvider = new ReportAlignWithMoveStrategyProvider(reportAlignWithWidgetCollector, interractionLayer, ALIGN_WITH_MOVE_DECORATOR_DEFAULT, false);
        reportAlignWithResizeStrategyProvider  = new ReportAlignWithResizeStrategyProvider(reportAlignWithWidgetCollector, interractionLayer, ALIGN_WITH_MOVE_DECORATOR_DEFAULT, false);
                
        bandSelectionAction = new BandSelectionAction();
        
        getActions().addAction(reportPopupMenuAction);
        getActions().addAction(bandSelectionAction);
        
        getActions().addAction(ActionFactory.createRectangularSelectAction(
                new TranslucentRectangularSelectDecorator(this), interractionLayer,
                ActionFactory.createObjectSceneRectangularSelectProvider(this)));
        
        
        
        getActions().addAction (ActionFactory.createMouseCenteredZoomAction (1.1));
        getActions().addAction (ActionFactory.createPanAction ());
        
        
        keyboardElementMoveAction = new KeyboardElementMoveAction();
        getActions().addAction( keyboardElementMoveAction );
        
        this.setMaximumBounds(new Rectangle(-10,-10,Integer.MAX_VALUE, Integer.MAX_VALUE) );
    }
    
    
    public void propertyChange(PropertyChangeEvent evt) {
        
        //System.out.println("Model changed: " + evt.getPropertyName() + " " + evt.getSource());
            
        Runnable r = null;
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
            evt.getPropertyName().equals(JasperDesign.PROPERTY_PAGE_WIDTH) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_PAGE_HEIGHT) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_TOP_MARGIN) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_BOTTOM_MARGIN) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_LEFT_MARGIN) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_RIGHT_MARGIN) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_COLUMN_COUNT) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_COLUMN_SPACING) ||
            evt.getPropertyName().equals(JasperDesign.PROPERTY_COLUMN_WIDTH) ||
            evt.getPropertyName().equals(JRDesignGroup.PROPERTY_GROUP_HEADER) ||
            evt.getPropertyName().equals(JRDesignGroup.PROPERTY_GROUP_FOOTER) ||
            evt.getPropertyName().equals(JRDesignDataset.PROPERTY_GROUPS))
        {
             r = new Runnable(){  
                 public void run()  {
                    refreshDocument();
                }};
        }
        
        
        if (r != null)
        {
           ThreadUtils.invokeInAWTThread(r);
        }
        
        // Update group listeners...
        if (evt.getPropertyName().equals(JRDesignDataset.PROPERTY_GROUPS))
        {
            // refresh group listening...
            for (int i=0; i<this.jasperDesign.getGroupsList().size(); ++i)
            {
                JRDesignGroup grp = (JRDesignGroup)this.jasperDesign.getGroupsList().get(i);
                grp.getEventSupport().removePropertyChangeListener(this);
                grp.getEventSupport().addPropertyChangeListener(this);
            }
        }
    }
    
    

    
    
}

