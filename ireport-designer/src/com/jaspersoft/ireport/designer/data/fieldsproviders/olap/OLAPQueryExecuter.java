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
