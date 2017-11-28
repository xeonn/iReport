/*
 * ComboBoxPropertyEditor.java
 * 
 * Created on Sep 20, 2007, 12:59:52 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors;

import com.jaspersoft.ireport.designer.sheet.*;
import java.beans.FeatureDescriptor;
import java.beans.PropertyEditorSupport;
import java.util.List;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.nodes.Node;


public class ComboBoxPropertyEditor extends PropertyEditorSupport
        implements ExPropertyEditor { //, InplaceEditor.Factory
    
    private java.util.List tagValues = null;
    private boolean editable = true;
            
    /** Creates a new instance of DataPropertyEditor 
     *  @param boolean editable
     *  @param java.util.List tagValues
     */
    public ComboBoxPropertyEditor(boolean editable, java.util.List tagValues) {
        
        super();
        this.tagValues = tagValues;
        this.editable = editable;
    }
    
    @java.lang.Override
    public String getAsText() {
        Object key = getValue();
        
        // Look for the right tag...
        Tag t = Tag.findTagByValue(key, tagValues);
        if (t == null) {
            return ""+key;
        }
        return t.toString();
    }
    
    @java.lang.Override
    public void setAsText(String s) {
        Tag t = Tag.findTagByName(s, tagValues);
        setValue( t != null ? t.getValue() : s);
    }
    
    private String instructions=null;
    private boolean oneline=false;
    private boolean customEd=true;
    private PropertyEnv env;
    
    
    public void attachEnv(PropertyEnv env) {
        this.env = env;       
        FeatureDescriptor desc = env.getFeatureDescriptor();
        if (desc instanceof Node.Property){
            Node.Property prop = (Node.Property)desc;
            instructions = (String) prop.getValue ("instructions"); //NOI18N
            oneline = Boolean.TRUE.equals (prop.getValue ("oneline")); //NOI18N
            customEd = !Boolean.TRUE.equals (prop.getValue("suppressCustomEditor")); //NOI18N
        }
    }

    @Override
    public String[] getTags() {
        
        String[] s = new String[tagValues.size()];
        for (int i=0; i<tagValues.size(); ++i)
        {
            s[i] = ""+tagValues.get(i);
        }
        return s;
    }
    
    public void setTagValues(List newValues)
    {
        this.tagValues = newValues;
        this.firePropertyChange();
    }


    @Override
    public boolean supportsCustomEditor () {
        return customEd;
    }

    
    public java.awt.Component getCustomEditor () {
        Object val = getValue();
        String s = ""; // NOI18N
        if (val != null) {
            s = val instanceof String ? (String) val : val.toString();
        }
        return new StringCustomEditor(s, editable, oneline, instructions, this, env); // NOI18N
    }
    
    
    
}

