/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 */
package com.jaspersoft.ireport.jasperserver;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.io.File;


/**
 *
 * @author gtoffoli
 */
public class RepositoryFile extends RepositoryFolder {

    private String localFileName = null;
    
    
    /** Creates a new instance of RepositoryFolder */
    public RepositoryFile(JServer server, ResourceDescriptor descriptor) {
        super( server, descriptor);
    }

    public String toString()
    {
        if (getDescriptor() != null)
        {
            return ""+getDescriptor().getLabel();
        }   
        return "???";
    }
    
    /**
     * This method return the file rapresented by this resource file.
     * The file is cached in a temporary directory for subsequent calls to this method.
     * Please note: the file is never removed... a delete of this file should be done
     * on plugin startup....
     * The method returns the cached file name.
     *
     */
    public String getFile() throws Exception
    {
        if (localFileName == null || localFileName.length() == 0 || !(new File(localFileName).exists()))
        {
            try {
                String localFile = JasperServerManager.getMainInstance().createTmpFileName("file",getExtension());
                File file = new File(localFile);
                getServer().getWSClient().get(getDescriptor(), file);
                this.localFileName = file.getCanonicalPath();
           } catch (Exception ex)
           {
                throw ex;
           }
        }
        return localFileName;
    }
    
    public String getExtension()
    {
        return null;
    }
    /**
     * If localFileName exists, remove it and set localFileName to NULL.
     */
    public void resetFileCache()
    {
        if (localFileName != null)
        {
            File f = new File(localFileName);
            if (f.exists())
            {
                f.delete();
            }
        }
        
        localFileName = null;
    }
    
}
