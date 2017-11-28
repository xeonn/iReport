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
 * PersonBeansDataSource.java
 * 
 */

package com.jaspersoft.ireport.examples;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import com.jaspersoft.ireport.examples.beans.*;
import java.util.*;

public class PersonBeansDataSource extends JRAbstractBeanDataSourceProvider {
  
  	public PersonBeansDataSource() {
  		super(PersonBean.class);
                
        }
        
    @Override
        public JRField[] getFields(JasperReport report) throws JRException
        {
            
            return super.getFields(report);
        }
  
        
        
    @SuppressWarnings("unchecked")
  	public JRDataSource create(JasperReport report) throws JRException {
  		
  		ArrayList list = new ArrayList();
  		list.add(new PersonBean("Aldo"));
  		list.add(new PersonBean("Giovanni"));
  		list.add(new PersonBean("Giacomo"));
                
               return new JRBeanCollectionDataSource(list);
  	
  	}
 	
  	public void dispose(JRDataSource dataSource) throws JRException {
 		// nothing to do
  	}
  }
