/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignTextElement;

/**
 * Class to manage the JRBaseStyle.PROPERTY_PDF_EMBEDDED property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class PdfEmbeddedProperty extends BooleanProperty{

    private final JRDesignTextElement element;

    @SuppressWarnings("unchecked")
    public PdfEmbeddedProperty(JRDesignTextElement element)
    {
        super(element);
        this.element = element;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_PDF_EMBEDDED;
    }

    @Override
    public String getDisplayName()
    {
        return "Pdf Embedded";
    }

    @Override
    public String getShortDescription()
    {
        return "";
    }

    @Override
    public Boolean getBoolean()
    {
        return element.isPdfEmbedded();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return element.isOwnPdfEmbedded();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isPdfEmbedded)
    {
    	element.setPdfEmbedded(isPdfEmbedded);
    }
    
}
