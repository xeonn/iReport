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

import com.jaspersoft.ireport.designer.widgets.BandSeparatorWidget;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

import java.awt.event.MouseEvent;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;

/**
 * @author David Kaspar
 */
public class BandDblClickResizeAction extends WidgetAction.Adapter {

    public WidgetAction.State mousePressed(Widget widget,   WidgetAction.WidgetMouseEvent event)
    {

        if (event.getButton() == MouseEvent.BUTTON1 &&
            event.getClickCount() == 2 &&
            widget instanceof BandSeparatorWidget)
        {

            JRDesignBand band = (JRDesignBand) ((BandSeparatorWidget)widget).getBand();
            // Find the lowest coordinate of a contained element...
            int height = 0;

            List children = band.getChildren();
            for (int i=0; i<children.size(); ++i)
            {
                if (children.get(i) instanceof JRDesignElement)
                {
                    JRDesignElement ele = (JRDesignElement)children.get(i);
                    if (ele.getY() + ele.getHeight() > height)
                    {
                        height = ele.getY() + ele.getHeight();
                    }
                }
            }

            if (height> 0 && height != band.getHeight())
            {
                band.setHeight(height);
            }
        }

        return WidgetAction.State.REJECTED; // let someone use it...
    }

}
