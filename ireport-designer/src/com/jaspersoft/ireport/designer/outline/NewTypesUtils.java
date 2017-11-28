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
import com.jaspersoft.ireport.designer.undo.AddStyleUndoableEdit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.NewType;

/**
 *
 * @author gtoffoli
 */
public class NewTypesUtils {

    public static final int PARAMETER = 0x1;
    public static final int FIELD = 0x2;
    public static final int VARIABLE = 0x4;
    public static final int STYLE = 0x8;
    
    private static final NewType[] NO_NEW_TYPES = {  };
    
    public static NewType[] getNewType(int types, Node node)
    {
        List<NewType> newTypes = new ArrayList<NewType>();
 
        if ( (types & PARAMETER) != 0)
        {
            newTypes.add(new NewObjectType(PARAMETER, node));
        }
        if ( (types & FIELD) != 0)
        {
            newTypes.add(new NewObjectType(FIELD, node));
        }
        if ( (types & VARIABLE) != 0)
        {
            newTypes.add(new NewObjectType(VARIABLE, node));
        }
        if ( (types & STYLE) != 0)
        {
            newTypes.add(new NewObjectType(STYLE, node));
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

        JRDesignDataset dataset = parentNode.getLookup().lookup(JRDesignDataset.class);
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
        }
        return super.getName();
    }
    
    
    
    
}


