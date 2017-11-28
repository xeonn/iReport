/*
 * SubreportCustomChooserVisualPanel.java
 *
 * Created on February 6, 2008, 5:58 PM
 */

package com.jaspersoft.ireport.designer.subreport;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.io.File;

/**
 *
 * @author  gtoffoli
 */
public class SubreportExpressionVisualPanel extends javax.swing.JPanel  {
 
    private SubreportExpressionWizardPanel panel = null;
    /** Creates new form SubreportCustomChooserVisualPanel */
    public SubreportExpressionVisualPanel(SubreportExpressionWizardPanel panel) {
        initComponents();
        this.panel = panel;
    }

    void validateForm() throws IllegalArgumentException {

    }
    
    public String getName()
    {
        return I18n.getString("SubreportExpressionVisualPanel.Label.Name");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabelExpression1 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabelExpression2 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(I18n.getString("SubreportCustomChooserVisualPanel.jRadioButton1.text")); // NOI18N
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        add(jRadioButton1, gridBagConstraints);

        jLabelExpression1.setFont(new java.awt.Font("Courier New", 0, 11));
        jLabelExpression1.setText(I18n.getString("SubreportCustomChooserVisualPanel.jLabelExpression1.text")); // NOI18N
        jLabelExpression1.setMinimumSize(new java.awt.Dimension(34, 20));
        jLabelExpression1.setPreferredSize(new java.awt.Dimension(34, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 20);
        add(jLabelExpression1, gridBagConstraints);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText(I18n.getString("SubreportCustomChooserVisualPanel.jRadioButton2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        add(jRadioButton2, gridBagConstraints);

        jLabelExpression2.setFont(new java.awt.Font("Courier New", 0, 11));
        jLabelExpression2.setText(I18n.getString("SubreportCustomChooserVisualPanel.jLabelExpression2.text")); // NOI18N
        jLabelExpression2.setMinimumSize(new java.awt.Dimension(34, 20));
        jLabelExpression2.setPreferredSize(new java.awt.Dimension(34, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 20);
        add(jLabelExpression2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabelExpression1;
    private javax.swing.JLabel jLabelExpression2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    // End of variables declaration//GEN-END:variables

    
    public void updateExpressions()
    {
        // Expression 1....
        File f = new File("" + getPanel().getWizard().getProperty("subreport_filename"));

        String name = f.getName();
        if (name.toLowerCase().endsWith(".jrxml")) name = name.substring(0, name.length() - 6) + ".jasper";

        jLabelExpression1.setText( "$P{SUBREPORT_DIR} + \"" +name +"\"");

        name = f.getPath();
        if (name.toLowerCase().endsWith(".jrxml")) name = name.substring(0, name.length() - 6) + ".jasper";
        name = Misc.string_replace("\\\\", "\\", name);
        jLabelExpression2.setText("\"" + name + "\"");
                
    }
    
    public boolean isAddSubreportParameter()
    {
        return jRadioButton1.isSelected();
    }
    
    public String getSubreportExpression()
    {
        return (jRadioButton1.isSelected()) ? jLabelExpression1.getText() : jLabelExpression2.getText();
    }

    public

    SubreportExpressionWizardPanel getPanel() {
        return panel;
    }

    public void setPanel(SubreportExpressionWizardPanel panel) {
        this.panel = panel;
    }
    
}
