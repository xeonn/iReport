/*
 * ReportAlignWithMoveStrategyProvider.java
 * 
 * Created on Aug 30, 2007, 3:24:02 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.undo.AggregatedUndoableEdit;
import com.jaspersoft.ireport.designer.undo.BandChangeUndoableEdit;
import com.jaspersoft.ireport.designer.undo.ObjectPropertyUndoableEdit;
import com.jaspersoft.ireport.designer.undo.UndoMoveChildrenUndoableEdit;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.jaspersoft.ireport.designer.widgets.SelectionWidget;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.AlignWithMoveDecorator;
import org.netbeans.api.visual.action.AlignWithWidgetCollector;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.MoveStrategy;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class ReportAlignWithMoveStrategyProvider extends AlignWithSupport implements MoveStrategy, MoveProvider {

    private boolean outerBounds;
    private boolean moveEnabled = false;
    private boolean snapToGrid = false;

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public boolean isOuterBounds() {
        return outerBounds;
    }

    public void setOuterBounds(boolean outerBounds) {
        this.outerBounds = outerBounds;
    }

    public boolean isSnapToGrid() {
        return snapToGrid;
    }

    public void setSnapToGrid(boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }
    private int gridSize = 13;
    
    public ReportAlignWithMoveStrategyProvider (AlignWithWidgetCollector collector, LayerWidget interractionLayer, AlignWithMoveDecorator decorator, boolean outerBounds) {
        super (collector, interractionLayer, decorator);
        this.outerBounds = outerBounds;
    }

    public Point locationSuggested (Widget widget, Point originalLocation, Point suggestedLocation) {
        
        if (!moveEnabled)
        {
            if (Math.abs(suggestedLocation.x-originalLocation.x) > 5 || 
                Math.abs(suggestedLocation.y-originalLocation.y) > 5)
            {
                moveEnabled = true;
            }
            else
            {
                return originalLocation;
            }
        }

        if (IReportManager.getPreferences().getBoolean("noMagnetic", false)) return suggestedLocation;
        
        Point widgetLocation = widget.getLocation();
        Rectangle widgetBounds = outerBounds ? widget.getBounds () : widget.getClientArea();
        Rectangle bounds = widget.convertLocalToScene (widgetBounds);
        bounds.translate (suggestedLocation.x - widgetLocation.x, suggestedLocation.y - widgetLocation.y);
        Insets insets = widget.getBorder ().getInsets ();
        
        if (!outerBounds) {
            suggestedLocation.x += insets.left;
            suggestedLocation.y += insets.top;
        }
        
        Point point = new Point(suggestedLocation);
        if (isSnapToGrid())
        {
            
            point = snapToGrid(point, getGridSize());
            //System.out.println("Snapping....x:" + widget.getBounds().x + " " +suggestedLocation + " " + widgetLocation + "=> " + point + " " + bounds);
            
            point.x += insets.left;
            point.y += insets.top;
        }
        else
        {
            //System.out.println("Free movements");
            //System.out.flush();
            point = super.locationSuggested(widget, bounds, point, true, true, true, true);
        }
        
        if (! outerBounds) {
            point.x -= insets.left;
            point.y -= insets.top;
        }
        return widget.getParentWidget().convertSceneToLocal (point);
    }

    java.util.List<AggregatedUndoableEdit> undoEdits = null;
    public void movementStarted (Widget widget) {
        moveEnabled = false;

        undoEdits = new java.util.ArrayList<AggregatedUndoableEdit>();
        
        List<Widget> selectedElements = ((AbstractReportObjectScene)widget.getScene()).getSelectionLayer().getChildren();
        
        // for each element, let's save x and y...
        for (Widget w : selectedElements)
        {
           if (w.isVisible())
           {
                JRDesignElementWidget dew = ((SelectionWidget)w).getRealWidget();
                JasperDesign jd = ((AbstractReportObjectScene)dew.getScene()).getJasperDesign();
                undoEdits.add(new ObjectPropertyUndoableEdit(dew.getElement(), "X", Integer.TYPE, new Integer(dew.getElement().getX()), new Integer(dew.getElement().getX())));
                undoEdits.add(new ObjectPropertyUndoableEdit(dew.getElement(), "Y", Integer.TYPE, new Integer(dew.getElement().getY()), new Integer(dew.getElement().getY())));
           }
        }
        show ();
    }

    public void movementFinished (Widget widget) {

        List<Widget> selectedElements = ((AbstractReportObjectScene)widget.getScene()).getSelectionLayer().getChildren();

        // Change the band if the direct parent is a band...
        List<JRDesignElement> elementsToAddToSelection = new ArrayList<JRDesignElement>();

        for (Widget w : selectedElements)
        {
            if (w.isVisible())
            {
                JRDesignElementWidget dew = ((SelectionWidget)w).getRealWidget();
                elementsToAddToSelection.add(dew.getElement());

                if (dew.getElement().getElementGroup() instanceof JRDesignBand)
                {
                    JRDesignBand oldBand = (JRDesignBand)dew.getElement().getElementGroup();
                    JasperDesign jd = ((ReportObjectScene)dew.getScene()).getJasperDesign();
                    Point localLocation = dew.convertModelToLocalLocation(new Point( dew.getElement().getX(), dew.getElement().getY() ));
                    JRDesignBand newBand = ModelUtils.getBandAt(jd, localLocation);
                    if (newBand != null && newBand != oldBand)
                    {
                        int y1 = ModelUtils.getBandLocation(newBand, jd);
                        int y0 = ModelUtils.getBandLocation(oldBand, jd);

                        int deltaBand = y0 - y1;
                        // Update element band...
                        oldBand.getChildren().remove(dew.getElement());
                        //oldBand.removeElement(dew.getElement());

                        // Update the element coordinates...
                        dew.getElement().setElementGroup(newBand);
                        dew.getElement().setY(dew.getElement().getY() + deltaBand);
                        newBand.getChildren().add(dew.getElement());
                        newBand.getEventSupport().firePropertyChange(JRDesignBand.PROPERTY_CHILDREN, null, null);
                        oldBand.getEventSupport().firePropertyChange(JRDesignBand.PROPERTY_CHILDREN, null, null);
                        BandChangeUndoableEdit bcUndo = new BandChangeUndoableEdit(
                            jd,  oldBand, newBand, dew.getElement());
                        undoEdits.add(bcUndo);
                        
                    }
                }

                if (dew.getChildrenElements() != null)
                {
                    undoEdits.add(0,new UndoMoveChildrenUndoableEdit(dew));
                }
            }
        }

        // add the undo operation...



        for (int i=0;i<undoEdits.size(); ++i)
        {
            AggregatedUndoableEdit theEdit = undoEdits.get(i);
            if (theEdit instanceof ObjectPropertyUndoableEdit)
            {
                ObjectPropertyUndoableEdit edit = (ObjectPropertyUndoableEdit)theEdit;
                if (edit.getNewValue() == null &&
                    edit.getOldValue() == null)
                {
                    undoEdits.remove(edit);
                    i--;
                    continue;
                }
                if (edit.getNewValue() != null &&
                    edit.getOldValue() != null &&
                    edit.getNewValue().equals(edit.getOldValue()))
                {
                    undoEdits.remove(edit);
                    i--;
                    continue;
                }
            }
            else if (theEdit instanceof BandChangeUndoableEdit)
            {
                BandChangeUndoableEdit edit = (BandChangeUndoableEdit)theEdit;
                if (edit.getOldBand() == null ||
                    edit.getNewBand() == null ||
                    edit.getNewBand() == edit.getOldBand())
                {
                    undoEdits.remove(edit);
                    i--;
                    continue;
                }
            }
        }
        
        if (undoEdits.size() > 0)
        {
            AggregatedUndoableEdit masterEdit = new AggregatedUndoableEdit("Move");
            
            for (int i=0;i<undoEdits.size(); ++i)
            {
                AggregatedUndoableEdit edit = undoEdits.get(i);
                masterEdit.concatenate(edit);
            }
            if (elementsToAddToSelection.size() == 0)
            {
                IReportManager.getInstance().setSelectedObject(null);
            }
            boolean first = true;
            for (JRDesignElement ele : elementsToAddToSelection)
            {
                if (first)
                {
                    IReportManager.getInstance().setSelectedObject(ele);
                    first = false;
                }
                else
                {
                    IReportManager.getInstance().addSelectedObject(ele);
                }
            }
            IReportManager.getInstance().addUndoableEdit(masterEdit);
        }
        
        
        hide ();
    }

    public Point getOriginalLocation (Widget widget) {
        return ActionFactory.createDefaultMoveProvider ().getOriginalLocation (widget);
    }

    public void setNewLocation (Widget widget, Point location) 
    {
        // Calculating movement delta...
        Point p = widget.getPreferredLocation();
        Point delta = new Point(location);
        delta.translate(-p.x, -p.y);
        if (delta.x == 0 && delta.y == 0) return; //Nothing to do...
        //ActionFactory.createDefaultMoveProvider().setNewLocation(widget, location);
        // Update all the selected objects...
        
        List<Widget> selectedElements = ((AbstractReportObjectScene)widget.getScene()).getSelectionLayer().getChildren();
        ArrayList<Widget> changedWidgets = new ArrayList<Widget>();
        
        for (Widget w : selectedElements)
        {
           if (w.isVisible())
           {
                JRDesignElementWidget dew = ((SelectionWidget)w).getRealWidget();
                
                if (changedWidgets.contains(dew)) continue;

                Point loc = w.getPreferredLocation();
                loc.translate(delta.x, delta.y);
                w.setPreferredLocation(loc);
                
                Point dewloc = dew.getPreferredLocation();
                dewloc.translate(delta.x, delta.y);
                dewloc = dew.convertLocalToModelLocation(dewloc);
                boolean b = dew.setChanging(true);
                try {
                    dew.getElement().setX( dewloc.x);
                    dew.getElement().setY( dewloc.y);
                    findEdit(dew.getElement(),"X").setNewValue(dewloc.x);
                    findEdit(dew.getElement(),"Y").setNewValue(dewloc.y);
                } finally {
                    dew.setChanging(b);
                }
                dew.updateBounds();
                if (dew.getChildrenElements() != null)
                {
                    updateChildren(dew, (AbstractReportObjectScene)dew.getScene(), changedWidgets);
                }
                
                changedWidgets.add(dew);
           }
        }
    }
    
    private ObjectPropertyUndoableEdit findEdit(Object obj, String property)
    {
        for (AggregatedUndoableEdit theEdit : undoEdits)
        {

            if (theEdit instanceof ObjectPropertyUndoableEdit)
            {
                ObjectPropertyUndoableEdit edit = (ObjectPropertyUndoableEdit)theEdit;
                if (edit.getObject() == obj && edit.getProperty().equals(property))
                {
                    return edit;
                }
            }
        }
        return null;
    }
    
    private void updateChildren(JRDesignElementWidget dew, AbstractReportObjectScene scene, ArrayList<Widget> changedWidgets)
    {
          List listOfElements = dew.getChildrenElements();

          for (int i=0; i < listOfElements.size(); ++i)
          {
               if (listOfElements.get(i) instanceof JRDesignElement)
               {
                   JRDesignElement element = (JRDesignElement)listOfElements.get(i);
                   JRDesignElementWidget w = (JRDesignElementWidget)scene.findWidget(element);
                   if (w == null || changedWidgets.contains(w)) continue;
                   w.updateBounds();
                   w.getSelectionWidget().updateBounds();

                   if (w.getChildrenElements() != null)
                   {
                       updateChildren(w, scene, changedWidgets);
                   }

                   changedWidgets.add(w);
               }
          }
    }
}
