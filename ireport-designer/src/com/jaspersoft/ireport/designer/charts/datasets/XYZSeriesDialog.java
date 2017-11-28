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
 * XYZSeriesDialog.java
 * 
 * Created on 17 agosto 2005, 11.19
 *
 */

package com.jaspersoft.ireport.designer.charts.datasets;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.utils.Misc;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignHyperlink;

/**
 *
 * @author  Administrator
 */
public class XYZSeriesDialog extends javax.swing.JDialog {
    
    private JRDesignExpression seriesExpression = null;
    private JRDesignExpression xValueExpression = null;
    private JRDesignExpression yValueExpression = null;
    private JRDesignExpression zValueExpression = null;
    
    private int dialogResult = javax.swing.JOptionPane.CANCEL_OPTION;
    
    /** Creates new form CategorySeriesDialog */
    public XYZSeriesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //applyI18n();
        
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        
        javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
        javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jButtonCancelActionPerformed(e);
            }
        };
       
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, I18n.getString("Global.Pane.Escape"));
        getRootPane().getActionMap().put(I18n.getString("Global.Pane.Escape"), escapeAction);


        //to make the default button ...
        this.getRootPane().setDefaultButton(this.jButtonOK);
    }
    
    /**
     * this method is used to pass the correct subdataset to the expression editor
     */
    public void setExpressionContext( ExpressionContext ec )
    {
        jRTextExpressionSeries.setExpressionContext(ec);
        jRTextExpressionXValue.setExpressionContext(ec);
        jRTextExpressionYValue.setExpressionContext(ec);
        jRTextExpressionZValue.setExpressionContext(ec);
        sectionItemHyperlinkPanel1.setExpressionContext( ec);
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
        jLabelSeriesExpression = new javax.swing.JLabel();
        jLabelXValueExpression = new javax.swing.JLabel();
        jLabelYValueExpression = new javax.swing.JLabel();
        jLabelZValueExpression = new javax.swing.JLabel();
        jRTextExpressionSeries = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        jRTextExpressionXValue = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        jRTextExpressionYValue = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        jRTextExpressionZValue = new com.jaspersoft.ireport.designer.editor.ExpressionEditorArea();
        sectionItemHyperlinkPanel1 = new com.jaspersoft.ireport.designer.tools.HyperlinkPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButtonOK = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(I18n.getString("XYZSeriesDialog.Title.XYZ_series")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelSeriesExpression.setText(I18n.getString("XYZSeriesDialog.Label.Series_expression_(required)")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabelSeriesExpression, gridBagConstraints);

        jLabelXValueExpression.setText(I18n.getString("XYZSeriesDialog.Label.X_value_expression_(required)")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabelXValueExpression, gridBagConstraints);

        jLabelYValueExpression.setText(I18n.getString("XYZSeriesDialog.Label.Y_value_expression_(required)")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabelYValueExpression, gridBagConstraints);

        jLabelZValueExpression.setText(I18n.getString("XYZSeriesDialog.Label.Z_value_expression")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        jPanel1.add(jLabelZValueExpression, gridBagConstraints);

        jRTextExpressionSeries.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionSeries.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionSeries.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jRTextExpressionSeries, gridBagConstraints);

        jRTextExpressionXValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionXValue.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionXValue.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jRTextExpressionXValue, gridBagConstraints);

        jRTextExpressionYValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionYValue.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionYValue.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jRTextExpressionYValue, gridBagConstraints);

        jRTextExpressionZValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionZValue.setMinimumSize(new java.awt.Dimension(10, 10));
        jRTextExpressionZValue.setPreferredSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jRTextExpressionZValue, gridBagConstraints);

        jTabbedPane1.addTab("Data", jPanel1);
        jTabbedPane1.addTab(I18n.getString("XYZSeriesDialog.Label.Item_hyperlink"), sectionItemHyperlinkPanel1); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jPanel7, gridBagConstraints);

        jButtonOK.setMnemonic('o');
        jButtonOK.setText(I18n.getString("Global.Button.Ok")); // NOI18N
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel6.add(jButtonOK, gridBagConstraints);

        jButtonCancel.setMnemonic('c');
        jButtonCancel.setText(I18n.getString("Global.Button.Cancel")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        jPanel6.add(jButtonCancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        sectionItemHyperlinkPanel1.openExtraWindows();
    }//GEN-LAST:event_formWindowOpened

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed

        seriesExpression =  Misc.createExpression(null,  jRTextExpressionSeries.getText());
        xValueExpression = Misc.createExpression("java.lang.Number",  jRTextExpressionXValue.getText());// NOI18N
        yValueExpression =  Misc.createExpression("java.lang.Number",  jRTextExpressionYValue.getText());//NOI18N
        zValueExpression =  Misc.createExpression("java.lang.Number",  jRTextExpressionZValue.getText());//NOI18N

       
        java.text.MessageFormat formatter = new java.text.MessageFormat( I18n.getString("XYZSeriesDialog.Message.cannot_be_blank!"));

        if (seriesExpression == null) {
            
           javax.swing.JOptionPane.showMessageDialog(this, formatter.format(new Object[]{I18n.getString("XYZSeriesDialog.Pane.Series_expression")}) ,I18n.getString("XYZSeriesDialog.Message.Invalid_expression"),javax.swing.JOptionPane.ERROR_MESSAGE);
           return;
        } 
        
        if (xValueExpression == null) {
           javax.swing.JOptionPane.showMessageDialog(this, formatter.format(new Object[]{I18n.getString("XYZSeriesDialog.Pane.X_Value_expression")}) ,I18n.getString("XYZSeriesDialog.Message.Invalid_expression"),javax.swing.JOptionPane.ERROR_MESSAGE);
           return;
        }  
        
        if (yValueExpression == null) {
           javax.swing.JOptionPane.showMessageDialog(this, formatter.format(new Object[]{I18n.getString("XYZSeriesDialog.Pane.Y_Value_expression")}) ,I18n.getString("XYZSeriesDialog.Message.Invalid_expression"),javax.swing.JOptionPane.ERROR_MESSAGE);
           return;
        } 
        
        if (zValueExpression == null) {
           javax.swing.JOptionPane.showMessageDialog(this, formatter.format(new Object[]{I18n.getString("XYZSeriesDialog.Pane.Y_Value_expression")}) ,I18n.getString("XYZSeriesDialog.Message.Invalid_expression"),javax.swing.JOptionPane.ERROR_MESSAGE);
           return;
        } 
        
        this.setDialogResult(javax.swing.JOptionPane.OK_OPTION);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CategorySeriesDialog(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }

    public JRDesignExpression getSeriesExpression() {
        return seriesExpression;
    }

    public void setSeriesExpression(JRDesignExpression exp) {
        this.seriesExpression = null;
        if (exp != null)
        {
            try {
                this.seriesExpression = (JRDesignExpression)exp.clone();
            }  catch (Exception ex) { }
        }
        jRTextExpressionSeries.setText(Misc.getExpressionText(exp));
    }
    
    public void setSectionItemHyperlink(JRDesignHyperlink sectionItemHyperlink)
    {
        sectionItemHyperlinkPanel1.setHyperlink( sectionItemHyperlink );
    }
    
    public JRHyperlink getSectionItemHyperlink()
    {
        return sectionItemHyperlinkPanel1.getHyperlink();
    }

 
    public int getDialogResult() {
        return dialogResult;
    }

    public void setDialogResult(int dialogResult) {
        this.dialogResult = dialogResult;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JLabel jLabelSeriesExpression;
    private javax.swing.JLabel jLabelXValueExpression;
    private javax.swing.JLabel jLabelYValueExpression;
    private javax.swing.JLabel jLabelZValueExpression;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionSeries;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionXValue;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionYValue;
    private com.jaspersoft.ireport.designer.editor.ExpressionEditorArea jRTextExpressionZValue;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.jaspersoft.ireport.designer.tools.HyperlinkPanel sectionItemHyperlinkPanel1;
    // End of variables declaration//GEN-END:variables
    
    /*
        public void applyI18n()
        {
                // Start autogenerated code ----------------------
                // End autogenerated code ----------------------
            jButtonOK.setText( it.businesslogic.ireport.util.I18n.getString("ok","Ok"));
            jButtonCancel.setText( it.businesslogic.ireport.util.I18n.getString("cancel","Cancel"));
             
            jLabelSeriesExpression.setText( it.businesslogic.ireport.util.I18n.getString("charts.seriesExpression","Series expression"));
            jLabelXValueExpression.setText( it.businesslogic.ireport.util.I18n.getString("charts.xValueExpression","X Value expression"));
            jLabelYValueExpression.setText( it.businesslogic.ireport.util.I18n.getString("charts.yValueExpression","Y Value expression"));
            jLabelZValueExpression.setText( it.businesslogic.ireport.util.I18n.getString("charts.zValueExpression","Z Value expression"));

            jTabbedPane1.setTitleAt(0,I18n.getString("chartSeries.tab.Data","Data"));
            jTabbedPane1.setTitleAt(1,I18n.getString("chartSeries.tab.ItemHyperlink","Item hyperlink"));
            
            this.setTitle(I18n.getString("xYZSeriesDialog.title","XYZ series"));
            jButtonCancel.setMnemonic(I18n.getString("xYZSeriesDialog.buttonCancelMnemonic","c").charAt(0));
            jButtonOK.setMnemonic(I18n.getString("xYZSeriesDialog.buttonOKMnemonic","o").charAt(0));
            
            this.getRootPane().updateUI();
        }	
	*/

    public JRDesignExpression getXValueExpression() {
        return xValueExpression;
    }

    public void setXValueExpression(JRDesignExpression exp) {
        this.xValueExpression = null;
        if (exp != null)
        {
            try {
                this.xValueExpression = (JRDesignExpression)exp.clone();
            }  catch (Exception ex) { }
        }
        jRTextExpressionXValue.setText(Misc.getExpressionText(exp));
    }

    public JRDesignExpression getYValueExpression() {
        return yValueExpression;
    }

    public void setYValueExpression(JRDesignExpression exp) {
        this.yValueExpression = null;
        if (exp != null)
        {
            try {
                this.yValueExpression = (JRDesignExpression)exp.clone();
            }  catch (Exception ex) { }
        }
        jRTextExpressionYValue.setText(Misc.getExpressionText(exp));
    }

    public JRDesignExpression getZValueExpression() {
        return zValueExpression;
    }

    public void setZValueExpression(JRDesignExpression exp) {
        this.zValueExpression = null;
        if (exp != null)
        {
            try {
                this.zValueExpression = (JRDesignExpression)exp.clone();
            }  catch (Exception ex) { }
        }
        jRTextExpressionZValue.setText(Misc.getExpressionText(exp));
    }
    
    public static final int COMPONENT_NONE=0;
    public static final int COMPONENT_SERIES_EXPRESSION=1;
    public static final int COMPONENT_X_EXPRESSION=2;
    public static final int COMPONENT_Y_EXPRESSION=3;
    public static final int COMPONENT_Z_EXPRESSION=4;
    public static final int COMPONENT_HYPERLINK=100;
        
    /**
     * This method set the focus on a specific component.
     * 
     * expressionInfo[0] can be something like:
     * COMPONENT_SERIES_EXPRESSION, ...
     *
     * If it is COMPONENT_HYPERLINK, other parameters are expected...
     * otherInfo is used here only for COMPONENT_HYPERLINK
     * otherInfo[0] = expression ID
     * otherInfo[1] = parameter #
     * otherInfo[2] = parameter expression ID
     */
    public void setFocusedExpression(Object[] expressionInfo)
    {
        if (expressionInfo == null) return;
        int expID = ((Integer)expressionInfo[0]).intValue();
        
        switch (expID)
        {
            case COMPONENT_SERIES_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionSeries);
                break;
            case COMPONENT_X_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionXValue);
                break;
            case COMPONENT_Y_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionYValue);
                break;
            case COMPONENT_Z_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionZValue);
                break;
            case COMPONENT_HYPERLINK:
                jTabbedPane1.setSelectedComponent( sectionItemHyperlinkPanel1 );
                Object newInfo[] = new Object[expressionInfo.length -1 ];
                for (int i=1; i< expressionInfo.length; ++i) newInfo[i-1] = expressionInfo[i];
                sectionItemHyperlinkPanel1.setFocusedExpression(newInfo);
                break;
        }
    }
}
