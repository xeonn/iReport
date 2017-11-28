/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.wizards;

import javax.swing.JPanel;
import com.jaspersoft.ireport.locale.I18n;

public final class ReportGroupVisualPanel2 extends JPanel {

    private ReportGroupWizardPanel2 wizardPanel = null;
    public ReportGroupVisualPanel2( ReportGroupWizardPanel2 wp)
    {
        initComponents();
        this.wizardPanel = wp;
    }
    
    /** Creates new form ReportGroupVisualPanel2 */
    public ReportGroupVisualPanel2() {
        initComponents();
    }

    @Override
    public String getName() {
        return I18n.getString("ReportGroupVisualPanel2.Name.Details");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        jCheckBox1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, I18n.getString("ReportGroupVisualPanel2.CheckBox.GroupHeader")); // NOI18N
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(24, 24, 0, 24);
        add(jCheckBox1, gridBagConstraints);

        jCheckBox2.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, I18n.getString("ReportGroupVisualPanel2.CheckBox.GroupFooter")); // NOI18N
        jCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 24, 0, 24);
        add(jCheckBox2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    // End of variables declaration//GEN-END:variables

    public ReportGroupWizardPanel2 getWizardPanel() {
        return wizardPanel;
    }

    public void setWizardPanel(ReportGroupWizardPanel2 wizardPanel) {
        this.wizardPanel = wizardPanel;
    }

    public boolean hasHeader() {
        return jCheckBox1.isSelected();
    }

    public boolean hasFooter() {
        return jCheckBox2.isSelected();
    }
}

