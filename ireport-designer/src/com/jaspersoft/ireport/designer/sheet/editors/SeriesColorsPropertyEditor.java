/*
 * ExpressionEditor.java
 * 
 * Created on 20-set-2007, 17.25.21
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
import org.openide.nodes.Node;


/** A property editor for String class.
* @author   Ian Formanek
* @version  1.00, 18 Sep, 1998
*/
public class SeriesColorsPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor
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
        //super.paintValue(gfx, box);
        SortedSet colors = (SortedSet)getValue();
        if (colors == null) super.paintValue(gfx, box);
        else
        {
            //gfx.clearRect(box.x, box.y, box.width, box.height);
            int cols = colors.size();
            int bw = 10;
            
            while (cols > 0 && ((bw+2)*cols) > box.width)
            {
                if (bw > 4) bw--;
                else
                {
                    cols--;
                }
            }
            
            int x = box.x+1;
            Iterator it = colors.iterator();
            for (int i=0; i< cols && it.hasNext(); ++i)
            {
                JRChartPlot.JRSeriesColor color = (JRChartPlot.JRSeriesColor)it.next();
                gfx.setColor(color.getColor());
                gfx.fillRect(x, box.y + 3, bw, Math.min(box.height-6, 10));
                gfx.setColor(Color.BLACK);
                gfx.drawRect(x, box.y + 3, bw, Math.min(box.height-6, 10));
                
                x += bw+2;
            }
        }
        
        
        
    }
    
    /** sets new value */
    @Override
    public String getAsText() {
        return "test";
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
        SortedSet val = (SortedSet)getValue();
        return new SeriesColorsPropertyCustomEditor(val, false, null, this, env); // NOI18N
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

