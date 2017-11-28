/*
 * EJBQLConnectionEditor.java
 *
 * Created on March 27, 2007, 1:18 PM
 */

package com.jaspersoft.ireport.designer.connection.gui;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;
import com.jaspersoft.ireport.designer.connection.JavaBeanDataSourceConnection;

/**
 *
 * @author  gtoffoli
 */
public class JavaBeanDataSourceConnectionEditor extends javax.swing.JPanel implements IReportConnectionEditor {
    
    private IReportConnection iReportConnection = null;
    private boolean init = false;
    
    /** Creates new form EJBQLConnectionEditor */
    public JavaBeanDataSourceConnectionEditor() {
        initComponents();
        //applyI18n();
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
        jPanelBeansSet = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldJBSetFactoryClass = new javax.swing.JTextField();
        jRadioButtonJBSetCollection = new javax.swing.JRadioButton();
        jRadioButtonJBSetArray = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldJBSetMethodToCall = new javax.swing.JTextField();
        jCheckBoxisUseFieldDescription = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jPanelBeansSet.setLayout(new java.awt.GridBagLayout());

        jLabel12.setText("Factory class (the class that will produce the set)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanelBeansSet.add(jLabel12, gridBagConstraints);

        jTextFieldJBSetFactoryClass.setText("com.jaspersoft.ireport.examples.SampleJRDataSourceFactory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 4, 4);
        jPanelBeansSet.add(jTextFieldJBSetFactoryClass, gridBagConstraints);

        buttonGroup1.add(jRadioButtonJBSetCollection);
        jRadioButtonJBSetCollection.setSelected(true);
        jRadioButtonJBSetCollection.setText(" Collection of javaBeans");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanelBeansSet.add(jRadioButtonJBSetCollection, gridBagConstraints);

        buttonGroup1.add(jRadioButtonJBSetArray);
        jRadioButtonJBSetArray.setText("Array of javaBeans");
        jRadioButtonJBSetArray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonJBSetArrayActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        jPanelBeansSet.add(jRadioButtonJBSetArray, gridBagConstraints);

        jLabel13.setText("The static method to call to retrive the array or the the collection of javaBeans");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanelBeansSet.add(jLabel13, gridBagConstraints);

        jTextFieldJBSetMethodToCall.setText("createBeanCollection");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 4, 4);
        jPanelBeansSet.add(jTextFieldJBSetMethodToCall, gridBagConstraints);

        jCheckBoxisUseFieldDescription.setText("Use field description");
        jCheckBoxisUseFieldDescription.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxisUseFieldDescription.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanelBeansSet.add(jCheckBoxisUseFieldDescription, gridBagConstraints);

        add(jPanelBeansSet, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox jCheckBoxisUseFieldDescription;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanelBeansSet;
    private javax.swing.JRadioButton jRadioButtonJBSetArray;
    private javax.swing.JRadioButton jRadioButtonJBSetCollection;
    private javax.swing.JTextField jTextFieldJBSetFactoryClass;
    private javax.swing.JTextField jTextFieldJBSetMethodToCall;
    // End of variables declaration//GEN-END:variables
    
    
    public void setIReportConnection(IReportConnection c) {
        
        this.iReportConnection = c;
        if (iReportConnection instanceof JavaBeanDataSourceConnection)
        {
            JavaBeanDataSourceConnection con = (JavaBeanDataSourceConnection)iReportConnection;
            this.jTextFieldJBSetFactoryClass.setText( con.getFactoryClass());
            this.jTextFieldJBSetMethodToCall.setText(con.getMethodToCall());
            this.jCheckBoxisUseFieldDescription.setSelected(con.isUseFieldDescription());
            if (con.getType().equals(JavaBeanDataSourceConnection.BEAN_ARRAY)  )
            {
                jRadioButtonJBSetArray.setSelected(true);
                jRadioButtonJBSetCollection.setSelected(false);
            }
            else
            {
                jRadioButtonJBSetArray.setSelected(false);
                jRadioButtonJBSetCollection.setSelected(true);
            }
        }
    }

    public IReportConnection getIReportConnection() {
        
        IReportConnection irConn = irConn = new JavaBeanDataSourceConnection();
        
        ((JavaBeanDataSourceConnection)irConn).setFactoryClass( this.jTextFieldJBSetFactoryClass.getText().trim() );
        ((JavaBeanDataSourceConnection)irConn).setMethodToCall( this.jTextFieldJBSetMethodToCall.getText().trim() );
        ((JavaBeanDataSourceConnection)irConn).setUseFieldDescription( this.jCheckBoxisUseFieldDescription.isSelected() );
        if (jRadioButtonJBSetArray.isSelected())
        {
            ((JavaBeanDataSourceConnection)irConn).setType( JavaBeanDataSourceConnection.BEAN_ARRAY );
        }
        else
        {
           ((JavaBeanDataSourceConnection)irConn).setType( JavaBeanDataSourceConnection.BEAN_COLLECTION );
        }

        iReportConnection = irConn;
        return iReportConnection;
    }
    
     private void jRadioButtonJBSetArrayActionPerformed(java.awt.event.ActionEvent evt) {                                                       

    }
    /*
    public void applyI18n(){
                jCheckBoxisUseFieldDescription.setText(I18n.getString("connectionDialog.checkBoxisUseFieldDescription","Use field description"));
                jRadioButtonJBSetArray.setText(I18n.getString("connectionDialog.radioButtonJBSetArray","Array of javaBeans"));
                jRadioButtonJBSetCollection.setText(I18n.getString("connectionDialog.radioButtonJBSetCollection"," Collection of javaBeans"));
                
                jLabel12.setText(I18n.getString("connectionDialog.label12","Factory class (the class that will produce the set)"));
                jLabel13.setText("<html>" + I18n.getString("connectionDialog.label13","The static method to call to retrive the array or the the collection of javaBeans"));
    }
     */
    
    
}
