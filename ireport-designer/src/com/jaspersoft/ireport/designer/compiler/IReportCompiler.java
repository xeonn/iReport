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
 * This program Iis distributed WITHOUT ANY WARRANTY; and without the
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
 *
 *
 *
 *
 * IReportCompiler.java
 *
 * Created on 6 giugno 2003, 0.44
 *
 */

package com.jaspersoft.ireport.designer.compiler;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.JrxmlEditorSupport;
import com.jaspersoft.ireport.designer.JrxmlPreviewView;
import com.jaspersoft.ireport.designer.ReportClassLoader;
import com.jaspersoft.ireport.designer.ThreadUtils;
import com.jaspersoft.ireport.designer.compiler.prompt.Prompter;
import com.jaspersoft.ireport.designer.compiler.xml.SourceLocation;
import com.jaspersoft.ireport.designer.compiler.xml.SourceTraceDigester;
import com.jaspersoft.ireport.designer.connection.EJBQLConnection;
import com.jaspersoft.ireport.designer.connection.JRDataSourceProviderConnection;
import com.jaspersoft.ireport.designer.connection.JRHibernateConnection;
import com.jaspersoft.ireport.designer.connection.MondrianConnection;
import com.jaspersoft.ireport.designer.data.queryexecuters.QueryExecuterDef;
import com.jaspersoft.ireport.designer.logpane.IRConsoleTopComponent;
import com.jaspersoft.ireport.designer.logpane.LogTextArea;
import com.jaspersoft.ireport.designer.logpane.ProblemItem;
import com.jaspersoft.ireport.designer.tools.TimeZoneWrapper;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRJdtCompiler;
import net.sf.jasperreports.engine.design.JRValidationException;
import net.sf.jasperreports.engine.design.JRValidationFault;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.olap.JRMondrianQueryExecuterFactory;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlDigesterFactory;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.xml.sax.SAXException;

/**
 * Please note that this class is totally different from the old IReportCompiler.class
 * shipped with iReport 1.
 * @author  Administrator
 */
public class IReportCompiler implements Runnable, JRExportProgressMonitor
{

   public static final int CMD_COMPILE  = 0x01;
   public static final int CMD_EXPORT   = 0x02;
   public static final int CMD_COMPILE_SCRIPTLET = 0x04;

   public static final String OUTPUT_DIRECTORY     = "OUTPUT_DIRECTORY";
   public static final String OUTPUT_FORMAT        = "OUTPUT_FORMAT";
   public static final String USE_EMPTY_DATASOURCE = "USE_EMPTY_DATASOURCE";
   public static final String USE_CONNECTION = "USE_CONNECTION";
   public static final String CONNECTION = "CONNECTION";
   public static final String SCRIPTLET_OUTPUT_DIRECTORY = "SCRIPTLET_OUTPUT_DIRECTORY";
   public static final String COMPILER = "COMPILER";
   public static final String EMPTY_DATASOURCE_RECORDS = "EMPTY_DATASOURCE_RECORDS";

   private String constTabTitle = "";

   static PrintStream myPrintStream = null;
   int filledpage=0;


   private String status="Starting";
   private IReportConnection iReportConnection;
   private int statusLevel = 0;

   private int maxBufferSize = 100000;
   private int command;
   private HashMap properties;
   private Thread thread;
   
   private FileObject file = null;

   private LogTextArea logTextArea = null;

   private String javaFile = "";
   static private StringBuffer outputBuffer = new StringBuffer();
   
   /**
    * This is used to enable the preview tab when the report is ready...
    */
   private JrxmlEditorSupport support = null;
   
   ProgressHandle handle = null;


   /**
    * added by Felix Firgau
    */
   private static Vector compileListener = new Vector();

   /** Creates a new instance of IReportCompiler */
   public IReportCompiler()
   {
      properties = new HashMap();
      command = 0;

      try {
        maxBufferSize = Integer.parseInt( System.getProperty("ireport.maxbufsize", "100000"));
      } catch (Exception ex)
      {
          maxBufferSize = 1000000;
      }
   }

   /**
    * This method should be called in case of interruption...
    */
   public void stopThread()
   {
       command = 0;
//       if (thread != null && thread.isAlive())
//       {
//           try  {
//                thread.interrupt();
//           } catch (Exception ex)
//           {
//               ex.printStackTrace();
//           }
//       }
       cleanup();

       getLogTextArea().setTitle("Killed" + constTabTitle);
       getLogTextArea().setRemovable(true);
       System.gc();
       System.gc();
   }

   /** When an object implementing interface <code>Runnable</code> is used
    * to create a thread, starting the thread causes the object's
    * <code>run</code> method to be called in that separately executing
    * thread.
    * <p>
    * The general contract of the method <code>run</code> is that it may
    * take any action whatsoever.
    *
    * @see     java.lang.Thread#run()
    *
    */
    @SuppressWarnings({"deprecation", "unchecked"})
   public void run()
   {

      PrintStream out = System.out;
      PrintStream err = System.err;
      
      String JRXML_FILE_NAME = FileUtil.toFile(getFile()).getPath();
      
      init();
      
      try {
          
      
          SourceTraceDigester digester = null;
          ErrorsCollector errorsCollector = new ErrorsCollector();

          File f_report_title = FileUtil.toFile(getFile());
          constTabTitle = " [" + f_report_title.getName() + "]";



          logTextArea = IRConsoleTopComponent.getDefault().createNewLog();
          status  = "Starting";
          updateHandleStatus(status);
          logTextArea.setTitle(status + constTabTitle);

          String backupJRClasspath = net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH);
          // System.getProperty("jasper.reports.compile.class.path");
          String backupSystemClasspath = System.getProperty("java.class.path");

          boolean compilation_ok = true;
          long start = System.currentTimeMillis();
          // Redirect output stream....

          if (myPrintStream == null)
             myPrintStream  =new PrintStream(new FilteredStream(new ByteArrayOutputStream()));

          if (out != myPrintStream)
             System.setOut(myPrintStream);
          if (err != myPrintStream)
             System.setErr(myPrintStream);

          outputBuffer= new StringBuffer();


            //by Egon - DEBUG: Something is wrong here, please check. ok? thx.
            //1 - Line 148 - srcScriptletFileName = C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxmScriptlet.java -> scriptlet filename
            //2 - Line 157 - Misc.nvl( new File(fileName).getParent(), ".") =>  .  -> report directory

          // Add an entry in the thread list...
        //by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxml
          String fileName = JRXML_FILE_NAME;

            //by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxml
          String srcFileName = JRXML_FILE_NAME;
            //by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jasper
          fileName = Misc.changeFileExtension(fileName,"jasper");


          File f = new File(fileName);
          if (properties.get(IReportCompiler.OUTPUT_DIRECTORY) != null)
          {
                    //by Egon - DEBUG: .\FirstJasper.jasper
             fileName = (String)properties.get(IReportCompiler.OUTPUT_DIRECTORY);
             if (!fileName.endsWith(File.separator))
             {
                    fileName += File.separator;
             }
             fileName += f.getName();
          }

            //by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxml
          String scriptletFileName = JRXML_FILE_NAME;
            //by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxml
          String srcScriptletFileName = JRXML_FILE_NAME;
            //by Egon - DEBUG: .\FirstJasper.
          //fileName = Misc.changeFileExtension(fileName,"");
            //by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxmScriptlet.java
          scriptletFileName = srcScriptletFileName.substring(0,scriptletFileName.length()-1)+"Scriptlet.java";
            //1 - by Egon - DEBUG: C:\jasperreports-0.5.3\demo\samples\jasper\FirstJasper.jrxmScriptlet.java
          srcScriptletFileName = scriptletFileName;

          File f2 = new File(scriptletFileName);
          if (properties.get(IReportCompiler.SCRIPTLET_OUTPUT_DIRECTORY) != null)
          {
             scriptletFileName = (String)properties.get(IReportCompiler.SCRIPTLET_OUTPUT_DIRECTORY) + f2.separatorChar + f2.getName();
          }


           String reportDirectory = new File(JRXML_FILE_NAME).getParent();
           //String classpath = System.getProperty("jasper.reports.compile.class.path");
           String classpath = net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH);

           if(classpath != null){
                classpath += File.pathSeparator + reportDirectory;
                //System.setProperty("jasper.reports.compile.class.path", classpath);
                net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH, classpath);
          } else if (System.getProperty("java.class.path") != null){
            classpath = System.getProperty("java.class.path");
            classpath += File.pathSeparator + reportDirectory;
            System.setProperty("java.class.path", classpath);
            }
           reportDirectory = reportDirectory.replace('\\', '/');
           if(!reportDirectory.endsWith("/")){
                reportDirectory += "/";//the file path separator must be present
           }
           if(!reportDirectory.startsWith("/")){
                reportDirectory = "/" + reportDirectory;//it's important to JVM 1.4.2 especially if contains windows drive letter
           }
           ReportClassLoader reportClassLoader = new ReportClassLoader(IReportManager.getInstance().getReportClassLoader());
           reportClassLoader.setRelodablePaths( reportDirectory );

           /******************/

           try{
                Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[]{new URL("file://"+reportDirectory)},  reportClassLoader));
           } catch (MalformedURLException mue){
                mue.printStackTrace();
           }

           if (Thread.interrupted()) throw new InterruptedException();
           
           /******************/




           /*
           if ((command & CMD_COMPILE_SCRIPTLET) != 0)
          {
             status  = "Compiling scriptlet";
             updateThreadList();
             start = System.currentTimeMillis();

             // Compile the scriptlet class...

             //String tempDirStr = System.getProperty("jasper.reports.compile.temp");
             String tempDirStr = net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_TEMP_DIR);

             String oldCompileTemp  = tempDirStr;
             if (tempDirStr == null || tempDirStr.length() == 0)
             {
                tempDirStr = new File(JRXML_FILE_NAME).getParent();
             }
             File tempDirFile = new File(tempDirStr);
             javaFile = srcScriptletFileName;
             javaFile = (new File(tempDirFile,javaFile)).getPath();

             javaFile = jrf.getReport().getScriptletFileName();



             if (Misc.getLastWriteTime(javaFile) > Misc.getLastWriteTime(Misc.changeFileExtension(javaFile, "class" )))
             {
                     getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\">" +
                             I18n.getFormattedString("iReportCompiler.compilingScriptlet", "Compiling scriptlet source file... {0}",
                                    new Object[]{javaFile }) + "</font>",true);
                     try
                     {
                        //JasperCompileManager.compileReportToFile(srcFileName, fileName);
                        net.sf.jasperreports.engine.design.JRJdk13Compiler compiler = new net.sf.jasperreports.engine.design.JRJdk13Compiler();
                        String errors = compiler.compileClass( new File(javaFile),   Misc.getClassPath() );
                        if (errors != null && errors.length() > 0)
                        {
                            getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#CC0000\"><b>" +
                                    I18n.getFormattedString("iReportCompiler.errorsCompilingScriptlet", "Errors compiling {0}!",
                                    new Object[]{javaFile }) +"</b></font>",true);
                            getLogTextArea().logOnConsole(errors);
                            compilation_ok = false;
                        }
                     }
                     catch (Exception ex)
                     {
                        getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#CC0000\"><b>" +
                                I18n.getString("iReportCompiler.errorsCompilingScriptletJavaSource", "Error compiling the Scriptlet java source!") +
                                "</b></font>",true);
                        StringWriter sw = new StringWriter(0);
                        ex.printStackTrace(new PrintWriter(sw));
                        myPrintStream.flush();
                        parseException( outputBuffer.toString()+sw.getBuffer()+"", null);
                        compilation_ok = false;
                     }
                     catch (Throwable ext)
                     {
                        getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#CC0000\"><b>" +
                                I18n.getString("iReportCompiler.errorsCompilingScriptletJavaSource", "Error compiling the Scriptlet java source!") +
                                "</b></font>",true);
                        StringWriter sw = new StringWriter(0);
                        ext.printStackTrace(new PrintWriter(sw));
                        myPrintStream.flush();
                        parseException( outputBuffer.toString()+sw.getBuffer()+"", null);
                        compilation_ok = false;
                     }
                     finally
                     {
                        if(mainFrame.isUsingCurrentFilesDirectoryForCompiles())
                        {

                        }//end if using current files directory for compiles
                     }//end finally
                     getLogTextArea().logOnConsole(outputBuffer.toString());
                     outputBuffer=new StringBuffer();
                     getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#0000CC\"><b>" +
                             I18n.getFormattedString("iReportCompiler.compilationRunningTime", "Compilation running time: {0,number}!",
                                    new Object[]{new Long(System.currentTimeMillis() - start)}) + "</b></font><hr>",true);
             }
          }

          if (!compilation_ok) {

              fireCompileListner(this, CL_COMPILE_FAIL, CLS_COMPILE_SCRIPTLET_FAIL);
              removeThread();
              return;
          }
          */


          
          if ((command & CMD_COMPILE) != 0)
          {
             status  = "Compiling report";
             updateHandleStatus(status);

             //System.setProperty("jasper.reports.compile.keep.java.file", "true");

             if (IReportManager.getInstance().getProperty("KeepJavaFile","false").equals("false") )
             {
                    net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_KEEP_JAVA_FILE, false);
             }
             else
             {
                    net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_KEEP_JAVA_FILE, true);
             }

             //String tempDirStr = System.getProperty("jasper.reports.compile.temp");
             String tempDirStr = net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_TEMP_DIR);

             String oldCompileTemp  = tempDirStr;
             if (tempDirStr == null || tempDirStr.length() == 0)
             {
                tempDirStr = new File(JRXML_FILE_NAME).getParent();
             }
             File tempDirFile = new File(tempDirStr);
             javaFile = (new File(tempDirFile,javaFile)).getPath();

             URL img_url_comp = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/comp1_mini.jpg");

             getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url_comp  +"\"> &nbsp;" +
                             Misc.formatString("Compiling to file... {0}",
                                    new Object[]{fileName}) + "</font>",true);

             String old_jr_classpath = Misc.nvl(net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH), "");
             @SuppressWarnings("deprecation")
             String old_defaul_compiler = Misc.nvl(net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS), "");

             try
             {
                //net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_TEMP_DIR, tempDirStr);
                //net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH, Misc.nvl( new File(fileName).getParent(), ".")  + File.pathSeparator  + Misc.getClassPath());

                String compiler_name  = "JasperReports default compiler";
                String compiler_code = IReportManager.getInstance().getProperty("DefaultCompiler",null);

                JRJdtCompiler jdtCompiler = null; 

                if (this.getProperties().get(COMPILER) != null)
                {
                    net.sf.jasperreports.engine.util.JRProperties. setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, ""+this.getProperties().get(COMPILER));
                    compiler_name = Misc.formatString("Special language compiler ({0})", new Object[]{this.getProperties().get(COMPILER)});
                }
                else if (compiler_code !=  null && !compiler_code.equals("0") && !compiler_code.equals(""))
                {
                    if (compiler_code.equals("1"))
                    {
                        net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, "net.sf.jasperreports.engine.design.JRJdk13Compiler");
                        compiler_name = "Java Compiler";
                    }
                    else if (compiler_code.equals("2"))
                    {
                        net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, "it.businesslogic.ireport.compiler.ExtendedJRJdtCompiler" );
                        compiler_name = "JDT Compiler";
                        jdtCompiler = new ExtendedJRJdtCompiler();
                    }
                    else if (compiler_code.equals("3"))
                    {
                        net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, "net.sf.jasperreports.compilers.JRBshCompiler" );
                        compiler_name = "BeanShell Compiler";
                    }
                    else if (compiler_code.equals("4"))
                    {
                        net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, "net.sf.jasperreports.engine.design.JRJikesCompiler" );
                        compiler_name = "Jikes Compiler";
                    }
                }
                else
                {
                     //Force to use the jdtCompiler compiler....
                     net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, "it.businesslogic.ireport.compiler.ExtendedJRJdtCompiler" );
                     jdtCompiler = new ExtendedJRJdtCompiler();
                }


               // getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#000000\"><b>Using compiler "+ compiler_name  + " (" + System.getProperty("jasper.reports.compiler.class","" ) +")</b></font>",true);
                start = System.currentTimeMillis();

                digester = IReportCompiler.createDigester();
                JasperDesign jd = IReportCompiler.loadJasperDesign( new FileInputStream(srcFileName) , digester);

                if (jdtCompiler != null)
                {
                    ((ExtendedJRJdtCompiler)jdtCompiler).setDigester(digester);
                    ((ExtendedJRJdtCompiler)jdtCompiler).setErrorHandler(errorsCollector);

                    JasperReport finalJR = jdtCompiler.compileReport( jd  );

                    if (errorsCollector.getProblemItems().size() > 0 || finalJR == null)
                    {

                        throw new JRException("");
                    }
                    JRSaver.saveObject(finalJR,  fileName);

                    //System.out.println("Report saved..." + finalJR + " " + errorsCollector.getProblemItems().size());
                }
                else
                {
                    JasperCompileManager.compileReportToFile(jd, fileName);
                }

                if (errorsCollector != null)
                {
                        //getJrf().setReportProblems( errorsCollector.getProblemItems() );
                        //MainFrame.getMainInstance().getLogPane().getProblemsPanel().updateProblemsList();
                }

             }
             catch (JRValidationException e)
             {
                 compilation_ok = false;

                    for (Iterator it = e.getFaults().iterator(); it.hasNext();)
                    {
                            JRValidationFault fault = (JRValidationFault) it.next();
                            Object source = fault.getSource();
                            SourceLocation sl = digester.getLocation( source );
                            if (sl == null)
                            {
                                errorsCollector.getProblemItems().add(new ProblemItem(ProblemItem.WARNING, fault.getMessage(), sl, null) );
                            }   
                            else
                            {
                                errorsCollector.getProblemItems().add(new ProblemItem(ProblemItem.WARNING, fault.getMessage(), sl, sl.getXPath()) );
                                System.out.println(fault + " " + fault.getMessage() + "\nLine: " + sl.getLineNumber() + ", Column: " + sl.getColumnNumber()  + " JRXML Element: " + sl.getXPath() );
                            }

                            //
                    }
                    //getJrf().setReportProblems( errorsCollector.getProblemItems() );
                    //MainFrame.getMainInstance().getLogPane().getProblemsPanel().updateProblemsList();

                    StringWriter sw = new StringWriter(0);
                    e.printStackTrace(new PrintWriter(sw));
                    System.out.println("\n\n\n");
                    myPrintStream.flush();
                    parseException( outputBuffer.toString()+sw.getBuffer()+"", null);



             } catch (JRException jrex)
             {
                 if (errorsCollector != null && errorsCollector.getProblemItems() != null)
                 {
                    //getJrf().setReportProblems( errorsCollector.getProblemItems() );
                    //MainFrame.getMainInstance().getLogPane().getProblemsPanel().updateProblemsList();
                 }

                 compilation_ok = false;
                 getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#CC0000\"><b>" +
                             Misc.formatString( "Errors compiling {0}!",
                                    new Object[]{fileName}) + "</b></font>",true);


                StringWriter sw = new StringWriter(0);
                jrex.printStackTrace(new PrintWriter(sw));

                System.out.println("\n\n\n");
                myPrintStream.flush();
                parseException( outputBuffer.toString()+sw.getBuffer()+"", null);

             }
             catch (Exception ex)
             {
                getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#CC0000\"><b>" +
                             "Error compiling the report java source!" + "</b></font>",true);
                StringWriter sw = new StringWriter(0);
                ex.printStackTrace(new PrintWriter(sw));
                myPrintStream.flush();
                parseException( outputBuffer.toString()+sw.getBuffer()+"", null);
                compilation_ok = false;
             }
             catch (Throwable ext)
             {
                getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#CC0000\"><b>" +
                             "Error compiling the report java source!" + "</b></font>",true);
                StringWriter sw = new StringWriter(0);
                ext.printStackTrace(new PrintWriter(sw));
                myPrintStream.flush();
                parseException( outputBuffer.toString()+sw.getBuffer()+"", null);
                compilation_ok = false;
             }
             finally
             {
                //System.setProperty("jasper.reports.compile.class.path", old_jr_classpath);
                net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH, old_jr_classpath );
                //System.setProperty("jasper.reports.compiler.class", old_defaul_compiler);
                net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASS, old_defaul_compiler );

                //if(mainFrame.isUsingCurrentFilesDirectoryForCompiles())
                //{
                   if( oldCompileTemp != null )
                   {
                      System.setProperty("jasper.reports.compile.temp", oldCompileTemp);
                      net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_TEMP_DIR, oldCompileTemp );
                   }
                   else
                   {
                      System.setProperty("jasper.reports.compile.temp", "");
                      net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_TEMP_DIR, "" );
                   }

                //}//end if using current files directory for compiles
             }//end finally
             getLogTextArea().logOnConsole(outputBuffer.toString());
             outputBuffer=new StringBuffer();
             getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#0000CC\"><b>" +
                             Misc.formatString("Compilation running time: {0,number}!",
                                    new Object[]{new Long(System.currentTimeMillis() - start)}) + "</b></font><hr>",true);

             if (errorsCollector != null && errorsCollector.getProblemItems().size() > 0)
             {
                        try {
                            SwingUtilities.invokeAndWait(new Runnable() {
                                public void run() {
                                       //IRConsoleTopComponent.getDefault().setActiveLogComponent( MainFrame.getMainInstance().getLogPane().getProblemsPanel() );
                                }
                            });
                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
             }
          }


          if (!compilation_ok) {

              fireCompilationStatus(CompilationStatusEvent.STATUS_FAILED, CLS_COMPILE_SOURCE_FAIL);
              fireCompileListner(this, CL_COMPILE_FAIL, CLS_COMPILE_SOURCE_FAIL);
              cleanup();
              handle.finish();
              showErrorConsole();
              
              
              
              
              return;
          }

          if  ((command & CMD_EXPORT) != 0)
          {

             status  = "Generating report";
             updateHandleStatus(status);
             
             String queryLanguage = "sql";
             JasperDesign jd = null;
             try {
                 jd = JRXmlLoader.load(JRXML_FILE_NAME);
                 if (jd.getQuery() != null &&
                  jd.getQuery().getText() != null)
                 {
                  queryLanguage = jd.getQuery().getText();
                 }
             } catch (Exception ex) { }

             // Try to look for a good QueryExecutor...
               List<QueryExecuterDef> configuredExecuters = IReportManager.getInstance().getQueryExecuters();
               for (QueryExecuterDef qe : configuredExecuters)
               {
                   if (qe.getLanguage().equals( queryLanguage ))
                   {
                       net.sf.jasperreports.engine.util.JRProperties.setProperty("net.sf.jasperreports.query.executer.factory." + qe.getLanguage(), qe.getClassName());
                       getLogTextArea().logOnConsole(
                                        Misc.formatString("Setting {0} as Query Executer Factory for language: {1}\n",
                                        new Object[]{qe.getClassName(), ""+qe.getLanguage() }));

                       break;
                   }

               }

             // Compile report....
             JasperPrint print = null;
             URL img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/rundb1_mini.jpg");

             getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;" +
                     "Filling report..."+ "</font>",true);



             statusLevel = 5;
             Map hm = Prompter.promptForParameters( jd );

             hm.put("REPORT_LOCALE",  Misc.getLocaleFromString(IReportManager.getInstance().getProperty("reportLocale","")));

             img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/world.png");
             getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;"+
                     Misc.formatString("Locale: <b>{0}</b>",
                     new Object[]{Misc.getLocaleFromString(IReportManager.getInstance().getProperty("reportLocale", null)).getDisplayName()}) + "</font>",true);

             String reportTimeZoneId = IReportManager.getInstance().getProperty("reportTimeZoneId","");
             String timeZoneName = "Default";
             if (reportTimeZoneId != null && reportTimeZoneId.length() > 0 )
             {
                java.util.TimeZone tz = java.util.TimeZone.getTimeZone(reportTimeZoneId);
                hm.put("REPORT_TIME_ZONE", tz );
                timeZoneName = new TimeZoneWrapper( tz ) + "";
             }

             img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/timezone.png");
             getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;" +
                     Misc.formatString("Time zone: <b>{0}</b>",
                     new Object[]{timeZoneName}) + "</font>",true);



             int reportMaxCount = 0;
             try {
                 if ( IReportManager.getPreferences().getBoolean("limitRecordNumber", false) )
                 {
                     reportMaxCount = IReportManager.getPreferences().getInt("maxRecordNumber",0);
                 }
             } catch (Exception ex) {}

             if (reportMaxCount > 0)
             {
                 img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/file-info.png");
                 getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;" +
                     Misc.formatString("Max number of records: <b>{0,number}</b>",
                     new Object[]{new Long(reportMaxCount)}) + "</font>",true);

                 hm.put("REPORT_MAX_COUNT",  new Integer(reportMaxCount) );
             }


            // Thread.currentThread().setContextClassLoader( reportClassLoader );



             if (IReportManager.getPreferences().getBoolean("isIgnorePagination",false))
             {
                 hm.put("IS_IGNORE_PAGINATION",  Boolean.TRUE );
                 img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/file-info.png");
                 getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;" +
                         "Ignoring pagination" + "</font>",true);

             }
             if (IReportManager.getPreferences().getBoolean("isUseReportVirtualizer",false))
             {
                 try {


                     net.sf.jasperreports.engine.JRVirtualizer virtualizer = null;
                     String rvName = IReportManager.getInstance().getProperty("ReportVirtualizer", "JRFileVirtualizer");
                     String vrTmpDirectory = IReportManager.getInstance().getProperty("ReportVirtualizerDirectory", System.getProperty("java.io.tmpdir") );
                     int vrSize = Integer.parseInt( IReportManager.getInstance().getProperty("ReportVirtualizerSize","100"));

                     String msg = "";

                     if (rvName.equals("JRGzipVirtualizer"))
                     {
                         msg = Misc.formatString("JRGzipVirtualizer Size: {0,number}<br>",
                                                  new Object[]{new Integer(vrSize)});
                         virtualizer = new net.sf.jasperreports.engine.fill.JRGzipVirtualizer(vrSize);
                     }
                     else if (rvName.equals("JRSwapFileVirtualizer"))
                     {
                         msg = Misc.formatString("JRSwapFileVirtualizer Size: {0,number} Swap directory: {1};<br>" +
                                                 "  ReportVirtualizerBlockSize: {2}<br>ReportVirtualizerGrownCount: {3}<br>",
                                                       new Object[]{new Integer(vrSize), vrTmpDirectory,
                                                                    IReportManager.getInstance().getProperty("ReportVirtualizerBlockSize","100"),
                                                                    IReportManager.getInstance().getProperty("ReportVirtualizerGrownCount","100")});

                         JRSwapFile swapFile = new JRSwapFile(vrTmpDirectory,
                                 Integer.parseInt( IReportManager.getInstance().getProperty("ReportVirtualizerBlockSize","100")),
                                 Integer.parseInt( IReportManager.getInstance().getProperty("ReportVirtualizerGrownCount","100")));
                         virtualizer = new net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer(vrSize,swapFile);
                     }
                     else // default if (rvName.equals("JRFileVirtualizer"))
                     {
                         msg = Misc.formatString("JRFileVirtualizer Size: {0,number} Swap directory: {1};<br>",
                                                       new Object[]{new Integer(vrSize),vrTmpDirectory});
                        virtualizer = new net.sf.jasperreports.engine.fill.JRFileVirtualizer(vrSize, vrTmpDirectory );
                     }

                     img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/file-info.png");
                     getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#000000\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;"
                             + "Using report virtualizer... " +  msg+ "</font>",true);

                     hm.put("REPORT_VIRTUALIZER", virtualizer );

                 } catch (Throwable ex)
                 {
                     getLogTextArea().logOnConsole("<font face=\"SansSerif\" size=\"3\" color=\"#660000\">" +
                             "WARNING: Report virtualizer not available." +
                             "</font>",true);

                 }
             }

             if (Thread.interrupted()) throw new InterruptedException();
             start = System.currentTimeMillis();

             if (properties.get(USE_EMPTY_DATASOURCE) != null && properties.get(USE_EMPTY_DATASOURCE).equals("true"))
             {
                try
                {
                   int records = 1;
                   try {

                       records = ((Integer)properties.get(EMPTY_DATASOURCE_RECORDS)).intValue();
                   } catch (Exception ex)
                   {
                       records = 1;
                   }

                   print = JasperFillManager.fillReport(fileName,hm,new JREmptyDataSource(records));


                }
                catch (OutOfMemoryError ex)
                {
                    getLogTextArea().logOnConsole(
                            "Out of memory exception!\n"
                            );
                }
                catch (Exception ex)
                {
                   getLogTextArea().logOnConsole(
                           Misc.formatString("Error filling print... {0}\n",
                                             new Object[]{ex.getMessage()}));

                   ex.printStackTrace();
                   getLogTextArea().logOnConsole(outputBuffer.toString());
                   outputBuffer = new StringBuffer();
                   
                   showErrorConsole();
                   
                }
             }
             else if (properties.get(USE_CONNECTION) != null && properties.get(USE_CONNECTION).equals("true"))
             {
                IReportConnection connection = (IReportConnection)properties.get(CONNECTION);

                try
                {
                   hm = connection.getSpecialParameters( hm );

                   if (connection.isJDBCConnection())
                   {
                      print = JasperFillManager.fillReport(fileName,hm, connection.getConnection());
                   }
                   else if (connection.isJRDataSource())
                   {
                       JRDataSource ds = null;
                       if (connection instanceof JRDataSourceProviderConnection)
                       {

                            JasperReport jasper_report_obj =  (JasperReport)JRLoader.loadObject(fileName);
                            ds = ((JRDataSourceProviderConnection) connection).getJRDataSource(jasper_report_obj);

                            if (ds == null) return;
                            print = JasperFillManager.fillReport(jasper_report_obj,hm,ds);

                            try { ((JRDataSourceProviderConnection)connection).disposeDataSource(); } catch (Exception ex) {

                                getLogTextArea().logOnConsole(
                                      Misc.formatString("Error closing datasource: {0}\n",
                                                       new Object[]{ex.getMessage()}) );

                            }
                       }
                       else
                       {
                           ds = connection.getJRDataSource();
                           print = JasperFillManager.fillReport(fileName,hm,ds);
                       }
                   }
                   else
                   {
                       if (connection instanceof JRHibernateConnection)
                       {
                           Session session = null;
                           Transaction transaction = null;
                           System.out.println();
                           getLogTextArea().logOnConsole(
                                      "Hibernate session opened");

                           try {
                                session = ((JRHibernateConnection)connection).createSession();
                                transaction = session.beginTransaction();
                                hm.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, session);
                                print = JasperFillManager.fillReport(fileName,hm);

                           } catch (Exception ex)
                           {
                               throw ex;
                           } finally
                           {
                                if (transaction != null) try {  transaction.rollback(); } catch (Exception ex) { }
                                if (transaction != null) try {  session.close(); } catch (Exception ex) { }
                           }
                       }
                       else if (connection instanceof EJBQLConnection)
                       {
                           EntityManager em = null;
                           try {

                               getLogTextArea().logOnConsole(
                                      "Creating entity manager");

                                em = ((EJBQLConnection)connection).getEntityManager();
                                hm.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
                                //Thread.currentThread().setContextClassLoader( reportClassLoader );
                                print = JasperFillManager.fillReport(fileName,hm);

                           } catch (Exception ex)
                           {
                               throw ex;
                           } finally
                           {
                               getLogTextArea().logOnConsole(
                                     "Closing entity manager");
                                ((EJBQLConnection)connection).closeEntityManager();
                           }
                       }
                       else if (connection instanceof MondrianConnection)
                       {
                           mondrian.olap.Connection mCon = null;
                           try {
                               getLogTextArea().logOnConsole(
                                      "Opening Mondrian connection");
                                mCon = ((MondrianConnection)connection).getMondrianConnection();
                                hm.put(JRMondrianQueryExecuterFactory.PARAMETER_MONDRIAN_CONNECTION, mCon);
                                //Thread.currentThread().setContextClassLoader( reportClassLoader );
                                print = JasperFillManager.fillReport(fileName,hm);

                           } catch (Exception ex)
                           {
                               throw ex;
                           } finally
                           {
                               getLogTextArea().logOnConsole(
                                      "Closing Mondrian connection");
                                ((MondrianConnection)connection).closeMondrianConnection();
                           }
                       }
                       else // Query Executor mode...
                       {
                           //Thread.currentThread().setContextClassLoader( reportClassLoader );
                           print = JasperFillManager.fillReport(fileName,hm);
                       }
                   }

                } catch (Exception ex)
                {
                   getLogTextArea().logOnConsole(
                           Misc.formatString("Error filling print... {0}\n",
                                                       new Object[]{ex.getMessage()}));
                   ex.printStackTrace();
                   getLogTextArea().logOnConsole(outputBuffer.toString());
                   outputBuffer = new StringBuffer();
                   
                   showErrorConsole();
                }
                catch (Throwable ext)
                {
                    getLogTextArea().logOnConsole(
                           Misc.formatString("Error filling print... {0}\n",
                                                       new Object[]{ext + " " + ext.getCause()}));
                   ext.printStackTrace();
                   getLogTextArea().logOnConsole(outputBuffer.toString());
                   outputBuffer = new StringBuffer();
                   
                   getJrxmlPreviewView().setJasperPrint(null);
                   showErrorConsole();
                }
                finally
                {
                    connection.disposeSpecialParameters(hm);
                    if (connection != null && connection instanceof JRDataSourceProviderConnection)
                    {
                            try { ((JRDataSourceProviderConnection)connection).disposeDataSource(); } catch (Exception ex) {
                                getLogTextArea().logOnConsole(
                                      Misc.formatString("Error closing datasource: {0}\n",
                                                       new Object[]{ex.getMessage()}) );
                            }

                    }

                }
             }
             net.sf.jasperreports.view.JRViewer jrv = null;
             net.sf.jasperreports.engine.JRExporter exporter=null;

             getLogTextArea().logOnConsole(outputBuffer.toString());
             outputBuffer = new StringBuffer();
             
             if (Thread.interrupted()) throw new InterruptedException();

             if (print != null)
             {
                getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#0000CC\">" +
                             Misc.formatString("<b>Report fill running time: {0,number}!</b> (pages generated: {1,number})",
                                    new Object[]{new Long(System.currentTimeMillis() - start), new Integer((print.getPages()).size())}) + "</font><hr>",true);

                status  = "Exporting report";
                updateHandleStatus(status);
                start = System.currentTimeMillis();
                String  format = Misc.nvl(properties.get(OUTPUT_FORMAT),"pdf");
                String  viewer_program = "";

                //getLogTextArea().logOnConsole(properties.get(OUTPUT_FORMAT) + "Exporting\n");
                getLogTextArea().logOnConsole(outputBuffer.toString());
                outputBuffer = new StringBuffer();

                String exportingMessage = "";

                try
                {

                   if (format.equalsIgnoreCase("pdf"))
                   {
                      exporter = new  net.sf.jasperreports.engine.export.JRPdfExporter();

                      if (IReportManager.getInstance().getProperty("PDF_IS_ENCRYPTED") != null)
                      {
                          exporter.setParameter( JRPdfExporterParameter.IS_ENCRYPTED, new Boolean( IReportManager.getInstance().getProperty("PDF_IS_ENCRYPTED") ) );
                      }
                      if (IReportManager.getInstance().getProperty("PDF_IS_128_BIT_KEY") != null)
                      {
                          exporter.setParameter( JRPdfExporterParameter.IS_128_BIT_KEY, new Boolean( IReportManager.getInstance().getProperty("PDF_IS_128_BIT_KEY") ) );
                      }
                      if (IReportManager.getInstance().getProperty("PDF_USER_PASSWORD") != null)
                      {
                          exporter.setParameter( JRPdfExporterParameter.USER_PASSWORD, IReportManager.getInstance().getProperty("PDF_USER_PASSWORD"));
                      }
                      if (IReportManager.getInstance().getProperty("PDF_OWNER_PASSWORD") != null)
                      {
                          exporter.setParameter( JRPdfExporterParameter.OWNER_PASSWORD, IReportManager.getInstance().getProperty("PDF_OWNER_PASSWORD"));
                      }
                      if (IReportManager.getInstance().getProperty("PDF_PERMISSIONS") != null)
                      {
                          exporter.setParameter( JRPdfExporterParameter.PERMISSIONS, new Integer( IReportManager.getInstance().getProperty("PDF_PERMISSIONS")));
                      }

                      fileName = Misc.changeFileExtension(fileName,"pdf");
                      exportingMessage = Misc.formatString("Exporting pdf to file (using iText)...  {0}!",  new Object[]{fileName});
                      viewer_program = IReportManager.getInstance().getProperty("ExternalPDFViewer");
                   }
                   else if (format.equalsIgnoreCase("csv"))
                   {
                      exporter = new  net.sf.jasperreports.engine.export.JRCsvExporter();

                      if (IReportManager.getInstance().getProperty("CSV_FIELD_DELIMITER") != null)
                      {
                          exporter.setParameter( JRCsvExporterParameter.FIELD_DELIMITER, IReportManager.getInstance().getProperty("CSV_FIELD_DELIMITER") );
                      }

                      fileName = Misc.changeFileExtension(fileName,"csv");
                      exportingMessage = Misc.formatString("Exporting CSV to file... {0}!",  new Object[]{fileName});
                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalCSVViewer"), "");
                   }
                   else if (format.equalsIgnoreCase("html"))
                   {
                      exporter = new  net.sf.jasperreports.engine.export.JRHtmlExporter();

                      if (IReportManager.getInstance().getProperty("HTML_IMAGES_DIR_NAME") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.IMAGES_DIR_NAME, IReportManager.getInstance().getProperty("HTML_IMAGES_DIR_NAME") ); }
                      if (IReportManager.getInstance().getProperty("HTML_IS_OUTPUT_IMAGES_TO_DIR") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, new Boolean( IReportManager.getInstance().getProperty("HTML_IS_OUTPUT_IMAGES_TO_DIR")) ); }
                      if (IReportManager.getInstance().getProperty("HTML_IMAGES_URI") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.IMAGES_URI, IReportManager.getInstance().getProperty("HTML_IMAGES_URI") ); }
                      if (IReportManager.getInstance().getProperty("HTML_HTML_HEADER") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.HTML_HEADER, IReportManager.getInstance().getProperty("HTML_HTML_HEADER") ); }
                      if (IReportManager.getInstance().getProperty("HTML_BETWEEN_PAGES_HTML") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.BETWEEN_PAGES_HTML, IReportManager.getInstance().getProperty("HTML_BETWEEN_PAGES_HTML") ); }
                      if (IReportManager.getInstance().getProperty("HTML_HTML_FOOTER") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.HTML_FOOTER, IReportManager.getInstance().getProperty("HTML_HTML_FOOTER") ); }
                      if (IReportManager.getInstance().getProperty("HTML_IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(IReportManager.getInstance().getProperty("HTML_IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS"))); }
                      if (IReportManager.getInstance().getProperty("HTML_IS_WHITE_PAGE_BACKGROUND") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(IReportManager.getInstance().getProperty("HTML_IS_WHITE_PAGE_BACKGROUND")) ); }
                      if (IReportManager.getInstance().getProperty("HTML_IS_USING_IMAGES_TO_ALIGN") != null)
                      { exporter.setParameter( JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, new Boolean(IReportManager.getInstance().getProperty("HTML_IS_USING_IMAGES_TO_ALIGN")) ); }

                      fileName = Misc.changeFileExtension(fileName,"html");
                      exportingMessage = Misc.formatString("Exporting HTML to file... {0}!",  new Object[]{fileName});

                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalHTMLViewer"), "");
                   }
                   else if (format.equalsIgnoreCase("xls"))
                   {

                      exporter = new  net.sf.jasperreports.engine.export.JRXlsExporter();

                      if (IReportManager.getInstance().getProperty("XLS_IS_ONE_PAGE_PER_SHEET") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, new Boolean( IReportManager.getInstance().getProperty("XLS_IS_ONE_PAGE_PER_SHEET")) ); }
                      if (IReportManager.getInstance().getProperty("XLS_IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(IReportManager.getInstance().getProperty("XLS_IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS"))); }
                      if (IReportManager.getInstance().getProperty("XLS_IS_WHITE_PAGE_BACKGROUND") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(IReportManager.getInstance().getProperty("XLS_IS_WHITE_PAGE_BACKGROUND")) ); }
                      if (IReportManager.getInstance().getProperty("XLS_IS_DETECT_CELL_TYPE") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_DETECT_CELL_TYPE, new Boolean(IReportManager.getInstance().getProperty("XLS_IS_DETECT_CELL_TYPE")) ); }


                      fileName = Misc.changeFileExtension(fileName,"xls");
                      exportingMessage = Misc.formatString("Exporting xls to file (using POI)... {0}!",  new Object[]{fileName});
                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalXLSViewer"), "");

                   }
                   else if (format.equalsIgnoreCase("xls2"))
                   {

                      exporter = new  net.sf.jasperreports.engine.export.JExcelApiExporter();

                      if (IReportManager.getInstance().getProperty("XLS_IS_ONE_PAGE_PER_SHEET") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, new Boolean( IReportManager.getInstance().getProperty("XLS_IS_ONE_PAGE_PER_SHEET")) ); }
                      if (IReportManager.getInstance().getProperty("XLS_IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, new Boolean(IReportManager.getInstance().getProperty("XLS_IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS"))); }
                      if (IReportManager.getInstance().getProperty("XLS_IS_WHITE_PAGE_BACKGROUND") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, new Boolean(IReportManager.getInstance().getProperty("XLS_IS_WHITE_PAGE_BACKGROUND")) ); }
                      if (IReportManager.getInstance().getProperty("XLS_IS_DETECT_CELL_TYPE") != null)
                      { exporter.setParameter( JRXlsExporterParameter.IS_DETECT_CELL_TYPE, new Boolean(IReportManager.getInstance().getProperty("XLS_IS_DETECT_CELL_TYPE")) ); }


                      if (IReportManager.getInstance().getProperty("XLS2_IS_FONT_SIZE_FIX_ENABLED") != null)
                      { exporter.setParameter( JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, new Boolean( IReportManager.getInstance().getProperty("XLS2_IS_FONT_SIZE_FIX_ENABLED")) ); }

                      fileName = Misc.changeFileExtension(fileName,"xls");
                      exportingMessage = Misc.formatString("Exporting xls to file (using JExcelApi)... {0}!",  new Object[]{fileName});
                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalXLSViewer"), "");

                   }
                   else if (format.equalsIgnoreCase("java2D"))
                   {

                      //exporter = new  net.sf.jasperreports.engine.export.JRGraphics2DExporter();
                      //exportingMessage = " ";
                      exportingMessage = "Exporting to Java2D...";
                      viewer_program = null;
                   }
                   else if (format.equalsIgnoreCase("jrviewer"))
                   {
                      exportingMessage = "Viewing with JasperReports Viewer";
                      exporter = null;
                      viewer_program = null;
                   }
    //               else if (format.equalsIgnoreCase("txt"))
    //               {
    //                  exporter = new  it.businesslogic.ireport.export.JRTxtExporter();
    //
    //                  if (IReportManager.getInstance().getProperty("TXT_PAGE_ROWS") != null)
    //                  { exporter.setParameter( it.businesslogic.ireport.export.JRTxtExporterParameter.PAGE_ROWS, IReportManager.getInstance().getProperty("TXT_PAGE_ROWS") ); }
    //                  if (IReportManager.getInstance().getProperty("TXT_PAGE_COLUMNS") != null)
    //                  { exporter.setParameter( it.businesslogic.ireport.export.JRTxtExporterParameter.PAGE_COLUMNS, IReportManager.getInstance().getProperty("TXT_PAGE_COLUMNS") ); }
    //                  if (IReportManager.getInstance().getProperty("TXT_ADD_FORM_FEED") != null)
    //                  { exporter.setParameter( it.businesslogic.ireport.export.JRTxtExporterParameter.ADD_FORM_FEED, new Boolean(IReportManager.getInstance().getProperty("TXT_ADD_FORM_FEED"))); }
    //
    //                  fileName = Misc.changeFileExtension(fileName,"txt");
    //                  exportingMessage = Misc.formatString("Exporting txt (iReport) to file... {0}!",  new Object[]{fileName});
    //                  viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalTXTViewer"), "");
    //               }
                   else if (format.equalsIgnoreCase("txtjr"))
                   {
                      exporter = new  net.sf.jasperreports.engine.export.JRTextExporter();

                      if (IReportManager.getInstance().getProperty("JRTXT_PAGE_WIDTH") != null)
                      { exporter.setParameter( net.sf.jasperreports.engine.export.JRTextExporterParameter.PAGE_WIDTH, new Integer( IReportManager.getInstance().getProperty("JRTXT_PAGE_WIDTH")) ); }
                      if (IReportManager.getInstance().getProperty("JRTXT_PAGE_HEIGHT") != null)
                      { exporter.setParameter( net.sf.jasperreports.engine.export.JRTextExporterParameter.PAGE_HEIGHT, new Integer( IReportManager.getInstance().getProperty("JRTXT_PAGE_HEIGHT")) ); }
                      if (IReportManager.getInstance().getProperty("JRTXT_CHARACTER_WIDTH") != null)
                      { exporter.setParameter( net.sf.jasperreports.engine.export.JRTextExporterParameter.CHARACTER_WIDTH, new Integer( IReportManager.getInstance().getProperty("JRTXT_CHARACTER_WIDTH")) ); }
                      if (IReportManager.getInstance().getProperty("JRTXT_CHARACTER_HEIGHT") != null)
                      { exporter.setParameter( net.sf.jasperreports.engine.export.JRTextExporterParameter.CHARACTER_HEIGHT, new Integer( IReportManager.getInstance().getProperty("JRTXT_CHARACTER_HEIGHT")) ); }
                      if (IReportManager.getInstance().getProperty("JRTXT_BETWEEN_PAGES_TEXT") != null)
                      { exporter.setParameter( net.sf.jasperreports.engine.export.JRTextExporterParameter.BETWEEN_PAGES_TEXT, IReportManager.getInstance().getProperty("JRTXT_BETWEEN_PAGES_TEXT") ); }

                      fileName = Misc.changeFileExtension(fileName,"txt");
                      exportingMessage = Misc.formatString("Exporting txt (jasperReports) to file... {0}!",  new Object[]{fileName});
                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalTXTViewer"), "");
                   }
                   else if (format.equalsIgnoreCase("rtf"))
                   {
                      exporter = new  net.sf.jasperreports.engine.export.JRRtfExporter();

                      fileName = Misc.changeFileExtension(fileName,"rtf");
                      exportingMessage = Misc.formatString("Exporting RTF to file... {0}!",  new Object[]{fileName});
                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalRTFViewer"), "");
                   }
                   else if (format.equalsIgnoreCase("odf"))
                   {
                      exporter = new  net.sf.jasperreports.engine.export.oasis.JROdtExporter();

                      fileName = Misc.changeFileExtension(fileName,"odf");
                      exportingMessage = Misc.formatString("Exporting OpenOffice documento to file... {0}!",  new Object[]{fileName});
                      viewer_program = Misc.nvl( IReportManager.getInstance().getProperty("ExternalODFViewer"), "");
                   }

                   img_url = this.getClass().getResource("/com/jaspersoft/ireport/designer/compiler/printer_mini.png");

                   getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\"><img align=\"right\" src=\""+  img_url  +"\"> &nbsp;" + exportingMessage + "</font>",true);

                   //((JrxmlPreviewView)getSupport().getDescriptions()[2]).setJasperPrint(null);
                          
                   if (exporter != null)
                   {
                      exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,fileName);
                      exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
                      exporter.setParameter(JRExporterParameter.PROGRESS_MONITOR, this);

                      String reportEncoding = Misc.nvl( IReportManager.getInstance().getProperty("CHARACTER_ENCODING"),"");
                      if (reportEncoding.trim().length() > 0)
                      {
                          exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, reportEncoding);
                      }

                      String offsetX = Misc.nvl( IReportManager.getInstance().getProperty("OFFSET_X"),"");
                      if (offsetX.trim().length() > 0)
                      {
                          try {
                              exporter.setParameter(JRExporterParameter.OFFSET_X, new Integer(offsetX));
                          } catch (Exception ex) {}
                      }

                      String offsetY = Misc.nvl( IReportManager.getInstance().getProperty("OFFSET_Y"),"");
                      if (offsetY.trim().length() > 0)
                      {
                          try {
                              exporter.setParameter(JRExporterParameter.OFFSET_Y,new Integer( offsetY));
                          } catch (Exception ex) {}
                      }


                      exporter.exportReport();
                      getLogTextArea().logOnConsole(outputBuffer.toString());
                      outputBuffer = new StringBuffer();
                   }
    //               else if (format.equalsIgnoreCase("java2D") )
    //               {
    //                   if (print.getPages().size() == 0)
    //                  {
    //                      try {
    //                      SwingUtilities.invokeLater(new Runnable() {
    //                          public void run() {
    //                                  JOptionPane.showMessageDialog( getLogTextArea()  , "The document has no pages");
    //                          }
    //                      });
    //                      } catch (Exception ex){}
    //                  }
    //                  else
    //                  {
    //                    PagesFrame pd = new PagesFrame(print);
    //                    pd.setVisible(true);
    //                  }
    //               }
                   else if (format.equalsIgnoreCase("jrviewer"))
                   {
                      //jrv = new net.sf.jasperreports.view.JRViewer(print);
                      if (print.getPages().size() == 0)
                      {
                          try {
                          SwingUtilities.invokeLater(new Runnable() {
                              public void run() {
                                      JOptionPane.showMessageDialog(getLogTextArea(), "The document has no pages");
                              }
                          });
                          } catch (Exception ex){}
                      }
                      else
                      {
                          final JasperPrint thePrint = print;
                          ThreadUtils.invokeInAWTThread(
                                  new Runnable()
                                  {
                                        public void run()
                                        {
                                            ((JrxmlPreviewView)getSupport().getDescriptions()[2]).setJasperPrint(thePrint);
                                            ((JrxmlPreviewView)getSupport().getDescriptions()[2]).requestVisible();
                                            ((JrxmlPreviewView)getSupport().getDescriptions()[2]).requestActive();
                                            ((JrxmlPreviewView)getSupport().getDescriptions()[2]).updateUI();
                                        }
                          });
                          //JasperViewer jasperViewer = new JasperViewer(print,false);
                          //jasperViewer.setTitle("iReport JasperViewer");
                          //jasperViewer.setVisible(true);

                      }
                      //net.sf.jasperreports.view.JasperViewer.viewReport( print, false);
                   }
                } catch (Throwable ex2)
                {

                   getLogTextArea().logOnConsole(
                           Misc.formatString("Error exporting print... {0}\n",
                                                       new Object[]{ex2.getMessage()}));
                   ex2.printStackTrace();
                   getLogTextArea().logOnConsole(outputBuffer.toString());
                   outputBuffer = new StringBuffer();

                   
                }

                getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\" color=\"#0000CC\"><b>" +
                             Misc.formatString("Export running time: {0,number}!",
                                    new Object[]{new Long(System.currentTimeMillis() - start), new Integer((print.getPages()).size())}) + "</b></font><hr>",true);

                // Export using the rigth program....

                if (Thread.interrupted()) throw new InterruptedException();
                
                Runtime rt = Runtime.getRuntime();
                if (viewer_program == null || viewer_program.equals(""))
                {

                   if (format.equalsIgnoreCase("jrviewer") || format.equalsIgnoreCase("java2D"))
                   {

                   }
                   else
                      getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\">" +
                              "No external viewer specified for this type of print. Set it in the options frame!" +
                              "</font>",true);

                }
                else
                {
                   try
                   {
                      String execute_string = viewer_program + " "+fileName+"";
                      getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\">" +
                             Misc.formatString("Executing: {0}",
                                    new Object[]{execute_string}) + "</font>",true);
                      rt.exec( execute_string );
                   } catch (Exception ex)
                   {

                      getLogTextArea().logOnConsole("Error viewing report...\n");
                      ex.printStackTrace();
                      getLogTextArea().logOnConsole(outputBuffer.toString());
                      outputBuffer = new StringBuffer();
                   }
                   //getLogTextArea().logOnConsole("Finished...\n");
                }
             }
             else
             {
                 getLogTextArea().logOnConsole("<font face=\"SansSerif\"  size=\"3\">" +
                             "Print not filled. Try to use an EmptyDataSource..." + "</font>",true);
                getLogTextArea().logOnConsole("\n");
             }
          }

          fireCompilationStatus(CompilationStatusEvent.STATUS_COMPLETED, CLS_COMPILE_OK);
          fireCompileListner(this, CL_COMPILE_OK, CLS_COMPILE_OK);
          cleanup();
          handle.finish();

          if (backupJRClasspath != null) {
              //System.setProperty("jasper.reports.compile.class.path",backupJRClasspath);
              net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH, backupJRClasspath );
          }
          else
          {
              //System.getProperties().remove("jasper.reports.compile.class.path");
              net.sf.jasperreports.engine.util.JRProperties.restoreProperties();
          }

          if (backupSystemClasspath != null) System.setProperty("java.class.path",backupSystemClasspath);
          else System.getProperties().remove("java.class.path");

     } catch (InterruptedException ex)
     {
         stopThread();
     } finally
     {
          System.gc();
          System.setOut(out);
          System.setErr(err);   
          System.gc();
     }
      
   }


   
   /** Getter for property command.
    * @return Value of property command.
    *
    */
   public int getCommand()
   {
      return command;
   }

   /** Setter for property command.
    * @param command New value of property command.
    *
    */
   public void setCommand(int command)
   {
      this.command = command;
   }

   /** Getter for property iReportConnection.
    * @return Value of property iReportConnection.
    *
    */
   public IReportConnection getIReportConnection()
   {
      return iReportConnection;
   }

   /** Setter for property iReportConnection.
    * @param iReportConnection New value of property iReportConnection.
    *
    */
   public void setIReportConnection(IReportConnection iReportConnection)
   {
      this.iReportConnection = iReportConnection;
   }

   
   /** Getter for property properties.
    * @return Value of property properties.
    *
    */
   public HashMap getProperties()
   {
      return properties;
   }

   /** Setter for property properties.
    * @param properties New value of property properties.
    *
    */
   public void setProperties(HashMap properties)
   {
      this.properties = properties;
   }

    @Override
   public String toString()
   {
      return status;
   }

    private void updateHandleStatus(String status) {
        handle.setDisplayName( file.getName() + " (" + status + ")");
        fireCompilationStatus(CompilationStatusEvent.STATUS_RUNNING, status);
    }

   class FilteredStream extends FilterOutputStream
   {
      public FilteredStream(OutputStream aStream)
      {
         super(aStream);
      }

        @Override
      public void write(byte b[]) throws IOException
      {
         String aString = new String(b);
         outputBuffer.append( aString );

         if (outputBuffer.length() > maxBufferSize) // 5000000
         {
             outputBuffer = outputBuffer.delete(0, outputBuffer.length()-maxBufferSize);
         }
      }

        @Override
      public void write(byte b[], int off, int len) throws IOException
      {
         String aString = new String(b , off , len);
         outputBuffer.append( aString );
         if (outputBuffer.length() > maxBufferSize)
         {
             outputBuffer = outputBuffer.delete(0, outputBuffer.length()-maxBufferSize);
         }
         //getLogTextArea().logOnConsole(aString);
      }
   }
   
   /**
    * This method should be called by run when the process is started.
    */
   protected void init()
   {
       thread = Thread.currentThread();
       
       handle = ProgressHandleFactory.createHandle(status,
               new Cancellable() {
                public boolean cancel() {
                    thread.interrupt();
                    return true;
                }
        });
        
        updateHandleStatus(status);
        handle.start();
       
       //using the report directory to load classes and resources
	try{
		  String reportDirectory = FileUtil.toFile(getFile()).getPath();

		  //set classpath
		  //String classpath = System.getProperty("jasper.reports.compile.class.path");
                  String classpath = net.sf.jasperreports.engine.util.JRProperties.getProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH );


		  if(classpath != null){

			  classpath += File.pathSeparator + reportDirectory;
			  //System.setProperty("jasper.reports.compile.class.path", classpath);
                          net.sf.jasperreports.engine.util.JRProperties.setProperty(net.sf.jasperreports.engine.util.JRProperties.COMPILER_CLASSPATH, classpath);

		  } else if( System.getProperty("java.class.path") != null){

			  classpath = System.getProperty("java.class.path");
			  classpath += File.pathSeparator + reportDirectory;
			  System.setProperty("java.class.path", classpath);
		  }

		  // Add all the hidden files.... (needed only by JWS)
//		  if (MainFrame.getMainInstance().isUsingWS())
//		  {
//		  	try {
//			   Enumeration e = MainFrame.getMainInstance().getReportClassLoader().getResources("META-INF/MANIFEST.MF");
//			   while (e.hasMoreElements()) {
//			      URL url = (URL) e.nextElement();
//			      String newJar = ""+url.getFile();
//
//			      if (newJar.endsWith("!/META-INF/MANIFEST.MF"))
//			      {
//			      	newJar = newJar.substring(0, newJar.length() - "!/META-INF/MANIFEST.MF".length());
//
//			        newJar = java.net.URLDecoder.decode(newJar, "UTF-8");
//
//			        MainFrame.getMainInstance().logOnConsole("JX:" + newJar);
//
//			      	newJar = newJar.replace('\\', '/');
//			  	if (newJar.startsWith("file://"))
//			  	{
//			  		newJar = newJar.substring(7);
//			  	}
//
//			  	if (newJar.startsWith("file:"))
//			  	{
//			  		newJar = newJar.substring(5);
//			  	}
//
//			  	if(!newJar.startsWith("/")){
//				  newJar = "/" + newJar;//it's important to JVM 1.4.2 especially if contains windows drive letter
//			  	}
//			      }
//
//			       if (classpath.indexOf(newJar + File.pathSeparator) < 0 &&
//			           !classpath.endsWith(newJar))
//			       {
//			      	classpath +=  File.pathSeparator + newJar;
//			       }
//			     }
//			  } catch (Exception exc) {
//			  	MainFrame.getMainInstance().logOnConsole("exception ex:" + exc.getMessage());
//			   	exc.printStackTrace();
//			  }
//			  System.setProperty("java.class.path", classpath);
//		  }
		  
		  //include report directory for resource search path
		  reportDirectory = reportDirectory.replace('\\', '/');
		  if(!reportDirectory.endsWith("/")){
			  reportDirectory += "/";//the file path separator must be present
		  }
		  if(!reportDirectory.startsWith("/")){
			  reportDirectory = "/" + reportDirectory;//it's important to JVM 1.4.2 especially if contains windows drive letter
		  }

		  Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[]{
		  	  new URL("file://"+reportDirectory)
		  }, IReportManager.getInstance().getReportClassLoader()));

                 
                 if (getJrxmlPreviewView() != null)
                 {
                     this.addCompilationStatusListener(getJrxmlPreviewView());
                 }
                  
                  
        } catch (MalformedURLException mue){
	  mue.printStackTrace();
	}
   }

   /**
    * This method is called by run when the process is going to end in order to
    * change the textArea status.
    */
   public void cleanup()
    {
        getLogTextArea().setTitle("Finished" + constTabTitle);
        getLogTextArea().setRemovable(true);
        
    }
   
   public void start()
   {
      this.thread = new Thread(this);
      init();
      this.thread.start();
   }

   
   /**
    * Initially a way to parse output, today this method just print as html
    * the output generated by the execution
    */
   public void parseException(String exception, Vector sourceLines)
   {

      // Create a single outString...
      String outString = "";

      // For each row, looking for a file name followed by a row number...
      //javax.swing.JOptionPane.showMessageDialog(null,exception);
      StringTokenizer st = new StringTokenizer(exception, "\n");
      while (st.hasMoreElements())
      {
         String line = st.nextToken();
         outString += Misc.toHTML(line+"\n");
      }
      getLogTextArea().logOnConsole(outString,true);
      //getLogTextArea().logOnConsole( "<a href=\"http://problem\">*****<hr><font face=\"Courier New\" size=\"3\">"+ exception +"</a>", true);
      outputBuffer = new StringBuffer();
   }

   

   /**
    * This methods honors the JRExportProgressMonitor interface
    */
   public void afterPageExport() {

       filledpage++;
       if (command == 0)
       {

       }


   }

   /**
    *  The logTextArea tied to this process
    */
    public LogTextArea getLogTextArea() {
        return logTextArea;
    }

    public void setLogTextArea(LogTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    /**
     * added by Felix Firgau
     */
    public static final int CL_COMPILE_OK = 1;
    public static final int CL_COMPILE_FAIL = 2;

    public static final String CLS_COMPILE_OK = "compileok";
    public static final String CLS_COMPILE_SCRIPTLET_FAIL = "scriptletfail";
    public static final String CLS_COMPILE_SOURCE_FAIL = "sourcefail";


    private java.util.List<CompilationStatusListener> compilationStatusListener = new java.util.ArrayList<CompilationStatusListener>();
    /**
     * (FF) addCompileListener to notify about compiling actions
     * @param listener ActionListener
     */
    @SuppressWarnings("unchecked")
    public void addCompilationStatusListener(CompilationStatusListener listener) {
      if(!compilationStatusListener.contains(listener))
        compilationStatusListener.add(listener);
    }
    
    public void removeCompilationStatusListener(CompilationStatusListener listener) {
      compilationStatusListener.remove(listener);
    }
    
    /**
     * (FF) fireCompileListner fires compiling action notifications
     * @param id int
     * @param status String
     */
    public void fireCompilationStatus(int status, String message) {
        @SuppressWarnings("unchecked")
        CompilationStatusEvent event = new CompilationStatusEvent(this, status, message );
        for (CompilationStatusListener listener : compilationStatusListener)
        {
            listener.compilationStatus(event);
        }
    }
    
    
    /**
     * (FF) addCompileListener to notify about compiling actions
     * @param listener ActionListener
     */
    @SuppressWarnings("unchecked")
    public static void addCompileListener(java.awt.event.ActionListener listener) {
      if(!compileListener.contains(listener))
        compileListener.add(listener);
    }

    /**
     * (FF) removeCompileListener removes notification
     * @param listener ActionListener
     */
    public static void removeCompileListener(java.awt.event.ActionListener listener) {
      compileListener.remove(listener);
    }

    /**
     * (FF) fireCompileListner fires compiling action notifications
     * @param id int
     * @param status String
     */
    public static void fireCompileListner(IReportCompiler ireportCompiler, int id, String status) {
        @SuppressWarnings("unchecked")
      java.awt.event.ActionListener[] list = (java.awt.event.ActionListener[])compileListener.toArray(
       new java.awt.event.ActionListener[compileListener.size()]);

      java.awt.event.ActionEvent e = new java.awt.event.ActionEvent(ireportCompiler, id, status);
      for (int i = 0; i < list.length; i++) {
        java.awt.event.ActionListener listener = list[i];
        listener.actionPerformed(e);
      }
    }
    //End FF
    
    
    
    
    
    
    /**
     * Code to load a JasperDesign. We should not load a jasperDesign, since we already have one in memory.
     * This code must be replaced at some point. Please note that this code use the SourceTraceDigester digester
     * (not used in the JrxmlLoader. We should merge this code with the one provided by JrxmlLoader.
     */
    public static JasperDesign loadJasperDesign(InputStream fileStream, SourceTraceDigester digester) throws JRException
    {
            JRXmlLoader xmlLoader = new JRXmlLoader(digester);

            try
            {
                    JasperDesign jasperDesign = xmlLoader.loadXML(fileStream);
                    return jasperDesign;
            }
            finally
            {
                    try
                    {
                            fileStream.close();
                    }
                    catch (IOException e)
                    {
                            // ignore
                    }
            }
    }

    public static SourceTraceDigester createDigester() throws JRException
    {
            SourceTraceDigester digester = new SourceTraceDigester();
            try
            {
                    JRXmlDigesterFactory.configureDigester(digester);
            }
            catch (SAXException e)
            {
                    throw new JRException(e);
            }
            catch (ParserConfigurationException e)
            {
                    throw new JRException(e);
            }
            return digester;
    }

    
    /**
     * The file object tied to this file.
     */
    public FileObject getFile() {
        return file;
    }

    public void setFile(FileObject file) {
        this.file = file;
    }

    public JrxmlEditorSupport getSupport() {
        return support;
    }

    public void setSupport(JrxmlEditorSupport support) {
        this.support = support;
    }
    
    private void showErrorConsole()
    {
        ThreadUtils.invokeInAWTThread( new Runnable()
              {
                  public void run()
                  {
                      ((TopComponent)getSupport().getDescriptions()[0]).requestVisible();
                      ((TopComponent)getSupport().getDescriptions()[0]).requestActive();
                      
                      TopComponent tc =WindowManager.getDefault().findTopComponent("IRConsoleTopComponent");
                      if (tc != null) 
                      {
                          tc.requestVisible();
                          tc.requestActive();
                      }
                  }
              });
    }
    
    public JrxmlPreviewView getJrxmlPreviewView()
    {
        
        return   (JrxmlPreviewView)getSupport().getDescriptions()[2];
    }
                   
}

