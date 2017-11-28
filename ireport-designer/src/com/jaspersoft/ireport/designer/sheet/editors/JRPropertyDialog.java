/*
 * Copyright (C) 2005 - 2008 JasperSoft Corporation.  All rights reserved. 
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
 * JRPropertyDialog.java
 * 
 * Created on 9 maggio 2003, 17.25
 *
 */

package com.jaspersoft.ireport.designer.sheet.editors;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.editor.ExpressionEditorArea;
import com.jaspersoft.ireport.designer.sheet.GenericProperty;
import com.jaspersoft.ireport.designer.tools.PropertyHint;
import com.jaspersoft.ireport.designer.tools.PropertyHintListCellRenderer;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
/**
 *
 * @author  Administrator
 */
public class JRPropertyDialog extends javax.swing.JDialog {
    /** Creates new form JRParameterDialog */
    GenericProperty tmpField = null;
    com.jaspersoft.ireport.designer.editor.ExpressionEditorArea expressionArea = null;
    private List<GenericProperty> properties = null;
    private String originalName = null;
    
    
    /**
     * this method is used to pass the correct subdataset to the expression editor
     */
    public void setExpressionContext( ExpressionContext expressionContext )
    {
        expressionArea.setExpressionContext(expressionContext);
    }
    
    public JRPropertyDialog(java.awt.Frame parent, boolean modal) { 
        this(parent, modal, false);
    }
    public JRPropertyDialog(java.awt.Frame parent, boolean modal, boolean canUseExpression) {        
        super(parent, modal);
        initComponents();
        applyI18n();
        
        jCheckBox1.setSelected(false);
        jCheckBox1.setVisible(canUseExpression);
        expressionArea = new ExpressionEditorArea();
        
        javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
        javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jButtonCancelActionPerformed(e);
            }
        };
       
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, I18n.getString("Global.Pane.Escape"));
        getRootPane().getActionMap().put(I18n.getString("Global.Pane.Escape"), escapeAction);


        jList1.setModel( new DefaultListModel());
        jList1.setCellRenderer(new PropertyHintListCellRenderer());
        addHints();
        
        //to make the default button ...
        this.getRootPane().setDefaultButton(this.jButtonOK);
    }

    private void addHints() {
        
        DefaultListModel dlm = (DefaultListModel)jList1.getModel();
        dlm.addElement(new PropertyHint("net.sf.jasperreports.text.truncate.at.char",
                I18n.getString("JRPropertyDialog.List.Prop1")
                ));
        
        dlm.addElement(new PropertyHint("net.sf.jasperreports.text.truncate.suffix",
                I18n.getString("JRPropertyDialog.List.Prop2")
                ));
        
        dlm.addElement(new PropertyHint("net.sf.jasperreports.print.keep.full.text",
                I18n.getString("JRPropertyDialog.List.Prop3")
                ));
        
        dlm.addElement(new PropertyHint("net.sf.jasperreports.text.measurer.factory",
                I18n.getString("JRPropertyDialog.List.Prop4")
                ));
        
    }
    
    public void addExporterHints()
    {
        DefaultListModel dlm = (DefaultListModel)jList1.getModel();
        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.character.encoding",
        "Default: UTF-8"));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.graphics2d.min.job.size",
        I18n.getString("JRPropertyDialog.List.Prop5") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.html.frames.as.nested.tables",
        I18n.getString("JRPropertyDialog.List.Prop6") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.html.remove.empty.space.between.rows",
        I18n.getString("JRPropertyDialog.List.Prop6") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.html.white.page.background",
        I18n.getString("JRPropertyDialog.List.Prop6") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.html.wrap.break.word",
        I18n.getString("JRPropertyDialog.List.Prop6") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.html.size.unit",
        I18n.getString("JRPropertyDialog.List.Prop6") +
        I18n.getString("JRPropertyDialog.List.DefaultPx")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.html.using.images.to.align",
        I18n.getString("JRPropertyDialog.List.Prop6") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.pdf.force.svg.shapes",
        I18n.getString("JRPropertyDialog.List.Prop7") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.pdf.force.linebreak.policy",
        I18n.getString("JRPropertyDialog.List.Prop7") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.pdf.create.batch.mode.bookmarks",
        I18n.getString("JRPropertyDialog.List.Prop7") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.pdf.compressed",
        I18n.getString("JRPropertyDialog.List.Prop7") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.pdf.encrypted",
        I18n.getString("JRPropertyDialog.List.Prop7") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.pdf.128.bit.key",
        I18n.getString("JRPropertyDialog.List.Prop7") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.create.custom.palette",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.one.page.per.sheet",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.remove.empty.space.between.rows",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.remove.empty.space.between.columns",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.white.page.background",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.detect.cell.type",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.size.fix.enabled",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.ignore.graphics",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.collapse.row.span",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.ignore.cell.border",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.DefaultFalse")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xls.max.rows.per.sheet",
        I18n.getString("JRPropertyDialog.List.Prop8") +
        I18n.getString("JRPropertyDialog.List.Default0")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.xml.validation",
        I18n.getString("JRPropertyDialog.List.Prop9") +
        I18n.getString("JRPropertyDialog.List.DefaultTrue")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.csv.field.delimiter",
        I18n.getString("JRPropertyDialog.List.Prop10") +
        I18n.getString("JRPropertyDialog.List.Default")));

        dlm.addElement(new PropertyHint("net.sf.jasperreports.export.csv.record.delimiter",
        I18n.getString("JRPropertyDialog.List.Prop10") +
        I18n.getString("JRPropertyDialog.List.Default1")));
    }
    
  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jPanelExpression = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jButtonOK = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setTitle(I18n.getString("JRPropertyDialog.Title.AddModProperty")); // NOI18N
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(I18n.getString("JRPropertyDialog.Label.PropName")); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(1000, 100));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 15));
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        getContentPane().add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        getContentPane().add(jTextFieldName, gridBagConstraints);

        jCheckBox1.setText(I18n.getString("JRPropertyDialog.CheckBox.UseExp")); // NOI18N
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(jCheckBox1, gridBagConstraints);

        jLabel4.setText(I18n.getString("JRPropertyDialog.Label.PropVal")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        getContentPane().add(jLabel4, gridBagConstraints);

        jPanelExpression.setMinimumSize(new java.awt.Dimension(10, 50));
        jPanelExpression.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 40));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 40));

        jTextAreaDescription.setMinimumSize(new java.awt.Dimension(0, 64));
        jTextAreaDescription.setPreferredSize(new java.awt.Dimension(0, 64));
        jScrollPane1.setViewportView(jTextAreaDescription);

        jPanelExpression.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        getContentPane().add(jPanelExpression, gridBagConstraints);

        jLabel5.setText(I18n.getString("JRPropertyDialog.Label.SpecialProp")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        getContentPane().add(jLabel5, gridBagConstraints);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 200));

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        jPanel1.setMinimumSize(new java.awt.Dimension(200, 35));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 35));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonOK.setMnemonic('o');
        jButtonOK.setText(I18n.getString("Global.Button.Ok")); // NOI18N
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonOK);

        jButtonCancel.setMnemonic('c');
        jButtonCancel.setText(I18n.getString("Global.Button.Cancel")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonCancel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        getContentPane().add(jPanel1, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-515)/2, (screenSize.height-358)/2, 515, 358);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        setVisible(false);
        this.setDialogResult( javax.swing.JOptionPane.CANCEL_OPTION);
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        
        if (this.jTextFieldName.getText().trim().length() <= 0)
        {
            javax.swing.JOptionPane.showMessageDialog(this,
                    I18n.getString("JRPropertyDialog.Message.Warning"),I18n.getString("JRPropertyDialog.Message.Warning2"),
                    //I18n.getString( "messages.jRPropertyDialog.notValidName","Please insert a valid property name!"),
                    //I18n.getString( "messages.jRPropertyDialog.notValidNameCaption","Invalid property!"),
                    javax.swing.JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        // check if the name is valid...
        if (getProperties() != null)
        {
            String name = jTextFieldName.getText().trim();
            for (GenericProperty prop : getProperties())
            {
                if (prop.getKey().equals(name) && (originalName == null ||
                        !originalName.equals(name)))
                {
                    
                    javax.swing.JOptionPane.showMessageDialog(this,
                    I18n.getString("JRPropertyDialog.Message.warning3"),I18n.getString("JRPropertyDialog.Message.Warning4"),
                    //I18n.getString( "messages.jRPropertyDialog.notValidName","Please insert a valid property name!"),
                    //I18n.getString( "messages.jRPropertyDialog.notValidNameCaption","Invalid property!"),
                    javax.swing.JOptionPane.WARNING_MESSAGE );
                    return;
                }
            }
        }
        
        tmpField = new GenericProperty();
        tmpField.setKey( jTextFieldName.getText().trim() );
        tmpField.setUseExpression( jCheckBox1.isSelected() );
        
        if (jCheckBox1.isSelected())
        {
            tmpField.setValue( Misc.createExpression("java.lang.Object", this.expressionArea.getText() ));
        }
        else
        {
            tmpField.setValue( this.jTextAreaDescription.getText() );
        }
        
        setVisible(false);
        this.setDialogResult( javax.swing.JOptionPane.OK_OPTION);
        dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        this.setDialogResult( javax.swing.JOptionPane.CLOSED_OPTION);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        
        if (evt.getClickCount() == 2 &&
            SwingUtilities.isLeftMouseButton(evt))
        {
            if (jList1.getSelectedValue() != null &&
                jList1.getSelectedValue() instanceof PropertyHint)
            {
                PropertyHint hint = (PropertyHint)jList1.getSelectedValue();
                jTextFieldName.setText( hint.getPropertyName());
            }
        }
        
        
    }//GEN-LAST:event_jList1MouseClicked

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        
        updateExpressionPanel();
        
    }//GEN-LAST:event_jCheckBox1StateChanged
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new JRPropertyDialog(new javax.swing.JFrame(), true).setVisible(true);
    }
    
    /** Getter for property tmpParameter.
     * @return Value of property tmpParameter.
     *
     */
    public GenericProperty getProperty() {
        return tmpField;
    }    
    
    /** Setter for property tmpParameter.
     * @param tmpParameter New value of property tmpParameter.
     *
     */
    public void setProperty(GenericProperty tmpField) {
        this.jTextFieldName.setText( tmpField.getKey());
        if (!tmpField.isUseExpression())
        {
            this.jTextAreaDescription.setText( ""+tmpField.getValue()); 
        }
        this.expressionArea.setText( Misc.getExpressionText(tmpField.getExpression())); 
        this.jCheckBox1.setSelected( tmpField.isUseExpression() );
        originalName = tmpField.getKey();
        updateExpressionPanel();
    }
    
    /** Getter for property dialogResult.
     * @return Value of property dialogResult.
     *
     */
    public int getDialogResult() {
        return dialogResult;
    }
    
    /** Setter for property dialogResult.
     * @param dialogResult New value of property dialogResult.
     *
     */
    public void setDialogResult(int dialogResult) {
        this.dialogResult = dialogResult;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelExpression;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables

    private int dialogResult;    
    
    public void applyI18n(){
        /*
                // Start autogenerated code ----------------------
                jButtonCancel.setText(I18n.getString("jRPropertyDialog.buttonCancel","Cancel"));
                jButtonOK.setText(I18n.getString("jRPropertyDialog.buttonOK","OK"));
                jLabel1.setText(I18n.getString("jRPropertyDialog.label1","Property name"));
                jLabel4.setText(I18n.getString("jRPropertyDialog.label4","Property value"));
                // End autogenerated code ----------------------
                
                this.setTitle(I18n.getString("jRPropertyDialog.title","Add/modify property"));
                jButtonCancel.setMnemonic(I18n.getString("jRPropertyDialog.buttonCancelMnemonic","c").charAt(0));
                jButtonOK.setMnemonic(I18n.getString("jRPropertyDialog.buttonOKMnemonic","o").charAt(0));
         */
    }

    private void updateExpressionPanel() {
        jPanelExpression.removeAll();
        
        if (jCheckBox1.isSelected())
        {
            jPanelExpression.add(expressionArea, BorderLayout.CENTER);
            if (jScrollPane1.getBorder() != null)
            {
                jPanelExpression.setBorder( jScrollPane1.getBorder() );
            }
        }
        else
        {
            jPanelExpression.add(jScrollPane1, BorderLayout.CENTER);
            jPanelExpression.setBorder( null );
        }
        jPanelExpression.updateUI();
    }

    public List<GenericProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<GenericProperty> properties) {
        this.properties = properties;
    }
}
