/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.wizards;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class CustomChooserVisualPanel extends JPanel {

    private CustomChooserWizardPanel panel = null;
    
    /** Creates new form NewJrxmlVisualPanel1 */
    public CustomChooserVisualPanel(CustomChooserWizardPanel panel) {
        initComponents();
        this.panel = panel;
        
        jTextFieldReportName.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                updateFileName();
                getPanel().fireChangeEvent();
            }

            public void removeUpdate(DocumentEvent e) {
                updateFileName();
                getPanel().fireChangeEvent();
            }

            public void changedUpdate(DocumentEvent e) {
                updateFileName();
                getPanel().fireChangeEvent();
            }
        });
        
        jTextFieldDirectory.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                updateFileName();
                getPanel().fireChangeEvent();
            }

            public void removeUpdate(DocumentEvent e) {
                updateFileName();
                getPanel().fireChangeEvent();
            }

            public void changedUpdate(DocumentEvent e) {
                updateFileName();
                getPanel().fireChangeEvent();
            }
        });
       
    }

    @Override
    public String getName() {
        return "Name and location";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        jTextFieldReportName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldDirectory = new javax.swing.JTextField();
        jButtonDirectory = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldFileName = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, "Report name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 4, 4);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 10);
        add(jTextFieldReportName, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Location:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 4, 4);
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 10);
        add(jTextFieldDirectory, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButtonDirectory, "Browse...");
        jButtonDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDirectoryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 10);
        add(jButtonDirectory, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, "File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 4);
        add(jLabel3, gridBagConstraints);

        jTextFieldFileName.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        add(jTextFieldFileName, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDirectoryActionPerformed
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int status = fileChooser.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
             File selectedDir = fileChooser.getSelectedFile();
             jTextFieldDirectory.setText(selectedDir.getPath());
        }
    }//GEN-LAST:event_jButtonDirectoryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDirectory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextFieldDirectory;
    private javax.swing.JTextField jTextFieldFileName;
    private javax.swing.JTextField jTextFieldReportName;
    // End of variables declaration//GEN-END:variables

    public

    CustomChooserWizardPanel getPanel() {
        return panel;
    }

    public void setPanel(CustomChooserWizardPanel panel) {
        this.panel = panel;
    }

    public void setTargetDirectory(String dir)
    {
        jTextFieldDirectory.setText(dir);
        updateFileName();
    }
    
    public void setReportName(String name)
    {
        if (name == null) name="";
        jTextFieldReportName.setText(name);
        updateFileName();
    }
    
    public String getTargetDirectory()
    {
        return jTextFieldDirectory.getText();
    }
    
    public String getReportName()
    {
        return jTextFieldReportName.getText();
    }
    
    public String getFileName()
    {
        return jTextFieldFileName.getText();
    }
    
    private void updateFileName()
    {
        String dir = jTextFieldDirectory.getText();
        String fname = jTextFieldReportName.getText().trim();
        if (!fname.endsWith(".jrxml"))
        {
            fname += ".jrxml";
        }
        File finalFile = new File(dir,fname);
        jTextFieldFileName.setText( finalFile.getPath());
    }

    public void validateForm() throws IllegalArgumentException
    {
        if (jTextFieldReportName.getText().trim().length() == 0)
        {
            throw new IllegalArgumentException("Invalid report name");
        }
        
        if (jTextFieldDirectory.getText().trim().length() == 0)
        {
            throw new IllegalArgumentException("Invalid directory");
        }
        String dir = jTextFieldDirectory.getText();
        File dirFile = new File(dir);
        if (!dirFile.exists())
        {
            throw new IllegalArgumentException("The specified directory does not exists");
        }
        
        String fname = jTextFieldReportName.getText().trim();
        fname += ".jrxml";
        File finalFile = new File(dir,fname);
        if (finalFile.exists())
        {
            throw new IllegalArgumentException("The specified file already exists " + fname );
        }
    }
}

