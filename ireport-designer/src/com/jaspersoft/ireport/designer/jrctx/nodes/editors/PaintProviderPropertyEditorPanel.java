/*
 * ExpressionPropertyEditor.java
 * 
 * Created on Oct 12, 2007, 11:38:17 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import net.sf.jasperreports.chartthemes.simple.PaintProvider;
import org.openide.explorer.propertysheet.PropertyEnv;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JasperDesignViewer.java 1988 2007-12-04 16:17:57Z teodord $
 */
public class PaintProviderPropertyEditorPanel extends PaintProviderEditorPanel implements PropertyChangeListener 
{
    private boolean oneline=false;
    private String instructions = null;

    private PropertyEnv env;
    private PropertyEditor editor;

    public PaintProviderPropertyEditorPanel()
    {
        super();
    }


    //enh 29294, provide one line editor on request
    /** Create a StringCustomEditor.
     * @param value the initial value for the string
     * @param editable whether to show the editor in read only or read-write mode (NOT USED)
     * @param oneline whether the text component should be a single-line or multi-line component (NOT USED)
     * @param instructions any instructions that should be displayed (NOT USED)
     */
    PaintProviderPropertyEditorPanel (PaintProvider value, boolean oneline, String instructions, PropertyEditor editor, PropertyEnv env) 
    {
        super();
        
        this.oneline = oneline;
        this.instructions = instructions;
        this.env = env;
        this.editor = editor;
        
        this.env.setState(PropertyEnv.STATE_NEEDS_VALIDATION);
        this.env.addPropertyChangeListener(this);
//        if (value != null)
//        {
            this.setPaintProvider(value);
//        }
    }
    
    /**
    * @return Returns the property value that is result of the CustomPropertyEditor.
    * @exception InvalidStateException when the custom property editor does not represent valid property value
    *            (and thus it should not be set)
    */
    private Object getPropertyValue () throws IllegalStateException {
        return getPaintProvider();
    }



    public void propertyChange(PropertyChangeEvent evt) {
        if (PropertyEnv.PROP_STATE.equals(evt.getPropertyName()) && evt.getNewValue() == PropertyEnv.STATE_VALID) {
            editor.setValue(getPropertyValue());
        }
    }

}

