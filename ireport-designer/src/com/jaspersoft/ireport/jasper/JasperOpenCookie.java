/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasper;

import org.openide.cookies.OpenCookie;
import com.jaspersoft.ireport.designer.utils.Misc;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author gtoffoli
 */
public class JasperOpenCookie implements OpenCookie {

    private JasperDataObject dataObject;

    public JasperOpenCookie(JasperDataObject dataObject)
    {
        this.dataObject = dataObject;
    }

    public void open() {

        if (dataObject != null)
        {
            ConvertJasperJrxmlDialog dialog = new ConvertJasperJrxmlDialog(Misc.getMainFrame(), true);
            dialog.setJasperFile( FileUtil.toFile( dataObject.getPrimaryFile() ) + "");
            dialog.setVisible(true);
        }
    }

    /**
     * @return the dataObject
     */
    public JasperDataObject getDataObject() {
        return dataObject;
    }

    /**
     * @param dataObject the dataObject to set
     */
    public void setDataObject(JasperDataObject dataObject) {
        this.dataObject = dataObject;
    }

}
