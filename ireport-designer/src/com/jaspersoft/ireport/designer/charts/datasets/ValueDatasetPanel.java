/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved. 
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 *
 *
 *
 *
 * ValueDatasetPanel.java
 * 
 * Created on 15 agosto 2005, 17.55
 *
 */

package com.jaspersoft.ireport.designer.charts.datasets;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.utils.Misc;
import net.sf.jasperreports.charts.design.JRDesignValueDataset;


/**
 *
 * @author  Administrator
 */
public class ValueDatasetPanel extends javax.swing.JPanel  implements ChartDatasetPanel {
    
    private JRDesignValueDataset valueDataset = null;
    
    /** Creates new form PieDatasetPanel */
    public ValueDatasetPanel() {
        initComponents();
        
        //applyI18n();
        
        this.jRTextExpressionValue.getExpressionEditorPane().getDocument().addDocumentListener( new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionValueTextChanged();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionValueTextChanged();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionValueTextChanged();
            }
        });
        

    }

    public JRDesignValueDataset getValueDataset() {
        return valueDataset;
    }
    
    /**
     * this method is used to pass the correct subdataset to the expression editor
     */
    public void setExpressionContext( ExpressionContext ec )
    {
        jRTextExpressionValue.setExpressionContext(ec);
    }

    public void setValueDataset(JRDesignValueDataset valueDataset) {
        this.valueDataset = valueDataset;
        jRTextExpressionValue.setText( Misc.getExpressionText( valueDataset.getValueExpression() ) ); 
    }
        
    public void jRTextExpressionValueTextChanged()
    {
        valueDataset.setValueExpression( Misc.createExpression("java.lung.Number", jRTextExpressionValue.getText() ) );// NOI18N
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabelValueExpression = new javax.swing.JLabel();
        jRTextExpressionValue = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelValueExpression.setText(I18n.getString("ValueDatasetPanel.Label.Value_expression")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanel1.add(jLabelValueExpression, gridBagConstraints);

        jRTextExpressionValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionValue.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionValue.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        jPanel1.add(jRTextExpressionValue, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelValueExpression;
    private javax.swing.JPanel jPanel1;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionValue;
    // End of variables declaration//GEN-END:variables
    
    /*
    public void applyI18n(){
                // Start autogenerated code ----------------------
                jLabelValueExpression.setText(I18n.getString("valueDatasetPanel.labelValueExpression","Value expression"));
                // End autogenerated code ----------------------
    }
    */
    
    public static final int COMPONENT_NONE=0;
    public static final int COMPONENT_VALUE_EXPRESSION=1;
    
    /**
     * This method set the focus on a specific component.
     * 
     */
    public void setFocusedExpression(Object[] expressionInfo)
    {
        if (expressionInfo == null) return;
        int expID = ((Integer)expressionInfo[0]).intValue();
        switch (expID)
        {
            case COMPONENT_VALUE_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionValue);
        }
    }
    
    public void containerWindowOpened() {
       
    }
}
