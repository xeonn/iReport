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
 * EJBQLBeanInspectorPanel.java
 *
 * Created on December 7, 2006, 1:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.data.fieldsproviders;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.data.fieldsproviders.ejbql.EJBQLFieldsReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import net.sf.jasperreports.engine.design.JRDesignField;

/**
 *
 * @author gtoffoli
 */
public class EJBQLBeanInspectorPanel extends BeanInspectorPanel {
    
    /** Creates a new instance of EJBQLBeanInspectorPanel */
    public EJBQLBeanInspectorPanel() {
        super();
        
    }
    
    /**
     * Ad hoc queryChanged method for EJBQL queries....
     */
    @SuppressWarnings("unchecked")
    public void queryChanged(String newQuery) {
    
        lastExecution++;
        int thisExecution = lastExecution;
        // Execute a thread to perform the query change...
        
        String error_msg = "";
        lastExecution++;
            
        int in = lastExecution;
            
        getReportQueryDialog().getJLabelStatusSQL().setText("Executing EJBQL query....");
        /////////////////////////////
            
        try {
        Thread.currentThread().setContextClassLoader( IReportManager.getInstance().getReportClassLoader());
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
            
        if (in < lastExecution) return; //Abort, new execution requested
        
        EJBQLFieldsReader ejbqlFR = new EJBQLFieldsReader(newQuery, getReportQueryDialog().getDataset().getParametersList());
            
            try {
                Vector fields = ejbqlFR.readFields();
                
                List columns = new ArrayList();
                for (int i=0; i<fields.size(); ++i)
                {
                    JRDesignField field = (JRDesignField)fields.elementAt(i);
                    columns.add( new Object[]{field, field.getValueClassName(), field.getDescription()} );
                }
                Vector v = null;
                if (ejbqlFR.getSingleClassName() != null)
                {
                    v = new Vector();
                    v.add( ejbqlFR.getSingleClassName() );
                }
                
                setBeanExplorerFromWorker(v,true,false);
                setColumnsFromWorker(columns);
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
                setBeanExplorerFromWorker(null,true,false);
                setColumnErrorFromWork( "Error: " +  ex.getMessage() );
            }
        
        getReportQueryDialog().getJLabelStatusSQL().setText("Ready");
    }
    
}
