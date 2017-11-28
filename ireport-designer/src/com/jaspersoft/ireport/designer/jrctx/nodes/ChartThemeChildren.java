/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.jrctx.nodes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.chartthemes.simple.ChartThemeSettings;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author gtoffoli
 */
class ChartThemeChildren  extends Children.Keys {

    ChartThemeSettings template = null;
    private Lookup doLkp = null;

    @SuppressWarnings("unchecked")
    public ChartThemeChildren(ChartThemeSettings template, Lookup doLkp) {
        this.template = template;
        this.doLkp=doLkp;
        //this.template.getEventSupport().addPropertyChangeListener(this);
    }

    @Override
    protected void addNotify() {
        super.addNotify();
        updateChildren();
    }


    protected Node[] createNodes(Object key) {

        Node node = null;
        
        if (key.equals("Chart"))
        {
            node = new ChartSettingsNode(template.getChartSettings(), doLkp);
        }
        else if (key.equals("Title"))
        {
            node = new TitleSettingsNode(template.getTitleSettings(), doLkp);
        }
        else if (key.equals("Subtitle"))
        {
            node = new TitleSettingsNode(template.getSubtitleSettings(), doLkp);
        }
        else if (key.equals("Legend"))
        {
            node = new LegendSettingsNode(template.getLegendSettings(), doLkp);
        }
        else if (key.equals("Plot"))
        {
            node = new PlotSettingsNode(template.getPlotSettings(), doLkp);
        }
        else if (key.equals("Domain Axis"))
        {
            node = new AxisSettingsNode(template.getDomainAxisSettings(), doLkp);
        }
        else if (key.equals("Range Axis"))
        {
            node = new AxisSettingsNode(template.getRangeAxisSettings(), doLkp);
        }

        node.setDisplayName(""+key);
        return new Node[]{node};
    }



    @SuppressWarnings("unchecked")
    public void updateChildren()
    {

        List l = new ArrayList();

        l.add( "Chart");
        l.add( "Title");
        l.add( "Subtitle");
        l.add( "Legend");
        l.add( "Plot");
        l.add( "Domain Axis");
        l.add( "Range Axis");

        setKeys(l);
    }

   
    

}
