/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver.ui.wizards;

import com.jaspersoft.ireport.JrxmlDataObject;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.ui.ResourceChooser;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.openide.filesystems.FileUtil;

public final class ReportUnitVisualPanel2 extends JPanel {

    private ReportUnitWizardPanel2 wizardPanel = null;
    
    public ReportUnitWizardPanel2 getWizardPanel() {
        return wizardPanel;
    }

    public void setWizardPanel(ReportUnitWizardPanel2 wizardPanel) {
        this.wizardPanel = wizardPanel;
    }
    
    public JServer getServer()
    {
        return getWizardPanel().getWizardDescriptor().getServer();
    }
    
    public String getParentFolder()
    {
        return getWizardPanel().getWizardDescriptor().getParentFolder();
    }

    
    /** Creates new form ReportUnitVisualPanel2 */
    public ReportUnitVisualPanel2(ReportUnitWizardPanel2 wizardPanel) {
        initComponents();
        this.wizardPanel = wizardPanel;
        
        javax.swing.event.DocumentListener dl = new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent evt) {
                if (getWizardPanel() != null) getWizardPanel().fireChangeEvent();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent evt) {
                if (getWizardPanel() != null) getWizardPanel().fireChangeEvent();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) {
                if (getWizardPanel() != null) getWizardPanel().fireChangeEvent();
            }
        };
        this.jTextFieldFile.getDocument().addDocumentListener(dl);
        this.jTextFieldFileRepo.getDocument().addDocumentListener(dl);

        jButtonGetCurrentReport.setEnabled(false);
        JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
        if (view != null && view.getLookup() != null)
        {
            JrxmlDataObject dobject = view.getLookup().lookup(JrxmlDataObject.class);
            if (dobject != null)
            {
                jButtonGetCurrentReport.setEnabled(true);
            }
        }
    
    }

    @Override
    public String getName() {
        return JasperServerManager.getString("newReportUnitWizard.step.jrxml","Main JRXML");
    }

    void validateForm() throws Exception
    {
        
        if (jRadioButtonRepo1.isSelected() && jTextFieldFileRepo.getText().trim().length() == 0)
        {
            throw new Exception("Please select a valid JRXML resource from the repository.");
        }
        
        if (jRadioButtonLocal1.isSelected() && jTextFieldFile.getText().trim().length() == 0)
        {
            File resourceFile = new File(jTextFieldFile.getText());
            if (!resourceFile.exists()) {
                throw new Exception(JasperServerManager.getFormattedString("newReportUnitWizard.message.fileNotFound","{0}\n\nFile not found!",new Object[]{jTextFieldFile.getText()}));
            }
        }
        
        
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
        jPanel1 = new javax.swing.JPanel();
        jLabelResourceFile = new javax.swing.JLabel();
        jRadioButtonRepo1 = new javax.swing.JRadioButton();
        jTextFieldFileRepo = new javax.swing.JTextField();
        jButtonPickJrxml = new javax.swing.JButton();
        jRadioButtonLocal1 = new javax.swing.JRadioButton();
        jTextFieldFile = new javax.swing.JTextField();
        jButtonBrowse = new javax.swing.JButton();
        jButtonGetCurrentReport = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabelResourceFile, "Locate the main JRXML file");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(8, 4, 20, 4);
        jPanel1.add(jLabelResourceFile, gridBagConstraints);

        buttonGroup1.add(jRadioButtonRepo1);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButtonRepo1, "From the repository");
        jRadioButtonRepo1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonRepo1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonRepo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonRepo1jRadioButtonRepoActionPerformed1(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 4, 4);
        jPanel1.add(jRadioButtonRepo1, gridBagConstraints);

        jTextFieldFileRepo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jTextFieldFileRepo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButtonPickJrxml, "Browse");
        jButtonPickJrxml.setEnabled(false);
        jButtonPickJrxml.setMinimumSize(new java.awt.Dimension(73, 21));
        jButtonPickJrxml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPickJrxmljButton1ActionPerformed12(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jButtonPickJrxml, gridBagConstraints);

        buttonGroup1.add(jRadioButtonLocal1);
        jRadioButtonLocal1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButtonLocal1, "Locally Defined");
        jRadioButtonLocal1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButtonLocal1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButtonLocal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLocal1jRadioButtonLocalActionPerformed1(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 4, 4);
        jPanel1.add(jRadioButtonLocal1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jTextFieldFile, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButtonBrowse, "Browse");
        jButtonBrowse.setMinimumSize(new java.awt.Dimension(73, 21));
        jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBrowseActionPerformed1(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jButtonBrowse, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButtonGetCurrentReport, "Get source from current opened report");
        jButtonGetCurrentReport.setMinimumSize(null);
        jButtonGetCurrentReport.setPreferredSize(null);
        jButtonGetCurrentReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetCurrentReportjButton1ActionPerformed11(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 30, 2, 0);
        jPanel1.add(jButtonGetCurrentReport, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonRepo1jRadioButtonRepoActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonRepo1jRadioButtonRepoActionPerformed1
        updateJrxmlFromType();
    }//GEN-LAST:event_jRadioButtonRepo1jRadioButtonRepoActionPerformed1

    private void jButtonPickJrxmljButton1ActionPerformed12(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPickJrxmljButton1ActionPerformed12
        ResourceChooser rc = new ResourceChooser();
        rc.setServer( getServer() );
        if (rc.showDialog(this, null) == JOptionPane.OK_OPTION) {
            ResourceDescriptor rd = rc.getSelectedDescriptor();
            
            if (rd == null || rd.getUriString() == null) {
                jTextFieldFileRepo.setText("");
            } else {
                if (!rd.getWsType().equals(rd.TYPE_JRXML) ) {
                    JOptionPane.showMessageDialog(Misc.getMainFrame(),
                            JasperServerManager.getString("newReportUnitWizard.chooseJRXML","Please choose a JRXML resource"),"",JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    jTextFieldFileRepo.setText( rd.getUriString() );
                }
            }

            if (wizardPanel != null) wizardPanel.fireChangeEvent();
        }
    }//GEN-LAST:event_jButtonPickJrxmljButton1ActionPerformed12

    private void jRadioButtonLocal1jRadioButtonLocalActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLocal1jRadioButtonLocalActionPerformed1
        updateJrxmlFromType();
    }//GEN-LAST:event_jRadioButtonLocal1jRadioButtonLocalActionPerformed1

    private void jButtonBrowseActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBrowseActionPerformed1
        String fileName = "";
        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(IReportManager.getInstance().getCurrentDirectory());
        
        jfc.setDialogTitle(JasperServerManager.getString("newReportUnitWizard.pickAFile","Pick a file..."));
        
        jfc.setFileFilter( new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File file) {
                String filename = file.getName();
                return (filename.toLowerCase().endsWith(".xml") || file.isDirectory() || filename.toLowerCase().endsWith(".jrxml")) ;
            }
            public String getDescription() {
                return "JasperReports XML *.xml, *.jrxml";
            }
        });
        
        jfc.setMultiSelectionEnabled(false);
        jfc.setDialogType( javax.swing.JFileChooser.OPEN_DIALOG);
        if  (jfc.showOpenDialog( this) == javax.swing.JOptionPane.OK_OPTION) {
            
            jTextFieldFile.setText(  jfc.getSelectedFile()+"");
            if (wizardPanel != null) wizardPanel.fireChangeEvent();
        }
    }//GEN-LAST:event_jButtonBrowseActionPerformed1

    private void jButtonGetCurrentReportjButton1ActionPerformed11(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetCurrentReportjButton1ActionPerformed11
        
        JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
        if (view != null && view.getLookup() != null)
        {
            JrxmlDataObject dobject = view.getLookup().lookup(JrxmlDataObject.class);
            if (dobject != null)
            {
                jTextFieldFile.setText(  FileUtil.toFile( dobject.getPrimaryFile() ) +"" );
            }
        }

    
    }//GEN-LAST:event_jButtonGetCurrentReportjButton1ActionPerformed11

    
    void storeSettings(ReportUnitWizardDescriptor wizardDescriptor) {
        wizardDescriptor.putProperty("jrxml_is_local", jRadioButtonLocal1.isSelected()+"");
        wizardDescriptor.putProperty("jrxml_file", 
                (jRadioButtonRepo1.isSelected()) ? jTextFieldFileRepo.getText() : jTextFieldFile.getText());
    }
    
    public void updateJrxmlFromType()
    {
        jTextFieldFileRepo.setEnabled( jRadioButtonRepo1.isSelected() );
        jButtonPickJrxml.setEnabled( jRadioButtonRepo1.isSelected() );
        jTextFieldFile.setEnabled( jRadioButtonLocal1.isSelected() );
        jButtonBrowse.setEnabled( jRadioButtonLocal1.isSelected() );
        jButtonGetCurrentReport.setEnabled( jRadioButtonLocal1.isSelected() );
        
        if (wizardPanel != null) wizardPanel.fireChangeEvent();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonBrowse;
    private javax.swing.JButton jButtonGetCurrentReport;
    private javax.swing.JButton jButtonPickJrxml;
    private javax.swing.JLabel jLabelResourceFile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonLocal1;
    private javax.swing.JRadioButton jRadioButtonRepo1;
    private javax.swing.JTextField jTextFieldFile;
    private javax.swing.JTextField jTextFieldFileRepo;
    // End of variables declaration//GEN-END:variables
}

