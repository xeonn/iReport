/*
 * ErrorHandlerPanel.java
 *
 * Created on July 17, 2008, 6:03 PM
 */

package com.jaspersoft.ireport.designer.errorhandler;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import com.jaspersoft.ireport.designer.JrxmlVisualViewActivatedListener;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.design.JRDesignElement;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author  gtoffoli
 */
public class ErrorHandlerPanel extends javax.swing.JPanel implements JrxmlVisualViewActivatedListener {

    //private final Lookup.Result <JrxmlVisualView> result;
    
    private ErrorLocator locator = null;
    
    /** Creates new form ErrorHandlerPanel */
    public ErrorHandlerPanel() {
        initComponents();
        
        jTable1.getColumnModel().getColumn(0).setMinWidth(20);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(20);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
        
        jTable1.getColumnModel().getColumn(0).setCellRenderer( new ProblemIconTableCellRenderer());
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(500);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(250);
        
        jTable1.getColumnModel().getColumn(2).setCellRenderer(new NodeCellRenderer());
        
       // jTable1.getColumnModel().getColumn(3).setPreferredWidth(550);
        
        //result = Utilities.actionsGlobalContext().lookup(new Lookup.Template(JrxmlVisualView.class));
        //result.addLookupListener(this);
        //result.allItems();
        IReportManager.getInstance().addJrxmlVisualViewActivatedListener(this);
        refreshErrors();
        
        locator = new ErrorLocator();
        
    }

    void refreshErrors() {
        JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
        
        if (view != null)
        {
            // Update errors
            List<ProblemItem> items = view.getReportProblems();
            
            ((DefaultTableModel)jTable1.getModel()).setRowCount(0);
            
            for (ProblemItem item : items)
            {
                Object errorSource = item.getProblemReference();
                Node node = IReportManager.getInstance().findNodeOf(errorSource, view.getExplorerManager().getRootContext());
                if (node != null) errorSource = node;
                
                ((DefaultTableModel)jTable1.getModel()).addRow(new Object[]{item,item.getDescription(),errorSource});
            }
        }
        else
        {
            ((DefaultTableModel)jTable1.getModel()).setRowCount(0);
        }
        
        jTable1.updateUI();
    
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " ", "Description", "Object"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2 &&
        jTable1.getSelectedRowCount() > 0)
    {
        JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
        if (view != null)
        {
            ProblemItem item = (ProblemItem)jTable1.getValueAt( jTable1.getSelectedRow(), 0);
            locator.locateError( IReportManager.getInstance().getActiveVisualView() , item);
        }
    }
    
}//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void jrxmlVisualViewActivated(JrxmlVisualView view) {
        refreshErrors();
    }
    
}
