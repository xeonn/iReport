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
package com.jaspersoft.ireport.jasperserver.ui.inputcontrols.impl;

import com.jaspersoft.ireport.designer.IReportManager;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 *
 * @author  gtoffoli
 */
public class DateInputControlUI extends javax.swing.JPanel implements InputControlUI {
    
    /** Creates new form BasicInputControlUI */
    public DateInputControlUI() {
        initComponents();
    }
    
    public Object getValue()
    {
        
        if (IReportManager.getPreferences().getBoolean("jasperserver.useRelativeDateExpressions", true))
        {
            if (dateRangePicker.getDateRangeExpression() != null)
            {
                return dateRangePicker.getDateRangeExpression();

            }
        }
        
        return dateRangePicker.getDate();
    }
    
    public void setValue(Object v)
    {
        /*
        for (int i=0; i<getJComboBoxValue().getItemCount(); ++i)
        {
            Object val = getJComboBoxValue().getItemAt(i);
            if (val.equals( v))
            {
                getJComboBoxValue().setSelectedIndex(i);
                return;
            }
        }
        
        getJComboBoxValue().setSelectedItem(v);
        */
        if (v == null) return;
        if (v instanceof Date)
        {
            dateRangePicker.setDate((Date)v);
        }
        if (v instanceof String)
        {
            dateRangePicker.setDateRangeExpression((String)v);
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

        jLabelName = new javax.swing.JLabel();
        dateRangePicker = new com.jaspersoft.ireport.designer.compiler.prompt.DateRangeDatePicker();

        setPreferredSize(new java.awt.Dimension(250, 40));
        setLayout(new java.awt.GridBagLayout());

        jLabelName.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        add(jLabelName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 4);
        add(dateRangePicker, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    public void setLabel(String lbl)
    {
        this.jLabelName.setText(lbl);
    }
    
    public void setHistory(java.util.List values){

    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jaspersoft.ireport.designer.compiler.prompt.DateRangeDatePicker dateRangePicker;
    private javax.swing.JLabel jLabelName;
    // End of variables declaration//GEN-END:variables
    
    public void setReadOnly( boolean b )
    {
        jLabelName.setEnabled(!b);
        dateRangePicker.setEnabled(!b);

    }

    public void addActionListener(ActionListener listener) {
        dateRangePicker.addActionListener(listener);
    }
}
