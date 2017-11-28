/*
 * ReportAlignWithWidgetCollector.java
 * 
 * Created on Aug 30, 2007, 11:56:10 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.widgets.GuideLineWidget;
import com.jaspersoft.ireport.designer.widgets.JRDesignElementWidget;
import com.jaspersoft.ireport.designer.widgets.SelectionWidget;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.apache.bcel.generic.GETSTATIC;
import org.netbeans.api.visual.action.AlignWithWidgetCollector;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class ReportAlignWithWidgetCollector implements AlignWithWidgetCollector {

    private List<Integer> verticalGuideLines = new ArrayList<Integer>();

    public List<Integer> getHorizontalGuideLines() {
        return horizontalGuideLines;
    }

    public void setHorizontalGuideLines(List<Integer> horizontalGuideLines) {
        this.horizontalGuideLines = horizontalGuideLines;
    }

    public List<Integer> getVerticalGuideLines() {
        return verticalGuideLines;
    }

    public void setVerticalGuideLines(List<Integer> verticalGuideLines) {
        this.verticalGuideLines = verticalGuideLines;
    }
    private List<Integer> horizontalGuideLines = new ArrayList<Integer>();
    private ReportObjectScene scene = null;
    
    public ReportAlignWithWidgetCollector(ReportObjectScene scene)
    {
        this.scene = scene;
    }
    
    public boolean isSelectedObject(Widget widget)
    {
        
        Object obj = null;
        if (widget instanceof JRDesignElementWidget)
        {
            obj = ((JRDesignElementWidget)widget).getElement();
        }
        else if (widget instanceof SelectionWidget)
        {
            obj = ((SelectionWidget)widget).getRealWidget().getElement();
        }
        
        if ( ((ReportObjectScene)widget.getScene()).getSelectedObjects().contains(obj) )
        {
            return true;
        }
        
        return false;
    }
    
    public boolean isChildOf(Widget widget, Widget parent)
    {
        if (widget instanceof JRDesignElementWidget)
        {
            if (parent instanceof SelectionWidget)
                parent = ((SelectionWidget)parent).getRealWidget();
            
            if (parent instanceof JRDesignElementWidget)
            {
                JRDesignElement frame = ((JRDesignElementWidget)parent).getElement();
                JRDesignElement element = ((JRDesignElementWidget)widget).getElement();

                if (frame instanceof JRDesignFrame)
                {
                    // Look if a child element has this widget....
                    return ModelUtils.isChildOf(element, ((JRDesignFrame)frame).getElements());
                }
            }
        }
        return false;
    }
    
    
    
    
    public Collection<Rectangle> getRegions(Widget movingWidget) {
        
        java.util.List<Widget> children = scene.getElementsLayer().getChildren();
        ArrayList<Rectangle> regions = new ArrayList<Rectangle> (children.size());
        for (Widget widget : children)
        {
            if (widget != movingWidget && !isSelectedObject(widget) && !isChildOf(widget, movingWidget))
            {
                regions.add(widget.convertLocalToScene (widget.getClientArea()));
            }
        }
        //System.out.println("Elements in regions: " + regions.size() + " " + movingWidget);
        
        children = scene.getGuideLinesLayer().getChildren();
        for (Widget widget : children)
        {
            if (widget instanceof GuideLineWidget)
            {
                Rectangle r = widget.getClientArea();
                r.width = 0;
                regions.add(widget.convertLocalToScene(r));
            }
        }
        
        /*
        for (Integer line : getVerticalGuideLines())
        {
            regions.add( new Rectangle(10 + line.intValue(), 0, 1, Integer.MAX_VALUE) );
        }
        
        for (Integer line : getHorizontalGuideLines())
        {
            regions.add( new Rectangle(0, 10 + line.intValue(), Integer.MAX_VALUE, 1 ) );
        }
        */
        
        JasperDesign jd = scene.getJasperDesign();
        // Add margins and bands position...
        int bandLocation = jd.getTopMargin(); // Scene location...
        regions.add( new Rectangle(jd.getLeftMargin(), bandLocation, jd.getPageWidth() - jd.getRightMargin() - jd.getLeftMargin(), 0 ) );
        for (JRBand band : ModelUtils.getBands(jd))
        {
            if (band.getHeight() > 0)
            {
                bandLocation += band.getHeight();
                regions.add( new Rectangle(jd.getLeftMargin(), bandLocation, jd.getPageWidth() - jd.getRightMargin() - jd.getLeftMargin(), 0 ) );
            }
        }
        
       
        
        return regions;
    }
}
