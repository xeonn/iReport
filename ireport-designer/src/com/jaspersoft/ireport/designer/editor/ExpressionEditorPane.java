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
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.event.HyperlinkListener;
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

    public void removeHyperlinkEditorKitListeners()
    {
        KeyListener[] hls = this.getListeners(KeyListener.class);
        for (int i=0; i<hls.length; ++i)
        {
            if (hls[i].getClass().getName().equals("org.netbeans.modules.languages.features.HyperlinkListener"))
            {

                this.removeKeyListener(hls[i]);
            }
        }

        MouseMotionListener[] hls1 = this.getListeners(MouseMotionListener.class);
        for (int i=0; i<hls1.length; ++i)
        {
            if (hls1[i].getClass().getName().equals("org.netbeans.modules.languages.features.HyperlinkListener")) this.removeMouseMotionListener(hls1[i]);
        }

        MouseListener[] hls2 = this.getListeners(MouseListener.class);
        for (int i=0; i<hls2.length; ++i)
        {
            if (hls2[i].getClass().getName().equals("org.netbeans.modules.languages.features.HyperlinkListener")) this.removeMouseListener(hls2[i]);
        }

    }

    public ExpressionEditorPane(ExpressionContext context)
    {
        super();
        this.expressionContext = context;
        EditorKit kit = CloneableEditorSupport.getEditorKit("text/jrxml-expression");
        setEditorKit(kit);
        // List all listeners...
        removeHyperlinkEditorKitListeners();


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
