/*
 * ReportElementWidget.java
 * 
 * Created on Aug 29, 2007, 2:18:27 PM
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

package com.jaspersoft.ireport.designer.widgets;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.borders.ElementSelectedBorder;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class SelectionWidget extends Widget {

    
    protected static final ElementSelectedBorder SELECTED_BORDER = new ElementSelectedBorder();
    
    /**
     * This is the widget virtually selected using this widget.
     */
    protected JRDesignElementWidget realWidget = null;

    public JRDesignElementWidget getRealWidget() {
        return realWidget;
    }
    /**
     *  Widget w is the widget to proxy.
     */
    public SelectionWidget(AbstractReportObjectScene scene, JRDesignElementWidget w) {
        super(scene);
        this.realWidget = w;
        setBorder(SELECTED_BORDER);
        updateBounds();
    }
    
    public void updateBounds()
    {
        Insets insets = getBorder().getInsets();
        Rectangle r = realWidget.getPreferredBounds();
        r.x -= insets.left;
        r.y -= insets.top;
        r.width += insets.left + insets.right;
        r.height += insets.top + insets.bottom;
        
        setPreferredBounds(r);
        Point p = realWidget.getPreferredLocation();
        
        setPreferredLocation(p);
    }
    
    @Override
    protected Cursor getCursorAt(Point localLocation) {
        Rectangle bounds = getBounds ();
        Insets insets = getBorder().getInsets();
        if (new Rectangle (bounds.x, bounds.y, insets.left, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + (bounds.width/2) - insets.top/2,  bounds.y , insets.top, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x+bounds.width-insets.right, bounds.y, insets.right, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x, bounds.y+bounds.height-insets.bottom, insets.left, insets.bottom).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + (bounds.width/2) - insets.bottom/2, bounds.y+bounds.height-insets.bottom, insets.bottom, insets.bottom).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + bounds.width-insets.right, bounds.y+bounds.height-insets.bottom, insets.left, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x, bounds.y + (bounds.height/2) - insets.left/2, insets.left, insets.left).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + bounds.width - insets.right, bounds.y + (bounds.height/2) - insets.right/2, insets.right, insets.right).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
        return super.getCursorAt(localLocation);
    }
}
