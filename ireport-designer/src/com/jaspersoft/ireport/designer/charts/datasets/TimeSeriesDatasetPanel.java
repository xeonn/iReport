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
package com.jaspersoft.ireport.designer.charts.datasets;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.sheet.Tag;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.event.ActionEvent;
import java.util.*;
import net.sf.jasperreports.charts.design.JRDesignTimeSeries;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignHyperlink;
import net.sf.jasperreports.engine.xml.JRXmlConstants;

/**
 *
 * @author  Administrator
 */
public class TimeSeriesDatasetPanel extends javax.swing.JPanel implements ChartDatasetPanel {
    
    private JRDesignTimeSeriesDataset timeSeriesDataset = null;
    private boolean init = false;
    private ExpressionContext expressionContext = null;
    
    /** Creates new form PieDatasetPanel */
    public TimeSeriesDatasetPanel() {
        initComponents();
        //applyI18n();
        
        // Year | Quarter | Month | Week | Day | Hour | Minute | Second | Milisecond
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Year")),I18n.getString("Global.ComboBox.Year")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Quarter")),I18n.getString("Global.ComboBox.Quarter")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Month")),I18n.getString("Global.ComboBox.Month")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Week")),I18n.getString("Global.ComboBox.Week")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Day")),I18n.getString("Global.ComboBox.Day")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Hour")),I18n.getString("Global.ComboBox.Hour")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Minute")),I18n.getString("Global.ComboBox.Minute")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Second")),I18n.getString("Global.ComboBox.Second")));
        this.jComboBoxPeriod.addItem(new Tag(JRXmlConstants.getTimePeriod(I18n.getString("Global.ComboBox.Milisecond")),I18n.getString("Global.ComboBox.Milisecond")));
        
        jList1.setModel( new javax.swing.DefaultListModel());
        jList1.setCellRenderer(new DatasetListsCellRenderer());
    }

    public void setTimeSeriesDataset(JRDesignTimeSeriesDataset timeSeriesDataset) {
        
        init = true;
        this.timeSeriesDataset = timeSeriesDataset;
        
        jButtonModify.setEnabled( false );
        jButtonModify.setEnabled( false );
        javax.swing.DefaultListModel lm = (javax.swing.DefaultListModel)jList1.getModel();
        
        lm.removeAllElements();
        
        List series = timeSeriesDataset.getSeriesList();
                        
        for (int i=0; i< series.size(); ++i)
        {
            lm.addElement(series.get(i) );
        }
        
        if (timeSeriesDataset.getTimePeriod() == null)
        {
            timeSeriesDataset.setTimePeriod(JRXmlConstants.getTimePeriod("Day"));
        }
        
        Misc.setComboboxSelectedTagValue(jComboBoxPeriod, timeSeriesDataset.getTimePeriod() );
        init = false;
    }

    public JRDesignTimeSeriesDataset getTimeSeriesDataset() {
        return timeSeriesDataset;
    }
  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenuSeries = new javax.swing.JPopupMenu();
        jMenuItemCopy = new javax.swing.JMenuItem();
        jMenuItemPaste = new javax.swing.JMenuItem();
        jPanelPeriod = new javax.swing.JPanel();
        jLabelPeriod = new javax.swing.JLabel();
        jComboBoxPeriod = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonModify = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        jMenuItemCopy.setText(I18n.getString("TimeseriesDatasetPanel.Menu.Copy_series")); // NOI18N
        jMenuItemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopyActionPerformed(evt);
            }
        });
        jPopupMenuSeries.add(jMenuItemCopy);

        jMenuItemPaste.setText(I18n.getString("TimeseriesDatasetPanel.Menu.Paste_series")); // NOI18N
        jMenuItemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteActionPerformed(evt);
            }
        });
        jPopupMenuSeries.add(jMenuItemPaste);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        jPanelPeriod.setLayout(new java.awt.GridBagLayout());

        jLabelPeriod.setText(I18n.getString("TimeseriesDatasetPanel.Label.Time_period")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanelPeriod.add(jLabelPeriod, gridBagConstraints);

        jComboBoxPeriod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPeriodActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        jPanelPeriod.add(jComboBoxPeriod, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanelPeriod, gridBagConstraints);

        jLabel1.setText(I18n.getString("TimeseriesDatasetPanel.Label.Time_series")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        add(jLabel1, gridBagConstraints);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(jScrollPane1, gridBagConstraints);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 0));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButtonAdd.setText(I18n.getString("Global.Button.Add")); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 4);
        jPanel1.add(jButtonAdd, gridBagConstraints);

        jButtonModify.setText(I18n.getString("Global.Button.Modify")); // NOI18N
        jButtonModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 4);
        jPanel1.add(jButtonModify, gridBagConstraints);

        jButtonRemove.setText(I18n.getString("Global.Button.Remove")); // NOI18N
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 4);
        jPanel1.add(jButtonRemove, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 99;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        openExtraWindows();
    }//GEN-LAST:event_formComponentShown

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount() == 1 && evt.getButton() == evt.BUTTON3)
        {
            jMenuItemCopy.setEnabled(jList1.getSelectedIndex() >= 0);
            jMenuItemPaste.setEnabled( IReportManager.getInstance().getChartSeriesClipBoard() != null &&
                                       IReportManager.getInstance().getChartSeriesClipBoard().size() > 0);
            
            jPopupMenuSeries.show(jList1, evt.getPoint().x, evt.getPoint().y);
        }
        else if (evt.getClickCount() == 2 && evt.getButton() == evt.BUTTON1)
        {
            jButtonModifyActionPerformed(null);
        } 
    }//GEN-LAST:event_jList1MouseClicked

    private void jMenuItemPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPasteActionPerformed
        
        java.util.List series = IReportManager.getInstance().getChartSeriesClipBoard();
        //getChartSeriesClipBoard()
        
        if (series != null && series.size() > 0)
        {
            for (int i=0; i<series.size(); ++i)
            {
                if (series.get(i) instanceof JRDesignTimeSeries)
                {
                    JRDesignTimeSeries cs = (JRDesignTimeSeries)series.get(i);
                    try {
                    cs = (JRDesignTimeSeries)cs.clone();
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                        continue;
                    }
                    timeSeriesDataset.addTimeSeries(cs);
                    ((javax.swing.DefaultListModel)jList1.getModel()).addElement(cs);
                }
            }
            jList1.updateUI();
        }
        
    }//GEN-LAST:event_jMenuItemPasteActionPerformed

    private void jMenuItemCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCopyActionPerformed
        Object[] values = jList1.getSelectedValues();
        java.util.List copy_c = new ArrayList();
        try {
            for (int i=0; i<values.length; ++i) copy_c.add( ((JRDesignTimeSeries)values[i]).clone() );
            IReportManager.getInstance().setChartSeriesClipBoard(copy_c);
        } catch (Exception ex) { }
    }//GEN-LAST:event_jMenuItemCopyActionPerformed

    private void jComboBoxPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPeriodActionPerformed

        if (init || timeSeriesDataset == null) return;
        timeSeriesDataset.setTimePeriod( (Class) ((Tag)jComboBoxPeriod.getSelectedItem()).getValue());
        
    }//GEN-LAST:event_jComboBoxPeriodActionPerformed

    private void jButtonModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyActionPerformed
        
        if (jList1.getSelectedIndex() >= 0)
        {
            JRDesignTimeSeries cs = (JRDesignTimeSeries)jList1.getSelectedValue();
            TimeSeriesDialog csd = new TimeSeriesDialog(Misc.getMainFrame() ,true);
            csd.setExpressionContext( this.getExpressionContext() );
            
            csd.setSeriesExpression( (JRDesignExpression)cs.getSeriesExpression() );
            csd.setTimePeriodExpression( (JRDesignExpression)cs.getTimePeriodExpression() );
            csd.setValueExpression( (JRDesignExpression)cs.getValueExpression() );
            csd.setLabelExpression( (JRDesignExpression)cs.getLabelExpression() );
            JRDesignHyperlink link = new JRDesignHyperlink();
            ModelUtils.copyHyperlink(cs.getItemHyperlink(), link);
            csd.setSectionItemHyperlink( link  );
            
            if (newInfo != null)
            {
                csd.setFocusedExpression(newInfo);
            }
            
            csd.setVisible(true);
            
            if (csd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION)
            {
                cs.setSeriesExpression( csd.getSeriesExpression() );
                cs.setTimePeriodExpression( csd.getTimePeriodExpression() );
                cs.setValueExpression( csd.getValueExpression() );
                cs.setLabelExpression( csd.getLabelExpression() );
                cs.setItemHyperlink( csd.getSectionItemHyperlink() );   

                jList1.updateUI();
            }
        
        }
    }//GEN-LAST:event_jButtonModifyActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed

        TimeSeriesDialog csd = new TimeSeriesDialog(Misc.getMainFrame() ,true);
        csd.setExpressionContext( this.getExpressionContext() );
        csd.setVisible(true);
        
        if (csd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION)
        {
            JRDesignTimeSeries cs = new JRDesignTimeSeries();
            cs.setSeriesExpression( csd.getSeriesExpression() );
            cs.setTimePeriodExpression( csd.getTimePeriodExpression() );
            cs.setValueExpression( csd.getValueExpression() );
            cs.setLabelExpression( csd.getLabelExpression() );
            //cs.setSectionItemHyperlink( csd.getSectionItemHyperlink() );
            cs.setItemHyperlink( csd.getSectionItemHyperlink() ); 
            
            getTimeSeriesDataset().getSeriesList().add(cs);
            ((javax.swing.DefaultListModel)jList1.getModel()).addElement(cs);
        }
        
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed

        while (jList1.getSelectedIndex() >= 0)
        {
            getTimeSeriesDataset().getSeriesList().remove( jList1.getSelectedValue() );
            ((javax.swing.DefaultListModel)jList1.getModel()).removeElementAt(jList1.getSelectedIndex());
        }
        
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

        if (jList1.getSelectedIndex() >= 0)
        {
            jButtonModify.setEnabled( true );
            jButtonRemove.setEnabled( true );
        }
        else
        {
            jButtonModify.setEnabled( false );
            jButtonRemove.setEnabled( false );
        }
    }//GEN-LAST:event_jList1ValueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JComboBox jComboBoxPeriod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelPeriod;
    private javax.swing.JList jList1;
    private javax.swing.JMenuItem jMenuItemCopy;
    private javax.swing.JMenuItem jMenuItemPaste;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelPeriod;
    private javax.swing.JPopupMenu jPopupMenuSeries;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    /*
     public void applyI18n()
    {
                // Start autogenerated code ----------------------
                // End autogenerated code ----------------------
                // Start autogenerated code ----------------------
                jLabel1.setText(I18n.getString("timeSeriesDatasetPanel.label1","Time series"));
                // End autogenerated code ----------------------
        jButtonAdd.setText(it.businesslogic.ireport.util.I18n.getString("charts.newseries", "Add series"));
        jButtonModify.setText(it.businesslogic.ireport.util.I18n.getString("charts.modifyseries", "Modify series"));
        jButtonRemove.setText(it.businesslogic.ireport.util.I18n.getString("charts.removeseries", "Remove series"));
        jLabelPeriod.setText(it.businesslogic.ireport.util.I18n.getString("charts.timePeriod", "Time period"));
        
        jMenuItemCopy.setText(it.businesslogic.ireport.util.I18n.getString("charts.copyseries", "Copy series"));
        jMenuItemPaste.setText(it.businesslogic.ireport.util.I18n.getString("charts.pasteseries", "Paste series"));
        
        this.updateUI();
        
    }
     */

    public void setExpressionContext(ExpressionContext ec) {
        this.expressionContext = ec;
    }
    
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public static final int COMPONENT_NONE=0;
    public static final int COMPONENT_TIME_SERIES_LIST=1;
    
    /**
     * This variable is checked by openExtraWindows() called when the component is shown.
     * If the value is != 0, the modify button will be action-performed.
     */
    public Object[]  newInfo = null;
    /**
     * This method set the focus on a specific component.
     * 
     * For this kind of datasource otherInfo must be:
     * [0] = Fixed to COMPONENT_PERIOD_SERIES_LIST (used for future extensions)
     * [1] = Integer, the category series to edit
     * [2] = The expression id in the category window to focus on 
     * [3] = The expression in the hyperlink...
     * [4] = The hyperlink parameter
     * [5] = The expression of the hyperlink parameter
     */
    public void setFocusedExpression(Object[] expressionInfo)
    {
        if (expressionInfo == null) return;
        int expID = ((Integer)expressionInfo[0]).intValue();
        switch (expID)
        {
            case COMPONENT_TIME_SERIES_LIST:
                int index = ((Integer)expressionInfo[1]).intValue();
                
                if (index >=0 && jList1.getModel().getSize() > index )
                {
                    jList1.setSelectedIndex(index);
                    newInfo = new Object[expressionInfo.length-2];
                    for (int i=2; i< expressionInfo.length; ++i) newInfo[i-2] = expressionInfo[i];
                    break;
                }
        }
    }
    
    /**
     * This method checks for the variable subExpID. It is called when the component is shown.
     * If the value is >= 0, the modify button will be action-performed
     */
    private void openExtraWindows()
    {
        if (newInfo != null)
        {
            jButtonModifyActionPerformed(new ActionEvent(jButtonModify,0,""));// NOI18N
        }
        newInfo = null;
    }
    
    public void containerWindowOpened() {
        openExtraWindows();
    }
     
}
