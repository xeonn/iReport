/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.designer.widgets.SelectionWidget;
import java.awt.Point;
import java.util.Collections;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class ReportObjectSelectProvider implements SelectProvider {

        private AbstractReportObjectScene scene = null;

        public ReportObjectSelectProvider(AbstractReportObjectScene scene)
        {
            this.scene = scene;
        }

        public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            if (widget instanceof SelectionWidget)
            {
                widget = ((SelectionWidget)widget).getRealWidget();
            }
            return scene.findObject (widget) != null;
        }

        public void select (Widget widget, Point localLocation, boolean invertSelection) {
            
            if (widget instanceof SelectionWidget)
            {
                widget = ((SelectionWidget)widget).getRealWidget();
            }

            Object object = scene.findObject(widget);

            scene.setFocusedObject (object);
            
            if (object != null) {

                if (!invertSelection  &&  scene.getSelectedObjects ().contains (object))
                   return;

                scene.userSelectionSuggested (Collections.singleton (object), invertSelection);
            } else
            {
                if (!invertSelection)
                {
                    scene.userSelectionSuggested (Collections.emptySet (), invertSelection);
                }
            }
        }
}
