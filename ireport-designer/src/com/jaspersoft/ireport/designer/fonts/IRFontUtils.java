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

package com.jaspersoft.ireport.designer.fonts;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import net.sf.jasperreports.chartthemes.simple.XmlChartTheme;
import net.sf.jasperreports.engine.util.JRFontUtil;
import org.apache.xerces.parsers.DOMParser;
import org.openide.filesystems.FileUtil;
import org.w3c.dom.*;

/**
 *
 * @version $Id: IRFontUtils.java 0 2009-10-26 11:14:37 CET gtoffoli $
 * @author Giulio Toffoli (giulio@jaspersoft.com)
 *
 */
public class IRFontUtils {

    public static List<SimpleFontFamilyEx> loadFonts()
    {
        List<SimpleFontFamilyEx> fontsList = new ArrayList<SimpleFontFamilyEx>();

        try {
            File fontsDir = Misc.getFontsDirectory();
            if (fontsDir == null)
            {
                throw new java.lang.Exception("I'm unable to find the fonts directory of iReport!!");
            }


            File xmlFile = new File(fontsDir,"irfonts.xml");


             ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
             Thread.currentThread().setContextClassLoader(DOMParser.class.getClassLoader());

             DOMParser parser = new DOMParser();
             java.io.FileInputStream fis = new FileInputStream(xmlFile);
             org.xml.sax.InputSource input_sss  = new org.xml.sax.InputSource(fis);
             //input_sss.setSystemId(filename);
             parser.parse( input_sss );

             Thread.currentThread().setContextClassLoader(oldClassLoader);


             Document document = parser.getDocument();
             Node node = document.getDocumentElement();


             NodeList list_child = node.getChildNodes(); // The root is beans
             for (int ck=0; ck< list_child.getLength(); ck++) {
                 Node connectionNode = list_child.item(ck);
                 if (connectionNode.getNodeName() != null && connectionNode.getNodeName().equals("bean"))
                 {
                    // Take the CDATA...
                    SimpleFontFamilyEx family = new SimpleFontFamilyEx();

                    // Get all connections parameters...
                    NodeList list_child2 = connectionNode.getChildNodes();
                    for (int ck2=0; ck2< list_child2.getLength(); ck2++) {
                        Node child_child = list_child2.item(ck2);
                        if (child_child.getNodeType() == Node.ELEMENT_NODE && child_child.getNodeName().equals("property")) {

                            NamedNodeMap nnm2 = child_child.getAttributes();

                            if ( nnm2.getNamedItem("name") != null)
                            {
                                String att = nnm2.getNamedItem("name").getNodeValue();

                                if (nnm2.getNamedItem("value") != null)
                                {
                                    String value = nnm2.getNamedItem("value").getNodeValue();

                                    if (att.equals("name")) family.setName(value);
                                    if (att.equals("normal")) family.setNormalFont(value);
                                    if (att.equals("bold")) family.setBoldFont(value);
                                    if (att.equals("italic")) family.setItalicFont(value);
                                    if (att.equals("boldItalic")) family.setBoldItalicFont(value);
                                    if (att.equals("pdfEncoding")) family.setPdfEncoding(value);
                                    if (att.equals("pdfEmbedded")) family.setPdfEmbedded(value.equals("true") );
                                }

                                if (att.equals("locales")) // property[locales]/set/value
                                {
                                    family.setLocales(new HashSet(getLocales(child_child)));
                                }

                                if (att.equals("exportFonts")) // property[exportFonts]/map/entry[key]/value
                                {
                                    family.setExportFonts(getMappings(child_child));
                                }
                            }
                        }
                    }

                    fontsList.add(family);

                }
             }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

         return fontsList;
    }


    /**
     * Extract the items in a set node child of thespecified node;
     * @param node
     * @return
     */
    private static List<String> getLocales(Node node)
    {
        List<String> locales = new ArrayList<String>();

        Node setNode = getSubNode(node, "set");
        if (setNode != null)
        {
            List<Node> valueNodes = getSubNodes(setNode, "value");
            for (Node tmpNode : valueNodes)
            {
                locales.add( Misc.readPCDATA(tmpNode, true));
            }
        }
        return locales;

    }

    private static List<Node> getSubNodes(Node node, String subNodeName)
    {
        List<Node> nodes = new ArrayList<Node>();

        NodeList list_child = node.getChildNodes(); // The root is beans
        for (int ck=0; ck< list_child.getLength(); ck++) {
         Node tmpNode = list_child.item(ck);
         if (tmpNode.getNodeName() != null && tmpNode.getNodeName().equals(subNodeName))
         {
             nodes.add(tmpNode);
             break;
         }
        }

        return nodes;
    }

    private static Node getSubNode(Node node, String subNodeName)
    {
        List<Node> nodes = getSubNodes(node, subNodeName);

        if (nodes.size() > 0) return nodes.get(0);
        return null;
    }

    /**
     * Extract the items in a set node child of thespecified node;
     * @param node
     * @return
     */
    private static Map<String, String> getMappings(Node node)
    {
        Map<String, String> map = new HashMap<String,String>();

        Node mapNode = getSubNode(node, "map");
        if (mapNode != null)
        {
            List<Node> entryNodes = getSubNodes(mapNode, "entry");
            for (Node tmpNode : entryNodes)
            {
                String key = tmpNode.getAttributes().getNamedItem("key").getNodeValue();
                String value = "";
                Node valNode = getSubNode(tmpNode, "value");
                if (valNode != null)
                {
                    value = Misc.readPCDATA(valNode, true);
                }

                if (value != null && value.length() > 0 &&
                    key != null && key.length() > 0)
                {
                    map.put(key, value);
                }
            }
        }
        return map;

    }

    /**
     * This method just save the list of SimpleFontFamilyEx.
     * No TTF files operations (like copy or delete) is performed.
     */
    public static void saveFonts(List<SimpleFontFamilyEx> fonts)
    {
        try {
            File fontsDir = Misc.getFontsDirectory();
            if (fontsDir == null)
            {
                throw new java.lang.Exception("I'm unable to find the fonts directory of iReport!!");
            }


            File xmlFile = new File(fontsDir,"irfonts.xml");

            PrintWriter pw = new PrintWriter(xmlFile);

            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            pw.println("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
            pw.println("       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
            pw.println("       xsi:schemaLocation=\"http://www.springframework.org/schema/beans");
            pw.println("           http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">\n");

            for (SimpleFontFamilyEx font : fonts)
            {
                pw.print(dumpBean(font));
            }

            pw.println("</beans>");

            pw.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static String dumpBean(SimpleFontFamilyEx font)
    {
        StringBuffer bean_xml = new StringBuffer("");

        bean_xml.append("   <bean id=\"fontBean"  + (new Date()).getTime() + (long)((Math.random()*100000)) + "\" class=\"net.sf.jasperreports.engine.fonts.SimpleFontFamily\">\n");
        bean_xml.append("       <property name=\"name\" value=\""  + Misc.escapeXMLEntity(font.getName()) +  "\"/>\n");
        if (font.getNormalFont() != null && font.getNormalFont().length() > 0)
        {
            bean_xml.append("       <property name=\"normal\" value=\""  + Misc.escapeXMLEntity(font.getNormalFont()) +  "\"/>\n");
        }

        if (font.getBoldFont() != null && font.getBoldFont().length() > 0)
        {
            bean_xml.append("       <property name=\"bold\" value=\""  + Misc.escapeXMLEntity(font.getBoldFont()) +  "\"/>\n");
        }

        if (font.getItalicFont() != null && font.getItalicFont().length() > 0)
        {
            bean_xml.append("       <property name=\"italic\" value=\""  + Misc.escapeXMLEntity(font.getItalicFont()) +  "\"/>\n");
        }

        if (font.getBoldItalicFont() != null && font.getBoldItalicFont().length() > 0)
        {
            bean_xml.append("       <property name=\"boldItalic\" value=\""  + Misc.escapeXMLEntity(font.getBoldItalicFont()) +  "\"/>\n");
        }

        if (font.getPdfEncoding() != null && font.getPdfEncoding().length() > 0)
        {
            bean_xml.append("       <property name=\"pdfEncoding\" value=\""  + Misc.escapeXMLEntity(font.getPdfEncoding()) +  "\"/>\n");
        }

        bean_xml.append("       <property name=\"pdfEmbedded\" value=\""  + font.isPdfEmbedded() +  "\"/>\n");



        if (font.getExportFonts() != null && font.getExportFonts().size() > 0)
        {

            bean_xml.append("       <property name=\"exportFonts\">\n");
            bean_xml.append("           <map>\n");

            java.util.Iterator<String> keys = font.getExportFonts().keySet().iterator();


            while (keys.hasNext())
            {
                String key = keys.next();
                bean_xml.append("               <entry key=\"" + Misc.escapeXMLEntity(key) +  "\">\n");
                bean_xml.append("                   <value><![CDATA[" + font.getExportFonts().get(key) +  "]]></value>\n");
                bean_xml.append("               </entry>\n");
            }


            bean_xml.append("           </map>\n");
            bean_xml.append("       </property>\n");
        }

        if (font.getLocales() != null && font.getLocales().size() > 0)
        {

            bean_xml.append("       <property name=\"locales\">\n");
            bean_xml.append("           <set>\n");

            java.util.Iterator<String> keys = font.getLocales().iterator();

            while (keys.hasNext())
            {
                String key = keys.next();
                bean_xml.append("               <value><![CDATA[" + key +  "]]></value>\n");
            }


            bean_xml.append("           </set>\n");
            bean_xml.append("       </property>\n");
        }

        bean_xml.append("   </bean>\n\n");

        return bean_xml.toString();
    }


    public static void export(Object[] fonts)
    {

        javax.swing.JFileChooser jfc = new javax.swing.JFileChooser( IReportManager.getInstance().getCurrentDirectory());

        jfc.setDialogTitle(I18n.getString("IReportPanel.Title.Dialog"));
        jfc.setDialogTitle(I18n.getString("IReportPanel.Title.Dialog"));//"addToClassPath"

        jfc.setAcceptAllFileFilterUsed(true);
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY  );
        jfc.addChoosableFileFilter( new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File file) {
                String filename = file.getName();
                return (filename.toLowerCase().endsWith(".jar") || file.isDirectory() ||
                        filename.toLowerCase().endsWith(".zip")
                        ) ;
            }
            public String getDescription() {
                return "*.jar, *.zip";
            }
        });

        jfc.setMultiSelectionEnabled(false);

        jfc.setDialogType( javax.swing.JFileChooser.SAVE_DIALOG);
        if  (jfc.showSaveDialog(Misc.getMainFrame()) == javax.swing.JOptionPane.OK_OPTION) {

            java.io.File jarFile = jfc.getSelectedFile();
            File fontsDir = Misc.getFontsDirectory();

            FileOutputStream fos = null;

            try
            {
                fos = new FileOutputStream(jarFile);
                ZipOutputStream zipos = new ZipOutputStream(fos);
                zipos.setMethod(ZipOutputStream.DEFLATED);

                String fontXmlFile = "fonts" + (new Date()).getTime()+".xml";

                ZipEntry propsEntry = new ZipEntry("jasperreports_extension.properties");
                zipos.putNextEntry(propsEntry);

                PrintWriter pw = new PrintWriter(zipos);


                pw.println("net.sf.jasperreports.extension.registry.factory.fonts=net.sf.jasperreports.extensions.SpringExtensionsRegistryFactory");
                pw.println("net.sf.jasperreports.extension.fonts.spring.beans.resource=/fonts/" + fontXmlFile);

                pw.flush();

                ZipEntry fontsXmlEntry = new ZipEntry("fonts/"+fontXmlFile);
                zipos.putNextEntry(fontsXmlEntry);

                pw = new PrintWriter(zipos);

                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

                pw.println("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
                pw.println("       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
                pw.println("       xsi:schemaLocation=\"http://www.springframework.org/schema/beans");
                pw.println("           http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">\n");

                List<File> files = new ArrayList<File>();
                for (Object font : fonts)
                {
                    if (font instanceof SimpleFontFamilyEx)
                    {
                        SimpleFontFamilyEx sff = (SimpleFontFamilyEx)font;

                        String[] fontNames = new String[4];
                        fontNames[0] = sff.getNormalFont();
                        fontNames[1] = sff.getBoldFont();
                        fontNames[2] = sff.getItalicFont();
                        fontNames[3] = sff.getBoldItalicFont();

                        for (int i=0; i<fontNames.length; ++i)
                        {
                            if ( fontNames[i] != null && fontNames[i].length() > 0)
                            {
                                File fromFile = new File(fontsDir,fontNames[i] );
                                if (fromFile.exists())
                                {
                                    files.add(fromFile);
                                }
                            }
                        }
                        
                        sff = convertFontFamily(sff,"fonts/");
                        pw.println(dumpBean(sff));
                    }
                }
                
                pw.println("</beans>");

                pw.flush();

                for (File f : files)
                {
                    ZipEntry ttfZipEntry = new ZipEntry("fonts/" + f.getName());
                    zipos.putNextEntry(ttfZipEntry);

                    FileUtil.copy(new FileInputStream(f),zipos);
                }


                zipos.flush();
                zipos.finish();

                javax.swing.JOptionPane.showMessageDialog(Misc.getMainFrame(),
                    "Extension Jar succesfully created",
                    "Done",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex)
            {
                javax.swing.JOptionPane.showMessageDialog(Misc.getMainFrame(),
                    ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE );
                return;
            }
            finally
            {
                if (fos != null)
                {
                    try
                    {
                        fos.close();
                    }
                    catch (IOException e)
                    {
                    }
                }
            }
        }
    }


    private static SimpleFontFamilyEx convertFontFamily(SimpleFontFamilyEx font, String fontPath)
    {
        SimpleFontFamilyEx newFont = new SimpleFontFamilyEx();
        newFont.setName( font.getName() );
        newFont.setLocales( font.getLocales() );
        newFont.setExportFonts( font.getExportFonts() );
        newFont.setPdfEmbedded( font.isPdfEmbedded() );
        newFont.setPdfEncoding( font.getPdfEncoding() );

        newFont.setName( font.getName() );

        String fname = font.getNormalFont();
        if (fname != null && fname.length() > 0)
        {
            File f = new File(fname);
            newFont.setNormalFont( fontPath + f.getName()  );
        }

        fname = font.getBoldFont();
        if (fname != null && fname.length() > 0)
        {
            File f = new File(fname);
            newFont.setBoldFont( fontPath +  f.getName()  );
        }

        fname = font.getItalicFont();
        if (fname != null && fname.length() > 0)
        {
            File f = new File(fname);
            newFont.setItalicFont( fontPath +  f.getName()  );
        }

        fname = font.getBoldItalicFont();
        if (fname != null && fname.length() > 0)
        {
            File f = new File(fname);
            newFont.setBoldItalicFont( fontPath +  f.getName()  );
        }

        return newFont;

    }

  
}
