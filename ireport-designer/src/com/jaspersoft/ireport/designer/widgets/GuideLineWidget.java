/*
 * GuideLine.java
 * 
 * Created on Oct 9, 2007, 1:44:27 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.widgets;

import com.jaspersoft.ireport.designer.utils.Java2DUtils;
import com.jaspersoft.ireport.designer.ruler.GuideLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import javax.swing.JComponent;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class GuideLineWidget extends Widget {
    
    private static final Stroke guideLineStroke = new BasicStroke (1.0f, BasicStroke.JOIN_BEVEL, BasicStroke.CAP_BUTT, 5.0f, new float[] { 3.0f, 2.0f }, 0.0f);

    
    private GuideLine guideLine = null;

    public GuideLine getGuideLine() {
        return guideLine;
    }

    public void setGuideLine(GuideLine guideLine) {
        this.guideLine = guideLine;
    }
    
    public GuideLineWidget(Scene scene, GuideLine guideLine)
    {
        super(scene);
        this.guideLine = guideLine;
    }
    
    @Override
    protected void paintWidget() {
        
        Graphics2D g = this.getGraphics();
        
        Stroke oldStroke = g.getStroke();
        double zoom = getScene().getZoomFactor();
        Stroke bs = Java2DUtils.getInvertedZoomedStroke(guideLineStroke, zoom);
        g.setStroke(bs);
        g.setPaint(new Color(0,0,255,128));
        JComponent view = getScene().getView();
        
        Rectangle b = getBounds();
        g.drawLine( b.x, b.y, b.width, b.height);
        
/*
        if (getGuideLine().isVertical())
        {
            int w = (int)(view.getWidth()/getScene().getZoomFactor());
            if (getScene() instanceof ReportObjectScene)
            {
                ReportObjectScene ros = (ReportObjectScene)getScene();
                if (ros.getJasperDesign() != null)
                {
                    int w2 = ros.getJasperDesign().getPageWidth();
                    w = Math.min(w, w2);
                }
            }
            g.drawLine( (int)(-10*zoom), 0, w, 0);
        }
        else
        {
            int h = (int)(view.getHeight()/getScene().getZoomFactor());
            if (getScene() instanceof ReportObjectScene)
            {
                ReportObjectScene ros = (ReportObjectScene)getScene();
                if (ros.getJasperDesign() != null)
                {
                    int h2 = ModelUtils.getDesignHeight(ros.getJasperDesign());
                    h = Math.min(h, h2);
                }
            }
            g.drawLine( 0, (int)(-10*zoom), 0, h);
        }
        */
        
        
        g.setStroke(oldStroke);
    }
    
}
