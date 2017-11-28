/*
 * JRDesignImageWidget.java
 * 
 * Created on 14-nov-2007, 15.56.26
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.examples.customcomponent;

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.*;
import com.jaspersoft.ireport.designer.utils.Java2DUtils;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 *
 * @author gtoffoli
 */
public class JRDesignJChartWidget extends JRDesignElementWidget {

    private Image chartImage = null;
    
    public JRDesignJChartWidget(AbstractReportObjectScene scene, JRDesignElement element) {
        super(scene, element);
    }
    
    
    @Override
    protected void paintWidgetImplementation() {
        
        Image image = getChartImage();
        
        if (image != null)
        {
            Graphics2D gr = getScene().getGraphics();
        
            //Java2DUtils.setClip(gr,getClientArea());
            // Move the gfx 10 pixel ahead...
            Rectangle r = getPreferredBounds();

            AffineTransform af = gr.getTransform();
            AffineTransform new_af = (AffineTransform) af.clone();
            AffineTransform translate = AffineTransform.getTranslateInstance(
                    getBorder().getInsets().left + r.x,
                    getBorder().getInsets().top + r.y);
            new_af.concatenate(translate);
            gr.setTransform(new_af);

            JRDesignElement e = this.getElement();

            Java2DUtils.setClip(gr,getClientArea());
            gr.drawImage(image, 0, 0, e.getWidth(), e.getHeight(),
                                0, 0,  image.getWidth(null), image.getHeight(null), null);
            Java2DUtils.resetClip(gr);
            
            gr.setTransform(af);
        }
        else
        {
            super.paintWidget();
        }
    }

    public java.awt.Image getChartImage() {
        
        if (chartImage == null)
        {
            chartImage = Misc.loadImageFromResources("/com/jaspersoft/ireport/examples/customcomponent/component.png");
        }
        return chartImage;
    }
}
