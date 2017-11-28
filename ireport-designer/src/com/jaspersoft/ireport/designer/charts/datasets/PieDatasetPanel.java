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
 * PieDatasetPanel.java
 * 
 * Created on 15 agosto 2005, 17.55
 *
 */

package com.jaspersoft.ireport.designer.charts.datasets;

import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.utils.Misc;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignHyperlink;

/**
 *
 * @author  Administrator
 */
public class PieDatasetPanel extends javax.swing.JPanel  implements ChartDatasetPanel {
    
    private JRDesignPieDataset pieDataset = null;
    
    /** Creates new form PieDatasetPanel */
    public PieDatasetPanel() {
        initComponents();
        
        //applyI18n();
        
        this.jRTextExpressionKey.getExpressionEditorPane().getDocument().addDocumentListener( new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionKeyTextChanged();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionKeyTextChanged();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionKeyTextChanged();
            }
        });
        
        
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
        
        this.jRTextExpressionLabel.getExpressionEditorPane().getDocument().addDocumentListener( new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionLabelTextChanged();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionLabelTextChanged();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                jRTextExpressionLabelTextChanged();
            }
        });
    }

    public JRDesignPieDataset getPieDataset() {
        return pieDataset;
    }
    
    /**
     * this method is used to pass the correct subdataset to the expression editor
     */
    public void setExpressionContext(ExpressionContext ec)
    {
        jRTextExpressionKey.setExpressionContext(ec);
        jRTextExpressionValue.setExpressionContext(ec);
        jRTextExpressionLabel.setExpressionContext(ec);
        sectionItemHyperlinkPanel1.setExpressionContext(ec);
    }

    public void setPieDataset(JRDesignPieDataset pieDataset)
    {
        this.pieDataset = pieDataset;
        jRTextExpressionKey.setText( Misc.getExpressionText( pieDataset.getKeyExpression()) );
        jRTextExpressionValue.setText( Misc.getExpressionText(pieDataset.getValueExpression()) );
        jRTextExpressionLabel.setText( Misc.getExpressionText(pieDataset.getLabelExpression()) );
        if (pieDataset.getSectionHyperlink() == null)
        {
            JRDesignHyperlink hl = new JRDesignHyperlink();
            hl.setHyperlinkType( hl.HYPERLINK_TYPE_NONE );
            pieDataset.setSectionHyperlink(hl);
        }
        sectionItemHyperlinkPanel1.setHyperlink( pieDataset.getSectionHyperlink() );
    }
    
    public void jRTextExpressionKeyTextChanged()
    {
        JRDesignExpression exp = null;
        if (jRTextExpressionKey.getText().trim().length() > 0)
        {
            exp = new JRDesignExpression();
            exp.setValueClassName("java.lang.Object");
            exp.setText(jRTextExpressionKey.getText());
        }
        pieDataset.setKeyExpression( exp );
    }
    
    public void jRTextExpressionValueTextChanged()
    {
        JRDesignExpression exp = null;
        if (jRTextExpressionValue.getText().trim().length() > 0)
        {
            exp = new JRDesignExpression();
            exp.setValueClassName("java.lang.Number");
            exp.setText(jRTextExpressionValue.getText());
        }
        pieDataset.setValueExpression( exp );
    }
    
    public void jRTextExpressionLabelTextChanged()
    {
        JRDesignExpression exp = null;
        if (jRTextExpressionLabel.getText().trim().length() > 0)
        {
            exp = new JRDesignExpression();
            exp.setValueClassName("java.lang.Object");
            exp.setText(jRTextExpressionLabel.getText());
        }
        pieDataset.setLabelExpression( exp );
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelKeyExpression = new javax.swing.JLabel();
        jRTextExpressionKey = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        jLabelValueExpression = new javax.swing.JLabel();
        jRTextExpressionValue = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        jLabelLabelExpression = new javax.swing.JLabel();
        jRTextExpressionLabel = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        jPanel2 = new javax.swing.JPanel();
        sectionItemHyperlinkPanel1 = new com.jaspersoft.ireport.designer.tools.HyperlinkPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelKeyExpression.setText("Key expression");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabelKeyExpression, gridBagConstraints);

        jRTextExpressionKey.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionKey.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionKey.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jRTextExpressionKey, gridBagConstraints);

        jLabelValueExpression.setText("Value expression");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
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
        jPanel1.add(jRTextExpressionValue, gridBagConstraints);

        jLabelLabelExpression.setText("Label expression");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabelLabelExpression, gridBagConstraints);

        jRTextExpressionLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionLabel.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionLabel.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jRTextExpressionLabel, gridBagConstraints);

        jTabbedPane1.addTab("Section value", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(sectionItemHyperlinkPanel1, gridBagConstraints);

        jTabbedPane1.addTab("Section hyperlink", jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelKeyExpression;
    private javax.swing.JLabel jLabelLabelExpression;
    private javax.swing.JLabel jLabelValueExpression;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionKey;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionLabel;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionValue;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.jaspersoft.ireport.designer.tools.HyperlinkPanel sectionItemHyperlinkPanel1;
    // End of variables declaration//GEN-END:variables
    
    /*
    public void applyI18n(){
                // Start autogenerated code ----------------------
                jLabelKeyExpression.setText(I18n.getString("pieDatasetPanel.labelKeyExpression","Key expression"));
                jLabelLabelExpression.setText(I18n.getString("pieDatasetPanel.labelLabelExpression","Label expression"));
                jLabelValueExpression.setText(I18n.getString("pieDatasetPanel.labelValueExpression","Value expression"));
                // End autogenerated code ----------------------
    
                jTabbedPane1.setTitleAt(0,I18n.getString("chartSeries.tab.SectionValue","Section value"));
                jTabbedPane1.setTitleAt(1,I18n.getString("chartSeries.tab.SectionHyperlink","Section hyperlink"));
    }
     */
    
    public static final int COMPONENT_NONE=0;
    public static final int COMPONENT_KEY_EXPRESSION=1;
    public static final int COMPONENT_VALUE_EXPRESSION=2;
    public static final int COMPONENT_LABEL_EXPRESSION=3;
    public static final int COMPONENT_HYPERLINK=100;
    
    /**
     * This method set the focus on a specific component.
     * Valid constants are something like:
     * COMPONENT_KEY_EXPRESSION, COMPONENT_VALUE_EXPRESSION, ...
     * otherInfo is used here only for COMPONENT_HYPERLINK
     * otherInfo[0] = expression ID
     * otherInfo[1] = parameter #
     * otherInfo[2] = parameter expression ID
     */
    public void setFocusedExpression(Object[] expressionInfo)
    {
        int expID = ((Integer)expressionInfo[0]).intValue();
        switch (expID)
        {
            case COMPONENT_KEY_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionKey.getExpressionEditorPane());
                break;
            case COMPONENT_VALUE_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionValue.getExpressionEditorPane());
                break;
            case COMPONENT_LABEL_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionLabel.getExpressionEditorPane());
                break;
            case COMPONENT_HYPERLINK:
                jTabbedPane1.setSelectedComponent( jPanel2 );
                Object newInfo[] = new Object[expressionInfo.length -1 ];
                for (int i=1; i< expressionInfo.length; ++i) newInfo[i-1] = expressionInfo[i];
                //sectionItemHyperlinkPanel1.setFocusedExpression(newInfo);
                break;   
        }
    }
    
    public void containerWindowOpened() {
        //sectionItemHyperlinkPanel1.openExtraWindows();
    }

}
