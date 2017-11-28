/*
 * ExpressionEditor.java
 * 
 * Created on 20-set-2007, 17.25.21
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors;

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
import net.sf.jasperreports.engine.design.JRDesignFont;
import org.openide.nodes.Node;


/** A property editor for String class.
* @author   Ian Formanek
* @version  1.00, 18 Sep, 1998
*/
public class JRFontPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor
{
    private static boolean useRaw = Boolean.getBoolean("netbeans.stringEditor.useRawCharacters");
    // bugfix# 9219 added editable field and isEditable() "getter" to be used in StringCustomEditor
    private boolean editable=true;   
    /** gets information if the text in editor should be editable or not */
    
    public boolean isEditable(){
        return false;
    }
                
    /** sets new value */
    public void setAsText(String s) {
            return;
    }

    @Override
    public String getAsText() {
        JRDesignFont font = (JRDesignFont)getValue();
        if (font != null)
        {
            return font.getFontName() + " " + font.getFontSize();
        }
        else
        {
            return "<default>";
        }
    }

    public boolean supportsCustomEditor () {
        return customEd;
    }

    public java.awt.Component getCustomEditor () {
        Object val = getValue();
        return new JRFontPropertyCustomEditor((JRDesignFont)val, isEditable(), false, null, this, env); // NOI18N
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
            editable = prop.canWrite();
            //enh 29294 - support one-line editor & suppression of custom
            //editor
            //instructions = (String) prop.getValue ("instructions"); //NOI18N
            customEd = !Boolean.TRUE.equals (prop.getValue ("suppressCustomEditor")); //NOI18N
        }
        this.env = env;
    }
}

