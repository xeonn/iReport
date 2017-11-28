/*
 * PageWidget.java
 * 
 * Created on Aug 28, 2007, 12:58:38 AM
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

package com.jaspersoft.ireport.designer.widgets;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.borders.ReportBorder;
import com.jaspersoft.ireport.designer.utils.Java2DUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.List;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Utilities;


/**
 *
 * @author gtoffoli
 */
public class PageWidget extends Widget {
    
    private int gridSize = 13;
    private TexturePaint gridTexture = null;
    private static final BasicStroke GRID_STROKE = new BasicStroke(0, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_BEVEL, 1.0f, new float[]{2f,2f}, 0.0f);
    
    public PageWidget(ReportObjectScene scene) {
        super(scene);
        
        
        
        //this.setMaximumSize( new Dimension( scene.getJasperDesign().getPageWidth(), getMaximumDesignHeight()) );
        
        /*
        setBorder( BorderFactory.createImageBorder(
                new Insets(10,10,10,10), 
                new Insets(14,14,14,14), Utilities.loadImage("com/jaspersoft/ireport/designer/widgets/pageborder.png") ));
        */
        setBorder(new ReportBorder());
        
        
        setBackground(Color.WHITE);
        setOpaque(true);
        setCheckClipping(true);
        updateBounds();
    }
    
    public void updateBounds()
    {
        JasperDesign jd = ((ReportObjectScene)getScene()).getJasperDesign();
        this.setPreferredSize(
                new Dimension( jd.getPageWidth()+20, ModelUtils.getDesignHeight(jd)+20) );
    }
    
    /*
    public Rectangle  calculateClientArea()
    {
        return new Rectangle(getJasperDesign().getPageWidth() + 20, getDesignHeight()+20 );
    }
    */
    
    public JasperDesign getJasperDesign()
    {
        return ((ReportObjectScene)this.getScene()).getJasperDesign();
    }
    
    

    @Override
    protected void paintWidget() {
        super.paintWidget();
        
        //Draw the bands...
        Graphics2D g = this.getGraphics();
        
        if (((ReportObjectScene)getScene()).isGridVisible())
        {
            paintGrid(g);
        }
        
        g.setColor(ReportObjectScene.DESIGN_LINE_COLOR);
        Stroke oldStroke = g.getStroke();
        //g.setStroke(new BasicStroke(0));

        double zoom = getScene().getZoomFactor();
        Stroke bs = Java2DUtils.getInvertedZoomedStroke(oldStroke, getScene().getZoomFactor());
        g.setStroke(bs);
        
        // LEFT MARGINE LINE
        JasperDesign jd = getJasperDesign();
        if (jd != null)
        {
            int dh = ModelUtils.getDesignHeight(jd);
            g.drawLine( jd.getLeftMargin(),0,
                        jd.getLeftMargin(),
                        dh);

            // RIGHT MARGIN LINE
            g.drawLine(jd.getPageWidth() - jd.getRightMargin(),0,
                       jd.getPageWidth() - jd.getRightMargin(),ModelUtils.getDesignHeight(jd));

            g.drawLine( 0, jd.getTopMargin(),
                        jd.getPageWidth(), jd.getTopMargin());

            g.drawLine( 0,
                    dh - jd.getBottomMargin(),
                    jd.getPageWidth(),
                    dh - jd.getBottomMargin());
            
            // Draw the columns....
            if (jd.getColumnCount() > 1)
            {
                int c_y0 = ModelUtils.getBandLocation(jd.getColumnHeader(), jd);
                int c_y1 = ModelUtils.getBandLocation(jd.getPageFooter(), jd);
                int c_x = jd.getLeftMargin();
                for (int i=1; i < jd.getColumnCount(); ++i)
                {
                    c_x += jd.getColumnWidth();
                    g.drawLine( c_x, c_y0,
                                c_x, c_y1);
                    
                    c_x += jd.getColumnSpacing();
                    g.drawLine( c_x, c_y0,
                                c_x, c_y1);
                    
                }
            }
            
            //g.setColor( Color.RED);
            //g.drawRect(0, 0, jd.getPageWidth()-4, dh-4);
            
            g.setFont( new Font( "Arial", Font.PLAIN, 20));
            
            int designHeight = jd.getTopMargin();
         
            g.setStroke(oldStroke);
            
            if (IReportManager.getPreferences().getBoolean( IReportManager.PROPERTY_SHOW_BAND_NAMES, true))
            {
                List<JRBand> bands = ModelUtils.getBands(jd);

                for (JRBand b : bands)
                {
                    designHeight += b.getHeight();
                    paintBand(g, jd, ModelUtils.nameOf(b, jd) , b, designHeight);
                }
            }
            
        }
        g.setStroke(oldStroke);
    }
    
    private void paintBand(Graphics2D g, JasperDesign jd, String title, JRBand b, int bandBottom)
    {
        if (b== null || b.getHeight() == 0) return;
        
        //g.drawLine( 0, bandBottom, jd.getPageWidth(), bandBottom);
        
        int txt_width = g.getFontMetrics().stringWidth(title)/2;
        int txt_height = g.getFontMetrics().getHeight()/2;
        txt_height -= g.getFontMetrics().getMaxDescent();
        
        
        
        Java2DUtils.setClip(g,
                    jd.getLeftMargin(),
                    bandBottom-b.getHeight(),
                    jd.getPageWidth() - jd.getRightMargin(),
                    b.getHeight() );
        
        Paint oldPaint = g.getPaint();
        g.setPaint(ReportObjectScene.GRID_LINE_COLOR);
        g.drawString( title,
                    (jd.getPageWidth()/2) - txt_width,
                    bandBottom - (b.getHeight()-txt_height)/2 ); 
        Java2DUtils.resetClip(g);
 
        g.setPaint(oldPaint);
    }
    
    
    protected void paintGrid(Graphics2D g) {
        Paint oldPaint = g.getPaint();
        g.setPaint( getGridTexture() );
        
        g.fill( getClientArea() );
        g.setPaint(oldPaint);
    }

    private TexturePaint getGridTexture()
    {
        if ( gridTexture == null || gridTexture.getImage().getWidth() != getGridSize() )
        {
                BufferedImage img = new BufferedImage( getGridSize(), getGridSize(), BufferedImage.TYPE_INT_RGB );
                Graphics2D g2 = img.createGraphics();
                g2.setColor(new Color(255,255,255,255));
                g2.fill( getClientArea() );
                g2.setColor( ReportObjectScene.GRID_LINE_COLOR );
                g2.setStroke( GRID_STROKE );
                g2.drawLine( getGridSize()-1, 0, getGridSize()-1, getGridSize()-1 );
                g2.drawLine( 0, getGridSize()-1, getGridSize()-1, getGridSize()-1 );
                gridTexture = new TexturePaint( img, new Rectangle(0,0, getGridSize(), getGridSize() ) );
        }
        return gridTexture;
    }


    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
   
    
}
