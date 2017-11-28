/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.jrx;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.export.ExporterFactory;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.locale.I18n;
import net.sf.jasperreports.engine.JRExporter;
import com.jaspersoft.jrx.export.JRTxtExporter;
import com.jaspersoft.jrx.export.JRTxtExporterParameter;
import java.util.prefs.Preferences;

/**
 *
 * @author gtoffoli
 */
public class JRTxtExporterFactory implements ExporterFactory {

    public String getExportFormat() {
        return "irtxt";
    }

    public String getExportFormatDisplayName() {
        return I18n.getString("format.irtxt");
    }

    public String getExporterFileExtension() {
        return "txt";
    }

    public String getViewer() {
        return Misc.nvl( IReportManager.getInstance().getProperty("ExternalTXTViewer"), "");
    }

    public JRExporter createExporter() {
        JRTxtExporter exporter = new JRTxtExporter();

        // configuring the exporter...
        Preferences pref = IReportManager.getPreferences();


        int pageHeight = pref.getInt( "irtext.pageHeight", 0);
        if (pageHeight > 0)
        {
            exporter.setParameter( JRTxtExporterParameter.PAGE_ROWS, "" + pageHeight);
        }

        int pageWidth = pref.getInt( "irtext.pageWidth", 0);
        if (pageWidth > 0)
        {
            exporter.setParameter( JRTxtExporterParameter.PAGE_COLUMNS, "" + pageWidth);
        }

        boolean addFormFeed = pref.getBoolean("irtext.addFormFeed", true);
        exporter.setParameter( JRTxtExporterParameter.ADD_FORM_FEED, "" + addFormFeed);

        String bidi = pref.get("irtext.bidi", "");
        if (bidi.length() > 0)
        {
           exporter.setParameter( JRTxtExporterParameter.BIDI_PREFIX, bidi);

        }

        String displaywidthProviderFactory = pref.get("irtext.displaywidthProviderFactory", "");
        if (displaywidthProviderFactory.length() > 0)
        {
           exporter.setParameter( JRTxtExporterParameter.DISPLAY_WIDTH_PROVIDER_FACTORY, displaywidthProviderFactory);
        }

        return exporter;

    }

}
