/*
 * ReportElementWidget.java
 * 
 * Created on Aug 29, 2007, 2:18:27 PM
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

import com.jaspersoft.ireport.designer.AbstractReportObjectScene;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.borders.SimpleLineBorder;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.ImageIcon;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBoxContainer;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.base.JRBaseLine;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBasePen;
import net.sf.jasperreports.engine.base.JRBaseStaticText;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGraphicElement;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.draw.DrawVisitor;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author gtoffoli
 */
public class JRDesignElementWidget extends Widget implements PropertyChangeListener {

    private SelectionWidget selectionWidget = null;
    private javax.swing.ImageIcon crosstabImage = new ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/crosstab-32.png"));
    private javax.swing.ImageIcon subreportImage = new ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/subreport-32.png"));
    
    public JRDesignElement getElement() {
        return element;
    }

    /**
     * This flag is used to avoid the processing of change events....
     */
    private boolean changing = false;  
    
    public void setElement(JRDesignElement element) {
        this.element = element;
    }
    JRDesignElement element = null;
    
    public JRDesignElementWidget(AbstractReportObjectScene scene, JRDesignElement element) {
        super(scene);
        this.element = element;
        setBorder(new SimpleLineBorder(this));
        updateBounds();
        selectionWidget = new SelectionWidget(scene, this);
        notifyStateChanged(null, ObjectState.createNormal());
        selectionWidget.addDependency(new Dependency() {

            public void revalidateDependency() {
                //setPreferredLocation(selectionWidget.getPreferredLocation() );
                //setPreferredBounds(selectionWidget.getPreferredBounds());
                //System.out.println("Revaludated");
            }
        });
        
        element.getEventSupport().addPropertyChangeListener(this);
        if (element instanceof JRDesignGraphicElement)
        {
            JRDesignGraphicElement gele = (JRDesignGraphicElement)element;
            ((JRBasePen)gele.getLinePen()).getEventSupport().addPropertyChangeListener(this);
        }
        
        if (element instanceof JRBoxContainer)
        {
            JRBoxContainer boxcontainer = (JRBoxContainer)element;
            JRBaseLineBox baseBox = (JRBaseLineBox)boxcontainer.getLineBox();
            baseBox.getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getTopPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getBottomPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getLeftPen()).getEventSupport().addPropertyChangeListener(this);
            ((JRBasePen)baseBox.getRightPen()).getEventSupport().addPropertyChangeListener(this);
        }
    }
    
    
    @Override
    protected void notifyStateChanged(ObjectState previousState, ObjectState state)
    {
        selectionWidget.setVisible( state.isSelected() );
        if (state.isSelected())
        {
            selectionWidget.updateBounds();
        }
         //setBorder(  ? getSelectedBorder() : getNormalBorder());
         //showHandles(state.isSelected());
    
         
    }
    
    public void updateBounds()
    {
        JasperDesign jd = ((AbstractReportObjectScene)this.getScene()).getJasperDesign();
        
        //Point p = 
        //JRBand band = ModelUtils.bandOfElement(element, jd);
        //int bandYLocation = ModelUtils.getBandLocation(band, jd);
        //setPreferredLocation(new Point( jd.getLeftMargin() + element.getX()-5, bandYLocation + element.getY() -5) );
        //setPreferredBounds(new Rectangle(0,0,element.getWidth()+10, element.getHeight()+10));
        
        Point location = convertModelToLocalLocation(new Point( element.getX(), element.getY()) );
        location.x -= getBorder().getInsets().left;
        location.y -= getBorder().getInsets().top;
        setPreferredLocation( location );
        setPreferredBounds(new Rectangle(0,0,
                element.getWidth() + getBorder().getInsets().left + getBorder().getInsets().right,
                element.getHeight() + getBorder().getInsets().top + getBorder().getInsets().bottom));
    }

    public SelectionWidget getSelectionWidget() {
        return selectionWidget;
    }

    public void setSelectionWidget(SelectionWidget selectionWidget) {
        this.selectionWidget = selectionWidget;
    }
    /*
    private void showHandles(boolean b)
    {
        List<Widget> children = getChildren();
        for (Widget child : children)
        {
            if (child instanceof ResizeHandleWidget)
            {
                child.setVisible(b);
            }
        }
        getLayout().layout(this);
        getScene().validate();
    }
     */

    /*
    @Override
    protected Cursor getCursorAt(Point localLocation) {
        Rectangle bounds = getBounds ();
        Insets insets = getBorder().getInsets();
        if (new Rectangle (bounds.x, bounds.y, insets.left, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + (bounds.width/2) - insets.top/2,  bounds.y , insets.top, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x+bounds.width-insets.right, bounds.y, insets.right, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x, bounds.y+bounds.height-insets.bottom, insets.left, insets.bottom).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + (bounds.width/2) - insets.bottom/2, bounds.y+bounds.height-insets.bottom, insets.bottom, insets.bottom).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + bounds.width-insets.right, bounds.y+bounds.height-insets.bottom, insets.left, insets.top).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x, bounds.y + (bounds.height/2) - insets.left/2, insets.left, insets.left).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
        else if (new Rectangle (bounds.x + bounds.width - insets.right, bounds.y + (bounds.height/2) - insets.right/2, insets.right, insets.right).contains (localLocation))
            return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
        return super.getCursorAt(localLocation);
    }
    */
    
    
    /**
     *  Convert the element coordinates from local to model...
     *  The coordinated are always the preferred location plus a base location that dependes
     *  by the parent of this element.
     *  The parent can be another element, a band or a cell.
     */
    public Point convertLocalToModelLocation(Point p)
    {
        return convertLocalToModelLocation( p, true);
    }
    
    /**
     *  Convert the element coordinates from local to model...
     *  The coordinated are always the preferred location plus a base location that dependes
     *  by the parent of this element.
     *  The parent can be another element, a band or a cell.
     * 
     *  If out is set to true, from this point is REMOVED the widget border.
     * 
     */
    public Point convertLocalToModelLocation(Point p, boolean out)
    {
        JasperDesign jd = ((AbstractReportObjectScene)getScene()).getJasperDesign();
        Point base = ModelUtils.getParentLocation(jd, getElement());
        
        if (out)
        {
            base.x -= getBorder().getInsets().left;
            base.y -= getBorder().getInsets().top;
        }
        return new Point(p.x - base.x, p.y - base.y);
    }
    
    
    /**
     *  Convert the element coordinates from local to model...
     *  The coordinated are always the preferred location plus a base location that dependes
     *  by the parent of this element.
     *  The parent can be another element, a band or a cell.
     * 
     */
    public Point convertModelToLocalLocation(Point p)
    {
        JasperDesign jd = ((AbstractReportObjectScene)getScene()).getJasperDesign();
        Point base = ModelUtils.getParentLocation(jd, getElement());
        
        // I need to discover the first logical parent of this element
        return new Point(base.x + p.x, base.y + p.y);
    }
    
    @Override
    protected void paintWidget() {
        
        super.paintWidget();
        Graphics2D gr = getScene().getGraphics();
        
        //Java2DUtils.setClip(gr,getClientArea());
        // Move the gfx 10 pixel ahead...
        Rectangle r = getPreferredBounds();
        
        long t = new Date().getTime();
        //if (getElement() instanceof JRDesignImage) return;
        
        AffineTransform af = gr.getTransform();
        AffineTransform new_af = (AffineTransform) af.clone();
        AffineTransform translate = AffineTransform.getTranslateInstance(
                getBorder().getInsets().left + r.x,
                getBorder().getInsets().top + r.y);
        new_af.concatenate(translate);
        gr.setTransform(new_af);
        
        JasperDesign jd = ((AbstractReportObjectScene)this.getScene()).getJasperDesign();
        JRDesignElement e = this.getElement();
        DrawVisitor dv = ((AbstractReportObjectScene)this.getScene()).getDrawVisitor();
        if (dv == null) return;
        
        if (e instanceof JRDesignCrosstab)
        {
            Composite oldComposite = gr.getComposite();
            gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); 
            try {
                e.visit( dv );
            } catch (Exception ex){}
            gr.setComposite(oldComposite);
            Shape oldClip = gr.getClip();
            Shape rect = new Rectangle2D.Float(0,0,element.getWidth(), element.getHeight());
            
            gr.clip(rect);
            gr.drawImage(crosstabImage.getImage(), 4, 4, null);
            gr.setClip(oldClip);
        }
        else if (e instanceof JRDesignSubreport)
        {
            Composite oldComposite = gr.getComposite();
            gr.fillRect(0, 0, element.getWidth(), element.getHeight());
            gr.setComposite(oldComposite);
            Shape oldClip = gr.getClip();
            Shape rect = new Rectangle2D.Float(0,0,element.getWidth(), element.getHeight());
            gr.clip(rect);
            gr.drawImage(subreportImage.getImage(), 4, 4, null);
            gr.setClip(oldClip);
        }
        else
        {
            dv.setGraphics2D(gr);
            try {
                e.visit( dv );
            } catch (Exception ex){
            
                System.err.println("iReport - Element rendering exception " + getElement() + " " + ex.getMessage());
                //ex.printStackTrace();
            }
        }
        gr.setTransform(af);
        //Java2DUtils.resetClip(gr);
        
        //System.out.println("Painted : " + (t - new Date().getTime()) + "   " + getElement());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        
        if (isChanging()) return;
        String propertyName = evt.getPropertyName();
        if (propertyName == null) return;
        if (propertyName.equals( JRDesignElement.PROPERTY_HEIGHT) ||
            propertyName.equals( JRDesignElement.PROPERTY_WIDTH) ||
            propertyName.equals( JRDesignElement.PROPERTY_ELEMENT_GROUP) ||
            propertyName.equals( JRDesignElement.PROPERTY_X) ||
            propertyName.equals( JRDesignElement.PROPERTY_Y) ||
            propertyName.equals( JRBaseStyle.PROPERTY_BACKCOLOR) ||
            propertyName.equals( JRBaseStyle.PROPERTY_FORECOLOR) ||
            propertyName.equals( JRDesignElement.PROPERTY_PARENT_STYLE) ||
            propertyName.equals( JRDesignElement.PROPERTY_PARENT_STYLE_NAME_REFERENCE) ||
            propertyName.equals( JRBaseStyle.PROPERTY_MODE ) ||
            //FIXME propertyName.equals( JRDesignGraphicElement.PROPERTY_PEN) ||
            propertyName.equals( JRBaseStyle.PROPERTY_FILL) ||
            propertyName.equals( JRBaseLine.PROPERTY_DIRECTION) ||
            propertyName.equals( JRBaseStyle.PROPERTY_RADIUS) ||
            propertyName.equals( JRBaseStyle.PROPERTY_HORIZONTAL_ALIGNMENT) ||
            propertyName.equals( JRBaseStyle.PROPERTY_VERTICAL_ALIGNMENT) ||
            propertyName.equals( JRBaseStyle.PROPERTY_SCALE_IMAGE) ||
//FIXME
//            propertyName.equals( JRBaseStyle.PROPERTY_BORDER) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_BORDER_COLOR) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_PADDING) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_TOP_BORDER) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_TOP_BORDER_COLOR) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_TOP_PADDING) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_LEFT_BORDER) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_LEFT_BORDER_COLOR) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_LEFT_PADDING) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_RIGHT_BORDER) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_RIGHT_BORDER_COLOR) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_RIGHT_PADDING) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_BOTTOM_BORDER) ||
//            propertyName.equals( JRBaseStyle.PROPERTY_BOTTOM_BORDER_COLOR) ||
            propertyName.equals( JRBaseStyle.PROPERTY_FONT_NAME) ||
            propertyName.equals( JRBaseStyle.PROPERTY_FONT_SIZE) ||
            propertyName.equals( JRBaseStyle.PROPERTY_BOLD) ||
            propertyName.equals( JRBaseStyle.PROPERTY_ITALIC) ||
            propertyName.equals( JRBaseStyle.PROPERTY_IS_STYLED_TEXT) ||
            propertyName.equals( JRBaseStyle.PROPERTY_UNDERLINE) ||
            propertyName.equals( JRBaseStyle.PROPERTY_STRIKE_THROUGH) ||
            propertyName.equals( JRBaseStyle.PROPERTY_ROTATION) ||
            propertyName.equals( JRBaseStyle.PROPERTY_LINE_SPACING) ||
            propertyName.equals( JRBaseStaticText.PROPERTY_TEXT) ||
            propertyName.equals( JRDesignTextField.PROPERTY_EXPRESSION) ||
            propertyName.equals("pen") ||           // Special property fired by the property sheet
            propertyName.equals("linebox") ||       // Special property fired by the property sheet
            propertyName.equals(JRBasePen.PROPERTY_LINE_COLOR) ||
            propertyName.equals(JRBasePen.PROPERTY_LINE_STYLE) ||
            propertyName.equals(JRBasePen.PROPERTY_LINE_WIDTH) ||
            propertyName.equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING) ||
            propertyName.equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING) ||
            propertyName.equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING) ||
            propertyName.equals(JRBaseLineBox.PROPERTY_BOTTOM_PADDING)
            )
        {        
            updateBounds();
            this.repaint(); 
            this.getSelectionWidget().updateBounds();
            getScene().validate();
        }
        
        if (propertyName.equals( JRDesignFrame.PROPERTY_CHILDREN))
        {
            if (getElement() instanceof JRElementGroup)
            {
                ((AbstractReportObjectScene)getScene()).refreshElementGroup( (JRElementGroup)getElement() );
            }
        }

    }

    /**
     * This flag is used to avoid the processing of change events....
     */
    public synchronized boolean isChanging() {
        return changing;
    }

    /**
     * This flag is used to avoid the processing of change events....
     * @return the old changing status.
     */
    public synchronized boolean setChanging(boolean changing) {
        boolean b = this.changing;
        this.changing = changing;
        return b;
    }
}
