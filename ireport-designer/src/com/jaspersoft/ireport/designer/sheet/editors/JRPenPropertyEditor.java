/*
 * ExpressionEditor.java
 * 
 * Created on 20-set-2007, 17.25.21
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors;

import com.jaspersoft.ireport.designer.sheet.editors.box.BoxBorderSelectionPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.beans.FeatureDescriptor;
import java.beans.PropertyEditorSupport;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
import java.beans.PropertyEditorSupport;

// bugfix# 9219 for attachEnv() method
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.SortedSet;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRPen;
import org.openide.nodes.Node;


/** A property editor for String class.
* @author   Ian Formanek
* @version  1.00, 18 Sep, 1998
*/
public class JRPenPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor
{
    public boolean isEditable(){
        return false;
    }

    @Override
    public boolean isPaintable() {
        return true;
    }

    @Override
    public void paintValue(Graphics gfx, Rectangle box) {
        
        JRPen pen = (JRPen)getValue();
        if (pen == null) super.paintValue(gfx, box);
        else
        {
            gfx.clearRect(box.x, box.y, box.width, box.height);
            gfx.setColor( pen.getLineColor() == null ? Color.BLACK : pen.getLineColor());
            Stroke s = BoxBorderSelectionPanel.createStroke(pen);
            if (s != null)
            {
                ((Graphics2D)gfx).setStroke(s);
                gfx.drawLine(box.x+4, box.y + box.height/2, box.x+box.width-4, box.y + box.height/2);
            }
        }
        
    }
    
    /** sets new value */
    @Override
    public String getAsText() {
        return "";
    }
    
    /** sets new value */
    @Override
    public void setAsText(String s) {
        return;
    }

    @Override
    public boolean supportsCustomEditor () {
        return customEd;
    }

    @Override
    public java.awt.Component getCustomEditor () {
        JRPen val = (JRPen)getValue();
        return new JRPenPropertyCustomEditor(val, false, null, this, env); // NOI18N
    }

    //private String instructions=null;
    //private boolean oneline=false;
    private boolean customEd=true;
    private PropertyEnv env;

    // bugfix# 9219 added attachEnv() method checking if the user canWrite in text box 
    public void attachEnv(PropertyEnv env) {

        FeatureDescriptor desc = env.getFeatureDescriptor();
        if (desc instanceof Node.Property){
            Node.Property prop = (Node.Property)desc;
            //enh 29294 - support one-line editor & suppression of custom
            //editor
            //instructions = (String) prop.getValue ("instructions"); //NOI18N
            //oneline = Boolean.TRUE.equals (prop.getValue ("oneline")); //NOI18N
            customEd = !Boolean.TRUE.equals (prop.getValue ("suppressCustomEditor")); //NOI18N
        }
        this.env = env;
        
        

    }
}

