/*
 * ExpressionEditorPane.java
 * 
 * Created on 23-ott-2007, 21.15.31
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.EditorKit;
import org.openide.text.CloneableEditorSupport;

/**
 *
 * @author gtoffoli
 */
public class ExpressionEditorPane extends javax.swing.JEditorPane {

    private ExpressionContext expressionContext = null;

    public ExpressionEditorPane()
    {
        this(null);
    }
    
    public ExpressionEditorPane(ExpressionContext context)
    {
        super();
        this.expressionContext = context;
        EditorKit kit = CloneableEditorSupport.getEditorKit("text/jrxml-expression");
        setEditorKit(kit);
        
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                ExpressionContext.setGlobalContext(getExpressionContext());
                ExpressionContext.activeEditor = ExpressionEditorPane.this;
            }

            public void focusLost(FocusEvent e) {
            }
        });
    }
    
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public void setExpressionContext(ExpressionContext expressionContext) {
        this.expressionContext = expressionContext;
    }
}
