/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.sheet.properties;

import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseStyle;

/**
 * Class to manage the JRBaseStyle.PROPERTY_PDF_EMBEDDED property
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */

public class PdfEmbeddedProperty extends BooleanProperty{

    private final JRFont font;

    @SuppressWarnings("unchecked")
    public PdfEmbeddedProperty(JRFont font)
    {
        super(font);
        this.font = font;
    }
    @Override
    public String getName()
    {
        return JRBaseStyle.PROPERTY_PDF_EMBEDDED;
    }

    @Override
    public String getDisplayName()
    {
        return I18n.getString("Global.Property.PdfEmbedded");
    }

    @Override
    public String getShortDescription()
    {
        return "";
    }

    @Override
    public Boolean getBoolean()
    {
        return font.isPdfEmbedded();
    }

    @Override
    public Boolean getOwnBoolean()
    {
        return font.isOwnPdfEmbedded();
    }

    @Override
    public Boolean getDefaultBoolean()
    {
        return null;
    }

    @Override
    public void setBoolean(Boolean isPdfEmbedded)
    {
    	font.setPdfEmbedded(isPdfEmbedded);
    }
    
}
