/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.jasperserver;

import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.util.FileResolver;

/**
 *
 * @author gtoffoli
 */
public class RepoImageCache extends HashMap<String, File> implements FileResolver {

    
    private static RepoImageCache instance = null;
    
    public static RepoImageCache getInstance()
    {
        if (instance == null)
        {
            instance = new RepoImageCache();
        }
        return instance;
    }

    public File resolveFile(String arg0) {
        
        if (containsKey(arg0))
        {
            return get(arg0);
        }
        
        return null;
    }
    
    
}
