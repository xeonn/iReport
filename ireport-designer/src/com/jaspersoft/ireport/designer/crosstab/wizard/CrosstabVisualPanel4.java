/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.crosstab.wizard;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.engine.design.JRDesignDataset;

public final class CrosstabVisualPanel4 extends JPanel {

    JRDesignDataset dataset = null;
    
    GroupPanel groupPanel1 = new GroupPanel(GroupPanel.MEASURE);
    
    /** Creates new form CrosstabVisualPanel2 */
    public CrosstabVisualPanel4() {
        initComponents();
        jPanelGroup1.add(groupPanel1, BorderLayout.CENTER);
        
        groupPanel1.setTitle("Data");

    }

    public void setDataset(JRDesignDataset dataset)
    {
        if (this.dataset == dataset) return;
        
        this.dataset = dataset;
        groupPanel1.setDataset(dataset, false);
    }
    
    @Override
    public String getName() {
        return "Measure";
    }

    public JRDesignCrosstabMeasure getMeasure()
    {
        JRDesignCrosstabMeasure measure = new JRDesignCrosstabMeasure();
        measure.setName( groupPanel1.getGroupMeasureName()+"Measure" );
        measure.setValueExpression( groupPanel1.getDesignExpression() );
        measure.setValueClassName( measure.getValueExpression().getValueClassName() );
        measure.setCalculation( groupPanel1.getCalculationType() );
        return measure;
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

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/wizard_details.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Define measure");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jPanelGroup1.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanelGroup1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanelGroup1.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
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
                .addContainerGap(129, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelGroup1;
    // End of variables declaration//GEN-END:variables
}

