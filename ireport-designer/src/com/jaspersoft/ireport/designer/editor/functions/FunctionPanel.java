/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 * 
 * This program is part of iReport.
 * 
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * FunctionPanel.java
 *
 * Created on 10-ago-2010, 15.52.20
 */

package com.jaspersoft.ireport.designer.editor.functions;

import com.jaspersoft.ireport.designer.editor.ExpressionEditor2;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author gtoffoli
 */
public class FunctionPanel extends javax.swing.JPanel {

    /** Creates new form FunctionPanel */
    public FunctionPanel() {
        initComponents();
    }

    private List<JComboBox> editors = new ArrayList<JComboBox>();


    
    public void setFunction(Function function)
    {
        jLabelFunctionName.setText(function.getName() );
        jLabelDescriprion.setText("<html>" + function.getDescription());
        jLabelReturnType.setText("<html><b>" + function.getReturnType());


        int i=0;
        int preferredHeight = 0;
        jPanel1.removeAll();


        for (Parameter p : function.getParameters())
        {
            Component editor = p.getUI();
            if (editor == null)
            {
                editor = new JComboBox();
                ((JComboBox)editor).setEditable(true);
            }

            getEditors().add(((JComboBox)editor));
            
            JLabel label = new JLabel(p.getLabel());
            
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
            jPanel1.add(label, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
            jPanel1.add(editor, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weightx = 0.0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);

            JButton functionButton = new JButton();
            functionButton.setText("");
            functionButton.setActionCommand(""+i);
            functionButton.setIcon(null);
            functionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/editor/text_edit.png"))); // NOI18N
            functionButton.setMargin(new java.awt.Insets(0, 2, 0, 2));
            functionButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    int index = Integer.parseInt(evt.getActionCommand());
                    ExpressionEditor2 editor = new ExpressionEditor2();
                    editor.setExpression( getTextArg(index) );

                    if (editor.showDialog(Misc.getMainFrame()) == JOptionPane.OK_OPTION)
                    {
                        setTextArg(index, editor.getExpression());
                    }
                }
            });

            jPanel1.add(functionButton, gridBagConstraints);


            preferredHeight += Math.max( editor.getPreferredSize().height, label.getPreferredSize().height);
            i++;
        }

        jPanel1.setPreferredSize(new Dimension(0,preferredHeight));
        jPanel1.updateUI();
        this.doLayout();
    }

    public String getTextArg(int index)
    {
        if (getEditors().get(index).getSelectedItem() == null) return  "";
        return ""+getEditors().get(index).getSelectedItem();
    }

    public void setTextArg(int index, String text)
    {
        getEditors().get(index).setSelectedItem(text);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jLabelFunctionName = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabelDescriprion = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jLabelReturnType = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();

        jLabelFunctionName.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabelFunctionName.setText(org.openide.util.NbBundle.getMessage(FunctionPanel.class, "FunctionPanel.jLabelFunctionName.text")); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(FunctionPanel.class, "FunctionPanel.jLabel10.text")); // NOI18N

        jLabelDescriprion.setText(org.openide.util.NbBundle.getMessage(FunctionPanel.class, "FunctionPanel.jLabelDescriprion.text")); // NOI18N

        jButton15.setText(org.openide.util.NbBundle.getMessage(FunctionPanel.class, "FunctionPanel.jButton15.text")); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabelReturnType.setText(org.openide.util.NbBundle.getMessage(FunctionPanel.class, "FunctionPanel.jLabelReturnType.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(FunctionPanel.class, "FunctionPanel.jLabel8.text")); // NOI18N

        jPanel1.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabelReturnType, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabelDescriprion, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabelFunctionName)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFunctionName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDescriprion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelReturnType, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton15;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelDescriprion;
    private javax.swing.JLabel jLabelFunctionName;
    private javax.swing.JLabel jLabelReturnType;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the editors
     */
    public List<JComboBox> getEditors() {
        return editors;
    }

    /**
     * @param editors the editors to set
     */
    public void setEditors(List<JComboBox> editors) {
        this.editors = editors;
    }

}
