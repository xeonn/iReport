/*
 * LayoutPreviewPanel.java
 *
 * Created on March 6, 2008, 3:44 PM
 */

package com.jaspersoft.ireport.designer.crosstab.wizard;

import com.jaspersoft.ireport.designer.utils.ColorSchemaGenerator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author  gtoffoli
 */
public class LayoutPreviewPanel extends javax.swing.JPanel {
    
    ImageIcon whiteIcon = new ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/wizard_preview_white.png"));
    ImageIcon blackIcon = new ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/wizard_preview.png"));
    
    
    Rectangle detailCell =   new Rectangle( 32, 18, 28,16);
    Rectangle rowCells =     new Rectangle( 4, 18,28,46);
    Rectangle colCells =     new Rectangle(32,  3,84,16);
    Rectangle total1Cells1 = new Rectangle( 4, 33,84,16);
    Rectangle total1Cells2 = new Rectangle(60,  3,28,46);
    Rectangle total2Cells1 = new Rectangle( 4, 48,111,16);
    Rectangle total2Cells2 = new Rectangle(87,  3,28,61);
    
    private Color color = Color.BLUE;
    private String variant = ColorSchemaGenerator.SCHEMA_SOFT;
    private boolean whiteGrid = false;
    
    /** Creates new form LayoutPreviewPanel */
    public LayoutPreviewPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(119, 67));
        setMinimumSize(new java.awt.Dimension(119, 67));
        setOpaque(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/wizard_preview.png"))); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(LayoutPreviewPanel.class, "LayoutPreviewPanel.jLabel1.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel1)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paint(Graphics g) {
        
        
        g.setColor(Color.WHITE);
        ((Graphics2D)g).fill(detailCell);
        
        Color c = ColorSchemaGenerator.createColor(getColor(), 3, variant);
        g.setColor(c);
        ((Graphics2D)g).fill(rowCells);
        ((Graphics2D)g).fill(colCells);
        
        c = ColorSchemaGenerator.createColor(getColor(), 2, variant);
        g.setColor(c);
        ((Graphics2D)g).fill(total1Cells1);
        ((Graphics2D)g).fill(total1Cells2);
        
        c = ColorSchemaGenerator.createColor(getColor(), 1, variant);
        g.setColor(c);
        ((Graphics2D)g).fill(total2Cells1);
        ((Graphics2D)g).fill(total2Cells2);
        
        super.paint(g);
        
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    public

    Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color != null && !this.color.equals(color))
        {
            this.color = color;
            this.repaint();
        }
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        if (variant != null && !this.variant.equals(variant))
        {
            this.variant = variant;
            this.repaint();
        }
    }

    public boolean isWhiteGrid() {
        return whiteGrid;
    }

    public void setWhiteGrid(boolean whiteGrid) {
        if (this.whiteGrid != whiteGrid)
        {
            this.whiteGrid = whiteGrid;
            jLabel1.setIcon(whiteGrid ? whiteIcon : blackIcon);
            jLabel1.updateUI();
            this.repaint();
        }
    }
    
}
