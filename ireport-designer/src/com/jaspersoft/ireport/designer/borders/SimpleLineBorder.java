/*
 * SimpleLineBorder.java
 * 
 * Created on Aug 29, 2007, 4:14:33 PM
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

package com.jaspersoft.ireport.designer.borders;


import com.jaspersoft.ireport.designer.utils.Java2DUtils;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.widget.Widget;

/**
 * @author Giulio Toffoli
 */
public final class SimpleLineBorder implements Border {

    private  Widget widget = null;
    public static Color COLOR_1 = new Color(192, 192, 192, 128);
    
    public static final Insets INSETS = new Insets(0, 0, 0, 0); //new Insets(9, 8, 10, 9);
    
    public Insets getInsets() {
        return INSETS;
    }
    
    public SimpleLineBorder()
    {
        super();
    }
    
    public SimpleLineBorder(Widget w)
    {
        super();
        this.widget = w;
    }

    public void paint(Graphics2D gr, Rectangle bounds) {
        
        Stroke oldStroke = gr.getStroke();
                
        Stroke bs = new BasicStroke(1);
        
        if (getWidget() != null && getWidget().getScene() != null)
        {
            double zoom = getWidget().getScene().getZoomFactor();
            bs = Java2DUtils.getInvertedZoomedStroke(bs, zoom);
        }
        
        gr.setStroke(bs);
        
        gr.setPaint(COLOR_1);
        Rectangle2D r = new Rectangle2D.Double (bounds.x + INSETS.left + 0.5, bounds.y + INSETS.top + 0.5, bounds.width - INSETS.left - INSETS.right - 1.0, bounds.height - INSETS.top - INSETS.bottom - 1.0);
        gr.draw(r);
        gr.setStroke(oldStroke);
        
    }

    public boolean isOpaque() {
        return false;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }
}
