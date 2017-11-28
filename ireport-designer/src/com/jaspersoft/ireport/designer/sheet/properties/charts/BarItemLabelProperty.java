/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.charts.design.JRDesignBarPlot;



/**
 *
 * @author gtoffoli
 */
public class BarItemLabelProperty extends AbstractItemLabelProperty {

    JRDesignBarPlot plot = null;
    public BarItemLabelProperty(JRDesignBarPlot plot)
    {
        super(plot, plot.getChart());
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBarPlot.PROPERTY_ITEM_LABEL;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.ItemLabel");
    }

    @Override
    public String getShortDescription()
    {
        return I18n.getString("Global.Property.ItemLabel");
    }


    @Override
    public JRItemLabel getItemLabel() {
        return plot.getItemLabel();
    }

    @Override
    public void setItemLabel(JRItemLabel item) {
        plot.setItemLabel(item);
    }

}
