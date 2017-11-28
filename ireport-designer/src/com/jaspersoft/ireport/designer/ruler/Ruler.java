/*
 * Ruler.java
 * 
 * Created on Oct 1, 2007, 8:33:09 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.ruler;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ReportDesignerPanel;
import com.jaspersoft.ireport.designer.utils.Unit;
import java.awt.Color;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.Scene.SceneListener;

/**
 *
 * @author gtoffoli
 */
public class Ruler extends JComponent implements SceneListener, ComponentListener {

    public static final int TYPE_VERTICAL = 1;
    public static final int TYPE_HORIZONTAL = 0;
    
    private int type = TYPE_HORIZONTAL;
    
    private int cursorPosition;    
    
    private boolean dragging = false;
    private java.awt.image.BufferedImage savedImage = null;
    private ImageIcon horizontalRuleStopIcon = null;
    
    private java.util.List guideLines = new java.util.ArrayList();

    public List getGuideLines() {
        return guideLines;
    }
    private int lastTempGuidePosition = -1; 
    
    private ReportDesignerPanel reportPanel = null;
    
    private JScrollPane sceneScrollPane = null;

    public ReportDesignerPanel getReportPanel() {
        return reportPanel;
    }

    /**
     *  Set the report panel to which the ruler refers to.
     */
    public void setReportPanel(ReportDesignerPanel reportPanel) {
        this.reportPanel = reportPanel;
    }
    
    /**
     *  Get the ruler type (TYPE_VERTICAL or TYPE_HORIZONTAL).
     *  The default is HORIZONTAL_RULER.
     */
    public int getType() {
        return type;
    }

    /**
     *  Set the ruler type (TYPE_VERTICAL or TYPE_HORIZONTAL).
     *  The default is HORIZONTAL_RULER.
     */
    public void setType(int type) {
        if (type != this.type)
        {
            horizontalRuleStopIcon = new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/tulers/rulestop" + ((type == TYPE_VERTICAL) ? "_vertical" : "_horizontal") + ".png"));
            this.type = type;
        }
    }
        
    public Ruler(int type, ReportDesignerPanel reportPanel)
    {
        super();
        setType(type);
        this.reportPanel = reportPanel;
        //setBackground(Color.WHITE);
        setOpaque(true);
        //reportPanel.getScene().addSceneListener(this);
        
    }
    
    public void addNotify() {
        super.addNotify();
        if (reportPanel == null) return;
        reportPanel.getScene().addSceneListener(this);
        JComponent viewComponent = reportPanel.getScene().getView();
        //if (viewComponent == null)
        //    viewComponent = scene.createView ();
        viewComponent.addComponentListener (this);
        repaint ();
    }

    public void removeNotify() {
        if (reportPanel != null)
        {
            reportPanel.getScene().getView().removeComponentListener (this);
            reportPanel.getScene().removeSceneListener (this);
        }
        super.removeNotify();
    }

    /**
     * Paints the component. This forwards the paint to any lightweight
     * components that are children of this container. If this method is
     * reimplemented, super.paint(g) should be called so that lightweight
     * components are properly rendered. If a child component is entirely
     * clipped by the current clipping setting in g, paint() will not be
     * forwarded to that child.
     *
     * @param g the specified Graphics window
     * @see   Component#update(Graphics)
     *
     *    0     x0    x1                                   x2    x3
     *    +-----+-----+------------------------------------+-----+-------
     *    |     |:::::|::::::::::::::::::::::::::::::::::::|:::::|
     *    |     |:::::|::::::::::::::::::::::::::::::::::::|:::::|
     *
     *    <============= scroll_h =========================================>
     *     
     *    x1 = Margin left position
     *    x2 = Margin right position
     *    x3 = Page width position
     *    scroll_h = horizontal position of the scroll bar.
     *    
     */
    @Override
    public void paint(Graphics g) {

        g.setFont( this.getFont());
        g.setColor(getBackground());
        g.fillRect(0 ,0,this.getWidth(), this.getHeight());
        
        String unitName =  IReportManager.getPreferences().get("Unit", "inches"); // FIXME get currently displayed unit
        
        double unit = Unit.CENTIMETERS; 
        
        if (unitName.equals("cm")) unit = Unit.CENTIMETERS;
        else if (unitName.equals("pixels")) unit = Unit.PIXEL;
        else if (unitName.equals("mm")) unit = Unit.MILLIMETERS;
        else if (unitName.equals("inches")) unit = Unit.INCHES;

        g.setColor(new Color( 255,255,255));
        
        double k=0;

        double zoomfactor = getReportPanel().getScene().getZoomFactor();   

        int line=0;
        int i=0;
        //int i = 10-HScrollBar1.getValue();
        int oldi=-100;
        double module = 2;

        boolean isMillimeters = (unit == Unit.MILLIMETERS);
        if (isMillimeters)
        {
            unit = Unit.CENTIMETERS;
        }

        boolean isPixel = false;
        // Choose module...
        int tick_space = 50;
        if (unit == Unit.PIXEL)
        {
            isPixel = true;
            unit = 50;
            tick_space = 100;
        }

        if (((int)(convertUnitToPixel(1,unit)*zoomfactor)) >= tick_space)
        {
            module = 10;
        }

        JasperDesign jd = getJasperDesign();
        if (getReportPanel() == null) return;
        
        sceneScrollPane = findScrollPane(getReportPanel().getScene().getView());
        
        if (getType() == TYPE_HORIZONTAL)
        {
                int x0=0; 
                int x1=0; 
                int x2=0; 
                int x3=0; 

                int hScrollBarVal = sceneScrollPane.getHorizontalScrollBar().getValue();
                if (jd != null)
                {
                    x0 = getZoomedDim( 10,  zoomfactor) - hScrollBarVal;
                    x1 = getZoomedDim( 10 + jd.getLeftMargin(),  zoomfactor) - hScrollBarVal;
                    x2 = getZoomedDim( 10 + jd.getPageWidth() -  jd.getRightMargin(),  zoomfactor) - hScrollBarVal;
                    x3 = getZoomedDim( 10 + jd.getPageWidth(),  zoomfactor) - hScrollBarVal;
                }
                
                java.awt.Color c = this.getBackground().darker();
                
                g.setColor(Color.WHITE);
                g.fillRect(Math.max(x0,0), 0, x3 - Math.max(x0,0), this.getHeight());        
                
                if (x1 > 0)
                {
                    g.setColor(c);
                    g.fillRect(Math.max(x0,0), 0, x1 - Math.max(x0,0), this.getHeight());        
                    //g.setColor(c.darker());
                    //g.drawRect(Math.max(x0 - hScrollBarVal,0), 0, x1 - x0 - hScrollBarVal, this.getHeight());    
                }

                if (x2 < this.getWidth())
                {
                    g.setColor(c);
                    g.fillRect(x2, 0, Math.min(this.getWidth(), x3) - x2, this.getHeight());        
                    g.setColor(c.darker());
                    g.drawRect(x2, 0, Math.min(this.getWidth(), x3) - x2, this.getHeight());    
                }

                
                g.setColor(new Color(122, 150, 223));
                g.drawLine(0,this.getHeight()-1, this.getWidth(), this.getHeight()-1);

                Paint p = new GradientPaint(0,this.getHeight()/2, new Color(255,255,255,0),
                                            0,this.getHeight(), new Color(100,100,100,128));
                ((Graphics2D)g).setPaint(p);
                g.fillRect(0, 0,this.getWidth(), this.getHeight());        
                
                g.setColor(new Color(0,0,0));
                
                i = x1;
                int xk = x1; // this is to be sure two lines have at least 8 opixels between...
                
                // 3 levels of unit:   1 -> BASIC -> 2
                // BIG: Less that  
                //
                //50
                // 
                while (i< this.getWidth())
                {
                        if (i>=0 && (i-xk > 8 || i==xk))
                        {
                                if ((line%module)==0)
                                {
                                        if (i-oldi > 20)
                                        {
                                                String s = ""+(int)k;
                                                if (isMillimeters) s += "0";
                                                if (isPixel) s = ""+((int)k*50);

                                                int w = g.getFontMetrics().stringWidth(s);
                                                g.drawString(s ,i - (w/2), (g.getFontMetrics().getHeight()/2)+3);
                                                //writeRotateString((Graphics2D)g,i - (w/2), (g.getFontMetrics().getHeight()/2)+3, s);
                                                oldi= i;
                                                g.drawLine(i,16,i,12);
                                        }
                                        else if (i-xk > 4)
                                        {
                                            g.drawLine(i,5,i,10);
                                        }
                                        
                                        if (i-xk > 4)
                                        {
                                            xk = i;
                                        }

                                }
                                else if (i-xk > 4)
                                {
                                    if (module == 10 && (line%5) !=0)
                                    {
                                        g.drawLine(i,7,i,8);
                                    }
                                    else 
                                    {
                                        g.drawLine(i,6,i,9);
                                    }
                                    xk = i;
                                }
                        }
                        line++;
                        k = line*(1.0/module);
                        i = x1 + (int)(convertUnitToPixel(k,unit) * zoomfactor);	


                }


                line=1;
                k = 1.0/module;
                oldi=x1;
                i = x1 - (int)(convertUnitToPixel(k,unit) * zoomfactor);
                while (x1 > 0 && i > -10)
                {
                        if (i>=0)
                        {
                                if ((line%module)==0)
                                {
                                        if (oldi-i > 20)
                                        {
                                                String s = ""+(int)k;
                                                if (isMillimeters) s += "0";
                                                if (isPixel) s = ""+((int)k*50);

                                                int w = g.getFontMetrics().stringWidth(s);
                                                g.drawString(s ,i - (w/2), (g.getFontMetrics().getHeight()/2)+3);
                                                oldi= i;
                                                g.drawLine(i,16,i,12);
                                        }
                                        else
                                        {
                                            g.drawLine(i,5,i,10);
                                        }

                                }
                                else 
                                {
                                    if (module == 10 && (line%5) !=0)
                                    {
                                        g.drawLine(i,7,i,8);
                                    }
                                    else
                                    {
                                        g.drawLine(i,6,i,9);
                                    }
                                }
                        }
                        line++;
                        k = line*(1.0/module);
                        i = x1 - (int)(convertUnitToPixel(k,unit) * zoomfactor);			
                }
                
                for (i=0; i<getGuideLines().size(); ++i)
                {
                    Integer pos = (Integer)getGuideLines().get(i);
                    int posI = pos.intValue();
                    // Calc posI....
                    posI = 10 + (int)(posI*zoomfactor) - hScrollBarVal;
                    g.drawImage(horizontalRuleStopIcon.getImage(),posI-4, 7, this);
                }

        } 

        
    }
    
    
    /**
     *  Return the jasperdesign displayed by the current underline
     *  report panel.
     *  Returns null if no JasperDesign is available.
     */
    private JasperDesign getJasperDesign()
    {
        if (reportPanel != null) return reportPanel.getJasperDesign();
        return null;
    }
    
    
    public int convertUnitToPixel( double value, double unit)
    {
        if (unit == Unit.PIXEL) return (int)value;
        return (int)Unit.convertToPixels(value,unit);
    }
    
    private JScrollPane findScrollPane (JComponent component) {
        for (;;) {
            if (component == null)
                return null;
            if (component instanceof JScrollPane)
                return ((JScrollPane) component);
            Container parent = component.getParent ();
            if (! (parent instanceof JComponent))
                return null;
            component = (JComponent) parent;
        }
    }
    
     public int getZoomedDim(int value, double zoomFactor) {
        if (zoomFactor == 1.0) return value;
        //if (((double)dim*(double)zoom_factor)<0.5) return 1;
        // Truncate, don't round!!
        return (int) Math.round( (float)value*zoomFactor ) ;
        //return (int)Math.ceil((double)dim*zoom_factor);
    }

    public void sceneRepaint() {
        repaint();
    }

    public void sceneValidating() {
    }

    public void sceneValidated() {
    }

    public void componentResized(ComponentEvent e) {
        repaint ();
    }

    public void componentMoved(ComponentEvent e) {
        repaint ();
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    
}
