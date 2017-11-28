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
 * ProblemItem.java
 *
 * Created on February 27, 2007, 11:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.errorhandler;

import com.jaspersoft.ireport.designer.compiler.xml.SourceLocation;
import net.sf.jasperreports.engine.design.JRValidationFault;

/**
 *
 * @author gtoffoli
 */
public class ProblemItem {
    
    public static final int INFORMATION = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;
    
    
    private String description = null;
    private String where = "";
    private Object problemReference = null;
    private JRValidationFault fault = null;
    private SourceLocation sourceLocation = null;
    
    private int problemType = 1;
    
    /** Creates a new instance of ProblemItem */
    public ProblemItem() {
        this(0,"no description available", null);
    }
    
    /** Creates a new instance of ProblemItem */
    public ProblemItem(int problemType, JRValidationFault fault) {
        this.fault = fault;
        this.problemType = problemType;
        if (fault != null)
        {
            this.description = fault.getMessage();
        }
    }
    
    /** Creates a new instance of ProblemItem */
    public ProblemItem(int problemType, JRValidationFault fault, SourceLocation sl) {
        this.fault = fault;
        this.problemType = problemType;
        if (fault != null)
        {
            this.description = fault.getMessage();
        }
        this.sourceLocation = sl;
        if (sl != null)
        {
            this.where = sl.getXPath();
        }
    }

    
    /** Creates a new instance of ProblemItem */
    public ProblemItem(int problemType, String description, Object problemReference) {
        this(0,"no description available", null, "");
    }
    
    /** Creates a new instance of ProblemItem */
    public ProblemItem(int problemType, String description, Object problemReference, String where) {
        this.problemType = problemType;
        this.description = description;
        this.problemReference = problemReference;
        this.where = where;
    }
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getProblemReference() {
        if (fault != null) return fault.getSource();
        return problemReference;
    }

    public void setProblemReference(Object problemReference) {
        this.problemReference = problemReference;
    }

    public int getProblemType() {
        return problemType;
    }

    public void setProblemType(int problemType) {
        this.problemType = problemType;
    }
    
    public void resolve()
    {
        
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public JRValidationFault getFault() {
        return fault;
    }

    public void setFault(JRValidationFault fault) {
        this.fault = fault;
    }

    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }
    
}
