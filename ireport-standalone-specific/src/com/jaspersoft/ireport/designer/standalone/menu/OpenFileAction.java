/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.standalone.menu;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.standalone.IReportStandaloneManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.ErrorManager;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.HelpCtx;
import org.openide.util.UserCancelException;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.WindowManager;

/**
 *
 * @author gtoffoli
 */
public class OpenFileAction extends CallableSystemAction {

	public OpenFileAction() {
		putValue("noIconInMenu", Boolean.TRUE); // NOI18N
	}
	
	public String getName() {
		return I18n.getString( IReportStandaloneManager.class, "CTL_OpenFileAction");
	}

	public HelpCtx getHelpCtx() {
		return new HelpCtx(OpenFileAction.class);
	}

	protected String iconResource() {
		return "com/transposix/nbext/openFile.png"; // NOI18N
	}

	/**
	 * Creates and initializes a file chooser.
	 *
	 * @return  the initialized file chooser
	 */
	protected JFileChooser prepareFileChooser() {
		JFileChooser chooser = new JFileChooser();
		File currDir = Misc.findStartingDirectory();
		FileUtil.preventFileChooserSymlinkTraversal(chooser, currDir);
		HelpCtx.setHelpIDString(chooser, getHelpCtx().getHelpID());

		chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {

                        @Override
                        public boolean accept(File f) {
                            
                            if (f.isDirectory()) return true;
                            if (f.exists() && f.getName().toLowerCase().endsWith(".jrxml")) // NOI18N
                            {
                                return true;
                            }
                            return false;
                        }

                        @Override
                        public String getDescription() {
                            return "Report template file (.jrxml)";
                        }
                    });

		chooser.setMultiSelectionEnabled(true);
		return chooser;
	}
	
	/**
	 * Displays the specified file chooser and returns a list of selected files.
	 *
	 * @param  chooser  file chooser to display
	 * @return  array of selected files,
	 * @exception  org.openide.util.UserCancelException
	 *                     if the user cancelled the operation
	 */
	public static File[] chooseFilesToOpen(JFileChooser chooser)
			throws UserCancelException {
		File[] files;
		do {
			int selectedOption = chooser.showOpenDialog(
				WindowManager.getDefault().getMainWindow());
			
			if (selectedOption != JFileChooser.APPROVE_OPTION) {
				throw new UserCancelException();
			}

			// If the chooser is not configured for multi files, then use
			// File file = chooser.getSelectedFile();
			files = chooser.getSelectedFiles();
		} while (files.length == 0);
		return files;
	}
	
	/**
	 * {@inheritDoc} Displays a file chooser dialog
	 * and opens the selected files.
	 */
	public void performAction() {
		 JFileChooser chooser = prepareFileChooser();
		 File[] files;
		 try {
			 files = chooseFilesToOpen(chooser);
		 } catch (UserCancelException ex) {
			 return;
		 }

		 for (int i = 0; i < files.length; i++) {
			// Small hack ... OpenFile is User Utilities, which is a private API
			//
			// OpenFile.openFile(files[i], -1);
			open(files[i]);
		}
                
                 // Save the last path used for the chooser..
                 if (files.length > 0)
                 {
                     String dir = files[0].getParent();
                     IReportManager.getPreferences().put( IReportManager.CURRENT_DIRECTORY, dir);
                 }
	}

	public static void open (File f) {
		FileObject fob = FileUtil.toFileObject(FileUtil.normalizeFile(f));
		if (fob == null)
		return;
		
                try {
			// the process succeeded 
			DataObject dob = DataObject.find (fob);
                        if (f.getName().toLowerCase().endsWith(".properties")) // NOI18N
                        {
                            EditCookie oc = (EditCookie) dob.getCookie (EditCookie.class);
                            if (oc != null)
				oc.edit();
                        }
                        else
                        {
                            OpenCookie oc = (OpenCookie) dob.getCookie (OpenCookie.class);
			if (oc != null)
				oc.open();
                        }
			
		} catch (DataObjectNotFoundException ex) {
			 ErrorManager.getDefault().notify(ex);
                }
	}

	
	protected boolean asynchronous() {
		return false;
	}

	

}
