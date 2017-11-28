/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.standalone.menu;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 *
 * @author gtoffoli
 */
public class FileFilterAdapter extends FileFilter {

    private String ext = "";
    private String desc = "";

    public FileFilterAdapter(String ext, String desc)
    {
        super();
        this.ext = ext;
        this.desc = desc;
    }

    @Override
    public boolean accept(File f) {

        if (f.isDirectory()) return true;
        if (f.exists() && f.getName().toLowerCase().endsWith(ext)) // NOI18N
        {
            return true;
        }
        return false;
    }

    public String getDescription() {
        return desc;
    }
}
