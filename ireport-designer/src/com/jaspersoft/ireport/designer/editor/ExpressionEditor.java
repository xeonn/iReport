/*
 * ExpressionEditorPanel.java
 *
 * Created on 21 settembre 2007, 9.18
 */

package com.jaspersoft.ireport.designer.editor;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.EditorKit;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabMeasure;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabParameter;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabRowGroup;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import org.openide.text.CloneableEditorSupport;

/**
 *
 * @author  gtoffoli
 */
public class ExpressionEditor extends javax.swing.JPanel {
    
    public static final String USER_DEFINED_EXPRESSIONS = "USER_DEFINED_EXPRESSIONS";
    public static final String PARAMETERS = "PARAMETERS";
    public static final String FIELDS = "FIELDS";
    public static final String VARIABLES = "VARIABLES";
    public static final String RECENT_EXPRESSIONS = "RECENT_EXPRESSIONS";
    public static final String WIZARDS = "WIZARDS";
    
    private ExpressionContext expressionContext = null;
    private JDialog dialog = null;
    private int dialogResult = JOptionPane.CANCEL_OPTION;
    
    
    
    public String getExpression() {
        return jEditorPane1.getText();
    }

    public void setExpression(String expression) {
        jEditorPane1.setText(expression);
    }

    public static ArrayList<String> getPredefinedExpressions() {


        ArrayList<String> exps = new ArrayList<String>();
        Preferences pref = IReportManager.getPreferences();
        if (pref.getBoolean("custom_expressions_set", false))
        {
            for (int i=0; pref.get("customexpression."+i, null) != null; ++i)
            {
                exps.add(pref.get("customexpression."+i, ""));
            }
        }
        else
        {
            exps.addAll(getDefaultPredefinedExpressions());
        }
        return exps;
    }

    public static ArrayList<String> getDefaultPredefinedExpressions() {
        return defaultExpressions;
    }

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public void setExpressionContext(ExpressionContext expressionContext) {
        this.expressionContext = expressionContext;
        refreshContext();
    }
    
    private static java.util.ArrayList<String> recentExpressions = new java.util.ArrayList<String>();
    private static java.util.ArrayList<String> defaultExpressions = null;
    
    static {
        defaultExpressions = new java.util.ArrayList<String>();
        defaultExpressions.add("( <condition> ? exp1 : exp2 )");
        defaultExpressions.add("msg(<pattern>, <arg0>)");
        defaultExpressions.add("msg(<pattern>, <arg0>, <arg1>)");
        defaultExpressions.add("msg(<pattern>, <arg0>, <arg1>, <arg2>)");
        defaultExpressions.add("str(<key>)");
        defaultExpressions.add("((net.sf.jasperreports.engine.data.JRXmlDataSource)$P{REPORT_DATA_SOURCE}).subDataSource(<select expression>)");
        defaultExpressions.add("((net.sf.jasperreports.engine.data.JRXmlDataSource)$P{REPORT_DATA_SOURCE}).dataSource(<select expression>)");
    }
    
    /** Creates new form ExpressionEditorPanel */
    public ExpressionEditor() {
        initComponents();
        
        jButtonApply.setVisible(false);
        jButtonCancel.setVisible(false);
        
        jList1.setCellRenderer(new NamedIconItemCellRenderer());
        jList2.setModel(new DefaultListModel());
        jList3.setModel(new DefaultListModel());
        jList1.setModel(new DefaultListModel());
        
        jList2.setCellRenderer(new ExpObjectCellRenderer(jList2));
        jList3.setCellRenderer(new MethodsListCellRenderer((jList3)));
        
        EditorKit kit = CloneableEditorSupport.getEditorKit("text/jrxml-expression");
        jEditorPane1.setEditorKit(kit);
        
        jEditorPane1.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                ExpressionContext.setGlobalContext(getExpressionContext());
                ExpressionContext.activeEditor = jEditorPane1;
            }

            public void focusLost(FocusEvent e) {
            }
        });
        
        refreshContext();
    }
    
    /**
     *  Refresh the content of the expression editor based on the current
     *  ExpressionContext.
     */
    public void refreshContext()
    {
        jList2.removeAll();
        jList3.removeAll();
        jList1.removeAll();
        
        DefaultListModel dlm1 = (DefaultListModel)jList1.getModel();
        dlm1.removeAllElements();
        
        if (getExpressionContext() != null)
        {
            // Aggregate the datasets....
            if (getExpressionContext().getDatasets().size() > 0)
            {
                JRDesignDataset ds = getExpressionContext().getDatasets().get(0);
                dlm1.addElement(new NamedIconItem(PARAMETERS, I18n.getString("ExpressionEditor.IconName.Parameters"), NamedIconItem.ICON_FOLDER_PARAMETERS) );
                dlm1.addElement( new NamedIconItem(FIELDS, I18n.getString("ExpressionEditor.IconName.Fields"), NamedIconItem.ICON_FOLDER_FIELDS) );
                dlm1.addElement( new NamedIconItem(VARIABLES, I18n.getString("ExpressionEditor.IconName.Variables"), NamedIconItem.ICON_FOLDER_VARIABLES) );
            }
            
            int i = 0;
            for (JRDesignCrosstab crosstab : getExpressionContext().getCrosstabs())
            {
                i++;
                String key = crosstab.getKey();
                if (key == null) key = "";
                
                dlm1.addElement(new NamedIconItem(crosstab, I18n.getString("ExpressionEditor.IconName.Crosstab") + i + ") " + key, NamedIconItem.ICON_CROSSTAB) );
            }
        }
        
        dlm1.addElement(new NamedIconItem(USER_DEFINED_EXPRESSIONS, I18n.getString("ExpressionEditor.IconName.UserDefinedExpr"), NamedIconItem.ICON_FOLDER_FORMULAS ) );
        dlm1.addElement( new NamedIconItem(RECENT_EXPRESSIONS, I18n.getString("ExpressionEditor.IconName.RecentExpr"), NamedIconItem.ICON_FOLDER_RECENT_EXPRESSIONS ) );
        dlm1.addElement( new NamedIconItem(WIZARDS, I18n.getString("ExpressionEditor.IconName.ExprWiz"), NamedIconItem.ICON_FOLDER_WIZARDS ) );
        
        jList1.updateUI();
        if (dlm1.size() > 0)
        {
            jList1.setSelectedIndex(0);
        }
        
        
    }
    
    
    
    public String getPrintableTypeName( String type )
    {
            if (type == null) return "void";

            if (type.endsWith(";")) type = type.substring(0,type.length()-1);
    
            while (type.startsWith("["))
            {
                type = type.substring(1) + "[]";
                if (type.startsWith("[")) continue;
                if (type.startsWith("L")) type = type.substring(1);
                if (type.startsWith("Z")) type = "boolean" + type.substring(1);
                if (type.startsWith("B")) type = "byte" + type.substring(1);
                if (type.startsWith("C")) type = "char" + type.substring(1);
                if (type.startsWith("D")) type = "double" + type.substring(1);
                if (type.startsWith("F")) type = "float" + type.substring(1);
                if (type.startsWith("I")) type = "int" + type.substring(1);
                if (type.startsWith("J")) type = "long" + type.substring(1);
                if (type.startsWith("S")) type = "short" + type.substring(1);
            }
            
            if (type.startsWith("java.lang."))
            {
                type = type.substring("java.lang.".length());
                if (type.indexOf(".") > 0)
                {
                    type = "java.lang." + type;
                }
            }
            return type;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButtonImport = new javax.swing.JButton();
        jButtonExport = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonApply = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(550, 450));

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.8);

        jScrollPane1.setViewportView(jEditorPane1);

        jSplitPane1.setTopComponent(jScrollPane1);

        jSplitPane2.setBorder(null);
        jSplitPane2.setResizeWeight(0.3);

        jSplitPane3.setBorder(null);
        jSplitPane3.setResizeWeight(0.5);

        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jSplitPane3.setLeftComponent(jScrollPane3);

        jList3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jList3);

        jSplitPane3.setRightComponent(jScrollPane4);

        jSplitPane2.setRightComponent(jSplitPane3);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jSplitPane2.setLeftComponent(jScrollPane2);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel1);

        jButtonImport.setText(org.openide.util.NbBundle.getMessage(ExpressionEditor.class, "ExpressionEditor.jButtonImport.text")); // NOI18N
        jButtonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportActionPerformed(evt);
            }
        });

        jButtonExport.setText(org.openide.util.NbBundle.getMessage(ExpressionEditor.class, "ExpressionEditor.jButtonExport.text")); // NOI18N
        jButtonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportActionPerformed(evt);
            }
        });

        jButtonCancel.setText(org.openide.util.NbBundle.getMessage(ExpressionEditor.class, "ExpressionEditor.jButtonCancel.text")); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonApply.setText(org.openide.util.NbBundle.getMessage(ExpressionEditor.class, "ExpressionEditor.jButtonApply.text")); // NOI18N
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jSplitPane1)
                    .add(layout.createSequentialGroup()
                        .add(jButtonImport)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonExport)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 240, Short.MAX_VALUE)
                        .add(jButtonApply)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .add(6, 6, 6)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonImport)
                    .add(jButtonExport)
                    .add(jButtonCancel)
                    .add(jButtonApply))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

        DefaultListModel dlm2 = (DefaultListModel)jList2.getModel();
        DefaultListModel dlm3 = (DefaultListModel)jList3.getModel();
        
        dlm2.removeAllElements();
        dlm3.removeAllElements();
        
        if (jList1.getSelectedValue() != null)
        {
            NamedIconItem item = (NamedIconItem)jList1.getSelectedValue();
            if (item.getItem().equals( USER_DEFINED_EXPRESSIONS))
            {
                ArrayList<String> exps = getPredefinedExpressions();
                for (String s : exps) dlm2.addElement(s);
            }
            else if (item.getItem().equals( RECENT_EXPRESSIONS))
            {
                for (String s : recentExpressions) dlm2.addElement(s);
            }
            else if (item.getItem().equals( PARAMETERS))
            {
                JRDesignDataset ds = getExpressionContext().getDatasets().get(0);
                Iterator parameters = ds.getParametersList().iterator();
                while (parameters.hasNext())
                {
                    dlm2.addElement(new ExpObject(parameters.next()));
                }
            }
            else if (item.getItem().equals( FIELDS))
            {
                JRDesignDataset ds = getExpressionContext().getDatasets().get(0);
                Iterator fields = ds.getFieldsList().iterator();
                while (fields.hasNext())
                {
                    dlm2.addElement(new ExpObject(fields.next()));
                }
            }
            else if (item.getItem().equals( VARIABLES))
            {
                JRDesignDataset ds = getExpressionContext().getDatasets().get(0);
                Iterator variables = ds.getVariablesList().iterator();
                while (variables.hasNext())
                {
                    dlm2.addElement(new ExpObject(variables.next()));
                }
            }
            else if (item.getItem() instanceof JRDesignCrosstab)
            {
                JRDesignCrosstab crosstab = (JRDesignCrosstab)item.getItem();
                List rowGroups = crosstab.getRowGroupsList();
                List columnGroups = crosstab.getColumnGroupsList();
                
                Iterator measures = crosstab.getMesuresList().iterator();
                while (measures.hasNext())
                {
                    JRDesignCrosstabMeasure measure = (JRDesignCrosstabMeasure)measures.next();
                    dlm2.addElement(new ExpObject(measure.getVariable()));
                    
                    for (int i=0; i<rowGroups.size(); ++i)
                    {
                        JRDesignCrosstabRowGroup rowGroup = (JRDesignCrosstabRowGroup)rowGroups.get(i);
                        dlm2.addElement(new CrosstabTotalVariable(measure, rowGroup, null));
                        
                        
                        for (int j=0; j<columnGroups.size(); ++j)
                        {
                            JRDesignCrosstabColumnGroup columnGroup = (JRDesignCrosstabColumnGroup)columnGroups.get(j);
                            if (j==0)
                            {
                                dlm2.addElement(new CrosstabTotalVariable(measure, null, columnGroup));
                            }
                            
                            dlm2.addElement(new CrosstabTotalVariable(measure, rowGroup, columnGroup));
                        }
                    }
                }
                
                for (int i=0; i<rowGroups.size(); ++i)
                {
                    JRDesignCrosstabRowGroup rowGroup = (JRDesignCrosstabRowGroup)rowGroups.get(i);
                    dlm2.addElement(new ExpObject(rowGroup.getVariable()));
                }
                
                for (int i=0; i<columnGroups.size(); ++i)
                {
                    JRDesignCrosstabColumnGroup columnGroup = (JRDesignCrosstabColumnGroup)columnGroups.get(i);
                    dlm2.addElement(new ExpObject(columnGroup.getVariable()));
                }
                
                List crosstabParameters = crosstab.getParametersList();
                for (int i=0; i<crosstabParameters.size(); ++i)
                {
                    JRDesignCrosstabParameter parameter = (JRDesignCrosstabParameter)crosstabParameters.get(i);
                    dlm2.addElement(new ExpObject(parameter));
                }
                
            }
            // TODO -> Wizards
            
            
            if (dlm2.size() > 0)
            {
                jList2.setSelectedIndex(0);
            }
            
        }
        
        
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        
        DefaultListModel dlm = (DefaultListModel)jList3.getModel();
        dlm.removeAllElements();
        
        Class clazz = null; //getSelectedObjectClass();
        
        if (jList2.getSelectedValue() instanceof ExpObject)
        {
            try {
                clazz = this.getClass().getClassLoader().loadClass( ((ExpObject)jList2.getSelectedValue()).getClassType());
        
            } catch (Throwable ex)
            {
                
            }
        }
        
        if (clazz != null)
        {
            java.lang.reflect.Method[] methods = clazz.getMethods();
            for (int i=0; i<methods.length; ++i)
            {
                if ((methods[i].getModifiers() & java.lang.reflect.Modifier.PUBLIC) != 0 )
                {
                    String method_firm = methods[i].getName() + "(";
                    Class[] params = methods[i].getParameterTypes();
                    int j=0;
                    for (j=0; j<params.length; ++j)
                    {
                        
                        if (j > 0) method_firm +=", ";
                        else method_firm +=" ";
                        method_firm +=  getPrintableTypeName( params[j].getName() );
                    }
                    if (j>0) method_firm+=" ";
                    method_firm += ") ";

                    String rname = methods[i].getReturnType().getName();
                    if (rname.equals("void")) continue; // we have to return something always!
                    method_firm += getPrintableTypeName( rname);
                    dlm.addElement( method_firm );
                }
            }
        }
        
        
    }//GEN-LAST:event_jList2ValueChanged

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
       
        if (dialog != null && dialog.isVisible()) 
        {
            dialogResult = JOptionPane.CANCEL_OPTION;
            dialog.setVisible(false);
            dialog.dispose();
        }
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jList3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList3MouseClicked
       if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2)
        {
            if (jList2.getSelectedValue() != null && jList3.getSelectedValue() != null)
            {
                try {
                    
                    String objName = "";
                    if (jList2.getSelectedValue() instanceof ExpObject)
                    {
                        objName = ((ExpObject)jList2.getSelectedValue()).getExpression();
                    }
                    else
                    {
                        objName = ""+jList2.getSelectedValue();
                    }
                    
                    String method = (jList3.getSelectedValue()+"");
                    method = method.substring(0, method.lastIndexOf(")")+1);
                    // Remove selected text...
                    jEditorPane1.replaceSelection(objName+"."+method);
            } catch (Exception ex){}
            }
        }
    }//GEN-LAST:event_jList3MouseClicked

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2)
        {
            try {
                    String objName = "";
                    if (jList2.getSelectedValue() instanceof ExpObject)
                    {
                        objName = ((ExpObject)jList2.getSelectedValue()).getExpression();
                    }
                    else
                    {
                        objName = ""+jList2.getSelectedValue();
                    }
                    
                    jEditorPane1.replaceSelection(objName+"");
             } catch (Exception ex){}
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed
        
        
        if (dialog != null && dialog.isVisible()) 
        {
            dialogResult = JOptionPane.OK_OPTION;
            dialog.setVisible(false);
            dialog.dispose();
        }
        
    }//GEN-LAST:event_jButtonApplyActionPerformed

    private void jButtonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportActionPerformed
        String expression = Misc.loadExpression(this);

        if (expression != null) {
            jEditorPane1.setText(expression);
        }
    }//GEN-LAST:event_jButtonImportActionPerformed

    private void jButtonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportActionPerformed
        Misc.saveExpression( jEditorPane1.getText(), this );
    }//GEN-LAST:event_jButtonExportActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonExport;
    private javax.swing.JButton jButtonImport;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Show the dialog to edit the expression.
     * If parent is not null, it is used to find a parent Window.
     */
    public int showDialog(Component parent)
    {
        jButtonApply.setVisible(true);
        jButtonCancel.setVisible(true);
        
        Window pWin = (parent != null) ? SwingUtilities.windowForComponent(parent) : null;
        
        if (pWin instanceof Dialog) dialog = new JDialog((Dialog)pWin);
        else if (pWin instanceof Frame) dialog = new JDialog((Frame)pWin);
        else dialog = new JDialog();
        
        dialog.setModal(true);
        dialog.getContentPane().add(this);
        dialog.pack();
        dialogResult = JOptionPane.CANCEL_OPTION;
        dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle(I18n.getString("ExpressionEditor.Title.ExpressionEditor"));
        dialog.setVisible(true);
        return dialogResult;
    }
 
    @Override
    public void addNotify () {
        super.addNotify();
        //force focus to the editable area
        if (isEnabled() && isFocusable()) {
            jEditorPane1.requestFocus();
        }
    }    
}

