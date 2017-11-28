/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaspersoft.ireport;

import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {

        if (System.getProperty("javax.xml.parsers.SAXParserFactory") == null)
        {
            System.setProperty("javax.xml.parsers.SAXParserFactory","com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
        }

        if (System.getProperty("javax.xml.parsers.DocumentBuilderFactory") == null)
        {
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory","com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        }

        if (System.getProperty("javax.xml.datatype.DatatypeFactory") == null)
        {
            System.setProperty("javax.xml.datatype.DatatypeFactory","com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl");
        }

        System.out.println("javax.xml.parsers.SAXParserFactory="+System.getProperty("javax.xml.parsers.SAXParserFactory"));
        System.out.println("javax.xml.parsers.DocumentBuilderFactory="+System.getProperty("javax.xml.parsers.DocumentBuilderFactory"));
        System.out.println("javax.xml.datatype.DatatypeFactory="+System.getProperty("javax.xml.datatype.DatatypeFactory"));
        System.out.flush();
    }
}
