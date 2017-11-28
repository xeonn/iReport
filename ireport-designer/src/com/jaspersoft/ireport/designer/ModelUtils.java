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
import java.awt.Point;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementDataset;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author gtoffoli
 */
public class ModelUtils {

    public static JRDesignStyle cloneStyle(JRDesignStyle style) {
        JRDesignStyle newStyle = new JRDesignStyle();
        // TODO: duplicate the style for real...
        newStyle.setName( style.getName());
        return newStyle;
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
                case JROrigin.BACKGROUND: return "background";
                case JROrigin.TITLE: return "title";
                case JROrigin.PAGE_HEADER: return "pageHeader";
                case JROrigin.COLUMN_HEADER: return "columnHeader";
                case JROrigin.DETAIL: return "detail";
                case JROrigin.COLUMN_FOOTER: return "columnFooter";
                case JROrigin.PAGE_FOOTER: return "pageFooter";
                case JROrigin.LAST_PAGE_FOOTER: return "lastPageFooter";
                case JROrigin.SUMMARY: return "summary";
                case JROrigin.NO_DATA: return "noData";
                case JROrigin.GROUP_HEADER:
                    return origin.getGroupName() + "GroupHeader";
                case JROrigin.GROUP_FOOTER:
                    return origin.getGroupName() + "GroupFooter";
            }
        }
        
        return "unknow";
        
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
            else if (grp instanceof JRCellContents)    // Element placed in a cell
            {
                // TODO: calculate cell position....
                JRCellContents cell = (JRCellContents)grp;
                base.x = 0;
                base.y = 0;
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
     * Check is an element is orphan or not.
     * An element is orphan when his parent is null, or if is null one of his ancestor parents
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
       
            JRHyperlinkParameter[] params = from.getHyperlinkParameters();
            List parameters = getHyperlinkParametersList(to);
            for (int i=0; i<params.length; ++i)
            {
                parameters.add( params[0].clone() );
            }
            
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
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
}
