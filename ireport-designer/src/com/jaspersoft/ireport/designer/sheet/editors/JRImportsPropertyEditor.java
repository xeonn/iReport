/*
 * ExpressionEditor.java
 * 
 * Created on 20-set-2007, 17.25.21
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors;

import com.jaspersoft.ireport.locale.I18n;

/**
 *
 * @author gtoffoli
 */
import java.beans.PropertyEditorSupport;

// bugfix# 9219 for attachEnv() method
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import java.beans.FeatureDescriptor;
import org.openide.nodes.Node;


/** A property editor for String class.
* @author   Ian Formanek
* @version  1.00, 18 Sep, 1998
*/
public class JRImportsPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor
{
    public boolean isEditable(){
        return false;
    }
                
    @Override
    public String getAsText() {
        Object val = getValue();
        if (val == null) {
            return I18n.getString("JRImportsPropertyEditor.Property.NoImportsSet");
        }
        if (val.getClass().isArray() && val.getClass().getComponentType().equals(String.class))
        {
            int len = ((String[])val).length;
            switch (len)
            {
                case 0: return I18n.getString("JRImportsPropertyEditor.Property.NoImportsSet");
                case 1: return I18n.getString("JRImportsPropertyEditor.Property.OneImportSet");
                default: return I18n.getString("JRImportsPropertyEditor.Property.ImportsSet", len);
            }   
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
        return new JRImportsPropertyCustomEditor(val, this, env); // NOI18N
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

