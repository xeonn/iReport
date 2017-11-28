package com.jaspersoft.ireport.designer.menu;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CallableSystemAction;
import org.openide.util.actions.Presenter;

public final class SelectDatasourceAction extends CallableSystemAction 
        implements Presenter.Toolbar, PropertyChangeListener, ActionListener {

    private boolean init = false;

    public synchronized boolean isInit() { return init; }
    
    /** Returns the old value of init;
     */
    public synchronized boolean setInit(boolean b) { boolean oldB = init; init = b; return oldB; }
    
    JComboBox comboBox = null;
    JPanel comboBoxContainer = null;
    
    public void performAction() {
        // Nothing to do...
    }

    public String getName() {
        return "";
    }

    @Override
    protected String iconResource() {
        return "com/jaspersoft/ireport/designer/menu/datasources.png";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
    
    @Override
    public Component getToolbarPresenter() 
    {
        if (comboBox == null)
        {
            comboBoxContainer = new JPanel();
            comboBoxContainer.setLayout(new GridBagLayout());
            comboBoxContainer.setPreferredSize(new Dimension(200,26));
            comboBoxContainer.setMaximumSize(new Dimension(250,26));
            
            comboBox = new JComboBox();
            comboBoxContainer.add(comboBox, new GridBagConstraints(0, 0, 1, 1, 1, 0, 
                    GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));

            IReportManager.getInstance().getPropertyChangeSupport().addPropertyChangeListener(this);
            updateConnections();
            
            comboBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    boolean oldB = setInit(true);
                     try {
                         IReportConnection con = (IReportConnection)comboBox.getSelectedItem();
                         if (con == null) return;
                         IReportManager.getInstance().setDefaultConnection(con);
                         IReportManager.getInstance().saveiReportConfiguration();
                     } finally {
                         setInit(oldB);
                     }
                }
            });
        }
        return comboBoxContainer;
    }
    
    
    public void propertyChange(PropertyChangeEvent evt) {
        
         boolean oldB = setInit(true);
         try {
             if (evt.getPropertyName()== null) return;
             if (evt.getPropertyName().equals(IReportManager.PROPERTY_CONNECTIONS))
             {
                 updateConnections();
             }
             else if (evt.getPropertyName().equals(IReportManager.PROPERTY_DEFAULT_CONNECTION))
             {
                 comboBox.setSelectedItem(evt.getNewValue());
             }
         } finally {
             setInit(oldB);
         }
     }
     
     private void updateConnections()
     {
         boolean oldB = setInit(true);
         try {
             comboBox.removeAllItems();
             comboBox.setModel( new DefaultComboBoxModel( IReportManager.getInstance().getConnections().toArray()));
             comboBox.updateUI();
             comboBox.setSelectedItem(  IReportManager.getInstance().getDefaultConnection() );
         } finally {
             setInit(oldB);
         }
     }
}