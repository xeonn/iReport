/*
 * ModelUtils.java
 * 
 * Created on Aug 28, 2007, 10:31:07 AM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
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

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldAction;
import com.jaspersoft.ireport.designer.sheet.GenericProperty;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.crosstabs.JRCrosstabCell;
import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.design.JRCrosstabOrigin;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabCell;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabDataset;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabParameter;
import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementDataset;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementGroup;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.action.WidgetAction.Adapter;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
public class ModelUtils {

    public static JRDesignCrosstabParameter cloneCrosstabParameter(JRDesignCrosstabParameter param) {
        JRDesignCrosstabParameter newParam = new JRDesignCrosstabParameter();
        newParam.setName( param.getName() );
        newParam.setForPrompting( param.isForPrompting() );
        newParam.setSystemDefined( param.isSystemDefined() );
        //newParam.setValueClass( param.getValueClass() );
        newParam.setValueClassName( param.getValueClassName() );
        newParam.setDescription( param.getDescription());
        if (param.getDefaultValueExpression() != null)
        {
            newParam.setDefaultValueExpression( cloneExpression( (JRDesignExpression)param.getDefaultValueExpression()) );
        }
        
        // Duplicate properties...
        replacePropertiesMap(param.getPropertiesMap(), newParam.getPropertiesMap());
        
        return newParam;
    }

    public static JRDesignStyle cloneStyle(JRDesignStyle style) {
        JRDesignStyle newStyle = (JRDesignStyle)style.clone();
        return newStyle;
    }

   /**
    * Get all the elements in the report
    * @param jd
    * @param includeCrosstabs
    * @return
    */
   public static List<JRDesignElement> getAllElements(JasperDesign jd, boolean includeCrosstabs) {

        List<JRDesignElement> list = new ArrayList<JRDesignElement>();

        List<JRBand> bands = getBands(jd);
        for (JRBand band : bands)
        {
            list.addAll(getAllElements(band));
        }

        if (includeCrosstabs)
        {
             List<JRDesignElement> list2 = new ArrayList<JRDesignElement>();
            for (int i=0; i<list.size(); ++i)
            {
                JRDesignElement ele = list.get(i);
                if (ele instanceof JRDesignCrosstab)
                {
                    list2.addAll(getAllElements((JRDesignCrosstab)ele));
                }
            }
            list.addAll(list2);
        }
        
        return list;
    }

    /**
     * Return all the elements contained in the cells of the specified crosstab.
     * @param crosstab
     * @return
     */
    public static List<JRDesignElement> getAllElements(JRDesignCrosstab crosstab) {

        List list = new ArrayList();
        List<JRDesignCellContents> cells = getAllCells(crosstab);

        for (JRDesignCellContents content : cells)
        {
            list.addAll(  getAllElements(content) );
        }
        return  list;
    }



    /**
     * Get all the elements in the report
     * Same as: getAllElements(jd, true);
     * @param jd
     * @return
     */
    public static List<JRDesignElement> getAllElements(JasperDesign jd) {
        
        return getAllElements(jd, true);
    }
    
    public static List<JRDesignElement> getAllElements(JRElementGroup group) {
        List list = new ArrayList();
        
        List elements = group.getChildren();
        
        for (Object ele : elements)
        {
            if (ele instanceof JRElementGroup)
            {
                list.addAll(getAllElements((JRElementGroup)ele));
            }
            else if (ele instanceof JRDesignElement)
            {
                list.add((JRDesignElement)ele);
                
                if(ele instanceof JRDesignCrosstab)
                {
                    JRDesignCrosstab crosstab = (JRDesignCrosstab)ele;
                    List cells = crosstab.getCellsList();
                    for (int i=0; i<cells.size(); ++i)
                    {
                        JRCrosstabCell cell = (JRCrosstabCell)cells.get(i);
                        list.addAll(getAllElements(cell.getContents()));
                    }
                }
            }
        }
        
        return list;
    }

    /**
     * Return the ordered list of bands available in the current report
     * @param jd the JasperDesign
     * @return a list of bands
     */
    public static List<JRBand> getBands(JasperDesign jd)
    {
        List<JRBand> list = new ArrayList<JRBand>();
        if (jd == null) return list;
        
        JRGroup[] groups = jd.getGroups();
        
        if (null != jd.getBackground()) list.add(jd.getBackground());
        if (null != jd.getTitle()) list.add(jd.getTitle());
        if (null != jd.getPageHeader()) list.add(jd.getPageHeader());
        if (null != jd.getColumnHeader()) list.add(jd.getColumnHeader());
        for (int i=0; i<groups.length; ++i)
        {
            if (null != groups[i].getGroupHeader()) list.add(groups[i].getGroupHeader());
        }
        if (null != jd.getDetail()) list.add(jd.getDetail());
        for (int i=groups.length-1; i>=0; --i)
        {
            if (null != groups[i].getGroupFooter()) list.add(groups[i].getGroupFooter());
        }
        if (null != jd.getColumnFooter()) list.add(jd.getColumnFooter());
        if (null != jd.getPageFooter()) list.add(jd.getPageFooter());
        if (null != jd.getLastPageFooter()) list.add(jd.getLastPageFooter());
        if (null != jd.getSummary()) list.add(jd.getSummary());
        if (null != jd.getNoData()) list.add(jd.getNoData());
        
        return list;
    }

    public static int getHeaderCellWidth(JRDesignCrosstab crosstab)
    {
        if (crosstab == null) return 0;
        //if (crosstab.getHeaderCell() != null) return crosstab.getHeaderCell().getWidth();
        JRCrosstabRowGroup[] row_groups = crosstab.getRowGroups();
        int tot = 0;
        for (int i=0; i<row_groups.length; ++i)
        {
            tot += row_groups[i].getWidth();
        }
        
        return tot;
    }
    
    public static int getHeaderCellHeight(JRDesignCrosstab crosstab)
    {
        if (crosstab == null) return 0;
        //if (crosstab.getHeaderCell() != null) return crosstab.getHeaderCell().getHeight();
        JRCrosstabColumnGroup[] col_groups = crosstab.getColumnGroups();
        int tot = 0;
        for (int i=0; i<col_groups.length; ++i)
        {
            tot += col_groups[i].getHeight();
        }
        
        return tot;
    }
    
    public static int findRowHeight(JRCrosstabCell[][] cells, int rowIndex) {
        for (int i=0; i<cells[rowIndex].length; ++i)
        {
            if (cells[rowIndex][i] != null && cells[rowIndex][i].getContents() != null)
            {
                return cells[rowIndex][i].getContents().getHeight();
            }
        }
        return 0;
    }
    
    public static int findColumnWidth(JRCrosstabCell[][] cells, int colIndex) {
        for (int i=0; i<cells.length; ++i)
        {
            if (cells[i][colIndex] != null && cells[i][colIndex].getContents() != null)
            {
                return cells[i][colIndex].getContents().getWidth();
            }
        }
        return 0;
    }
    
    public static JRDesignCellContents getCellAt(JRDesignCrosstab crosstab, Point point)
    {
        return getCellAt(crosstab,point, false);
        
    }
    public static JRDesignCellContents getCellAt(JRDesignCrosstab crosstab, Point point, boolean createIt) {
        
            
            int header_width = getHeaderCellWidth(crosstab);
            int header_height = getHeaderCellHeight(crosstab);

            JRCrosstabRowGroup[] row_groups = crosstab.getRowGroups();
            JRCrosstabColumnGroup[] col_groups = crosstab.getColumnGroups();
            
            JRCrosstabCell[][] cells = normalizeCell(crosstab.getCells(),row_groups,col_groups);
        
            int x = header_width;
            int y = header_height;
            
            Rectangle r1 = new Rectangle(0,0,header_width,header_height);
            if (r1.contains(point)) return (JRDesignCellContents)crosstab.getHeaderCell();

            for (int i=cells.length-1; i>=0; --i)
            {
                x = header_width;
                for (int k=cells[i].length-1; k>=0; --k)
                {
                    JRCrosstabCell cell = cells[i][k];
                    if (cell == null) continue;
                    Rectangle rect = new Rectangle(x,y,cell.getContents().getWidth(),cell.getContents().getHeight());

                    if (rect.contains(point)) 
                    {
                        if (cell.getContents() == null && createIt)
                        {
                            ((JRDesignCrosstabCell)cell).setContents(new JRDesignCellContents());
                        }
                        return (JRDesignCellContents)cell.getContents();
                    }
                    
                    x += ModelUtils.findColumnWidth(cells, k);
                }
                
                y += findRowHeight(cells, i);
            }

            int data_width = x - header_width;
            int data_height = y - header_height;

            x = 0;
            y = header_height;


            // paint row cells...
            for (int i=0; i<row_groups.length; ++i)
            {
                switch (row_groups[i].getTotalPosition())
                {
                    case BucketDefinition.TOTAL_POSITION_START:
                        Rectangle rect1 = new Rectangle(x,y,row_groups[i].getTotalHeader().getWidth(),row_groups[i].getTotalHeader().getHeight());
                        if (rect1.contains(point)) 
                        {
                            if ((JRDesignCellContents)row_groups[i].getTotalHeader() == null && createIt)
                            {
                                 ((JRDesignCrosstabGroup)row_groups[i]).setTotalHeader(new JRDesignCellContents());   
                            }
                            return (JRDesignCellContents)row_groups[i].getTotalHeader();
                        }
                        data_height -= row_groups[i].getTotalHeader().getHeight();
                        y += row_groups[i].getTotalHeader().getHeight();
                        break;
                    case BucketDefinition.TOTAL_POSITION_END:
                        int y_loc = y + data_height - row_groups[i].getTotalHeader().getHeight();
                        Rectangle rect2 = new Rectangle(x,y_loc,row_groups[i].getTotalHeader().getWidth(),row_groups[i].getTotalHeader().getHeight());
                        if (rect2.contains(point)) 
                        {
                            if ((JRDesignCellContents)row_groups[i].getTotalHeader() == null && createIt)
                            {
                                 ((JRDesignCrosstabGroup)row_groups[i]).setTotalHeader(new JRDesignCellContents());   
                            }
                            return (JRDesignCellContents)row_groups[i].getTotalHeader();
                        }

                        data_height -= row_groups[i].getTotalHeader().getHeight();
                        break;
                }
                
                Rectangle rect3 = new Rectangle(x,y,row_groups[i].getHeader().getWidth(),row_groups[i].getHeader().getHeight());
                if (rect3.contains(point)) 
                {
                        if ((JRDesignCellContents)row_groups[i].getHeader() == null && createIt)
                        {
                             ((JRDesignCrosstabGroup)row_groups[i]).setHeader(new JRDesignCellContents());   
                        }
                        return (JRDesignCellContents)row_groups[i].getHeader();
                }
                
                x += row_groups[i].getHeader().getWidth();
            }


            x = header_width;
            y = 0;

            // paint col cells...
            for (int i=0; i<col_groups.length; ++i)
            {
                switch (col_groups[i].getTotalPosition())
                {
                    case BucketDefinition.TOTAL_POSITION_START:
                        Rectangle rect1 = new Rectangle(x,y,col_groups[i].getTotalHeader().getWidth(),col_groups[i].getTotalHeader().getHeight());
                        if (rect1.contains(point))
                        {
                            if ((JRDesignCellContents)col_groups[i].getTotalHeader() == null && createIt)
                            {
                              ((JRDesignCrosstabGroup)col_groups[i]).setTotalHeader(new JRDesignCellContents());   
                            }
                            return (JRDesignCellContents)col_groups[i].getTotalHeader();
                        }
                        data_width -= col_groups[i].getTotalHeader().getWidth();
                        x += col_groups[i].getTotalHeader().getWidth();
                        break;
                    case BucketDefinition.TOTAL_POSITION_END:
                        int x_loc = x + data_width - col_groups[i].getTotalHeader().getWidth();
                        Rectangle rect2 = new Rectangle(x_loc,y,col_groups[i].getTotalHeader().getWidth(),col_groups[i].getTotalHeader().getHeight());
                        if (rect2.contains(point))
                        {
                            if ((JRDesignCellContents)col_groups[i].getTotalHeader() == null && createIt)
                            {
                              ((JRDesignCrosstabGroup)col_groups[i]).setTotalHeader(new JRDesignCellContents());   
                            }
                            return (JRDesignCellContents)col_groups[i].getTotalHeader();
                        }
                            
                        data_width -= col_groups[i].getTotalHeader().getWidth();
                        break;
                    case BucketDefinition.TOTAL_POSITION_NONE:
                        break;
                }

                Rectangle rect3 = new Rectangle(x,y,col_groups[i].getHeader().getWidth(),col_groups[i].getHeader().getHeight());
                if (rect3.contains(point))
                {
                    if ((JRDesignCellContents)col_groups[i].getHeader() == null && createIt)
                    {
                      ((JRDesignCrosstabGroup)col_groups[i]).setHeader(new JRDesignCellContents());   
                    }
                    return (JRDesignCellContents)col_groups[i].getHeader();
                }    

                y += col_groups[i].getHeader().getHeight();
            }
        
            return null;
    }
    
    /**
     * It could be a very expensive operation, don't use it if you have to explore the whole crosstab
     * 
     * @param crosstab
     * @param content
     * @return
     */
    public static Point getCellLocation(JRDesignCrosstab crosstab, JRDesignCellContents content) {
 
            if (content == null || content == crosstab.getHeaderCell()) return new Point(0,0);

           // System.out.println("We are looking for this cell contents: " + content + " ----- " + ModelUtils.nameOf(content) + " in " + crosstab);


            int header_width = getHeaderCellWidth(crosstab);
            int header_height = getHeaderCellHeight(crosstab);

            JRCrosstabRowGroup[] row_groups = crosstab.getRowGroups();
            JRCrosstabColumnGroup[] col_groups = crosstab.getColumnGroups();
            
            JRCrosstabCell[][] jr_cells = crosstab.getCells();
            JRCrosstabCell baseCell = jr_cells[jr_cells.length-1][jr_cells[0].length-1];
            
            JRCrosstabCell[][] cells = normalizeCell(jr_cells,row_groups,col_groups);

            int x = header_width;
            int y = header_height;
            
            for (int i=cells.length-1; i>=0; --i)
            {
                x = header_width;
                int height = 0;
                for (int k=cells[i].length-1; k>=0; --k)
                {
                    JRCrosstabCell cell = cells[i][k];
                    if (cell == null)
                    {
                        //x += baseCell.getWidth();
                    }
                    else
                    {
                       // System.out.println("Cell " + i + "/" + k +" " + (cell.getContents() == content) + " "+ ModelUtils.nameOf((JRDesignCellContents)cell.getContents()) + " (" + x + "," + y + ") " + cell.getContents());
                       // System.out.flush();

                        if (cell.getContents() == content) return new Point(x,y);
                        x += cell.getContents().getWidth();
                        height = cell.getContents().getHeight();
                    }
                }
                y += height;
            }
            
            int data_width = x - header_width;
            int data_height = y - header_height;


            // paint row cells...
            if (content.getOrigin().getType() == JRCrosstabOrigin.TYPE_ROW_GROUP_HEADER ||
                content.getOrigin().getType() == JRCrosstabOrigin.TYPE_ROW_GROUP_TOTAL_HEADER)
            {
                x = 0;
                y = header_height;
            
                for (int i=0; i<row_groups.length; ++i)
                {
                    switch (row_groups[i].getTotalPosition())
                    {
                        case BucketDefinition.TOTAL_POSITION_START:
                            if (row_groups[i].getTotalHeader() == content) return new Point(x,y);
                            data_height -= row_groups[i].getTotalHeader().getHeight();
                            y += row_groups[i].getTotalHeader().getHeight();
                            break;
                        case BucketDefinition.TOTAL_POSITION_END:
                            int y_loc = y + data_height - row_groups[i].getTotalHeader().getHeight();
                            if (row_groups[i].getTotalHeader() == content) return new Point(x,y_loc);
                            data_height -= row_groups[i].getTotalHeader().getHeight();
                            break;
                    }

                    if (row_groups[i].getHeader() == content) return new Point(x,y);
                    x += row_groups[i].getHeader().getWidth();
                }
            }

            x = header_width;
            y = 0;

            if (content.getOrigin().getType() == JRCrosstabOrigin.TYPE_COLUMN_GROUP_HEADER ||
                content.getOrigin().getType() == JRCrosstabOrigin.TYPE_COLUMN_GROUP_TOTAL_HEADER)
            {
                // paint col cells...
                for (int i=0; i<col_groups.length; ++i)
                {
                    switch (col_groups[i].getTotalPosition())
                    {
                        case BucketDefinition.TOTAL_POSITION_START:
                            if (col_groups[i].getTotalHeader() == content) return new Point(x,y);
                            data_width -= col_groups[i].getTotalHeader().getWidth();
                            x += col_groups[i].getTotalHeader().getWidth();
                            break;
                        case BucketDefinition.TOTAL_POSITION_END:
                            int x_loc = x + data_width - col_groups[i].getTotalHeader().getWidth();
                            if (col_groups[i].getTotalHeader() == content) return new Point(x_loc,y);
                            data_width -= col_groups[i].getTotalHeader().getWidth();
                            break;
                        case BucketDefinition.TOTAL_POSITION_NONE:
                            break;
                    }

                    if (col_groups[i].getHeader() == content) return new Point(x,y);
                    y += col_groups[i].getHeader().getHeight();
                }
            }

            System.out.println("Unable to find the cell location of: " + content + ModelUtils.nameOf(content));
            System.out.flush();

            return new Point(0,0);
    }

    /**
     * Look in the crosstab if the provided new name is already a name of a group or a measure...
     * 
     * @param crosstab
     * @param new_name
     * @return
     */
    public static boolean isValidNewCrosstabObjectName(JRDesignCrosstab crosstab, String new_name) {
        
        if (crosstab.getRowGroupIndicesMap().containsKey(new_name)) return false;
        if (crosstab.getColumnGroupIndicesMap().containsKey(new_name)) return false;
        if (crosstab.getMeasureIndicesMap().containsKey(new_name)) return false;
        
        return true;
    }
    
    public static String nameOf(JRCrosstabOrigin origin) {
        
        String title =I18n.getString("ModelUtils.Title.unknow");
        if (origin != null)
        {
            
            switch (origin.getType())
            {
                case JRCrosstabOrigin.TYPE_HEADER_CELL:
                    title = I18n.getString("ModelUtils.Title.Header");
                    break;
                case JRCrosstabOrigin.TYPE_DATA_CELL:
                    title = (origin.getRowGroupName() == null ? I18n.getString("ModelUtils.Title.Detail") : origin.getRowGroupName()) + " / " +
                            (origin.getColumnGroupName() == null ? I18n.getString("ModelUtils.Title.Detail") : origin.getColumnGroupName());
                    break;
                case JRCrosstabOrigin.TYPE_COLUMN_GROUP_HEADER:
                    title = origin.getColumnGroupName() + I18n.getString("ModelUtils.Column.header");
                    break;
                case JRCrosstabOrigin.TYPE_COLUMN_GROUP_TOTAL_HEADER:
                    title = origin.getColumnGroupName() + I18n.getString("ModelUtils.Title.Totalheader");
                    break;
                case JRCrosstabOrigin.TYPE_ROW_GROUP_HEADER:
                    title = origin.getRowGroupName() + I18n.getString("ModelUtils.Title.HeaderH");
                    break;
                case JRCrosstabOrigin.TYPE_ROW_GROUP_TOTAL_HEADER:
                    title = origin.getRowGroupName() + I18n.getString("ModelUtils.Title.Totalheader");
                    break;
                case JRCrosstabOrigin.TYPE_WHEN_NO_DATA_CELL:
                    title = I18n.getString("ModelUtils.Title.WhenNoData");
                    break;
            }
        }
        
        return title;
        
    }
    
    /**
     * Look the contents origin to guess the cell name
     * 
     * @param contents
     * @return
     */
    public static String nameOf(JRDesignCellContents contents)
    {
        JRCrosstabOrigin origin = contents.getOrigin();
        
        return nameOf(origin);
        
        /*
        
        // Draw this cell...
        if (contents == crosstab.getHeaderCell()) return "Crosstab header";
        
        // Paint the data cells...
        JRCrosstabCell[][] cells = crosstab.getCells();
        for (int i=cells.length-1; i>=0; --i)
        {
            for (int k=cells[i].length-1; k>=0; --k)
            {
                JRCrosstabCell cell = cells[i][k];
                String title = (cell.getRowTotalGroup() == null ? "Detail" : cell.getRowTotalGroup()) + " / " +
                               (cell.getColumnTotalGroup() == null ? "Detail" : cell.getColumnTotalGroup());
                if (contents == cell.getContents()) return title;
            }
        }
        
        JRCrosstabRowGroup[] row_groups = crosstab.getRowGroups();
        
        // paint row cells...
        for (int i=0; i<row_groups.length; ++i)
        {
            if (row_groups[i].getHeader() == contents) return row_groups[i].getName() + " header";
            if (row_groups[i].getTotalHeader() == contents) return row_groups[i].getName() + " total header";
        }
        
        JRCrosstabColumnGroup[] col_groups = crosstab.getColumnGroups();
        
        for (int i=0; i<col_groups.length; ++i)
        {
            if (col_groups[i].getHeader() == contents) return col_groups[i].getName() + " header";
            if (col_groups[i].getTotalHeader() == contents) return col_groups[i].getName() + " total header";
        }
        
        return "?";
        */
    }
    
    public static List<JRDesignCellContents> getAllCells(JRDesignCrosstab designCrosstab) {
        
        List<JRDesignCellContents> list = new ArrayList<JRDesignCellContents>();
        
        list.add( (JRDesignCellContents)designCrosstab.getHeaderCell() );
        
        // Row cells
        List cells = designCrosstab.getCellsList();
        for (int i=0; i<cells.size(); ++i)
        {
            JRCrosstabCell cell = (JRCrosstabCell)cells.get(i);
            if (cell != null && (JRDesignCellContents)cell.getContents() != null)
            {
                list.add( (JRDesignCellContents)cell.getContents() );
            }
        }
        
        JRCrosstabRowGroup[] row_groups = designCrosstab.getRowGroups();
        for (int i=0; i<row_groups.length; ++i)
        {
            switch (row_groups[i].getTotalPosition())
            {
                case BucketDefinition.TOTAL_POSITION_START:
                case BucketDefinition.TOTAL_POSITION_END:
                    list.add( (JRDesignCellContents)row_groups[i].getTotalHeader());
                    break;
                default: break;
            }
            list.add( (JRDesignCellContents)row_groups[i].getHeader());
        }
        
        JRCrosstabColumnGroup[] col_groups = designCrosstab.getColumnGroups();
        for (int i=0; i<col_groups.length; ++i)
        {
            switch (col_groups[i].getTotalPosition())
            {
                case BucketDefinition.TOTAL_POSITION_START:
                case BucketDefinition.TOTAL_POSITION_END:
                    list.add( (JRDesignCellContents)col_groups[i].getTotalHeader());
                    break;
                default: break;
            }
            list.add( (JRDesignCellContents)col_groups[i].getHeader());
        }
        
        return list;
    }

    /**
     * Returns the dataset of an element. If the element is contained in a crosstab referencing a specific
     * dataset, it is found.
     * If not, the main report dataset is returned.
     */
    public static JRDesignDataset getElementDataset(JRDesignElement element, JasperDesign jd) {
        
        JRDesignDataset dataset = jd.getMainDesignDataset();
        
        JRElementGroup group = getTopElementGroup(element);
        
        if (group instanceof JRDesignCellContents) {  // Main datasource
            // Check if this crosstab is using a different dataset...
            JRDesignCellContents cellContent = (JRDesignCellContents)group;
            if (cellContent.getOrigin().getCrosstab().getDataset() != null)
            {
                JRElementDataset elementDataset = cellContent.getOrigin().getCrosstab().getDataset();
                if (elementDataset.getDatasetRun() != null)
                {
                    // Get the dataset name...
                    String datasetName = elementDataset.getDatasetRun().getDatasetName();
                    
                    dataset = (JRDesignDataset)jd.getDatasetMap().get(datasetName);
                }
            }
        }
        
        return dataset;
    }

    
    /**
     * Returns the dataset tied to a dataset run of a crosstab, or the main jd dataset
     * if the crosstab dataset and/or the dataset run are null.
     * 
     * @param element
     * @param jd
     * @return crosstab's JRDesignDataset
     */
    public static JRDesignDataset getCrosstabDataset(JRDesignCrosstab element, JasperDesign jd)
    {
                JRElementDataset elementDataset = element.getDataset();
                if (elementDataset != null && elementDataset.getDatasetRun() != null)
                {
                    // Get the dataset name...
                    String datasetName = elementDataset.getDatasetRun().getDatasetName();
                    
                    return (JRDesignDataset)jd.getDatasetMap().get(datasetName);
                }    
                
                return (JRDesignDataset)jd.getMainDataset();
    }
    
    
    /**
     * Return a JRDesignGroup from a band if this band is the header or the foorter of a group.
     */
    public static JRDesignGroup getGroupFromBand(JasperDesign jd, JRDesignBand band) {
        
        if (band == null || jd == null || band.getOrigin().getGroupName() == null) return null;
        String s = band.getOrigin().getGroupName();

        JRGroup[] groups = jd.getGroups();
        for (int i=0; i<groups.length; ++i)
        {
                if (groups[i].getName().equals(s) ) return (JRDesignGroup)groups[i];
        }
        
        return null;
    }

    /**
     * Check if  parent is Ancestor of g
     */
    public static boolean isAncestorElemenetGroup(JRElementGroup parent, JRElementGroup g) {
        
        while (g != null)
        {
            if (g instanceof JRDesignBand || g instanceof JRDesignCellContents) return false;
            if (g == parent) return true;
            g = g.getElementGroup();
        }
        
        return false;
    }

    /**
     * Find element in elements. If an element is a frame, look inside the frame
     * recursively
     **/
    public static boolean isChildOf(JRDesignElement element,  JRElement[] elements)
    {
        for (int i=0; i<elements.length; ++i)
        {
            if (element == elements[i]) return true;
            if (elements[i] instanceof JRDesignFrame)
            {
                if (isChildOf(element, ((JRDesignFrame)elements[i]).getElements() ))
                    return true;
            }
        }
        return false;
    }

    public static boolean isGroupHeader(JRBand b, JasperDesign jd) {
        
        if (b == null) return false;
        JRGroup[] groups = jd.getGroups();
        for (int i=0; i<groups.length; ++i)
        {
            if (b == groups[i].getGroupHeader()) return true;
        }
        return false;
    }
    
    public static boolean isGroupFooter(JRBand b, JasperDesign jd) {
        
        if (b == null) return false;
        JRGroup[] groups = jd.getGroups();
        for (int i=0; i<groups.length; ++i)
        {
            if (b == groups[i].getGroupFooter()) return true;
        }
        return false;
    }

    public static String nameOf(JROrigin origin) {
        if (origin != null)
        {
            switch (origin.getBandType())
            {
                case JROrigin.BACKGROUND: return I18n.getString("band.name.background");
                case JROrigin.TITLE: return I18n.getString("band.name.title");
                case JROrigin.PAGE_HEADER: return I18n.getString("band.name.pageHeader");
                case JROrigin.COLUMN_HEADER: return I18n.getString("band.name.columnHeader");
                case JROrigin.DETAIL: return I18n.getString("band.name.detail");
                case JROrigin.COLUMN_FOOTER: return I18n.getString("band.name.columnFooter");
                case JROrigin.PAGE_FOOTER: return I18n.getString("band.name.pageFooter");
                case JROrigin.LAST_PAGE_FOOTER: return I18n.getString("band.name.lastPageFooter");
                case JROrigin.SUMMARY: return I18n.getString("band.name.summary");
                case JROrigin.NO_DATA: return I18n.getString("band.name.noData");
                case JROrigin.GROUP_HEADER:
                    return  I18n.getString("band.name.GroupHeader", origin.getGroupName());
                case JROrigin.GROUP_FOOTER:
                    return  I18n.getString("band.name.GroupFooter", origin.getGroupName());
            }
        }
        
        return I18n.getString("ModelUtils.Title.unknow");
        
    }
    
    public static String nameOf(JRBand b, JasperDesign jd)
    {
        /*
        if (b == null) return null;
        if (b == jd.getTitle()) return "title";
        if (b == jd.getPageHeader()) return "pageHeader";
        if (b == jd.getColumnHeader()) return "columnHeader";
        if (b == jd.getDetail()) return "detail";
        if (b == jd.getColumnFooter()) return "columnFooter";
        if (b == jd.getPageFooter()) return "pageFooter";
        if (b == jd.getSummary()) return "summary";
        if (b == jd.getNoData()) return "noData";
        
        JRGroup[] groups = jd.getGroups();
        for (int i=0; i<groups.length; ++i)
        {
            if (b == groups[i].getGroupHeader()) return groups[i].getName() + "GroupHeader";
            if (b == groups[i].getGroupFooter()) return groups[i].getName() + "GroupFooter";
        }
        */
        return nameOf( ((JRDesignBand)b).getOrigin() );
    }
    
    /**
     * Returns null if b is the first available band.
     * 
     * @param b 
     * @param jd 
     * @return 
     */
    public static JRBand getPreviousBand(JRBand b, JasperDesign jd)
    {
        if (b == null || jd == null) return null;
        List<JRBand> bands = getBands(jd);
        JRBand previous = null;
        for (JRBand tmpBand : bands)
        {
            if (tmpBand == b) return previous;
            previous = tmpBand;
        }
        return previous;
    }
    
    public static int getBandLocation(JRBand b, JasperDesign jd)
    {
        int yLocation = jd.getTopMargin();
        List<JRBand> bands = ModelUtils.getBands(jd);
            
        for (JRBand tmpBand : bands)
        {
            if (tmpBand == b) return yLocation;
            yLocation += tmpBand.getHeight();
        }
        
        return yLocation;
    }
    
    public static int getBandHeight(JRBand b)
    {
        return (b != null) ? b.getHeight() : 0;
    }
    
    /**
     * This method summarize the JasperReports rules for bands height.
     * The real check should be done by the JRVerifier class, probably
     * we should move that code there providing a similar static method.
     * 
     * @param b
     * @param jd
     * @return
     */
    public static int getMaxBandHeight(JRDesignBand b, JasperDesign jd)
    {
        if (b == null || jd == null) return 0;
        
        JROrigin origin = b.getOrigin();
        
        int topBottomMargins = jd.getTopMargin() + jd.getBottomMargin();
        
        if ( (origin.getBandType() == JROrigin.TITLE && jd.isTitleNewPage()) ||
             (origin.getBandType() == JROrigin.SUMMARY && jd.isSummaryNewPage()) ||
             origin.getBandType() == JROrigin.BACKGROUND ||
             origin.getBandType() == JROrigin.NO_DATA)
        {
            return jd.getPageHeight() - topBottomMargins;
        }
        
        int basicBandsHeight = 0;
        int titleHeight = jd.getTitle() != null ? jd.getTitle().getHeight() : 0;
        
        basicBandsHeight += topBottomMargins;
        basicBandsHeight += jd.getPageHeader() != null ? jd.getPageHeader().getHeight() : 0;
        basicBandsHeight += jd.getColumnHeader() != null ? jd.getColumnHeader().getHeight() : 0;
        basicBandsHeight += jd.getColumnFooter() != null ? jd.getColumnFooter().getHeight() : 0;
        basicBandsHeight += jd.getPageFooter() != null ? jd.getPageFooter().getHeight() : 0;
        
        if (origin.getBandType() == JROrigin.GROUP_FOOTER ||
            origin.getBandType() == JROrigin.GROUP_HEADER)
        {
            return jd.getPageHeight() - basicBandsHeight - (jd.isTitleNewPage() ? 0 : titleHeight);
        }
        
        int detailHeight = jd.getDetail() != null ? jd.getDetail().getHeight() : 0;
        
        // Calcolate the design page without extra bands and the current band...
        return jd.getPageHeight() - basicBandsHeight + b.getHeight() - detailHeight;
    }
    
    public static JRBand bandOfElement(JRElement element, JasperDesign jd)
    {
        if (element == null || jd == null) return null;
        
        List<JRBand> bands = ModelUtils.getBands(jd);
        
        for (JRBand tmpBand : bands)
        {
            JRElement[] elements = tmpBand.getElements();
            for (int i=0; i<elements.length; ++i)
            {
                if (element == elements[i]) 
                {
                    return tmpBand;
                }
            }
        }
        
        return null;
    }
    
    
    public static int getDesignHeight(JasperDesign jd )
    {
        int designHeight = 0;
        if (jd != null)
        {
            designHeight += jd.getTopMargin();
            List<JRBand> bands = ModelUtils.getBands(jd);
            
            for (JRBand b : bands)
            {
                designHeight += b.getHeight();
            }
            
            designHeight += jd.getBottomMargin();
        }
        
        return designHeight;
    }
    
    
    
    public static int getMaximumDesignHeight(JasperDesign jd )
    {
        int maxDesignHeight = 3*jd.getPageHeight(); // BG + page + noData
        if (jd.isTitleNewPage()) maxDesignHeight += jd.getPageHeight();
        if (jd.isSummaryNewPage()) maxDesignHeight += jd.getPageHeight();
        return maxDesignHeight;
    }
    
    public static Node findElementNode(Node rootNode, JRElement element)
    {
        if (rootNode instanceof ElementNode)
        {
            if ( ((ElementNode)rootNode).getElement().equals(element))
            {
                return rootNode;
            }
        }
        
        if (rootNode == null) return null;
        Children children = rootNode.getChildren();
        Node[] nodes = children.getNodes();
        for (int i=0; i<nodes.length; ++i)
        {
            Node node = findElementNode(nodes[i], element);
            if (node != null) return node;
        }
        return null;
    }
    
    /**
     *  Utility function to duplicate a parameter. All the parameter properties
     *  and parameter default value expression are duplicated as well.
     */
    public static JRDesignParameter cloneParameter(JRDesignParameter param)
    {
        JRDesignParameter newParam = new JRDesignParameter();
        newParam.setName( param.getName() );
        newParam.setForPrompting( param.isForPrompting() );
        newParam.setSystemDefined( param.isSystemDefined() );
        //newParam.setValueClass( param.getValueClass() );
        newParam.setValueClassName( param.getValueClassName() );
        newParam.setDescription( param.getDescription());
        if (param.getDefaultValueExpression() != null)
        {
            newParam.setDefaultValueExpression( cloneExpression( (JRDesignExpression)param.getDefaultValueExpression()) );
        }
        
        // Duplicate properties...
        replacePropertiesMap(param.getPropertiesMap(), newParam.getPropertiesMap());
        
        return newParam;
    }

    /**
     * This method creates a new cell matrix ordering the cells based on total position....
     * The matrix is displayed like that:
     *  <pre>
     *       2         1         0
     *   +--------+---------+---------+
     * 3 |  3/2   |   3/1   |  3/0    |
     *   +--------+---------+---------+
     * 2 |  2/2   |   2/1   |  2/0    |
     *   +--------+---------+---------+
     * 1 |  1/2   |   1/1   |  1/0    |
     *   +--------+---------+---------+
     * 0 |  0/2   |   0/1   |  0/0    |
     *   +--------+---------+---------+
     * </pre>
     * @param cells
     * @param row_groups
     * @param col_groups
     * @return
     */
    public static JRCrosstabCell[][] normalizeCell(JRCrosstabCell[][] cells, JRCrosstabRowGroup[] row_groups, JRCrosstabColumnGroup[] col_groups) {
        
        JRCrosstabCell[][] newCells = new JRCrosstabCell[row_groups.length+1][col_groups.length+1];
        
//        System.out.println("cell     Rows " + cells.length + " x columns " + cells[0].length);
//        System.out.flush();
//        System.out.println("newCells Rows " + newCells.length + " x columns " + newCells[0].length);
//        System.out.flush();
        
        int minRow = 0;
        int maxRow = row_groups.length;
        int minCol = 0;
        int maxCol = col_groups.length;
        
        int[] rowsConversion = new int[row_groups.length+1];
        int[] colsConversion = new int[col_groups.length+1];
        
        for (int i=0; i<row_groups.length; ++i)
        {
            if (row_groups[i].getTotalPosition() == BucketDefinition.TOTAL_POSITION_START)
            {
                rowsConversion[i] = maxRow;
                maxRow--;
            }
            else
            {
                rowsConversion[i] = minRow;
                minRow++;
            }
        }
        rowsConversion[row_groups.length] = minRow;
        
        
        for (int i=0; i<col_groups.length; ++i)
        {
            if (col_groups[i].getTotalPosition() == BucketDefinition.TOTAL_POSITION_START)
            {
                colsConversion[i] = maxCol;
                maxCol--;
            }
            else
            {
                colsConversion[i] = minCol;
                minCol++;
            }
        }
        colsConversion[col_groups.length] = minCol;
        
        for (int i=0; i<rowsConversion.length; ++i)
        {
            for (int j=0; j<colsConversion.length; ++j)
            {
                int x = rowsConversion[i];
                int y = colsConversion[j];
                try {
                    JRCrosstabCell cell = cells[i][j];
                    newCells[x][y] = cell;
                } catch (Exception ex)
                {
                }
            }
        }
        
        
//        // Print all:
//        for (int i=cells.length-1; i>=0; --i)
//        {
//            System.out.print(" >> ");
//            for (int j=cells[i].length-1; j>=0; --j)
//            { 
//                
//                 JRCrosstabCell cell = cells[i][j];
//                 if (cell == null)   System.out.print("NULL\t|");
//                 else System.out.print(ModelUtils.nameOf((JRDesignCellContents)cell.getContents()) + "\t|");
//            }
//            System.out.println("");
//        }
//        System.out.println("---------------");
//        for (int i=newCells.length-1; i>=0; --i)
//        {
//            for (int j=newCells[i].length-1; j>=0; --j)
//            { 
//                 JRCrosstabCell cell = newCells[i][j];
//                 if (cell == null)   System.out.print("NULL\t|");
//                 else System.out.print(ModelUtils.nameOf((JRDesignCellContents)cell.getContents()) + "\t|");
//            }
//            System.out.println("");
//        }
//        System.out.println("----------------------------------");
//        System.out.flush();
        return newCells;
        
    }

    
    public static void fixElementsExpressions(JasperDesign jd, String oldName, String newName, byte chunckType, String newClassName) {
        List<JRBand> bands = ModelUtils.getBands(jd);
        for (JRBand b : bands)
        {
            if (b != null && b instanceof JRDesignBand)
            {
                fixElementsExpressions((JRDesignBand)b,oldName,newName,chunckType,newClassName);
            }
        }
    }
    
    public static void fixElementsExpressions(JRDesignCrosstab crosstab, String oldName, String newName, byte chunckType, String newClassName) {
        
        List<JRDesignCellContents> cells = ModelUtils.getAllCells(crosstab);
        for (JRDesignCellContents cell : cells)
        {
            if (cell != null && cell instanceof JRDesignCellContents)
            {
                fixElementsExpressions(cell,oldName,newName,chunckType,newClassName);
            }
        }
    }
    
    public static void fixElementsExpressions(JRDesignElementGroup group, String oldName, String newName, byte chunckType, String newClassName) {
        
        List list = group.getChildren();
        for (int i=0; i<list.size(); ++i)
        {
            Object obj = list.get(i);
            if (obj == null) continue;
            if (obj instanceof JRDesignElementGroup)
            {
                fixElementsExpressions((JRDesignElementGroup)obj,oldName,newName,chunckType,newClassName);
            }
            else if (obj instanceof JRDesignTextField)
            {
                fixElementExpressionText((JRDesignTextField)obj,oldName,newName,chunckType,newClassName);
            }
            else if (obj instanceof JRDesignImage)
            {
                fixElementExpressionImage((JRDesignImage)obj,oldName,newName,chunckType,newClassName);
            }
            
            
        }
        
    }
    
    public static void fixElementExpressionText(JRDesignTextField textfield, String oldName, String newName, byte chunckType, String newClassName) {
        
        JRDesignExpression exp = (JRDesignExpression)textfield.getExpression();
        replaceChunkText( exp, oldName, newName, chunckType, newClassName);
        if (exp != null && exp.getValueClassName() != null && newClassName != null)
        {
            CreateTextFieldAction.setMatchingClassExpression(exp, exp.getValueClassName(), true);
            textfield.getEventSupport().firePropertyChange( JRDesignTextField.PROPERTY_EXPRESSION, null, exp);
        }
        
    }
    
    public static void fixElementExpressionImage(JRDesignImage imageelement, String oldName, String newName, byte chunckType, String newClassName) {
        
        JRDesignExpression exp = (JRDesignExpression)imageelement.getExpression();
        replaceChunkText( exp, oldName, newName, chunckType, newClassName);
        if (exp != null)
        {
            imageelement.getEventSupport().firePropertyChange( JRDesignTextField.PROPERTY_EXPRESSION, null, exp);
        }
        
    }
    
    /**
     * Replace a particular name with another name.
     * If newClassName is null, it is ignored, otherwise the expression will take the new class value.
     * if oldName is null or newName is null, nothing is done
     * if oldName and newName are equals,  nothing is done
     * if exp is null, nothing is done
     * 
     * @param exp
     * @param oldName
     * @param newName
     * @param chunckType
     * @param newClassName (can be null)
     */
    public static void replaceChunkText(JRDesignExpression exp, String oldName, String newName, byte chunckType, String newClassName) {
        if (exp == null) return;
        if (oldName == null || newName == null) return;
        if (oldName.equals(newName)) return;
        
        // replace the correct chunk. TODO: better implementation?
        String oldString = "";
        String pre = "";
        String post = "";
        
        switch (chunckType)
        {
            case JRExpressionChunk.TYPE_FIELD:
                pre = "$F{";  post = "}"; break;
            case JRExpressionChunk.TYPE_VARIABLE:
                pre = "$V{";  post = "}"; break;
            case JRExpressionChunk.TYPE_PARAMETER:
                pre = "$P{";  post = "}";  break;
            case JRExpressionChunk.TYPE_RESOURCE:
                pre = "$R{";  post = "}";  break;
            default:
               break;
        }
        
        oldName = pre + oldName + post;
        newName = pre + newName + post;
        
        if (exp.getText() == null || exp.getText().indexOf(oldName) < 0) return;
          
        exp.setText( Misc.string_replace(newName, oldName, exp.getText()));
        if (exp.getValueClassName() != null &&
            newClassName != null &&
            !exp.getValueClassName().equals(newClassName))
        {
            exp.setValueClassName( newClassName );
        }
    }
    
    /**
     * replace the properties in dest with the ones found in source.
     */
    public static void replacePropertiesMap(JRPropertiesMap source, JRPropertiesMap dest)
    {
        // Update names.
        String[] propertyNames = source.getPropertyNames();
        if (propertyNames != null && propertyNames.length > 0)
        {
                for(int i = 0; i < propertyNames.length; i++)
                {
                        dest.setProperty(propertyNames[i], source.getProperty(propertyNames[i]));
                }
        }
        
        // Remove unset names...
        propertyNames = dest.getPropertyNames();
        if (propertyNames != null && propertyNames.length > 0)
        {
                for(int i = 0; i < propertyNames.length; i++)
                {
                    if (!source.containsProperty(propertyNames[i]))
                        dest.removeProperty(propertyNames[i]);
                }
        }
    }
    
    /**
     * replace the expression properties in element with the ones found in newExpressionProperties.
     */
    public static void replaceExpressionProperties(JRDesignElement element, List<GenericProperty> newExpressionProperties)
    {
        // Update names.
        
        List usedProps = new ArrayList();
        List propertyExpressions = element.getPropertyExpressionsList();
        
        for(int i = 0; i < propertyExpressions.size(); i++)
        {
            element.removePropertyExpression((JRPropertyExpression)propertyExpressions.get(i));
        }
        
        if (newExpressionProperties == null) return;
        for(GenericProperty prop : newExpressionProperties)
        {
            if (!prop.isUseExpression()) continue;
            JRDesignPropertyExpression newProp = new JRDesignPropertyExpression();
            newProp.setName(prop.getKey());
            newProp.setValueExpression(prop.getExpression());
            element.addPropertyExpression(newProp);
        }
        
    }
    
    /**
     *  Utility function to duplicate an expression.
     */
    public static JRDesignExpression cloneExpression(JRExpression exp)
    {
        if (exp == null) return null;
        JRDesignExpression newExp = new JRDesignExpression();
        //exp.setValueClass( exp.getValueClass() );
        newExp.setValueClassName( exp.getValueClassName() );
        newExp.setText( exp.getText() );
        return newExp;
    }
    
    /**
     * This utility looks for the phisical parent of an element and returns his position.
     * This position is refers to the plain document preview, where 0,0 are the coordinates
     * of the upperleft corner of the document.
     * If no parent is found, the method returns 0,0
     */ 
    public static Point getParentLocation(JasperDesign jd, JRDesignElement element )
    {
        Point base = new Point(0,0);
        if (element == null) return base;
        
        JRElementGroup grp = element.getElementGroup();

        // I need to discover the first logical parent of this element
        while (grp != null)    // Element placed in a frame
        {
            if (grp instanceof JRDesignBand)    // Element placed in a band
            {
                JRDesignBand band = (JRDesignBand)grp;
                base.x = jd.getLeftMargin();
                base.y = ModelUtils.getBandLocation(band, jd);
                break;
            }
            else if (grp instanceof JRDesignCellContents)    // Element placed in a cell
            {
                // TODO: calculate cell position....
                JRDesignCellContents cell = (JRDesignCellContents)grp;
                
                base = getCellLocation(cell.getOrigin().getCrosstab(),cell);
                break;
            }
            else if (grp instanceof JRDesignFrame)
            {
                JRDesignFrame frame = (JRDesignFrame)grp;
                base = getParentLocation(jd, frame);
                base.x += frame.getX();
                base.y += frame.getY();
                // In this case we return immediatly
                break;
            }
            else
            {
                grp = grp.getElementGroup();
            }
        }
        
        return base;
        
    }
    
    /**
     * This utility looks for the phisical parent of an element and returns his position.
     * This position is refers to the plain document preview, where 0,0 are the coordinates
     * of the upperleft corner of the document.
     * If no parent is found, the method returns 0,0
     */ 
    public static Rectangle getParentBounds(JasperDesign jd, JRDesignElement element )
    {
        Rectangle base = new Rectangle(0,0,0,0);
        if (element == null) return base;
        
        JRElementGroup grp = element.getElementGroup();
        
        // I need to discover the first logical parent of this element
        while (grp != null)    // Element placed in a frame
        {
            if (grp instanceof JRDesignBand)    // Element placed in a band
            {
                JRDesignBand band = (JRDesignBand)grp;
                base.x = jd.getLeftMargin();
                base.y = ModelUtils.getBandLocation(band, jd);
                base.width = jd.getPageWidth() - jd.getLeftMargin() - jd.getRightMargin();
                base.height = band.getHeight();
                break;
            }
            else if (grp instanceof JRDesignCellContents)    // Element placed in a cell
            {
                // TODO: calculate cell position....
                JRDesignCellContents cell = (JRDesignCellContents)grp;
                Point p = getCellLocation(cell.getOrigin().getCrosstab(),cell);
                base.x = p.x;
                base.y = p.y;
                base.width = cell.getWidth();
                base.height = cell.getHeight();
                break;
            }
            else if (grp instanceof JRDesignFrame)
            {
                JRDesignFrame frame = (JRDesignFrame)grp;
                Point p = getParentLocation(jd, frame);
                base.x = p.x + frame.getX();
                base.y = p.y + frame.getY();
                base.width = frame.getWidth();
                base.height = frame.getHeight();
                // In this case we return immediatly
                break;
            }
            else
            {
                grp = grp.getElementGroup();
            }
        }
        
        return base;
        
    }
    
    /**
     *  Utility function to duplicate a parameter. All the parameter properties
     *  and parameter default value expression are duplicated as well.
     */
    public static JRDesignField cloneField(JRDesignField field) throws CloneNotSupportedException
    {
        /*
        JRDesignField newField = new JRDesignField();
        newField.setName( field.getName() );
        newField.setValueClassName( field.getValueClassName() );
        newField.setDescription(field.getDescription());
        // Duplicate properties...
        replacePropertiesMap(field.getPropertiesMap(), newField.getPropertiesMap());
        
        return newField;
         */
        return (JRDesignField)field.clone();
    }
    
    
    /**
     *  Utility function to duplicate a parameter. All the parameter properties
     *  and parameter default value expression are duplicated as well.
     */
    public static JRDesignVariable cloneVariable(JRDesignVariable variable)
    {
        JRDesignVariable newVariable = new JRDesignVariable();
        newVariable.setName( variable.getName() );
        newVariable.setValueClassName( variable.getValueClassName() );
        newVariable.setCalculation( variable.getCalculation() );
        newVariable.setExpression( cloneExpression( variable.getExpression()) );
        newVariable.setIncrementGroup( variable.getIncrementGroup() );
        newVariable.setIncrementType( variable.getIncrementType() );
        newVariable.setIncrementerFactoryClassName( variable.getIncrementerFactoryClassName() );
        newVariable.setInitialValueExpression( cloneExpression( variable.getInitialValueExpression() ));
        newVariable.setResetGroup( variable.getResetGroup() );
        newVariable.setResetType( variable.getResetType());
        newVariable.setSystemDefined( variable.isSystemDefined() );
        return newVariable;
    }

    /**
     *  Utility function to duplicate a group.
     *  No elements are considered, just the group for subdataset
     */
    public static JRDesignGroup cloneGroup(JRDesignGroup group)
    {
        JRDesignGroup newGroup = new JRDesignGroup();
        newGroup.setName( group.getName() );
        newGroup.setExpression( cloneExpression( group.getExpression()) );
        return newGroup;
    }
    
    /**
     * Looks for propertyName in the sets of properties.
     * @param Node.PropertySet[] sets The sets of properties (see Node.getPropertySets()
     * @param String propertyName The name of the property to look for
     * @return true if propertyName is in one of the sets.
     * 
     */
    public static boolean containsProperty(Node.PropertySet[] sets, String propertyName)
    {
        for (int i=0; i<sets.length; ++i)
        {
            Node.Property[] pp = sets[i].getProperties();
            for (int j=0; j<pp.length; ++j)
            {
                String name = pp[j].getName();
                if (name != null && name.equals(propertyName)) return true;
            }
        }
        
        return false;
    }

    /**
     * Look if an element is referencing as parent the group or some group child of this group.
     * Please note that we are not looking iside the group.getElements since the element can
     * no longer be part of the model, but it can still referencing a parent.
     */
    public static boolean isElementChildOf(JRDesignElement element, JRElementGroup group) {
        
        JRElementGroup g1 = element.getElementGroup();
        while (g1 != null)
        {
            if (g1 == group) return true;
            g1 = g1.getElementGroup();
        }
        return false;
    }

    /**
     * Check if an element is orphan or not.
     * An element is orphan when his parent is null, or if is null one of his ancestor parents
     */
    public static boolean isOrphan(JRDesignElement element) {
        
        return getTopElementGroup(element) == null;
    }
    
    /**
     * Check if an element is orphan or not.
     * An element is orphan when his parent is null, or if it is null one of his ancestors
     */
    public static JRElementGroup getTopElementGroup(JRDesignElement element) {
        
        JRElementGroup g1 = element.getElementGroup();
        while (g1 != null)
        {
            if (g1 instanceof JRDesignBand || g1 instanceof JRDesignCellContents) return g1;
            g1 = g1.getElementGroup();
        }
        return null;
    }

    
    /**
     * Return the band at the specified point.
     * In the point is not inside a band, it returns null;
     **/
    public static JRDesignBand getBandAt(JasperDesign jd, Point p)
    {
        if (p.x < jd.getLeftMargin()) return null;
        if (p.x > jd.getPageWidth() - jd.getRightMargin()) return null;
        if (p.y < jd.getTopMargin()) return null;
         
        List<JRBand> bands = ModelUtils.getBands(jd);
            
        int currentHeight = jd.getTopMargin();
        for (JRBand tmpBand : bands)
        {
            currentHeight += tmpBand.getHeight();
            if (p.y < currentHeight) return (JRDesignBand)tmpBand;
        }
        
        return null;
    }
    
    public static JRDesignDataset getDatasetFromChartDataset(JRDesignChartDataset dataset, JasperDesign jd)
    {
        JRDesignDataset ds = (JRDesignDataset)jd.getMainDataset();
        if (dataset.getDatasetRun() != null &&
            dataset.getDatasetRun().getDatasetName() != null)
        {
            ds = (JRDesignDataset)jd.getDatasetMap().get( dataset.getDatasetRun().getDatasetName() );
        }     
        
        return ds;
    }
    
    public static JRDesignDataset getDatasetFromCrosstabDataset(JRDesignCrosstabDataset dataset, JasperDesign jd)
    {
        JRDesignDataset ds = (JRDesignDataset)jd.getMainDataset();
        if (dataset.getDatasetRun() != null &&
            dataset.getDatasetRun().getDatasetName() != null)
        {
            ds = (JRDesignDataset)jd.getDatasetMap().get( dataset.getDatasetRun().getDatasetName() );
        }     
        
        return ds;
    }

    public static void copyHyperlink(JRHyperlink from, JRHyperlink to)
    {
        if (from == null || to == null) return;
        try {
            
            setHyperlinkAttribute(to, "HyperlinkAnchorExpression", JRExpression.class, (from.getHyperlinkAnchorExpression() == null) ? null : from.getHyperlinkAnchorExpression().clone() );
            setHyperlinkAttribute(to, "HyperlinkPageExpression", JRExpression.class, (from.getHyperlinkPageExpression() == null) ? null : from.getHyperlinkPageExpression().clone() );
            setHyperlinkAttribute(to, "HyperlinkReferenceExpression", JRExpression.class, (from.getHyperlinkReferenceExpression() == null) ? null : from.getHyperlinkReferenceExpression().clone() );
            setHyperlinkAttribute(to, "HyperlinkTarget", Byte.TYPE, from.getHyperlinkTarget() );
            setHyperlinkAttribute(to, "HyperlinkTooltipExpression", JRExpression.class, (from.getHyperlinkTooltipExpression() == null) ? null : from.getHyperlinkTooltipExpression().clone() );
            setHyperlinkAttribute(to, "LinkType", String.class, (from.getLinkType() == null) ? null : from.getLinkType() );

            // remove all the old params...
            JRHyperlinkParameter[] params = from.getHyperlinkParameters();
            List parameters = getHyperlinkParametersList(to);
            parameters.clear();

            for (int i=0; i<params.length; ++i)
            {
                parameters.add( params[i].clone() );
            }
            
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    /**
     * This methos looks if this node is in some way a Nephew or a Child or the same Crosstab node.
     * It does it first looking in the node lookup, then going trough the parent chain
     * 
     * @param node
     * @param designCrosstab
     * @return
     */
    static boolean isNephewOf(Node node, JRDesignCrosstab designCrosstab)
    {
        if (node == null || designCrosstab == null) return false;
        if (node.getLookup().lookup(JRDesignCrosstab.class) == designCrosstab) return true;
        
        if (node.getParentNode() != null)
        {
            return isNephewOf(node.getParentNode(),designCrosstab);
        }
        
        return false;
    }
    
    /**
     * We assume the  JRHyperlink has always a way to get the parameters a list...
     * @param hl
     * @return the list of parameters
     */
    private static List getHyperlinkParametersList(JRHyperlink hyperlink)
    {
        if (hyperlink == null) return null;
        try {
            Method m = hyperlink.getClass().getMethod("getHyperlinkParametersList");
            return (List)m.invoke(hyperlink);
        }
        catch (Throwable t) {
            t.printStackTrace(); 
        }
        return null;
    }
    
    /**
     * We assume the  JRHyperlink has always a way to get the parameters a list...
     * @param hl
     * @return the list of parameters
     */
    private static void setHyperlinkAttribute(JRHyperlink hyperlink, String attribute, Class clazz, Object value)
    {
        if (hyperlink == null) return;
        try {
            Method m = hyperlink.getClass().getMethod("set" + attribute, clazz);
            m.invoke(hyperlink, value);
            IReportManager.getInstance().notifyReportChange();
        }
        catch (Throwable t) { t.printStackTrace(); }
    }
    
    
    public static void applyBoxProperties(JRLineBox to, JRLineBox from)
    {
        // Paddings...
        to.setPadding(from.getOwnPadding());
        to.setLeftPadding( from.getOwnLeftPadding() );
        to.setRightPadding( from.getOwnRightPadding() );
        to.setBottomPadding( from.getOwnBottomPadding() );
        to.setTopPadding( from.getOwnTopPadding() );
        
        // Pens...
        applyPenProperties( to.getPen(), from.getPen());
        applyPenProperties( to.getTopPen(), from.getTopPen());
        applyPenProperties( to.getLeftPen(), from.getLeftPen());
        applyPenProperties( to.getBottomPen(), from.getBottomPen());
        applyPenProperties( to.getRightPen(), from.getRightPen());
        
    }
    
    public static void applyPenProperties(JRPen to, JRPen from)
    {
        // Paddings...
        to.setLineColor( from.getOwnLineColor() );
        to.setLineWidth( from.getOwnLineWidth() );
        to.setLineStyle( from.getOwnLineStyle() );
     }

     public static void applyDiff(JRLineBox main, JRLineBox box) {
        
         if (main.getOwnPadding() != box.getOwnPadding()) main.setPadding(null);
         if (main.getOwnLeftPadding() != box.getOwnLeftPadding()) main.setLeftPadding(null);
         if (main.getOwnRightPadding() != box.getOwnRightPadding()) main.setRightPadding(null);
         if (main.getOwnBottomPadding() != box.getOwnBottomPadding()) main.setBottomPadding(null);
         if (main.getOwnTopPadding() != box.getOwnTopPadding()) main.setTopPadding(null);
         
         applyDiff( main.getPen(), box.getPen());
         applyDiff( main.getTopPen(), box.getTopPen());
         applyDiff( main.getLeftPen(), box.getLeftPen());
         applyDiff( main.getBottomPen(), box.getBottomPen());
         applyDiff( main.getRightPen(), box.getRightPen());
         
     }
     
     public static void applyDiff(JRPen main, JRPen from)
     {
        // Paddings...
        if (main.getOwnLineColor() != null && !main.getOwnLineColor().equals(from.getOwnLineColor())) main.setLineColor(null);
        if (main.getOwnLineWidth() != null && !main.getOwnLineWidth().equals(from.getOwnLineWidth())) main.setLineWidth(null);
        if (main.getOwnLineStyle() != null && !main.getOwnLineStyle().equals(from.getOwnLineStyle())) main.setLineStyle(null);
     }
}
