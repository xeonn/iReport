/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport.designer.standalone.actions;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.openide.awt.DynamicMenuContent;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 * Action that presents list of recently closed files/documents.
 *
 * @author Dafe Simonek
 */
public class RecentFileAction extends AbstractAction implements Presenter.Menu, PopupMenuListener {

    /** property of menu items where we store fileobject to open */
    private static final String FO_PROP = "RecentFileAction.Recent_FO";

    /** number of maximum shown items in submenu */ 
    private static final int MAX_COUNT = 15;
            
    private JMenu menu;
    
    public RecentFileAction() {
        super(NbBundle.getMessage(RecentFileAction.class, "LBL_RecentFileAction_Name")); // NOI18N
        RecentFiles.init();
    }
    
    /********* Presenter.Menu impl **********/
    
    public JMenuItem getMenuPresenter() {
        if (menu == null) {
            menu = new UpdatingMenu(this);
            menu.setMnemonic(NbBundle.getMessage(RecentFileAction.class,
                                                 "MNE_RecentFileAction_Name").charAt(0));
            menu.getPopupMenu().addPopupMenuListener(this);
        }
        return menu;
    }
    
    /******* PopupMenuListener impl *******/
    
    public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
        fillSubMenu();
    }
    
    public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
        menu.removeAll();
    }
    
    public void popupMenuCanceled(PopupMenuEvent arg0) {
    }
    
    /** Fills submenu with recently closed files got from RecentFiles support */
    private void fillSubMenu () {
        List<String> files = RecentFiles.getRecentFiles();

        int counter = 0;
        for (String hItem : files) {
            try {
                // obtain file object
                // note we need not check for null or validity, as it is ensured
                // by RecentFiles.getRecentFiles()
                FileObject fo = RecentFiles.convertURL2File(new URL(hItem));
                // allow only up to max items
                if (fo != null)
                {
                    if (++counter > MAX_COUNT) {
                        break;
                    }
                    // obtain icon for fileobject
                    Image icon = null;
                    try {

                        DataObject dObj = DataObject.find(fo);
                        icon = dObj.getNodeDelegate().getIcon(BeanInfo.ICON_COLOR_16x16);
                    } catch (DataObjectNotFoundException ex) {
                        // should not happen, log and skip to next
                        Logger.getLogger(RecentFiles.class.getName()).log(Level.INFO, ex.getMessage(), ex);
                        continue;
                    }
                    // create and configure menu item
                    JMenuItem jmi = null;
                    if (icon != null) {
                        jmi = new JMenuItem(fo.getNameExt(), new ImageIcon(icon));
                    } else {
                        jmi = new JMenuItem(fo.getNameExt());
                    }
                    jmi.putClientProperty(FO_PROP, fo);
                    jmi.addActionListener(this);
                    menu.add(jmi);
                }
            } catch (MalformedURLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    /** Opens recently closed file, using OpenFile support.
     *
     * Note that method works as action handler for individual submenu items
     * created in fillSubMenu, not for whole RecentFileAction.
     */
    public void actionPerformed(ActionEvent evt) {
        JMenuItem source = (JMenuItem) evt.getSource();
        FileObject fo = (FileObject) source.getClientProperty(FO_PROP);
        if (fo != null) {
        
            try {
                DataObject.find(fo).getCookie(OpenCookie.class).open();
            } catch (Exception ex)
            {
                // do nothing...
            }
        }
    }
    
    /** Menu that checks its enabled state just before is populated */
    private static class UpdatingMenu extends JMenu implements DynamicMenuContent {
        
        private final JComponent[] content = new JComponent[] { this };
        
        public UpdatingMenu (Action action) {
            super(action);
        }
    
        public JComponent[] getMenuPresenters() {
            setEnabled(!RecentFiles.getRecentFiles().isEmpty());
            return content;
        }

        public JComponent[] synchMenuPresenters(JComponent[] items) {
            return getMenuPresenters();
        }
    }
    
}

