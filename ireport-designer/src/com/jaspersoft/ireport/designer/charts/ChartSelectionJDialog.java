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
 *
 *
 *
 *
 * ChartSelectionJDialog.java
 * 
 * Created on 8 luglio 2005, 23.26
 *
 */

package com.jaspersoft.ireport.designer.charts;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.charts.ChartDescriptor;
import com.jaspersoft.ireport.designer.charts.JListView;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.*;
import java.util.*;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;
/**
 *
 * @author  Administrator
 */
public class ChartSelectionJDialog extends javax.swing.JDialog {
    
    private int dialogResult = javax.swing.JOptionPane.CANCEL_OPTION;
    private JListView jListView = null;
    private JList jList1 = null;
    private JasperDesign jasperDesign = null;

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }

    public void setJasperDesign(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
    }
    
    private JRDesignChart chart = null;
    
    private boolean multiAxisMode = false;
    
    public ChartSelectionJDialog(Dialog parent, boolean modal) 
    {
         super(parent,modal);
         initAll();
    }

    /** Creates new form ReportQueryFrame */
    public ChartSelectionJDialog(Frame parent, boolean modal) 
    {
         super(parent,modal);
         initAll();
    }

    
    public void updateCharts()
    {
        
        javax.swing.DefaultListModel dlm = (javax.swing.DefaultListModel)jList1.getModel();
        
        dlm.removeAllElements();
        
        if (!isMultiAxisMode())  dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/pie.png",I18n.getString("ChartSelectionJDialog.Chart.Pie"), JRDesignChart.CHART_TYPE_PIE));
        if (!isMultiAxisMode())dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/pie3d.png",I18n.getString("ChartSelectionJDialog.Chart.Pie3D"), JRDesignChart.CHART_TYPE_PIE3D));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/bar.png",I18n.getString("ChartSelectionJDialog.Chart.Bar"), JRDesignChart.CHART_TYPE_BAR));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/bar3d.png",I18n.getString("ChartSelectionJDialog.Chart.Bar3D"), JRDesignChart.CHART_TYPE_BAR3D));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/xybar.png",I18n.getString("ChartSelectionJDialog.Chart.YX_Bar"), JRDesignChart.CHART_TYPE_XYBAR));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/stackedbar.png",I18n.getString("ChartSelectionJDialog.Chart.Stacked_Bar"), JRDesignChart.CHART_TYPE_STACKEDBAR));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/stackedbar3d.png",I18n.getString("ChartSelectionJDialog.Chart.Stacked_Bar_3D"), JRDesignChart.CHART_TYPE_STACKEDBAR3D));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/line.png",I18n.getString("ChartSelectionJDialog.Chart.Line"), JRDesignChart.CHART_TYPE_LINE));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/xyline.png",I18n.getString("ChartSelectionJDialog.Chart.XY_Line"), JRDesignChart.CHART_TYPE_XYLINE));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/area.png",I18n.getString("ChartSelectionJDialog.Chart.Area"), JRDesignChart.CHART_TYPE_AREA));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/xyarea.png",I18n.getString("ChartSelectionJDialog.Chart.YX_Area"), JRDesignChart.CHART_TYPE_XYAREA));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/stackedarea.png",I18n.getString("ChartSelectionJDialog.Chart.Stacked_Area"), JRDesignChart.CHART_TYPE_STACKEDAREA));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/scatter.png",I18n.getString("ChartSelectionJDialog.Chart.Scatter"), JRDesignChart.CHART_TYPE_SCATTER));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/bubble.png",I18n.getString("ChartSelectionJDialog.Chart.Bubble"), JRDesignChart.CHART_TYPE_BUBBLE));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/timeseries.png",I18n.getString("ChartSelectionJDialog.Chart.Time_Series"), JRDesignChart.CHART_TYPE_TIMESERIES));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/highlow.png",I18n.getString("ChartSelectionJDialog.Chart.High_Low"), JRDesignChart.CHART_TYPE_HIGHLOW));
        dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/candlestick.png",I18n.getString("ChartSelectionJDialog.Chart.Candlestick"), JRDesignChart.CHART_TYPE_CANDLESTICK));
        if (!isMultiAxisMode()) dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/meter.png",I18n.getString("ChartSelectionJDialog.Chart.Meter"), JRDesignChart.CHART_TYPE_METER));
        if (!isMultiAxisMode()) dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/thermometer.png",I18n.getString("ChartSelectionJDialog.Chart.Thermometer"), JRDesignChart.CHART_TYPE_THERMOMETER));
        if (!isMultiAxisMode()) dlm.addElement(new ChartDescriptor("/com/jaspersoft/ireport/designer/charts/icons/multiaxis.png",I18n.getString("ChartSelectionJDialog.Chart.Multi_Axis"), JRDesignChart.CHART_TYPE_MULTI_AXIS));
        
        jList1.updateUI();
        
    }
    
    public void initAll()
    {
        initComponents();
        //applyI18n();
        
        this.setDialogResult( javax.swing.JOptionPane.CANCEL_OPTION);
        jListView = new JListView();
        jList1 = (JList)jListView.getList();
        
        
        jPanelChartType.add(jListView, java.awt.BorderLayout.CENTER);
        
        javax.swing.DefaultListModel dlm =  new javax.swing.DefaultListModel() ;
        jList1.setModel(dlm );
        jList1.setCellRenderer(new ChartCellRenderer());
        
        updateCharts();
        
        
        jList1.setLayoutOrientation( JList.HORIZONTAL_WRAP);
        this.setSize(400,400);
        setLocationRelativeTo(null);
        
        jList1.setSelectionMode( DefaultListSelectionModel.SINGLE_SELECTION );
        jList1.addListSelectionListener( new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) 
            {
                selectedChart();
            }
            
        });
        
        javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
        javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jButtonCancelActionPerformed(e);
            }
        };
       
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, I18n.getString("Global.Pane.Escape"));
        getRootPane().getActionMap().put(I18n.getString("Global.Pane.Escape"), escapeAction);


        //to make the default button ...
        this.getRootPane().setDefaultButton(this.jButtonOk);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelChartType = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabelChartNameVal = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelChartType.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Chart info"));
        jPanel5.setMinimumSize(new java.awt.Dimension(10, 50));
        jPanel5.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel5.add(jLabelChartNameVal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jPanel6, gridBagConstraints);

        jPanelChartType.add(jPanel5, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanelChartType, java.awt.BorderLayout.CENTER);

        jPanel1.setMinimumSize(new java.awt.Dimension(10, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(10, 30));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jButtonOk.setText(I18n.getString("Global.Button.Ok")); // NOI18N
        jButtonOk.setEnabled(false);
        jButtonOk.setMaximumSize(new java.awt.Dimension(200, 25));
        jButtonOk.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 4);
        jPanel1.add(jButtonOk, gridBagConstraints);

        jButtonCancel.setText(I18n.getString("Global.Button.Cancel")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        jPanel1.add(jButtonCancel, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        
        setDialogResult( javax.swing.JOptionPane.CANCEL_OPTION );
        this.setVisible(false);
        this.dispose();    
        
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed

        setDialogResult( javax.swing.JOptionPane.OK_OPTION );
        
        ChartDescriptor cd = (ChartDescriptor)jList1.getSelectedValue();
        try {
            this.setChart(new JRDesignChart(getJasperDesign(), cd.getChartType()) );
        } catch (Exception ex)
        {
            
        }
        this.setVisible(true);
        this.dispose();    
        
    }//GEN-LAST:event_jButtonOkActionPerformed
    
    public int getDialogResult() {
        return dialogResult;
    }

    public void setDialogResult(int dialogResult) {
        this.dialogResult = dialogResult;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelChartNameVal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelChartType;
    // End of variables declaration//GEN-END:variables

      void selectedChart()
    {
          if (jList1.getSelectedIndex() >= 0)
          {
            ChartDescriptor cd = (ChartDescriptor)jList1.getSelectedValue();
            this.jLabelChartNameVal.setText(cd.getDisplayName());
            
            this.jButtonOk.setEnabled(true);
          }
          else
          {
              this.jButtonOk.setEnabled(false);
          }
    }

    public JRDesignChart getChart() {
        return chart;
    }

    public void setChart(JRDesignChart chart) {
        this.chart = chart;
    }

    public boolean isMultiAxisMode() {
        return multiAxisMode;
    }

    public void setMultiAxisMode(boolean multiAxisMode) {
        this.multiAxisMode = multiAxisMode;
        
        updateCharts();
    }
    
    /*
    public void applyI18n(){
                // Start autogenerated code ----------------------
                jButtonCancel.setText(I18n.getString("chartSelectionJDialog.buttonCancel","Cancel"));
                jButtonOk.setText(I18n.getString("chartSelectionJDialog.buttonOk","OK"));
                // End autogenerated code ----------------------
                ((javax.swing.border.TitledBorder)jPanel5.getBorder()).setTitle( I18n.getString("chartSelectionJDialog.panelBorder.ChartInfo","Chart info") );
                            
                jButtonCancel.setMnemonic(I18n.getString("chartSelectionJDialog.buttonCancelMnemonic","c").charAt(0));
                jButtonOk.setMnemonic(I18n.getString("chartSelectionJDialog.buttonOkMnemonic","o").charAt(0));
                
    }
    */
}
