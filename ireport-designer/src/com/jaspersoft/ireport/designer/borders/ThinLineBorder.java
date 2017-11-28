/*
 * ReportBorder.java
 * 
 * Created on Oct 8, 2007, 1:54:02 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.borders;

import com.jaspersoft.ireport.designer.utils.RoundGradientPaint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.border.Border;

/**
 *
 * @author gtoffoli
 */
public class ThinLineBorder implements Border {

    Color color = Color.BLACK;
    
    public ThinLineBorder(Color c)
    {
        this.color = c;
    }
    private static Insets insets = new Insets(0, 0, 0, 0);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        
        ((Graphics2D)g).setPaint(color);
        ((Graphics2D)g).setStroke( new BasicStroke(1));
        
        Rectangle2D r = new Rectangle2D.Double(x+0.5, y+0.5,width-1.0, height-1.0);
        
        ((Graphics2D)g).draw(r);
        
    }

    public Insets getBorderInsets(Component c) {
        return insets;
    }

    public boolean isBorderOpaque() {
        return true;
    }

}
