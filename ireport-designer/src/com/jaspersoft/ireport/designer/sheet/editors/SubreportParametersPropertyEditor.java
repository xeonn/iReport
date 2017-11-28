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
import java.util.Map;
import org.openide.nodes.Node;


/** A property editor for String class.
* @author   Giulio Toffoli
*/
public class SubreportParametersPropertyEditor extends PropertyEditorSupport implements ExPropertyEditor
{
    public boolean isEditable(){
        return false;
    }
                
    @Override
    public String getAsText() {
        Object val = getValue();
        if (val == null) {
            return "No parameters defined";
        }
        if (val instanceof Map)
        {
            int len = ((Map)val).size();
            switch (len)
            {
                case 0: return "No parameters defined";
                case 1: return "One parameter defined";
                default: return len + " properties defined";
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
        return new SubreportParametersPropertyCustomEditor((Map)val, this, env); // NOI18N
    }

    private PropertyEnv env;

    // bugfix# 9219 added attachEnv() method checking if the user canWrite in text box 
    public void attachEnv(PropertyEnv env) {

        FeatureDescriptor desc = env.getFeatureDescriptor();
        if (desc instanceof Node.Property){
            Node.Property prop = (Node.Property)desc;
        }
        this.env = env;
    }
}

