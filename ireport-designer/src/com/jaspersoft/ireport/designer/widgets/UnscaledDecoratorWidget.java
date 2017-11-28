/*
 * ReportBorder.java
 * 
 * Created on Oct 8, 2007, 1:54:02 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.widgets;

import com.jaspersoft.ireport.designer.utils.Java2DUtils;
import java.awt.BasicStroke;
import java.awt.Stroke;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author gtoffoli
 */
public class UnscaledDecoratorWidget extends ConnectionWidget {

    public UnscaledDecoratorWidget(Scene scene)
    {
        super(scene);
    }

    /**
     * Paint the connection using a inverse-zoomed stroke.
     * 
     * 
     */
    @Override
    protected void paintWidget() {
        
        Stroke oldStroke = getStroke();
        double zoom = getScene().getZoomFactor();
        Stroke bs = Java2DUtils.getInvertedZoomedStroke(oldStroke, zoom);
        this.setStroke(bs);

        super.paintWidget();

        this.setStroke(oldStroke);
    }
    
}
