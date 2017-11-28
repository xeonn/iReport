/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.charts.datasets;

import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import net.sf.jasperreports.charts.design.JRDesignCategorySeries;
import net.sf.jasperreports.charts.design.JRDesignGanttSeries;
import net.sf.jasperreports.charts.design.JRDesignPieSeries;
import net.sf.jasperreports.charts.design.JRDesignTimePeriodSeries;
import net.sf.jasperreports.charts.design.JRDesignTimeSeries;
import net.sf.jasperreports.charts.design.JRDesignXySeries;
import net.sf.jasperreports.charts.design.JRDesignXyzSeries;

/**
 *
 * @author gtoffoli
 */
public class DatasetListsCellRenderer extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(
         JList list,
         Object value,
         int index,
         boolean isSelected,
         boolean cellHasFocus)
     {
         JLabel label = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected, cellHasFocus);
         label.setIcon(null);
         
         if (value instanceof JRDesignTimePeriodSeries)
         {
                  label.setText( "Time period series [" + Misc.getExpressionText( ((JRDesignTimePeriodSeries)value).getSeriesExpression() ) +"]");
         }
         else if (value instanceof JRDesignCategorySeries)
         {
                  label.setText( "Category series [" + Misc.getExpressionText( ((JRDesignCategorySeries)value).getSeriesExpression() ) +"]");
         }
         else if (value instanceof JRDesignXySeries)
         {
                  label.setText( "XY series [" + Misc.getExpressionText( ((JRDesignXySeries)value).getSeriesExpression() ) +"]");
         }
         else if (value instanceof JRDesignTimeSeries)
         {
                  label.setText( "Time series [" + Misc.getExpressionText( ((JRDesignTimeSeries)value).getSeriesExpression() ) +"]");
         }
         else if (value instanceof JRDesignXyzSeries)
         {
                  label.setText( "XYZ series [" + Misc.getExpressionText( ((JRDesignXyzSeries)value).getSeriesExpression() ) +"]");
         }
         else if (value instanceof JRDesignGanttSeries)
         {
                  label.setText( "Gantt series [" + Misc.getExpressionText( ((JRDesignGanttSeries)value).getSeriesExpression() ) +"]");
         }
         else if (value instanceof JRDesignPieSeries)
         {
                  if (list.getModel().getSize() <= 1)
                  {
                      label.setText( "Default pie series");
                  }
                  else
                  {
                      label.setText( "Pie series [" + (index+1) + "]");
                  }
         }
         
         return this;
     }
    
    
}
