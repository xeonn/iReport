/*
 * MethodsListCellRenderer.java
 * 
 * Created on Oct 11, 2007, 10:37:31 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.editor;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author gtoffoli
 */
public class MethodsListCellRenderer extends JTextPane implements ListCellRenderer {
        private Color selectionBackground;
        private Color background;
        
        // Create a style object and then set the style attributes
        Style methodStyle = null;
        Style returnTypeStyle = null;

        public MethodsListCellRenderer(JList list) {
            super();
            selectionBackground = list.getSelectionBackground();
            background = list.getBackground();
            StyledDocument doc = new DefaultStyledDocument();
            this.setDocument( doc );
            methodStyle = doc.addStyle("methodStyle", null);
            StyleConstants.setBold(methodStyle, true);
            returnTypeStyle = doc.addStyle("returnType", null);
            StyleConstants.setForeground(returnTypeStyle, Color.gray);
        }
        public Component getListCellRendererComponent(JList list, Object object,
                int index, boolean isSelected, boolean cellHasFocus) {
            
            String s = (String)object;
            this.setText("");
            StyledDocument doc = (StyledDocument)this.getDocument();
            try {
            doc.insertString(doc.getLength(), s.substring(0, s.indexOf("(")), methodStyle);
            doc.insertString(doc.getLength(), s.substring(s.indexOf("("), s.lastIndexOf(")")+1), null);
            doc.insertString(doc.getLength(), s.substring(s.lastIndexOf(")")+1), returnTypeStyle);
            } catch (Exception ex){}
            setBackground(isSelected ? selectionBackground : background);
            return this;
        }
}

