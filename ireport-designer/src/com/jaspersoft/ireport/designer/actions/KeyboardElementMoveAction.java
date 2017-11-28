/*
 * BandMoveAction.java
 * 
 * Created on Aug 29, 2007, 10:25:59 AM
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

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetKeyEvent;
import org.netbeans.api.visual.widget.Widget;


/**
 * @author Giulio Toffoli
 * 
 * This action moves all the selected widgets/elements of 1 pixel
 * (or 10 pixels if the shift key is down).
 *  
 */
public class KeyboardElementMoveAction extends WidgetAction.Adapter {

    public KeyboardElementMoveAction() {
        super();
    }

    @Override
    public State keyPressed(Widget widget, WidgetKeyEvent event) {
        
        
        if (event.getKeyCode() == KeyEvent.VK_LEFT ||
            event.getKeyCode() == KeyEvent.VK_RIGHT ||
            event.getKeyCode() == KeyEvent.VK_UP ||
            event.getKeyCode() == KeyEvent.VK_DOWN)
        {
            int len = (event.isShiftDown()) ? 10 : 1;
            
            Point delta = new Point(0,0);
            switch (event.getKeyCode())
            {
                case KeyEvent.VK_LEFT: delta.x -= len; break;
                case KeyEvent.VK_RIGHT: delta.x = len; break;
                case KeyEvent.VK_UP: delta.y -= len; break;
                case KeyEvent.VK_DOWN: delta.y = len; break;
            }
            
            List<JRDesignElementWidget> widgets = new ArrayList<JRDesignElementWidget>();
            ReportObjectScene scene = (ReportObjectScene)widget.getScene();

            Iterator iterSelectedObject = scene.getSelectedObjects().iterator();
            while (iterSelectedObject.hasNext())
            {
                Object obj = iterSelectedObject.next();
                Widget w = scene.findWidget(obj);
                if (w instanceof JRDesignElementWidget)
                {
                    widgets.add((JRDesignElementWidget)w);
                }
            }
            
            move(widgets, delta);
            
            return State.CHAIN_ONLY;
        }
        
        return State.REJECTED;
    }
    
    private void move (List<JRDesignElementWidget> widgets, Point delta) {
        
        for (JRDesignElementWidget dew : widgets)
        {
            Point dewloc = dew.getPreferredLocation();
            dewloc.translate(delta.x, delta.y);
            dewloc = dew.convertLocalToModelLocation(dewloc);
            boolean b = dew.setChanging(true);
            try {
                dew.getElement().setX( dewloc.x);
                dew.getElement().setY( dewloc.y);
            } finally {
                dew.setChanging(b);
            }
            dew.updateBounds();
            
            // Sync the selection...
            dew.getSelectionWidget().updateBounds();
        }
    }
}

