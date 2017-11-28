/*
 * JRDesignImageWidget.java
 * 
 * Created on 14-nov-2007, 15.56.26
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.widgets;

import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.utils.Java2DUtils;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author gtoffoli
 */
public class JRDesignChartWidget extends JRDesignElementWidget {

    private Image chartImage = null;
    private Image staticChartImage = null;
    
    public JRDesignChartWidget(ReportObjectScene scene, JRDesignChart element) {
        super(scene, element);
        ((JRBaseChartPlot)element.getPlot()).getEventSupport().addPropertyChangeListener(this);
    }
    
    
    @Override
    protected void paintWidget() {
        
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

            JasperDesign jd = ((ReportObjectScene)this.getScene()).getJasperDesign();
            JRDesignChart e = (JRDesignChart)this.getElement();

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
            chartImage = recreateChartImage();
        }
        
        return chartImage;
    }

    public void setChartImage(java.awt.Image image) {
        this.chartImage = image;
    }
    
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
    }
    
    
    private Image recreateChartImage()
    {
        JRDesignChart chart = (JRDesignChart)getElement();
        String imgUri = null;
        switch (chart.getChartType())
        {
            case JRDesignChart.CHART_TYPE_AREA:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/area_big.png";
                break;
            case JRDesignChart.CHART_TYPE_BAR:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/bar_big.png";
                break;
            case JRDesignChart.CHART_TYPE_BAR3D:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/bar3d_big.png";
                break;
            case JRDesignChart.CHART_TYPE_BUBBLE:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/bubble_big.png";
                break;
            case JRDesignChart.CHART_TYPE_CANDLESTICK:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/candlestick_big.png";
                break;
            case JRDesignChart.CHART_TYPE_HIGHLOW:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/highlow_big.png";
                break;
            case JRDesignChart.CHART_TYPE_LINE:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/line_big.png";
                break;
            case JRDesignChart.CHART_TYPE_METER:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/meter_big.png";
                break;
            case JRDesignChart.CHART_TYPE_MULTI_AXIS:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/multiaxis_big.png";
                break;
            case JRDesignChart.CHART_TYPE_PIE:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/pie_big.png";
                break;
            case JRDesignChart.CHART_TYPE_PIE3D:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/pie3d_big.png";
                break;
            case JRDesignChart.CHART_TYPE_SCATTER:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/scatter_big.png";
                break;
            case JRDesignChart.CHART_TYPE_STACKEDAREA:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/stackedarea_big.png";
                break;
            case JRDesignChart.CHART_TYPE_STACKEDBAR:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/stackedbar_big.png";
                break;
            case JRDesignChart.CHART_TYPE_STACKEDBAR3D:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/stackedbar3d_big.png";
                break;
            case JRDesignChart.CHART_TYPE_THERMOMETER:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/thermometer_big.png";
                break;
            case JRDesignChart.CHART_TYPE_TIMESERIES:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/timeseries_big.png";
                break;
            case JRDesignChart.CHART_TYPE_XYAREA:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/xyarea_big.png";
                break;
            case JRDesignChart.CHART_TYPE_XYBAR:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/xybar_big.png";
                break;
            case JRDesignChart.CHART_TYPE_XYLINE:
                imgUri = "/com/jaspersoft/ireport/designer/charts/icons/xyline_big.png";
                break;
        }
        
        if (staticChartImage == null && imgUri != null)
        {
            staticChartImage = Misc.loadImageFromResources(imgUri);
        }
        chartImage = staticChartImage;
        
        return chartImage;
    }
    
    
    
    
}
