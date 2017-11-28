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
 *
 *
 *
 *
 * OLAPQueryExecuter.java
 * 
 * Created on February 22, 2006, 1:27 PM
 *
 */

package com.jaspersoft.ireport.designer.data.fieldsproviders.olap;

import bsh.Interpreter;
import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.connection.MondrianConnection;
import com.jaspersoft.ireport.designer.data.ReportQueryDialog;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.util.ArrayList;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import mondrian.olap.Connection;
import mondrian.olap.Query;
import mondrian.olap.Result;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;


/**
 *
 * @author gtoffoli
 */
public class OLAPQueryExecuter {
    
    private Interpreter interpreter = null;
    private List  reportParameters = null;
    private String queryString = "";
    private HashMap queryParameters = new HashMap();
        
    /** Creates a new instance of HQLFieldsReader */
    public OLAPQueryExecuter(String queryStr, List reportParameters) {
        
        this.setQueryString(queryStr);
        this.setReportParameters(reportParameters);
        
    }
    
    public String prepareQuery() throws Exception
    {
       Iterator iterParams = getReportParameters().iterator();
       
       while( iterParams.hasNext() ) {
           
           JRDesignParameter param = (JRDesignParameter)iterParams.next();
           String parameterName = param.getName();
           
           if (queryString.indexOf("$P!{" + parameterName + "}") > 0)
           {
               String expStr = (param.getDefaultValueExpression() == null) ? "" : param.getDefaultValueExpression().getText();
                Object paramVal = ReportQueryDialog.recursiveInterpreter( getInterpreter(), expStr,getReportParameters());
           
                if (paramVal == null)
                {
                    paramVal = "";
                }
                
                queryString = Misc.string_replace(""+paramVal, "$P!{" + parameterName + "}", queryString);
           }
           else if (getQueryString().indexOf("$P{" + parameterName + "}") > 0)
           {
               String expStr = (param.getDefaultValueExpression() == null) ? "" : param.getDefaultValueExpression().getText();
               Object paramVal = ReportQueryDialog.recursiveInterpreter( getInterpreter(), expStr,getReportParameters());
           
                if (paramVal == null)
                {
                    paramVal = "";
                }
                
                queryString = Misc.string_replace(""+paramVal, "$P!{" + parameterName + "}", queryString);
            }
        } 
       return queryString;
    }

    private Interpreter prepareExpressionEvaluator() throws bsh.EvalError {
        
        Interpreter interpreter1 = new Interpreter();
        interpreter1.setClassLoader(interpreter1.getClass().getClassLoader());
        return interpreter1;
    }
    
    
    
	
	
	

    public Interpreter getInterpreter() {
        
        if (interpreter == null)
        {
            try {
            interpreter = prepareExpressionEvaluator();
            } catch (Exception ex)
            {
            
            }
        }
        return interpreter;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public List getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(List reportParameters) {
        this.reportParameters = reportParameters;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public HashMap getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(HashMap queryParameters) {
        this.queryParameters = queryParameters;
    }
    

    public Query createOlapQuery() throws Exception
    {
        prepareQuery();

        try {
                IReportConnection conn = IReportManager.getInstance().getDefaultConnection();
               if (!(conn instanceof MondrianConnection))
               {
                   throw new Exception("No OLAP (Mondrian) connection selected.");
               }
              
               Connection mconn = ((MondrianConnection)conn).getMondrianConnection();
               if (mconn == null)
               {
                 throw new Exception("The supplied mondrian.olap.Connection object is null.");
               }
                        
               Query query = mconn.parseQuery( queryString );
               
               return query;

           } catch (Exception ex)
           {
               ex.printStackTrace();
               throw ex;
            } finally {
               
              
           }
    }        
    
    public Result executeOlapQuery() throws Exception
    {
        prepareQuery();
        try {
            
               IReportConnection conn = IReportManager.getInstance().getDefaultConnection();
               if (!(conn instanceof MondrianConnection))
               {
                   throw new Exception("No OLAP (Mondrian) connection selected.");
               }
              
               Connection mconn = ((MondrianConnection)conn).getMondrianConnection();
               if (mconn == null)
               {
                 throw new Exception("The supplied mondrian.olap.Connection object is null.");
               }
                        
               Query query = mconn.parseQuery( queryString );
	       Result result = mconn.execute(query);
               
               return result;

           } catch (Exception ex)
           {
               ex.printStackTrace();
               throw ex;
            } finally {
               
              
           }
    }
    
    @SuppressWarnings("unchecked")
    public List getFields(Object obj)
    {
        
        List fields = new ArrayList();
        java.beans.PropertyDescriptor[] pd = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(obj.getClass());
        for (int nd =0; nd < pd.length; ++nd)
        {
                   String fieldName = pd[nd].getName();
                   if (pd[nd].getPropertyType() != null && pd[nd].getReadMethod() != null)
                   {
                       String returnType =  pd[nd].getPropertyType().getName();
                       JRDesignField field = new JRDesignField();
                       field.setName( fieldName );
                       field.setValueClassName(Misc.getJRFieldType(returnType));
                       field.setDescription(""); //Field returned by " +methods[i].getName() + " (real type: "+ returnType +")");
                       fields.add(field);
                   }
        }
        
        return fields;
    }



}
