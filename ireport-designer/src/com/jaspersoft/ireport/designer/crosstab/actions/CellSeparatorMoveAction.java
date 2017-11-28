/*
 * CellSeparatorMoveAction.java
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

package com.jaspersoft.ireport.designer.crosstab.actions;

import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.MoveStrategy;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author David Kaspar
 */
public class CellSeparatorMoveAction extends WidgetAction.LockedAdapter {

    private MoveStrategy strategy;
    private MoveProvider provider;

    private Widget movingWidget = null;
    private Point dragSceneLocation = null;
    private Point originalSceneLocation = null;
    
    private int modifiers = 0;
    private boolean reversOrder = false;
    
    public void setModifiers(int modifiers)
    {
        this.modifiers = modifiers;
    }
    
    public int getModifiers()
    {
        return this.modifiers;
    }

    public CellSeparatorMoveAction() {
        this(false);
    }
    
    public CellSeparatorMoveAction (boolean reversOrder) {
        this(reversOrder,0);
    }
    
    public CellSeparatorMoveAction (boolean reversOrder, int modifiers) {
        this.strategy = new CellSeparatorMoveStrategy(reversOrder);
        this.provider = new CellSeparatorMoveProvider(reversOrder);
        this.modifiers = modifiers;
        this.reversOrder = reversOrder;
    }

    protected boolean isLocked () {
        return movingWidget != null;
    }

    public State mousePressed (Widget widget, WidgetMouseEvent event) {
        if (event.getButton () == MouseEvent.BUTTON1  &&  event.getClickCount () == 1) {
            
            
            if (getModifiers() == 0 || ((event.getModifiersEx() & getModifiers()) == getModifiers()) )
            {
                movingWidget = widget;
                originalSceneLocation = provider.getOriginalLocation (widget);
                if (originalSceneLocation == null)
                    originalSceneLocation = new Point ();
                dragSceneLocation = widget.convertLocalToScene (event.getPoint ());
                provider.movementStarted (widget);
                return State.createLocked (widget, this);
            }
        }
        return State.REJECTED;
    }

    public State mouseReleased (Widget widget, WidgetMouseEvent event) {
        boolean state = move (widget, event.getPoint ());
        if (state) {
            movingWidget = null;
            provider.movementFinished (widget);
        }
        return state ? State.CONSUMED : State.REJECTED;
    }

    public State mouseDragged (Widget widget, WidgetMouseEvent event) {
        return move (widget, event.getPoint ()) ? State.createLocked (widget, this) : State.REJECTED;
    }

    private boolean move (Widget widget, Point newLocation) {
        if (movingWidget != widget)
            return false;
        newLocation = widget.convertLocalToScene (newLocation);
        Point location = new Point (originalSceneLocation.x + newLocation.x - dragSceneLocation.x, originalSceneLocation.y + newLocation.y - dragSceneLocation.y);
        provider.setNewLocation (widget, strategy.locationSuggested (widget, originalSceneLocation, location));
        return true;
    }

}

