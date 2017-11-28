/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties.charts;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.charts.design.JRDesignPie3DPlot;



/**
 *
 * @author gtoffoli
 */
public class Pie3DItemLabelProperty extends AbstractItemLabelProperty {

    JRDesignPie3DPlot plot = null;
    public Pie3DItemLabelProperty(JRDesignPie3DPlot plot)
    {
        super(plot, plot.getChart());
        this.plot = plot;
    }

    @Override
    public String getName()
    {
        return JRDesignPie3DPlot.PROPERTY_ITEM_LABEL;
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
