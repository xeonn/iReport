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
 * ChartDescriptor.java
 * 
 * Created on 9 luglio 2005, 0.27
 *
 */

package com.jaspersoft.ireport.designer.charts;

import net.sf.jasperreports.engine.JRChart;

/**
 *
 * @author Administrator
 */
public class ChartDescriptor {
    
    private javax.swing.ImageIcon icon;
    private String name = "";
    private String displayName = "";
    private byte chartType = JRChart.CHART_TYPE_PIE;

    public byte getChartType() {
        return chartType;
    }

    public void setChartType(byte chartType) {
        this.chartType = chartType;
    }
    
    /** Creates a new instance of ChartDescriptor */
    public ChartDescriptor(String iconName, String name, byte chartType) {
        
        setIcon(new javax.swing.ImageIcon( getClass().getResource(iconName) ));
        this.name = name;
        this.displayName = name;
        this.chartType = chartType;
    }

    public javax.swing.ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(javax.swing.ImageIcon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
