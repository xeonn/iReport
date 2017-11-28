/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

import com.jaspersoft.ireport.designer.utils.Misc;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.TemplateWizard;

/**
 * Default report generator based of the iReport template files
 * having special fields as templates.
 * This report generator assumes the wizard is of type TemplateWizard.
 * If it is not, the filename property is used as destination file name.
 * The generator looks for the following properties:
 * - reportType
 * - query
 * - queryLanguage
 * - reportname
 * - selectedFields
 * - groupFields
 * - filename (optional)
 * 
 * @author gtoffoli
 */
public class DefaultReportGenerator extends AbstractReportGenerator {

    public FileObject generateReport(WizardDescriptor wizard) {
        
        try {
            // 1. Load the selected template...
            JasperDesign jasperDesign = generateDesign(wizard);
            File f = getFile(wizard);
             
            JRXmlWriter.writeReport(jasperDesign, new FileOutputStream(f),"UTF8");
            
            return FileUtil.toFileObject(f);
        
        } catch (Exception ex) {
            ex.printStackTrace();
            Misc.showErrorMessage("An error has occurred generating the report:\n" + ex.getMessage(), "Error", ex);
            return null;
        }
    }
    
    protected File getFile(WizardDescriptor wizard) throws Exception
    {
            // If we used our cool custom file chooser panel, we should find the
            // property filename set.
            File f = null;
            if (wizard.getProperty("filename") != null)
            {
               f = new File( ""+wizard.getProperty("filename"));
               
               if (wizard instanceof TemplateWizard)
               {
                   // Let's set the file folder...
                   ((TemplateWizard)wizard).setTargetFolder(DataFolder.findFolder( FileUtil.toFileObject( f.getParentFile() )) );
                   // Let's set the target folder...
                   ((TemplateWizard)wizard).setTargetName(f.getName());
                }
            }
            
            if (wizard instanceof TemplateWizard)
            {
                if (((TemplateWizard)wizard).getTargetFolder() != null)
                {
                    String fname = ((TemplateWizard)wizard).getTargetName();
                    String directory = ((TemplateWizard) wizard).getTargetFolder().getPrimaryFile().getPath();
                    // We do some strong assumptions here:
                    // 1. the directory exists
                    // 2. we are not replacing another file if it was specified
                    // 3. if specified, the file ends with .jrxml

                    // Default name specified...
                    // let's look for a new valid file name...
                    if (fname == null)
                    {
                        fname = "Report.jrxml";
                        f = new File( directory,fname);
                        int i=1;
                        while (f.exists())
                        {
                            fname = "Report_" + i + ".jrxml";
                            f = new File( directory,fname);
                            i++;
                        }
                    }
                    else
                    {
                        f = new File( directory,fname);
                    }

                    ((TemplateWizard)wizard).setTargetName(fname);
                }
            }
            
            if (f == null)
            {
                throw new Exception("Filename not specified");
            }
            
            return f;
    }
    
    protected JasperDesign generateDesign(WizardDescriptor wizard) throws Exception
    {
        FileObject reportTemplate = (FileObject) wizard.getProperty("reportTemplate");
            String reportType = (String) wizard.getProperty("reportType");
            List<JRDesignField> selectedFields = (List<JRDesignField>) wizard.getProperty("selectedFields");
            List<JRDesignField> groupFields = (List<JRDesignField>) wizard.getProperty("groupFields");
            String query = (String) wizard.getProperty("query");
            String queryLanguage = (String) wizard.getProperty("queryLanguage");
                    
            JasperDesign jasperDesign = JRXmlLoader.load( reportTemplate.getInputStream() );

            jasperDesign.setName(""+wizard.getProperty("reportname"));
            
            if (selectedFields == null) selectedFields = new ArrayList<JRDesignField>();
            if (groupFields == null) groupFields = new ArrayList<JRDesignField>();
            
            // Adding fields
            for (JRDesignField f : selectedFields)
            {
                jasperDesign.addField(f);
            }
            
            // Query...
            if (query != null)
            {
                JRDesignQuery designQuery = new JRDesignQuery();
                designQuery.setText(query);
                if (queryLanguage != null)
                {
                    designQuery.setLanguage(queryLanguage);
                }
                
                jasperDesign.setQuery(designQuery);
            }
            
            // Adjusting groups
            for (int i=0; i<4; ++i)
            {
                String gname = "Group"+(i+1);
                if (jasperDesign.getGroupsMap().containsKey(gname))
                {
                    JRDesignGroup group = (JRDesignGroup)jasperDesign.getGroupsMap().get(gname);
                    if (groupFields.size() > i)
                    {
                        group.setName(groupFields.get(i).getName());
                        group.setExpression(Misc.createExpression("java.lang.Object", "$F{" + groupFields.get(i).getName() + "}"));
                        // find the two elements having as expression: G1Label and G1Field
                        if (group.getGroupHeader() != null)
                        {
                            JRDesignStaticText st = findStaticTextElement(group.getGroupHeader(), "G"+(i+1)+"Label");
                            if (st != null)
                            {
                                st.setText(groupFields.get(i).getName());
                            }
                            
                            JRDesignTextField tf = findTextFieldElement(group.getGroupHeader(), "G"+(i+1)+"Field");
                            if (tf != null)
                            {
                                JRDesignExpression expression = Misc.createExpression( groupFields.get(i).getValueClassName(), "$F{" + groupFields.get(i).getName() + "}");
                                // Fix the class (the Textfield has a limited set of type options...)
                                com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldAction.setMatchingClassExpression(expression, groupFields.get(i).getValueClassName(), true);
                                tf.setExpression(expression);
                            }
                        }
                    }
                    else
                    {
                        jasperDesign.removeGroup(group);
                    }
                }
            }
            
            // Adjusting detail...
            if (reportType != null && reportType.equals("tabular"))
            {
                // Add the labels to the column header..
                JRDesignBand columnHeaderBand = (JRDesignBand)jasperDesign.getColumnHeader();
                JRDesignBand detailBand = (JRDesignBand)jasperDesign.getDetail();
                // Find the label template...
                JRDesignStaticText labelElement = findStaticTextElement(columnHeaderBand, "DetailLabel" );
                JRDesignTextField fieldElement = findTextFieldElement(detailBand, "DetailField" );
                if (labelElement != null) columnHeaderBand.removeElement(labelElement);
                if (fieldElement != null) detailBand.removeElement(fieldElement);
                
                int width = jasperDesign.getPageWidth() - jasperDesign.getRightMargin() - jasperDesign.getLeftMargin();
                int cols = selectedFields.size() - groupFields.size();
                if (cols > 0)
                {
                     width /= cols;
                     int currentX = 0;
                     for (JRDesignField f : selectedFields)
                     {
                         if (groupFields.contains(f)) continue;
                         if (labelElement != null)
                         {
                             JRDesignStaticText newLabel = (JRDesignStaticText)labelElement.clone();
                             newLabel.setText( f.getName() );
                             newLabel.setX(currentX);
                             newLabel.setWidth(width);
                             columnHeaderBand.addElement(newLabel);
                         }
                         if (fieldElement != null)
                         {
                             JRDesignTextField newTextField = (JRDesignTextField)fieldElement.clone();
                             JRDesignExpression expression = Misc.createExpression( f.getValueClassName(), "$F{" + f.getName() + "}");
                                // Fix the class (the Textfield has a limited set of type options...)
                             com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldAction.setMatchingClassExpression(expression, f.getValueClassName(), true);
                             newTextField.setExpression(expression);
                             newTextField.setX(currentX);
                             newTextField.setWidth(width);
                             detailBand.addElement(newTextField);
                         }
                                 
                         currentX += width;
                     }
                }
                
            }
            else if (reportType != null && reportType.equals("columnar"))
            {
                // Add the labels to the column header..
                JRDesignBand detailBand = (JRDesignBand)jasperDesign.getDetail();
                // Find the label template...
                JRDesignStaticText labelElement = findStaticTextElement(detailBand, "DetailLabel" );
                JRDesignTextField fieldElement = findTextFieldElement(detailBand, "DetailField" );
                if (labelElement != null) detailBand.removeElement(labelElement);
                if (fieldElement != null) detailBand.removeElement(fieldElement);
                
                
                int currentY = 0;
                int rowHeight = 0; // Just to set a default...
                if (labelElement != null) rowHeight = labelElement.getHeight();
                if (fieldElement != null) rowHeight = Math.max( rowHeight, fieldElement.getHeight());
                // if rowHeight is still 0... no row will be added...
                for (JRDesignField f : selectedFields)
                {
                    if (groupFields.contains(f)) continue;
                    if (labelElement != null)
                    {
                        JRDesignStaticText newLabel = (JRDesignStaticText)labelElement.clone();
                        newLabel.setText( f.getName() );
                        newLabel.setY(currentY);
                        detailBand.addElement(newLabel);
                    }
                    if (fieldElement != null)
                    {
                        JRDesignTextField newTextField = (JRDesignTextField)fieldElement.clone();
                        JRDesignExpression expression = Misc.createExpression( f.getValueClassName(), "$F{" + f.getName() + "}");
                            // Fix the class (the Textfield has a limited set of type options...)
                        com.jaspersoft.ireport.designer.palette.actions.CreateTextFieldAction.setMatchingClassExpression(expression, f.getValueClassName(), true);
                        newTextField.setExpression(expression);
                        newTextField.setY(currentY);
                        detailBand.addElement(newTextField);
                    }

                    currentY += rowHeight;
                }
                
                detailBand.setHeight(currentY);
                
            }
            
            return jasperDesign;
    }

}
