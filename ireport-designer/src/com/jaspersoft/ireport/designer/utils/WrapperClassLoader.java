/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.utils;

/**
 *
 * @author gtoffoli
 */
public class WrapperClassLoader extends java.lang.ClassLoader {


    public static ClassLoader wrapClassloader(ClassLoader parent)
    {
        if (parent == null) return null;
        if (parent instanceof WrapperClassLoader)
        {
            return new WrapperClassLoader( parent.getParent() );
        }

        return new WrapperClassLoader(parent);
    }


    public WrapperClassLoader(ClassLoader parent)
    {
        super(parent);
    }


}
