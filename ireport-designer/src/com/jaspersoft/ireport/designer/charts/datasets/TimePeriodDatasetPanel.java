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
 * TimePeriodDatasetPanel.java
 * 
 * Created on 15 agosto 2005, 17.55
 *
 */

package com.jaspersoft.ireport.designer.charts.datasets;


import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.editor.ExpressionContext;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.event.ActionEvent;
import java.util.*;
import net.sf.jasperreports.charts.design.JRDesignTimePeriodDataset;
import net.sf.jasperreports.charts.design.JRDesignTimePeriodSeries;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignHyperlink;

/**
 *
 * @author  Administrator
 */
public class TimePeriodDatasetPanel extends javax.swing.JPanel implements ChartDatasetPanel {
    
    private JRDesignTimePeriodDataset timePeriodDataset = null;
    private ExpressionContext expressionContext = null;
    
    /** Creates new form PieDatasetPanel */
    public TimePeriodDatasetPanel() {
        initComponents();
        //applyI18n();
        jList1.setModel( new javax.swing.DefaultListModel());
        jList1.setCellRenderer(new DatasetListsCellRenderer());
    }

    public void setTimePeriodDataset(JRDesignTimePeriodDataset timePeriodDataset) {
        this.timePeriodDataset = timePeriodDataset;
        
        
        jButtonModify.setEnabled( false );
        jButtonRemove.setEnabled( false );
        javax.swing.DefaultListModel lm = (javax.swing.DefaultListModel)jList1.getModel();
        
        lm.removeAllElements();
        
        List series = timePeriodDataset.getSeriesList();
                        
        for (int i=0; i< series.size(); ++i)
        {
            lm.addElement(series.get(i) );
        }

    }

    public JRDesignTimePeriodDataset getTimePeriodDataset() {
        return timePeriodDataset;
    }
  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenuSeries = new javax.swing.JPopupMenu();
        jMenuItemCopy = new javax.swing.JMenuItem();
        jMenuItemPaste = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonModify = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jMenuItemCopy.setText("Copy series");
        jMenuItemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopyActionPerformed(evt);
            }
        });

        jPopupMenuSeries.add(jMenuItemCopy);

        jMenuItemPaste.setText("Paste series");
        jMenuItemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteActionPerformed(evt);
            }
        });

        jPopupMenuSeries.add(jMenuItemPaste);

        setLayout(new java.awt.GridBagLayout());

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(jScrollPane1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 0));
        jButtonAdd.setText("Add");
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

        jButtonModify.setText("Modify");
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

        jButtonRemove.setText("Remove");
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);

        jLabel1.setText("Time period series");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        add(jLabel1, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        openExtraWindows();
    }//GEN-LAST:event_formComponentShown

    private void jMenuItemPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPasteActionPerformed
        java.util.List series = IReportManager.getInstance().getChartSeriesClipBoard();
        //getChartSeriesClipBoard()
        
        if (series != null && series.size() > 0)
        {
            for (int i=0; i<series.size(); ++i)
            {
                if (series.get(i) instanceof JRDesignTimePeriodSeries)
                {
                    JRDesignTimePeriodSeries cs = (JRDesignTimePeriodSeries)series.get(i);
                    try {
                    cs = (JRDesignTimePeriodSeries)cs.clone();
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                        continue;
                    }
                    timePeriodDataset.addTimePeriodSeries(cs);
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
            for (int i=0; i<values.length; ++i) copy_c.add( ((JRDesignTimePeriodSeries)values[i]).clone() );
            IReportManager.getInstance().setChartSeriesClipBoard(copy_c);
        } catch (Exception ex) { }
    }//GEN-LAST:event_jMenuItemCopyActionPerformed

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

    private void jButtonModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyActionPerformed
        
        if (jList1.getSelectedIndex() >= 0)
        {
            JRDesignTimePeriodSeries cs = (JRDesignTimePeriodSeries)jList1.getSelectedValue();
            TimePeriodSeriesDialog csd = new TimePeriodSeriesDialog(Misc.getMainFrame() ,true);
            
            csd.setSeriesExpression( (JRDesignExpression)cs.getSeriesExpression() );
            csd.setStartDateExpression( (JRDesignExpression)cs.getStartDateExpression() );
            csd.setEndDateExpression( (JRDesignExpression)cs.getEndDateExpression() );
            csd.setValueExpression( (JRDesignExpression)cs.getValueExpression() );
            csd.setLabelExpression( (JRDesignExpression)cs.getLabelExpression() );
            JRDesignHyperlink link = new JRDesignHyperlink();
            ModelUtils.copyHyperlink(cs.getItemHyperlink(), link);
            csd.setSectionItemHyperlink( link  );
            
            csd.setExpressionContext( this.getExpressionContext() );
            if (newInfo != null)
            {
                csd.setFocusedExpression(newInfo);
            }
            csd.setVisible(true);
            
            if (csd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION)
            {
                cs.setSeriesExpression( csd.getSeriesExpression() );
                cs.setStartDateExpression( csd.getStartDateExpression() );
                cs.setEndDateExpression( csd.getEndDateExpression() );
                cs.setValueExpression( csd.getValueExpression() );
                cs.setLabelExpression( csd.getLabelExpression() );
                //cs.setSectionItemHyperlink( csd.getSectionItemHyperlink() );
                cs.setItemHyperlink( csd.getSectionItemHyperlink() );              
                        
                jList1.updateUI();
            }
        
        }
    }//GEN-LAST:event_jButtonModifyActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed

        TimePeriodSeriesDialog csd = new TimePeriodSeriesDialog(Misc.getMainFrame() ,true);
        csd.setExpressionContext( this.getExpressionContext() );
        csd.setVisible(true);
        if (csd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION)
        {
            JRDesignTimePeriodSeries cs = new JRDesignTimePeriodSeries();
            cs.setSeriesExpression( csd.getSeriesExpression() );
            cs.setStartDateExpression( csd.getStartDateExpression() );
            cs.setEndDateExpression( csd.getEndDateExpression() );
            cs.setValueExpression( csd.getValueExpression() );
            cs.setLabelExpression( csd.getLabelExpression() );
            //cs.setSectionItemHyperlink( csd.getSectionItemHyperlink() );
            cs.setItemHyperlink( csd.getSectionItemHyperlink() ); 
            
            getTimePeriodDataset().addTimePeriodSeries(cs);
            ((javax.swing.DefaultListModel)jList1.getModel()).addElement(cs);
        }
        
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed

        while (jList1.getSelectedIndex() >= 0)
        {
            getTimePeriodDataset().removeTimePeriodSeries( (JRDesignTimePeriodSeries)jList1.getSelectedValue() );
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JMenuItem jMenuItemCopy;
    private javax.swing.JMenuItem jMenuItemPaste;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenuSeries;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    /*
     public void applyI18n()
    {
                // Start autogenerated code ----------------------
                // End autogenerated code ----------------------
                // Start autogenerated code ----------------------
                jLabel1.setText(I18n.getString("timePeriodDatasetPanel.label1","Time period series"));
                // End autogenerated code ----------------------
        jButtonAdd.setText(it.businesslogic.ireport.util.I18n.getString("charts.newseries", "Add series"));
        jButtonModify.setText(it.businesslogic.ireport.util.I18n.getString("charts.modifyseries", "Modify series"));
        jButtonRemove.setText(it.businesslogic.ireport.util.I18n.getString("charts.removeseries", "Remove series"));
        
        jMenuItemCopy.setText(it.businesslogic.ireport.util.I18n.getString("charts.copyseries", "Copy series"));
        jMenuItemPaste.setText(it.businesslogic.ireport.util.I18n.getString("charts.pasteseries", "Paste series"));
        
        this.updateUI();
        
    }
    */

    public static final int COMPONENT_NONE=0;
    public static final int COMPONENT_PERIOD_SERIES_LIST=1;
    
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
            case COMPONENT_PERIOD_SERIES_LIST:
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
            jButtonModifyActionPerformed(new ActionEvent(jButtonModify,0,""));
        }
        newInfo = null;
    }
    
    public void containerWindowOpened() {
        openExtraWindows();
    }

    public void setExpressionContext(ExpressionContext ec) {
        this.expressionContext = ec;
    }
    
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }
    
}
