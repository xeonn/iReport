/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

import java.nio.charset.Charset;
import org.netbeans.spi.queries.FileEncodingQueryImplementation;
import org.openide.filesystems.FileObject;

/**
 *
 * @author gtoffoli
 */
public class FileEncodingQueryImpl extends FileEncodingQueryImplementation{

    @Override
    public Charset getEncoding(FileObject arg0) {
        System.out.println("Charset used: UTF-8!!!!");
        System.out.flush();
        return Charset.forName("UTF-8");
    }

}
