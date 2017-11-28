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
 */
package com.jaspersoft.ireport.jasperserver.ui.inputcontrols;

import com.jaspersoft.ireport.jasperserver.ui.inputcontrols.impl.MultiColumnListInputControlUI;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.InputControlQueryDataRow;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import java.util.List;

/**
 *
 * @author gtoffoli
 */
public class MultiColumnListInputControl extends BasicInputControl{
    
    java.util.List wrappedItems = null;
    
    /** Creates a new instance of BooleanInputControl */
    public MultiColumnListInputControl() {
        super();
        setInputControlUI( new MultiColumnListInputControlUI());
    }
    
     public void setInputControl(ResourceDescriptor inputControl, List items) {
        
         this.inputControl = inputControl;
         
        String label = inputControl.getLabel() + ((inputControl.isMandatory()) ? "*" : "");
        getInputControlUI().setLabel(label);
        getInputControlUI().setReadOnly( inputControl.isReadOnly() );
        
        wrappedItems = new java.util.ArrayList();
        
        if (!inputControl.isMandatory())
        {
            InputControlQueryDataRow icqdr = new InputControlQueryDataRow();
            icqdr.setValue(null);
            wrappedItems.add(icqdr);
        }
        
        for (int i=0; i < items.size(); ++i)
        {
            InputControlQueryDataRow item = (InputControlQueryDataRow)items.get(i);
            wrappedItems.add( item);
        }

        getInputControlUI().setHistory( wrappedItems );
    
        List history = getHistory(inputControl.getUriString());
     
        if (history != null && history.size() > 0)
        {
            getInputControlUI().setValue( history.get(0) );
        }
     }
     
     public Object validate() throws InputValidationException
     {
        return getInputControlUI().getValue();
     }
     
}
