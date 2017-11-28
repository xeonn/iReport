/*
 * TranslucentRectangularSelectDecorator.java
 * 
 * Created on Aug 29, 2007, 6:10:44 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.borders.ThinLineBorder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import org.netbeans.api.visual.action.RectangularSelectDecorator;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class TranslucentRectangularSelectDecorator implements RectangularSelectDecorator {

    private Scene scene;

    public TranslucentRectangularSelectDecorator (Scene scene) {
        this.scene = scene;
    }

    public Widget createSelectionWidget () {
        Widget widget = new Widget(scene);
        //widget.setBorder (scene.getLookFeel ().getMiniBorder (ObjectState.createNormal ().deriveSelected (true)));
        widget.setBorder ( new ThinLineBorder(Color.BLUE));
        widget.setBackground(new Color(0,0,255,60));
        widget.setOpaque(true);
        return widget;
    }
    
    class RectangularWidget extends Widget
    {
        public RectangularWidget(Scene scene)
        {
            super(scene);
        }

        @Override
        protected void paintBackground() {
            
            Graphics2D g = this.getGraphics();
            g.setPaint(getBackground());
            Rectangle b = getBounds();
            Rectangle2D r = new Rectangle2D.Double(b.x +0.5, b.y+0.5, b.width-1.0, b.height-1.0);
            g.fill(r);
        }
        
    }

}
