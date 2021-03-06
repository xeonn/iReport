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
package com.jaspersoft.ireport.designer.welcome;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.URLMapper;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

/**
 *
 * @author gtoffoli
 */
public class RecentReportsPanel extends javax.swing.JPanel {

    /** Creates new form RecentReportsPanel */
    public RecentReportsPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables



    private static final int MAX_REPORTS = 10;
    private PreferenceChangeListener changeListener;

    @Override
    public void addNotify() {
        super.addNotify();
        removeAll();
        add( rebuildContent(), BorderLayout.CENTER );
        IReportManager.getPreferences().addPreferenceChangeListener( getPreferenceChangeListener() );
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        IReportManager.getPreferences().removePreferenceChangeListener( getPreferenceChangeListener() );
    }

    private PreferenceChangeListener getPreferenceChangeListener() {
        if( null == changeListener ) {
            changeListener = new PreferenceChangeListener() {

                public void preferenceChange(PreferenceChangeEvent evt) {

                        removeAll();
                        add( rebuildContent(), BorderLayout.CENTER );
                        invalidate();
                        revalidate();
                        repaint();
                    }
            };
        }
        return changeListener;
    }

    private JPanel rebuildContent() {
        JPanel panel = new JPanel( new GridBagLayout() );
        panel.setOpaque( false );
        int row = 0;

        String s = IReportManager.getPreferences().get("RecentFiles",null); // NOI18N
        if (s != null)
        {
            String[] files = s.split("\n");
            for (int i=0; i<files.length; ++i)
            {
                if (files[i].trim().length() == 0) continue;

                if (row > MAX_REPORTS) break;
                String item = files[i];
                FileObject fo = null;
                try {
                    fo = convertURL2File(new URL(item));
                } catch (MalformedURLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                if (fo == null || !fo.isValid()) continue;

                addProject( panel, row, fo );
                row++;
            }
        }

        if( 0 == row ) {
            panel.add( new JLabel(I18n.getString( "RecentReportsPanel.noRecentFiles" )), //NOI18N
                    new GridBagConstraints( 0,row,1,1,1.0,1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(10,10,10,10), 0, 0 ) );
        } else {
            panel.add( new JLabel(), new GridBagConstraints( 0,row,1,1,0.0,1.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0,0,0,0), 0, 0 ) );
        }

        return panel;
    }

    private void addProject( JPanel panel, int row, final FileObject fileObject ) {
        try {
            OpenFileObjectAction action = new OpenFileObjectAction(fileObject);
            ActionButton b = new ActionButton(action, true, fileObject.getURL().toString());
            b.setFont(LinkButton.BUTTON_FONT);
            panel.add(b, new GridBagConstraints(0, row, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
        } catch (FileStateInvalidException ex) {
            //Exceptions.printStackTrace(ex);
        }
    }

    private static class OpenFileObjectAction extends AbstractAction {
        private FileObject fileObject;
        public OpenFileObjectAction( FileObject fileObject ) {

            super();
            this.fileObject = fileObject;
            Image icon = null;
            try {

                DataObject dObj = DataObject.find(fileObject);
                icon = dObj.getNodeDelegate().getIcon(BeanInfo.ICON_COLOR_16x16);

                this.putValue(AbstractAction.SMALL_ICON,new ImageIcon(icon));
            } catch (DataObjectNotFoundException ex) {
                ex.printStackTrace();
            }
            this.putValue(AbstractAction.NAME,  fileObject.getName());
        }

        public void actionPerformed(ActionEvent e) {
            
            if (fileObject != null)
            {
                try {
                    DataObject.find(fileObject).getCookie(OpenCookie.class).open();
                } catch (Exception ex)
                {
                    // do nothing...
                }
            }
        }

    }


    static URL convertFile2URL (FileObject fo) {
        URL url = URLMapper.findURL(fo, URLMapper.EXTERNAL);
        return url;
    }

    static FileObject convertURL2File (URL url) {
        FileObject fo = URLMapper.findFileObject(url);
        return fo;
    }


}
