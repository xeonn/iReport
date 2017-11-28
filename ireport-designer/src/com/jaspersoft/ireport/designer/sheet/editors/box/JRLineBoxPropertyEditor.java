/*
 * ExpressionEditor.java
 * 
 * Created on 20-set-2007, 17.25.21
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors.box;

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
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPropertiesMap;
import org.openide.nodes.Node;


/** A property editor for String class.
* @author   Ian Formanek
* @version  1.00, 18 Sep, 1998
*/
public class JRLineBoxPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor
{
    public boolean isEditable(){
        return false;
    }
                
    @Override
    public String getAsText() {
        Object val = getValue();
        
        
        if (val == null) {
            return "";
        }
        if (val instanceof JRLineBox)
        {
            JRLineBox lineBox = (JRLineBox)val;
            return "[" + lineBox.getTopPadding() + ", " +
                    lineBox.getLeftPadding() + ", " +
                    lineBox.getBottomPadding() + ", " +
                    lineBox.getRightPadding() + "]";
        }
        return "";
    }
    
    @Override
    public boolean supportsCustomEditor () {
        return true;
    }
    
    @Override
    public java.awt.Component getCustomEditor () {
        Object val = getValue();
        return new JRLineBoxPropertyCustomEditor((JRLineBox)val, this, env); // NOI18N
    }

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

