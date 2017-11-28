/*
 * ReportNode.java
 * 
 * Created on Aug 31, 2007, 4:55:47 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.designer.sheet.Tag;
import java.util.List;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class LanguageProperty extends StringListProperty
{
    private final JasperDesign jasperDesign;

    @SuppressWarnings("unchecked")
    public LanguageProperty(JasperDesign jasperDesign)
    {
        super(jasperDesign);
        this.jasperDesign = jasperDesign;
    }

    @Override
    public String getName()
    {
        return JasperDesign.PROPERTY_LANGUAGE;
    }

    @Override
    public String getDisplayName()
    {
        return "Language";
    }

    @Override
    public String getShortDescription()
    {
        return "The language used in all the expressions.";
    }

    @Override
    public List getTagList() 
    {
        List tags = new java.util.ArrayList();
        tags.add(new Tag(JasperDesign.LANGUAGE_JAVA, "Java"));//FIXMETD confusion between lower case and upper case values
        tags.add(new Tag(JasperDesign.LANGUAGE_GROOVY, "Groovy"));
        return tags;
    }

    @Override
    public String getString()
    {
        return jasperDesign.getLanguage();
    }

    @Override
    public String getOwnString()
    {
        return jasperDesign.getLanguage();
    }

    @Override
    public String getDefaultString()
    {
        return JRReport.LANGUAGE_JAVA;
    }

    @Override
    public void setString(String language)
    {
        jasperDesign.setLanguage(language);
    }

}
