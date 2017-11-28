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
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ListItem;
/**
 *
 * @author gtoffoli
 */
public class ListItemWrapper implements Comparable {
    
    private ListItem item;
    /**
     * Creates a new instance of ListItemWrapper
     */
    public ListItemWrapper(ListItem item  ) {
        this.item = item;
    }
    
    public boolean equals(Object obj)
    {
        try {
        if (obj instanceof ListItem)
        {
            Object val = ((ListItem)obj).getValue();
            return (val == null) ? getItem().getValue() == null : val.equals(getItem().getValue());
        }
        if (obj instanceof ListItemWrapper)
        {
            Object val = ((ListItemWrapper)obj).getItem().getValue();
            return (val == null) ? getItem().getValue() == null : val.equals(getItem().getValue());
        }
        } catch (Exception ex)
        {
            
        }
        
        return super.equals(obj);
    }

    public ListItem getItem() {
        return item;
    }

    public void setItem(ListItem item) {
        this.item = item;
    }
    
    public String toString()
    {
        if (item != null) return ""+item.getLabel();
        return super.toString();
    }

    public int compareTo(Object o) {
        if (o instanceof ListItemWrapper)
        {
            ListItem newItem = ((ListItemWrapper)o).getItem();
            return item.getLabel().compareTo(newItem.getLabel());
        }
        return -1;
    }
}
