/*
 * ElementSelectedBorder.java
 * 
 * Created on Aug 29, 2007, 3:09:00 PM
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

import org.netbeans.api.visual.border.Border;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import org.openide.util.Utilities;

/**
 * @author David Kaspar
 */
public class ElementSelectedBorder implements Border {

    public static final BasicStroke STROKE = new BasicStroke(1f);
    public static final Insets INSETS = new Insets(5, 5, 5, 5); //new Insets(9, 8, 10, 9);
    public static Color COLOR_1 = Color.LIGHT_GRAY;
    public static Color COLOR_2 = new Color(255,175,0, 200);
    private static final ImageIcon gripIcon = new ImageIcon(Utilities.loadImage("com/jaspersoft/ireport/designer/borders/grip.png"));
    
    public ElementSelectedBorder() {
    }

    public Insets getInsets () {
        return INSETS;
    }

    public void paint (Graphics2D g, Rectangle bounds) {
        
        
        g.setPaint(COLOR_2);
        g.setStroke(new BasicStroke(2));
        //Rectangle2D r = new Rectangle2D.Double(bounds.x + INSETS.left + 0.5, bounds.y + INSETS.top + 0.5, bounds.width - INSETS.left - INSETS.right - 1.0, bounds.height - INSETS.top - INSETS.bottom - 1.0);
        //g.draw(r);
        Rectangle2D r = new Rectangle2D.Double(bounds.x + INSETS.left -1 + 0.5, bounds.y + INSETS.top -1 + 0.5, bounds.width - INSETS.left - INSETS.right - 1.0 +2, bounds.height - INSETS.top - INSETS.bottom - 1.0+2);
        g.draw(r);
        
        paintGrip(g, bounds.x, bounds.y);
        paintGrip(g, bounds.x + bounds.width-5, bounds.y);
        paintGrip(g, bounds.x, bounds.y + bounds.height-5);
        paintGrip(g, bounds.x + bounds.width-5, bounds.y + bounds.height-5);
        paintGrip(g, bounds.x + (bounds.width/2)-2, bounds.y);
        paintGrip(g, bounds.x + (bounds.width/2)-2, bounds.y + bounds.height-5);
        paintGrip(g, bounds.x, bounds.y + (bounds.height/2)-3);
        paintGrip(g, bounds.x + bounds.width-5, bounds.y + (bounds.height/2)-3);
        /*
        Stroke stroke = g.getStroke ();
        g.setStroke(STROKE);
        g.setPaint(COLOR_2);
        g.draw(new Rectangle2D.Double (bounds.x + INSETS.left - 2.0, bounds.y + INSETS.top - 2.0, bounds.width - INSETS.right - INSETS.left + 4.0, bounds.height - INSETS.top - INSETS.bottom + 4.0));
        
        g.setStroke (stroke);
        
        g.setPaint(COLOR_1);
        //g.draw(bounds);
        g.drawImage(gripIcon.getImage(),bounds.x, bounds.y, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x + bounds.width-5, bounds.y, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x, bounds.y + bounds.height-5, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x + bounds.width-5, bounds.y + bounds.height-5, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x + (bounds.width/2)-2, bounds.y, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x + (bounds.width/2)-2, bounds.y + bounds.height-5, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x, bounds.y + (bounds.height/2)-3, gripIcon.getImageObserver());
        g.drawImage(gripIcon.getImage(),bounds.x + bounds.width-5, bounds.y + (bounds.height/2)-3, gripIcon.getImageObserver());
        */

    }

    public boolean isOpaque() {
        return false;
    }
    
    
    public void paintGrip(Graphics2D g, double x, double y)
    {
        Stroke oldStroke = g.getStroke();
        Paint oldPain = g.getPaint();
        
        Rectangle2D r = new Rectangle2D.Double(x+ 0.5, y + 0.5, 4, 4);
        
        GradientPaint gp = new GradientPaint(0,0,Color.WHITE,3,3, new Color(194,209,219));
        g.setPaint(gp);
        
        g.fill(r);
        
        g.setPaint(new Color(162,185,206));
        g.setStroke(new BasicStroke(1));
        g.draw(r);
        
        g.setPaint(oldPain);
        g.setStroke(oldStroke);
    }
}

