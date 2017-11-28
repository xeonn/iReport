/**
 *
 * @author gtoffoli
 */
/*
 * Copyright (C) 2005-2007 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
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
 *
 *
 * DesignVerifyerThread.java
 *
 * Created on February 27, 2007, 1:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package com.jaspersoft.ireport.designer.errorhandler;


import com.jaspersoft.ireport.designer.JrxmlVisualView;
import com.jaspersoft.ireport.designer.ModelChangeListener;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JRValidationFault;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * This class is tied to the JrxmlVisualView.
 * All the times there is a change in the model, the document is verified.
 * The verification starts 500 milliseconds after the change, so it can
 * collect other changed before to proceed...
 * The verification should be very fast since we are operating directly
 * on the model...
 * 
 * @author gtoffoli
 */
public class DesignVerifyerThread implements Runnable, ModelChangeListener {
    
    private boolean reportChanged = true;
    private Thread thisThread = null;
    private boolean stop = false;
    private JrxmlVisualView jrxmlVisualView = null;
    
    
    /** Creates a new instance of DesignVerifyerThread */
    public DesignVerifyerThread(JrxmlVisualView view) {
    
        this.jrxmlVisualView = view;
        // Listen for changes in the document...
        view.addModelChangeListener(this);
        thisThread = new Thread( this );
    }
    
    public void start()
    {
        thisThread.start();
    }
    
    public void stop()
    {
        setStop(true);
    }

    public void run() {
        
        while (!isStop())
        {
            try {
                Thread.sleep(2000);
            } catch (Exception ex)
            {
            }
        
            if (isReportChanged())    
            {
                setReportChanged(false);
                verifyDesign();
            }
        }
    }

    public boolean isReportChanged() {
        return reportChanged;
    }

    public void setReportChanged(boolean reportChanged) {
        this.reportChanged = reportChanged;
    }

    public void modelChanged(JrxmlVisualView view) {
        if (this.getJrxmlVisualView() == view)
        {
            setReportChanged(true);
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    
    public void verifyDesign()
    {
       // Remove all the WARNINGS...
        
        
       for (int i=0; i<getJrxmlVisualView().getReportProblems().size(); ++i)
       {
           ProblemItem pii = (ProblemItem)getJrxmlVisualView().getReportProblems().get(i);
           if (pii.getProblemType() == ProblemItem.WARNING ||
               pii.getProblemType() == ProblemItem.INFORMATION)
           {
               getJrxmlVisualView().getReportProblems().remove(i);
               i--;
           }
       }
       //getJReportFrame().getReportProblems().clear();
       
       try {
            //SourceTraceDigester digester = IReportCompiler.createDigester();
            //ReportWriter rw = new ReportWriter(getJReportFrame().getReport());
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //rw.writeToOutputStream(baos);
            //JasperDesign jd = IReportCompiler.loadJasperDesign( new ByteArrayInputStream( baos.toByteArray() ), digester);
            
            if (getJrxmlVisualView().getModel() != null &&
                getJrxmlVisualView().getModel().getJasperDesign() != null)
            {
                JasperDesign design = getJrxmlVisualView().getModel().getJasperDesign();
            
                Collection ls = JasperCompileManager.verifyDesign(design);
                Iterator iterator = ls.iterator();
                while (iterator.hasNext())
                {
                    JRValidationFault fault = (JRValidationFault)iterator.next();
                    String s = fault.getMessage();
                    //SourceLocation sl = digester.getLocation( fault.getSource() );
                    getJrxmlVisualView().getReportProblems().add( new ProblemItem(ProblemItem.WARNING, fault));
                }
            }
       } catch (Exception ex)
        {
            ex.printStackTrace();
            getJrxmlVisualView().getReportProblems().add(new ProblemItem(ProblemItem.WARNING, ex.getMessage(), null, null) );
        }
       
       Runnable runner = new Runnable(){
                public void run()
                {
                    //MainFrame.getMainInstance().getLogPane().getProblemsPanel().update();
                    ErrorHandlerTopComponent.getDefault().refreshErrors();
                }
        };

        SwingUtilities.invokeLater(runner);
        
    }

    public JrxmlVisualView getJrxmlVisualView() {
        return jrxmlVisualView;
    }

    public void setJrxmlVisualView(JrxmlVisualView jrxmlVisualView) {
        this.jrxmlVisualView = jrxmlVisualView;
    }
}
