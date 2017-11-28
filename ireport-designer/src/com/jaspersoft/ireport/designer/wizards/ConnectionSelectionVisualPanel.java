/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.wizards;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.connection.gui.ConnectionDialog;
import com.jaspersoft.ireport.designer.data.WizardFieldsProvider;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class ConnectionSelectionVisualPanel extends JPanel {

    private ConnectionSelectionWizardPanel panel = null;
    private boolean useQuery = false;
            
    /** Creates new form ConnectionSelectionVisualPanel */
    public ConnectionSelectionVisualPanel(ConnectionSelectionWizardPanel panel) {
        this.panel = panel;
        initComponents();
        updateConnections();
        
        jEditorPaneQuery.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                getPanel().fireChangeEvent();
            }

            public void removeUpdate(DocumentEvent e) {
                getPanel().fireChangeEvent();
            }

            public void changedUpdate(DocumentEvent e) {
                getPanel().fireChangeEvent();
            }
        });
    }
    
    private void updateConnections()
    {
        jComboBoxConnections.removeAllItems();
        //jComboBoxConnections.addItem("No connection or datasource");
        
        List<IReportConnection> cons = IReportManager.getInstance().getConnections();
        for (IReportConnection con : cons)
        {
            jComboBoxConnections.addItem(con);
        }
        
    }

    @Override
    public String getName() {
        return I18n.getString("ConnectionSelectionVisualPanel.Name.Query");
    }
    
    
    public IReportConnection getConnection()
    {
        return (IReportConnection)jComboBoxConnections.getSelectedItem();
    }
    
    public String getQuery()
    {
        if (isUseQuery())
        {
            return jEditorPaneQuery.getText();
        }
        return null;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelQuery = new javax.swing.JPanel();
        jLabelLanguageName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPaneQuery = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonDesign = new javax.swing.JButton();
        jButtonLoadQuery = new javax.swing.JButton();
        jButtonSaveQuery = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxConnections = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();

        jPanelQuery.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabelLanguageName, I18n.getString("ConnectionSelectionVisualPanel.Label.ReportQuery")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 4, 0);
        jPanelQuery.add(jLabelLanguageName, gridBagConstraints);

        jEditorPaneQuery.setPreferredSize(new java.awt.Dimension(20, 20));
        jScrollPane1.setViewportView(jEditorPaneQuery);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelQuery.add(jScrollPane1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jButtonDesign, I18n.getString("ConnectionSelectionVisualPanel.Button.DesignQuery")); // NOI18N
        jButtonDesign.setEnabled(false);
        jButtonDesign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDesignActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jButtonDesign, gridBagConstraints);

        jButtonLoadQuery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/data/folder_database.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButtonLoadQuery, I18n.getString("ConnectionSelectionVisualPanel.Button.LoadQuery")); // NOI18N
        jButtonLoadQuery.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonLoadQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadQueryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jButtonLoadQuery, gridBagConstraints);

        jButtonSaveQuery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/data/database_save.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButtonSaveQuery, I18n.getString("ConnectionSelectionVisualPanel.Button.SaveQuery")); // NOI18N
        jButtonSaveQuery.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonSaveQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveQueryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        jPanel1.add(jButtonSaveQuery, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        jPanelQuery.add(jPanel1, gridBagConstraints);

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, I18n.getString("ConnectionSelectionVisualPanel.Label.ConnDataSources")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 4, 0, 4);
        add(jLabel1, gridBagConstraints);

        jComboBoxConnections.setMinimumSize(new java.awt.Dimension(28, 20));
        jComboBoxConnections.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxConnectionsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(jComboBoxConnections, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, I18n.getString("ConnectionSelectionVisualPanel.Button.New")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        add(jButton1, gridBagConstraints);

        jPanelMain.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(jPanelMain, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxConnectionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxConnectionsActionPerformed
        
        IReportConnection con = (IReportConnection)jComboBoxConnections.getSelectedItem();
        
        if (con instanceof WizardFieldsProvider)
        {
            WizardFieldsProvider wFieldsProvider = (WizardFieldsProvider)con;
            String lang = wFieldsProvider.getQueryLanguage();
            if (lang == null)
            {
                jPanelMain.removeAll();
                setUseQuery(false);
            }
            else
            {
                jLabelLanguageName.setText(I18n.getString("ConnectionSelectionVisualPanel.Label.Query(") + lang + ")");
                jEditorPaneQuery.setText("");
                jPanelMain.add(jPanelQuery, BorderLayout.CENTER);
                jButtonDesign.setEnabled(wFieldsProvider.supportsDesign());
                setUseQuery(true);
            }
        }
        else
        {
            jPanelMain.removeAll();
            setUseQuery(false);
        }
        jPanelMain.updateUI();
        getPanel().fireChangeEvent();
        
    }//GEN-LAST:event_jComboBoxConnectionsActionPerformed

    private void jButtonLoadQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadQueryActionPerformed
        String query = Misc.loadSQLQuery(this);
        
        if (query != null) {
            jEditorPaneQuery.setText(query);
        }
    }//GEN-LAST:event_jButtonLoadQueryActionPerformed

    private void jButtonSaveQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveQueryActionPerformed
        Misc.saveSQLQuery( jEditorPaneQuery.getText(), this );
    }//GEN-LAST:event_jButtonSaveQueryActionPerformed

    private void jButtonDesignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDesignActionPerformed
        
        IReportConnection con = (IReportConnection)jComboBoxConnections.getSelectedItem();
        if (con instanceof WizardFieldsProvider)
        {
            WizardFieldsProvider wFieldsProvider = (WizardFieldsProvider)con;
            String q = wFieldsProvider.designQuery(jEditorPaneQuery.getText());
            if (q != null)
            {
                jEditorPaneQuery.setText(q);
            }
        }
    }//GEN-LAST:event_jButtonDesignActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        Object pWin = Misc.getParentWindow(this);
        
        
        //ConnectionDialog cd = new ConnectionDialog(parent,false);
        ConnectionDialog cd = null;
        if (pWin instanceof Dialog) cd = new ConnectionDialog((Dialog)pWin, true);
        else if (pWin instanceof Frame) cd = new ConnectionDialog((Frame)pWin, true);
        else cd = new ConnectionDialog((Dialog)null, true);
        
        cd.setVisible(true);

        if (cd.getDialogResult() == JOptionPane.OK_OPTION)
        {
            IReportConnection con = cd.getIReportConnection();
            IReportManager.getInstance().addConnection(con);
            IReportManager.getInstance().setDefaultConnection(con);
            IReportManager.getInstance().saveiReportConfiguration();
            updateConnections();
            jComboBoxConnections.setSelectedItem(con);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonDesign;
    private javax.swing.JButton jButtonLoadQuery;
    private javax.swing.JButton jButtonSaveQuery;
    private javax.swing.JComboBox jComboBoxConnections;
    private javax.swing.JEditorPane jEditorPaneQuery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelLanguageName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelQuery;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public boolean isUseQuery() {
        return useQuery;
    }

    public void setUseQuery(boolean useQuery) {
        this.useQuery = useQuery;
    }

    public

    ConnectionSelectionWizardPanel getPanel() {
        return panel;
    }

    public void setPanel(ConnectionSelectionWizardPanel panel) {
        this.panel = panel;
    }
}

