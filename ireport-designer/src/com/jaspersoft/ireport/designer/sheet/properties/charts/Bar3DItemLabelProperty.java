/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.charts.design.JRDesignBar3DPlot;



/**
 *
 * @author gtoffoli
 */
public class Bar3DItemLabelProperty extends AbstractItemLabelProperty {

    JRDesignBar3DPlot plot = null;
    public Bar3DItemLabelProperty(JRDesignBar3DPlot plot)
    {
        super(plot, plot.getChart());
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignBar3DPlot.PROPERTY_ITEM_LABEL;
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
