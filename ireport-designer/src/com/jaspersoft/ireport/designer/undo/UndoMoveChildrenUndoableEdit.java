/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.undo;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.util.List;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *
 * @author gtoffoli
 */
public class UndoMoveChildrenUndoableEdit  extends AggregatedUndoableEdit {

    private JRDesignElementWidget widget = null;


    public UndoMoveChildrenUndoableEdit(JRDesignElementWidget widget)
    {
        this.widget = widget;
    }

    @Override
    public void undo() throws CannotUndoException {

        super.undo();
        updateChildren();
    }

    @Override
    public void redo() throws CannotRedoException {

        super.redo();
        updateChildren();
    }



    @Override
    public String getPresentationName() {
        return "Element childrens update";
    }

    /**
     * @return the widget
     */
    public JRDesignElementWidget getWidget() {
        return widget;
    }

    /**
     * @param widget the widget to set
     */
    public void setWidget(JRDesignElementWidget widget) {
        this.widget = widget;
    }

    private void updateChildren() {
        updateChildren(widget);
        AbstractReportObjectScene scene = (AbstractReportObjectScene) widget.getScene();
        scene.validate();
    }
    private void updateChildren(JRDesignElementWidget wid) {

          List listOfElements = wid.getChildrenElements();
          AbstractReportObjectScene scene = (AbstractReportObjectScene) widget.getScene();

          for (int i=0; i < listOfElements.size(); ++i)
          {
               if (listOfElements.get(i) instanceof JRDesignElement)
               {
                   JRDesignElement element = (JRDesignElement)listOfElements.get(i);
                   JRDesignElementWidget w = (JRDesignElementWidget)scene.findWidget(element);
                   w.updateBounds();
                   w.getSelectionWidget().updateBounds();

                   if (w.getChildrenElements() != null)
                   {
                       updateChildren(w);
                   }
               }
          }


    }



}
