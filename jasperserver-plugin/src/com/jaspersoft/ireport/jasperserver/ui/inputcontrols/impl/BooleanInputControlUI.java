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
 */
package com.jaspersoft.ireport.jasperserver.ui.inputcontrols.impl;

import javax.swing.JLabel;

/**
 *
 * @author  gtoffoli
 */
public class BooleanInputControlUI extends javax.swing.JPanel implements InputControlUI {
    
    /** Creates new form BasicInputControlUI */
    public BooleanInputControlUI() {
        initComponents();
    }
    
    public Object getValue()
    {
        return new Boolean(jCheckBox1.isSelected());
    }
    
    public void setValue(Object v)
    {
        try {
            jCheckBox1.setSelected( Boolean.valueOf(v+"").booleanValue() );
        } catch (Exception ex)
        {
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jCheckBox1 = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        setPreferredSize(new java.awt.Dimension(250, 24));
        jCheckBox1.setText("jCheckBox1");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(jCheckBox1, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    public void setLabel(String lbl)
    {
        this.jCheckBox1.setText(lbl);
    }
    
    public void setHistory(java.util.List values){
        
        if (values != null && values.size() > 0)
        {
            setValue(values.get(0)+"");
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    // End of variables declaration//GEN-END:variables
    
    public void setReadOnly( boolean b )
    {
        jCheckBox1.setEnabled(!b);
    }
}
