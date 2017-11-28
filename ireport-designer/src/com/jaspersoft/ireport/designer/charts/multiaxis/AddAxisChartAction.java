package com.jaspersoft.ireport.designer.charts.multiaxis;

import com.jaspersoft.ireport.designer.charts.ChartSelectionJDialog;
import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.utils.Misc;
import javax.swing.JOptionPane;
import net.sf.jasperreports.charts.design.JRDesignChartAxis;
import net.sf.jasperreports.charts.design.JRDesignMultiAxisPlot;
import net.sf.jasperreports.engine.design.JRDesignChart;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class AddAxisChartAction extends NodeAction {

    private static AddAxisChartAction instance = null;
    
    public static synchronized AddAxisChartAction getInstance()
    {
        if (instance == null)
        {
            instance = new AddAxisChartAction();
        }
        
        return instance;
    }
    
    private AddAxisChartAction()
    {
        super();
    }
    
    
    public String getName() {
        return I18n.getString("AddAxisChart.Name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {

        JRDesignChart theChart = (JRDesignChart)((ElementNode)activatedNodes[0]).getElement();
        // Show a window with all the available charts...
        ChartSelectionJDialog cd = new ChartSelectionJDialog(Misc.getMainFrame(), true);
        cd.setMultiAxisMode(true);
        cd.setJasperDesign( ((ElementNode)activatedNodes[0]).getJasperDesign());
        cd.setVisible(true);
        if (cd.getDialogResult() == JOptionPane.OK_OPTION)
        {
            JRDesignChart designChart = cd.getChart();
            JRDesignChartAxis axis = new JRDesignChartAxis(theChart);
            axis.setChart(designChart);
            ((JRDesignMultiAxisPlot)theChart.getPlot()).setChart(theChart);
            ((JRDesignMultiAxisPlot)theChart.getPlot()).addAxis(axis);
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length != 1) return false;
        if (!(activatedNodes[0] instanceof ElementNode)) return false;
        ElementNode node = (ElementNode)activatedNodes[0];
        if (node.getElement() instanceof JRDesignChart)
        {
            return ((JRDesignChart)node.getElement()).getChartType() == JRDesignChart.CHART_TYPE_MULTI_AXIS;
        }
        return false;
    }
}