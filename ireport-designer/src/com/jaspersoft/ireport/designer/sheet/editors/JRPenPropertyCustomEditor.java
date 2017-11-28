/*
 * ExpressionPropertyEditor.java
 * 
 * Created on Oct 12, 2007, 11:38:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.editors;

import com.jaspersoft.ireport.designer.sheet.editors.box.PenEditorPanel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import net.sf.jasperreports.engine.JRPen;
import org.openide.explorer.propertysheet.PropertyEnv;

/**
 *
 * @author gtoffoli
 */
public class JRPenPropertyCustomEditor extends PenEditorPanel implements PropertyChangeListener {

    public JRPenPropertyCustomEditor()
    {
        super();
    }
    
    boolean oneline=false;
    String instructions = null;

    private PropertyEnv env;

    private PropertyEditor editor;


    //enh 29294, provide one line editor on request
    /** Create a StringCustomEditor.
     * @param value the initial value for the string
     * @param editable whether to show the editor in read only or read-write mode (NOT USED)
     * @param oneline whether the text component should be a single-line or multi-line component (NOT USED)
     * @param instructions any instructions that should be displayed (NOT USED)
     */
    JRPenPropertyCustomEditor (JRPen value, boolean oneline, String instructions, PropertyEditor editor, PropertyEnv env) {
        
        super();
        
        this.oneline = oneline;
        this.instructions = instructions;
        this.env = env;
        this.editor = editor;
        
        this.env.setState(PropertyEnv.STATE_NEEDS_VALIDATION);
        this.env.addPropertyChangeListener(this);
        if (value != null)
        {
            this.setPen(value);
        }
    }
    
    /**
    * @return Returns the property value that is result of the CustomPropertyEditor.
    * @exception InvalidStateException when the custom property editor does not represent valid property value
    *            (and thus it should not be set)
    */
    private Object getPropertyValue () throws IllegalStateException {
        return getPen();
    }



    public void propertyChange(PropertyChangeEvent evt) {
        if (PropertyEnv.PROP_STATE.equals(evt.getPropertyName()) && evt.getNewValue() == PropertyEnv.STATE_VALID) {
            editor.setValue(getPropertyValue());
        }
    }

}

