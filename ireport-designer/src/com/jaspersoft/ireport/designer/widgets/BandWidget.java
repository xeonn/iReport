/*
 * BandBorderWidget.java
 * 
 * Created on Aug 28, 2007, 9:48:57 AM
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

import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.ReportObjectScene;
import com.jaspersoft.ireport.designer.ThreadUtils;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.widget.SeparatorWidget;

/**
 * @author Teodor Danciu (teodor@users.sourceforge.net)
 */
public class BandWidget extends SeparatorWidget implements PropertyChangeListener 
{
    //final private Stroke stroke = new BasicStroke(1.0f);
    final private JRBand band;

    public BandWidget(ReportObjectScene scene, JRBand band) 
    {
        super(scene, Orientation.HORIZONTAL);
        this.band = band;
        
        // We set a border to improve the sensible area....
//        setBorder( BorderFactory.createEmptyBorder(0, 3) );
//        setCursor( Cursor.getPredefinedCursor( Cursor.S_RESIZE_CURSOR) );
//        setForeground(ReportObjectScene.DESIGN_LINE_COLOR);
        
        // Add a listener to the band changes...
        if (band instanceof JRDesignBand)
        {
            ((JRDesignBand)band).getEventSupport().addPropertyChangeListener(this);
        }
        
        setToolTipText(ModelUtils.nameOf(band, scene.getJasperDesign()));
        
        updateBounds();
    }
    
    public JRBand getBand() {
        return band;
    }

    public void updateBounds()
    {
        JasperDesign jasperDesign = ((ReportObjectScene)this.getScene()).getJasperDesign();
        setPreferredLocation(new Point( 0, ModelUtils.getBandLocation(band,jasperDesign)) );
        setPreferredBounds(new Rectangle(0, 0, jasperDesign.getPageWidth(), band.getHeight()));
    }
    
    
    /**
     * Paints the separator widget.
     */
    @Override
    protected void paintWidget() 
    {
//        Graphics2D gr = getGraphics();
//        gr.setColor( new Color((int)(255*Math.random()), (int)(255*Math.random()), (int)(255*Math.random())) );
//        Rectangle bounds = getBounds ();
//        Insets insets = getBorder ().getInsets ();
//        
//        gr.fillRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        Runnable r = null;
        
        if (evt.getPropertyName() == null) return;
        if (evt.getPropertyName().equals( JRDesignBand.PROPERTY_HEIGHT))
        {
            r = new Runnable(){  
                 public void run()  {
                    ((ReportObjectScene)getScene()).refreshDocument();
                }};
        }
//      The children update is performed by the band separators widget!!!!  
//        else if (evt.getPropertyName().equals( JRDesignBand.PROPERTY_CHILDREN))
//        {
//            r = new Runnable(){  
//                 public void run()  {
//                    ((ReportObjectScene)getScene()).refreshElementGroup( (JRDesignBand)band);
//                }};
//        }
         
        if (r != null)
        {
             ThreadUtils.invokeInAWTThread(r);
        }
    }
}
