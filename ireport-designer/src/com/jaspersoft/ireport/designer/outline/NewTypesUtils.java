/*
 * NewTypesUtils.java
 * 
 * Created on Sep 19, 2007, 10:54:35 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.outline;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.ModelUtils;
import com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldAction;
import com.jaspersoft.ireport.designer.undo.AddStyleUndoableEdit;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabMeasure;
import net.sf.jasperreports.crosstabs.JRCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.base.JRBaseCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabBucket;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabCell;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabParameter;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.Pair;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.NewType;

/**
 *
 * @author gtoffoli
 */
public class NewTypesUtils {

    public static final int PARAMETER = 1;
    public static final int FIELD = 2;
    public static final int VARIABLE = 3;
    public static final int STYLE = 4;
    public static final int CROSSTAB_PARAMETER = 5;
    public static final int CROSSTAB_MEASURE = 6;
    public static final int CROSSTAB_ROW_GROUP = 7;
    public static final int CROSSTAB_COLUMN_GROUP = 8;
    public static final int CONDITIONAL_STYLE = 9;
    
    
    private static final NewType[] NO_NEW_TYPES = {  };
    
    public static NewType[] getNewType(Node node, int... types)
    {
        
        Arrays.sort(types);
        
        List<NewType> newTypes = new ArrayList<NewType>();
 
        if ( Arrays.binarySearch( types, PARAMETER) >= 0)
        {
            newTypes.add(new NewObjectType(PARAMETER, node));
        }
        if ( Arrays.binarySearch( types, FIELD) >= 0)
        {
            newTypes.add(new NewObjectType(FIELD, node));
        }
        if ( Arrays.binarySearch( types, VARIABLE) >= 0)
        {
            newTypes.add(new NewObjectType(VARIABLE, node));
        }
        if ( Arrays.binarySearch( types, STYLE) >= 0)
        {
            newTypes.add(new NewObjectType(STYLE, node));
        }
        if ( Arrays.binarySearch( types, CROSSTAB_PARAMETER) >= 0)
        {
            newTypes.add(new NewObjectType(CROSSTAB_PARAMETER, node));
        }
        if ( Arrays.binarySearch( types, CROSSTAB_MEASURE) >= 0)
        {
            newTypes.add(new NewObjectType(CROSSTAB_MEASURE, node));
        }
        if ( Arrays.binarySearch( types, CROSSTAB_ROW_GROUP) >= 0)
        {
            newTypes.add(new NewObjectType(CROSSTAB_ROW_GROUP, node));
        }
        if ( Arrays.binarySearch( types, CROSSTAB_COLUMN_GROUP) >= 0)
        {
            newTypes.add(new NewObjectType(CROSSTAB_COLUMN_GROUP, node));
        }
        if ( Arrays.binarySearch( types, CONDITIONAL_STYLE) >= 0)
        {
            newTypes.add(new NewObjectType(CONDITIONAL_STYLE, node));
        }
        

        return newTypes.toArray(new NewType[newTypes.size()]);
    }
}

class NewObjectType extends NewType {

    Node parentNode = null;  
    int type = -1;
    public NewObjectType(int type, Node parentNode)
    {
        this.parentNode = parentNode;
        this.type = type;
    }
    
    public void create() throws IOException {
        JasperDesign jd = parentNode.getLookup().lookup(JasperDesign.class);
        if (jd == null) return;

        JRDesignCrosstab crosstab = null;
        
        JRDesignDataset dataset = parentNode.getLookup().lookup(JRDesignDataset.class);
        if (dataset == null)
        {
            crosstab = parentNode.getLookup().lookup(JRDesignCrosstab.class);
            if (crosstab != null) dataset = ModelUtils.getElementDataset(crosstab, jd);
        }    
            
        if (dataset == null) dataset = jd.getMainDesignDataset();
        if (dataset == null) return;
        
        Object obj = null;
        
        switch (type)
        {
            case NewTypesUtils.PARAMETER:
            {
                try {
                    JRDesignParameter p = new JRDesignParameter();
                    String baseName = "parameter";
                    String new_name = baseName;

                    List list = dataset.getParametersList();
                    boolean found = true;
                    for (int j = 1; found; j++) {
                        found = false;
                        new_name = baseName + j;
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignParameter tmpP = (JRDesignParameter) list.get(i);
                            if (tmpP.getName().equals(new_name)) {
                                found = true;
                            }
                        }
                    }

                    p.setName(new_name);
                    obj = p;
                    dataset.addParameter(p);
                    
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            }
            case NewTypesUtils.FIELD:
            {
                try {
                    JRDesignField f = new JRDesignField();
                    String baseName = "field";
                    String new_name = baseName;

                    List list = dataset.getFieldsList();
                    boolean found = true;
                    for (int j = 1; found; j++) {
                        found = false;
                        new_name = baseName + j;
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignField tmpP = (JRDesignField) list.get(i);
                            if (tmpP.getName().equals(new_name)) {
                                found = true;
                            }
                        }
                    }
                    f.setName(new_name);
                    obj = f;
                    dataset.addField(f);
                    
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            } 
            case NewTypesUtils.VARIABLE:
            {
                try {
                    JRDesignVariable v = new JRDesignVariable();
                    String baseName = "variable";
                    String new_name = baseName;

                    List list = dataset.getVariablesList();
                    boolean found = true;
                    for (int j = 1; found; j++) {
                        found = false;
                        new_name = baseName + j;
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignVariable tmpP = (JRDesignVariable) list.get(i);
                            if (tmpP.getName().equals(new_name)) {
                                found = true;
                            }
                        }
                    }
                    v.setName(new_name);
                    obj = v;
                    dataset.addVariable(v);
                
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            } 
            case NewTypesUtils.STYLE:
            {
                try {
                    JRDesignStyle v = new JRDesignStyle();
                    String baseName = "style";
                    String new_name = baseName;

                    List list = jd.getStylesList();
                    boolean found = true;
                    for (int j = 1; found; j++) {
                        found = false;
                        new_name = baseName + j;
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignStyle tmpP = (JRDesignStyle) list.get(i);
                            if (tmpP.getName().equals(new_name)) {
                                found = true;
                            }
                        }
                    }
                    v.setName(new_name);
                    obj = v;
                    jd.addStyle(v);
                    
                    AddStyleUndoableEdit undo = new AddStyleUndoableEdit(v, jd); //newIndex
                    IReportManager.getInstance().addUndoableEdit(undo);
                    
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            }
            case NewTypesUtils.CONDITIONAL_STYLE:
            {
                JRDesignStyle parentStyle = parentNode.getLookup().lookup(JRDesignStyle.class);
                if (parentStyle == null)
                {
                    return;
                }    
               
                JRDesignConditionalStyle v = new JRDesignConditionalStyle();
                parentStyle.addConditionalStyle(v);

                //AddconditionalStyleUndoableEdit undo = new AddconditionalStyleUndoableEdit(v, jd); //newIndex
                //IReportManager.getInstance().addUndoableEdit(undo);

                
                break;
            } 
            case NewTypesUtils.CROSSTAB_PARAMETER:
            {
                if (crosstab == null) return;
                try {
                    JRDesignCrosstabParameter p = new JRDesignCrosstabParameter();
                    String baseName = "parameter";
                    String new_name = baseName;

                    List list = crosstab.getParametersList();
                    boolean found = true;
                    for (int j = 1; found; j++) {
                        found = false;
                        new_name = baseName + j;
                        for (int i = 0; i < list.size(); ++i) {
                            JRDesignParameter tmpP = (JRDesignParameter) list.get(i);
                            if (tmpP.getName().equals(new_name)) {
                                found = true;
                            }
                        }
                    }

                    p.setName(new_name);
                    obj = p;
                    crosstab.addParameter(p);
                    
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            }
            case NewTypesUtils.CROSSTAB_MEASURE:
            {
                if (crosstab == null) return;
                try {
                    JRDesignCrosstabMeasure p = new JRDesignCrosstabMeasure();
                    String baseName = "measure";
                    String new_name = baseName;

                    for (int j = 1; ; j++) {
                        new_name = baseName + j;
                        if (ModelUtils.isValidNewCrosstabObjectName(crosstab, new_name))
                        {
                            break;
                        }
                    }

                    
                    p.setName(new_name);
                    
                    JRDesignExpression exp = Misc.createExpression("java.lang.Integer", "\"\"");
                    p.setValueExpression(exp);
                    p.setValueClassName("java.lang.Integer");
                    obj = p;
                    crosstab.addMeasure(p);
                    
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            }
            case NewTypesUtils.CROSSTAB_ROW_GROUP:
            {
                if (crosstab == null) return;
                try {
                    JRDesignCrosstabRowGroup group = new JRDesignCrosstabRowGroup();
                    String baseName = "group";
                    String new_name = baseName;

                    for (int j = 1; ; j++) {
                        new_name = baseName + j;
                        if (ModelUtils.isValidNewCrosstabObjectName(crosstab, new_name))
                        {
                            break;
                        }
                    }

                    
                    group.setName(new_name);
                    group.setWidth(100);
                    
                    JRDesignExpression exp = Misc.createExpression("java.lang.String", "");
                    JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();
                    bucket.setExpression(exp);
                    group.setBucket(bucket);
                    JRDesignCellContents headerCell = new JRDesignCellContents();
                    group.setHeader(headerCell);
                    // the width is the with of the current base cell...
                    JRDesignCrosstabCell baseCell = (JRDesignCrosstabCell)crosstab.getCellsMap().get(new Pair(null,null));
                    int baseHeight = (baseCell.getHeight() != null) ? baseCell.getHeight() : ( (baseCell.getContents() != null) ? baseCell.getContents().getHeight() : 30);
                    
                    headerCell.addElement( 
                            createField(jd, Misc.createExpression( "java.lang.String", "$V{" + new_name + "}"), 100, baseHeight, "Crosstab Data Text"));
                
                    group.setTotalHeader(new JRDesignCellContents());
                    
                    obj = group;
                    
                    
                    crosstab.addRowGroup(group);
                    
                    // I need to add the extra cells...
                    JRCrosstabColumnGroup[] columns = crosstab.getColumnGroups();
                    JRDesignCrosstabCell dT =  new JRDesignCrosstabCell();
                    dT.setRowTotalGroup(new_name);
                    crosstab.addCell(dT);
                    // for each column, we need to add the total...
                    for (int i=0; i<columns.length; ++i)
                    {
                        JRDesignCrosstabCell cell =  new JRDesignCrosstabCell();
                        cell.setRowTotalGroup(new_name);
                        cell.setColumnTotalGroup(columns[i].getName());
                        crosstab.addCell(cell);
                        
                        // Add some cells...
                        
                    }
                   
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            }
            case NewTypesUtils.CROSSTAB_COLUMN_GROUP:
            {
                if (crosstab == null) return;
                try {
                    JRDesignCrosstabColumnGroup group = new JRDesignCrosstabColumnGroup();
                    String baseName = "group";
                    String new_name = baseName;

                    for (int j = 1; ; j++) {
                        
                        new_name = baseName + j;
                        
                        if (ModelUtils.isValidNewCrosstabObjectName(crosstab, new_name))
                        {
                            break;
                        }
                    }

                    
                    group.setName(new_name);
                    group.setHeight(30);
                    
                    JRDesignExpression exp = Misc.createExpression("java.lang.String", "");
                    JRDesignCrosstabBucket bucket = new JRDesignCrosstabBucket();
                    bucket.setExpression(exp);
                    group.setBucket(bucket);
                    JRDesignCellContents headerCell = new JRDesignCellContents();
                    group.setHeader(headerCell);
                    // the width is the with of the current base cell...
                    JRDesignCrosstabCell baseCell = (JRDesignCrosstabCell)crosstab.getCellsMap().get(new Pair(null,null));
                    int baseWidth = (baseCell.getWidth() != null) ? baseCell.getWidth() : ( (baseCell.getContents() != null) ? baseCell.getContents().getWidth() : 50);
                    
                    headerCell.addElement( 
                            createField(jd, Misc.createExpression( "java.lang.String", "$V{" + new_name + "}"), baseWidth, 30, "Crosstab Data Text"));
                
                    group.setTotalHeader(new JRDesignCellContents());
                    
                    obj = group;
                    
                    crosstab.addColumnGroup(group);
                    
                    // I need to add the extra cells...
                    JRCrosstabRowGroup[] rows = crosstab.getRowGroups();
                    JRDesignCrosstabCell dT =  new JRDesignCrosstabCell();
                    dT.setColumnTotalGroup(new_name);
                    crosstab.addCell(dT);
                    // for each column, we need to add the total...
                    for (int i=0; i<rows.length; ++i)
                    {
                        JRDesignCrosstabCell cell =  new JRDesignCrosstabCell();
                        cell.setColumnTotalGroup(new_name);
                        cell.setRowTotalGroup(rows[i].getName());
                        crosstab.addCell(cell);
                    }
                    
                    //TODO there are not totals by default? We should ask for...
                    
                    
                    // at this point the bucket is null...
                    
                    
                    
                    
                    
                    
                } catch (JRException ex) {
                    Exceptions.printStackTrace(ex);
                }
                break;
            }
        }
        
        if (obj != null)
        {
            IReportManager.getInstance().setSelectedObject(obj);
        }
    }

    @Override
    public String getName() {
        switch (type)
        {
            case NewTypesUtils.PARAMETER: return "Parameter";
            case NewTypesUtils.FIELD: return "Field";
            case NewTypesUtils.VARIABLE: return "Variable";
            case NewTypesUtils.STYLE: return "Style";
            case NewTypesUtils.CONDITIONAL_STYLE: return "Conditional Style";
            case NewTypesUtils.CROSSTAB_PARAMETER: return "Crosstab Parameter";
            case NewTypesUtils.CROSSTAB_MEASURE: return "Measure";
            case NewTypesUtils.CROSSTAB_ROW_GROUP: return "Row Group";
            case NewTypesUtils.CROSSTAB_COLUMN_GROUP: return "Column Group";
        }
        return super.getName();
    }
    
    
    
    
    
    private JRDesignStaticText createLabel(JasperDesign jd, String text, int w, int h, String styleName)
    {
        JRDesignStaticText element = new JRDesignStaticText();
        element.setX(0);
        element.setY(0);
        element.setWidth(w);
        element.setHeight(h);
        element.setText(text);
        element.setHorizontalAlignment( JRAlignment.HORIZONTAL_ALIGN_CENTER );
        element.setVerticalAlignment( JRAlignment.VERTICAL_ALIGN_MIDDLE);
        
        if (styleName != null && jd.getStylesMap().containsKey(styleName))
        {
            element.setStyle( (JRStyle) jd.getStylesMap().get(styleName) );
        }
        return element;
    }
    
    private JRDesignTextField createField(JasperDesign jd, JRDesignExpression exp, int w, int h, String styleName)
    {
        JRDesignTextField element = new JRDesignTextField();
        element.setX(0);
        element.setY(0);
        element.setWidth(w);
        element.setHeight(h);
        
        if (styleName != null && jd.getStylesMap().containsKey(styleName))
        {
            element.setStyle( (JRStyle) jd.getStylesMap().get(styleName) );
        }
        
        try {
            CreateTextFieldAction.setMatchingClassExpression(exp,exp.getValueClassName(), true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        element.setExpression(exp);
        return element;
    }
    
    
}


