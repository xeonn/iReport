
/*
 * MarkerPropertyPanel.java
 *
 * Created on Nov 2, 2012, 11:00:50 AM
 */

package com.jaspersoft.ireport.components.map;

import com.jaspersoft.ireport.designer.editor.ExpressionEditorArea;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import net.sf.jasperreports.components.map.StandardMarkerProperty;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 *
 * @author gtoffoli
 */


public class MarkerPropertyPanel extends javax.swing.JPanel {

    private boolean init = false;
    private StandardMarkerProperty markerProperty = new StandardMarkerProperty();
    private ExpressionEditorArea jrExpressionArea = new ExpressionEditorArea();
    
    /** Creates new form MarkerPropertyPanel */
    public MarkerPropertyPanel() {
        initComponents();
        
        this.jrExpressionArea.getExpressionEditorPane().getDocument().addDocumentListener( new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                // As for documentation, plain text components do not fire these events
            }
            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionAreaTextChanged();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionAreaTextChanged();
            }
        });
        
        this.jTextArea1.getDocument().addDocumentListener( new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                if (evt.getLength() == 0) return;
                jTextAreaTextChanged();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                jTextAreaTextChanged();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                jTextAreaTextChanged();
            }
        });
    }
    
    
     public void jRTextExpressionAreaTextChanged() {
        if (this.isInit()) return;
        if (markerProperty != null)
        {
            JRDesignExpression exp = null;
            if (jrExpressionArea.getText().trim().length() > 0)
            {
                exp = new JRDesignExpression();
                exp.setText(jrExpressionArea.getText());
            }

            markerProperty.setValueExpression(exp);
        }
    }
     
    public void jTextAreaTextChanged() {
        if (this.isInit()) return;
        if (markerProperty != null)
        {
            markerProperty.setValueExpression(null);
            markerProperty.setValue( jTextArea1.getText() );
        }
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MarkerPropertyPanel.class, "MarkerPropertyPanel.border.title"))); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(MarkerPropertyPanel.class, "MarkerPropertyPanel.jCheckBox1.text")); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jCheckBox1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (isInit()) return;
        
        if (jCheckBox1.isSelected())
        {
            markerProperty.setValue(null);
            markerProperty.setValueExpression(Misc.createExpression(null, ""));
        }
        else
        {
            markerProperty.setValueExpression(null);
            markerProperty.setValue("");
        }
        
        updateEditorComponent();
       
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the markerProperty
     */
    public StandardMarkerProperty getMarkerProperty() {
        
        
        
        return markerProperty;
    }

    /**
     * @param markerProperty the markerProperty to set
     */
    public void setMarkerProperty(StandardMarkerProperty markerProperty) {

        
        setInit(true);
        this.markerProperty = markerProperty;
        
        ((TitledBorder)getBorder()).setTitle( markerProperty.getName() );
        this.updateUI();
        
        jCheckBox1.setSelected(markerProperty.getValueExpression() != null);
        updateEditorComponent();
        
        if (markerProperty.getValueExpression() == null)
        {
            jTextArea1.setText( markerProperty.getValue() + "");
        }
        else
        {
            jrExpressionArea.setText(  Misc.getExpressionText( markerProperty.getValueExpression() ) );
        }
        
        setInit(false);
    }

    /**
     * @return the init
     */
    public boolean isInit() {
        return init;
    }

    /**
     * @param init the init to set
     */
    public void setInit(boolean init) {
        this.init = init;
    }

    private void updateEditorComponent() {

        jPanel1.removeAll();

        if (jCheckBox1.isSelected())
        {
            jPanel1.removeAll();
            jPanel1.add( jrExpressionArea, BorderLayout.CENTER);
        }
        else
        {
            jPanel1.add( jTextArea1, BorderLayout.CENTER);
        }
        
        jPanel1.updateUI();
    }
}
