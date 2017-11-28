/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.crosstab.wizard;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabBucket;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;

public final class CrosstabVisualPanel2 extends JPanel {

    JRDesignDataset dataset = null;
    
    GroupPanel groupPanel1 = new GroupPanel(GroupPanel.GROUP);
    GroupPanel groupPanel2 = new GroupPanel(GroupPanel.GROUP);
    
    /** Creates new form CrosstabVisualPanel2 */
    public CrosstabVisualPanel2() {
        initComponents();
        jPanelGroup1.add(groupPanel1, BorderLayout.CENTER);
        jPanelGroup2.add(groupPanel2, BorderLayout.CENTER);
        
        groupPanel1.setTitle("Row Group 1");
        groupPanel2.setTitle("Row Group 2");
    }

    public void setDataset(JRDesignDataset dataset)
    {
        if (this.dataset == dataset) return;
        
        this.dataset = dataset;
        groupPanel1.setDataset(dataset, false);
        groupPanel2.setDataset(dataset, true);
    }
    
    @Override
    public String getName() {
        return "Rows";
    }

    public JRDesignCrosstabRowGroup getRowGroup1()
    {
        JRDesignCrosstabRowGroup group = new JRDesignCrosstabRowGroup();
        group.setName( groupPanel1.getGroupMeasureName() + "" );
        JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();
        bucket.setExpression( groupPanel1.getDesignExpression() );
        group.setBucket(bucket);
        return group;
    }
    
    public JRDesignCrosstabRowGroup getRowGroup2()
    {
        if (!groupPanel2.isUsed()) return null;
        JRDesignCrosstabRowGroup group = new JRDesignCrosstabRowGroup();
        group.setName( groupPanel2.getGroupMeasureName() + "" );
        JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();
        bucket.setExpression( groupPanel2.getDesignExpression() );
        group.setBucket(bucket);
        return group;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanelGroup1 = new javax.swing.JPanel();
        jPanelGroup2 = new javax.swing.JPanel();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/wizard_rows.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Define row groups");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jPanelGroup1.setBackground(new java.awt.Color(255, 255, 153));
        jPanelGroup1.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanelGroup1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanelGroup1.setLayout(new java.awt.BorderLayout());

        jPanelGroup2.setBackground(new java.awt.Color(204, 255, 204));
        jPanelGroup2.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanelGroup2.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanelGroup2.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelGroup2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelGroup1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanelGroup1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelGroup2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelGroup1;
    private javax.swing.JPanel jPanelGroup2;
    // End of variables declaration//GEN-END:variables
}

