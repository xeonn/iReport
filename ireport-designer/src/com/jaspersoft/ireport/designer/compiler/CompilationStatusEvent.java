/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.compiler;

/**
 *
 * @author gtoffoli
 */
public class CompilationStatusEvent {

    public static final int STATUS_UNDEFINED= -1;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_COMPLETED = 2;

    private IReportCompiler compiler = null;

    public IReportCompiler getCompiler() {
        return compiler;
    }

    public void setCompiler(IReportCompiler compiler) {
        this.compiler = compiler;
    }
    private int status = STATUS_UNDEFINED;
    private String message = "";
    
    public CompilationStatusEvent(IReportCompiler compiler, int status, String message)
    {
        this.compiler = compiler;
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
