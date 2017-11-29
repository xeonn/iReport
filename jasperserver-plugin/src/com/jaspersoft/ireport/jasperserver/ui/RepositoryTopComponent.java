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
package com.jaspersoft.ireport.jasperserver.ui;

import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.jasperserver.JServer;
import com.jaspersoft.ireport.jasperserver.JasperServerManager;
import com.jaspersoft.ireport.jasperserver.ui.actions.PublishReportUnitAction;
import com.jaspersoft.ireport.jasperserver.ui.actions.RunReportUnitButton;
import com.jaspersoft.ireport.jasperserver.ui.nodes.ServerChildren;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
final public class RepositoryTopComponent extends TopComponent implements ExplorerManager.Provider {

    private static RepositoryTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private final ExplorerManager manager = new ExplorerManager();
    
    private static final String PREFERRED_ID = "RepositoryTopComponent";

    private RepositoryTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(RepositoryTopComponent.class, "CTL_RepositoryTopComponent"));
        setToolTipText(NbBundle.getMessage(RepositoryTopComponent.class, "HINT_RepositoryTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));
        
        ActionMap map = this.getActionMap ();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        associateLookup (ExplorerUtils.createLookup (manager, map));
        BeanTreeView view = new BeanTreeView();
        view.setRootVisible(false);
        manager.setRootContext(new AbstractNode(new ServerChildren()));
        add (view, BorderLayout.CENTER);
        //if ((Utilities.getOperatingSystem() & Utilities.OS_WINDOWS_MASK) != 0) {
        //    view.getViewport().getView().setFont(new Font ("SansSerif", Font.PLAIN, 12));
        //}
        
        jToolBar1.add(SystemAction.get(PublishReportUnitAction.class));
        jToolBar1.add(SystemAction.get(RunReportUnitButton.class).createContextAwareInstance(Utilities.actionsGlobalContext()));
    
        
    
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddServer = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jButtonAddServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/jasperserver/res/server_add.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButtonAddServer, "\n");
        jButtonAddServer.setToolTipText("Add new server");
        jButtonAddServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddServerActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAddServer);

        add(jToolBar1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddServerActionPerformed
        
        ServerDialog sd = new ServerDialog(Misc.getMainFrame(), true);
        sd.setVisible(true);
        if (sd.getDialogResult() == JOptionPane.OK_OPTION) {
            JasperServerManager.getMainInstance().getJServers().add(sd.getJServer());
            JasperServerManager.getMainInstance().saveConfiguration();
            ((ServerChildren)getExplorerManager().getRootContext().getChildren()).recalculateKeys();
        }
        
    }//GEN-LAST:event_jButtonAddServerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddServer;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized RepositoryTopComponent getDefault() {
        if (instance == null) {
            instance = new RepositoryTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the RepositoryTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized RepositoryTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(RepositoryTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof RepositoryTopComponent) {
            return (RepositoryTopComponent) win;
        }
        Logger.getLogger(RepositoryTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
    // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
    // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return RepositoryTopComponent.getDefault();
        }
    }
    
    
    public ExplorerManager getExplorerManager() {
        return manager;
    }
}
