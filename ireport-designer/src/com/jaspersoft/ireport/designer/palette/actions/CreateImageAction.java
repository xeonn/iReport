/*
 * NewReportElementPaletteItemAction.java
 * 
 * Created on 12-nov-2007, 21.30.43
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.palette.actions;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlVisualView;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Mutex;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class CreateImageAction extends CreateReportElementAction 
{

    public JRDesignElement createReportElement(JasperDesign jd)
    {
        File parent = new File(IReportManager.getInstance().getCurrentDirectory());
        // Try to figure it out the current directory of the report...
        if (IReportManager.getInstance().getActiveVisualView() != null)
        {
            JrxmlVisualView view = IReportManager.getInstance().getActiveVisualView();
            FileObject obj = view.getEditorSupport().getDataObject().getPrimaryFile();
            File f = FileUtil.toFile(obj);
            if (f != null && f.getParentFile().exists())
            {
                parent = f.getParentFile();
            }
        }
        final javax.swing.JFileChooser jfc = new javax.swing.JFileChooser( parent  );
        jfc.setDialogTitle("Select an image file....");
        jfc.setFileFilter( new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File file) {
                String filename = file.getName();
                return (filename.endsWith(".jpg") ||
                        filename.endsWith(".jpeg") ||
                        filename.endsWith(".gif") ||
                        file.isDirectory()) ;
            }
            public String getDescription() {
                return "Image *.gif|*.jpg";
            }
        });
        
        jfc.setMultiSelectionEnabled(false);

        final JRDesignImage element = new JRDesignImage(jd);
        element.setWidth(100);
        element.setHeight(50);

        jfc.setDialogType( javax.swing.JFileChooser.OPEN_DIALOG);
        if  (jfc.showOpenDialog( null) == javax.swing.JOptionPane.OK_OPTION) {
            element.setExpression( Misc.createExpression("java.lang.String", "\""+ Misc.string_replace("\\\\","\\",jfc.getSelectedFile().getPath() +"\"")));
            IReportManager.getInstance().setCurrentDirectory(jfc.getSelectedFile().getParent(), true);
            // Try to identify the image size...

            SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        try {
                            ImageIcon image = new ImageIcon(jfc.getSelectedFile().getPath());
                            element.setWidth( image.getIconWidth());
                            element.setHeight( image.getIconHeight());
                        } catch (Exception ex)
                        {

                        }
                    }
                });

        }

        return element;
    }
    
}
