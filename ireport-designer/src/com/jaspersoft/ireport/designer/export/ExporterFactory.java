/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.export;

import net.sf.jasperreports.engine.JRExporter;

/**
 *
 * @author gtoffoli
 */
public interface ExporterFactory {

    /**
     * Return a code that will be set as preview type
     * in order to use this exporter (i.e. myTxtFormat)
     * @return
     */
    public String getExportFormat();

    /**
     * Return the name that should be appear in the Preview
     * menu when specifying this format. I.e. (John Smith's custom text)
     * @return
     */
    public String getExportFormatDisplayName();

    /**
     * The extension of the file that will be used to replace the .jasper
     * extension in the original file (i.e. txt)
     * @return
     */
    public String getExporterFileExtension();
    
    /**
     * Return the name of an application to be executed to view this file
     * The command should be executed with the file name as first parameter.
     * If the return is null or an empty string, the internal preview will be used.
     * @return
     */
    public String getViewer();

    /**
     * This function creates and configures the exporter. Extra parameters
     * can be set or replaced by the IReportCompiler class. In particular this
     * class will set these parameters:
     * JRExporterParameter.OUTPUT_FILE_NAME
     * JRExporterParameter.JASPER_PRINT
     * JRExporterParameter.PROGRESS_MONITOR
     * JRExporterParameter.IGNORE_PAGE_MARGINS
     * JRExporterParameter.PAGE_INDEX
     * JRExporterParameter.START_PAGE_INDEX
     * JRExporterParameter.END_PAGE_INDEX
     * JRExporterParameter.PROPERTY_CHARACTER_ENCODING
     * JRExporterParameter.CHARACTER_ENCODING
     * JRExporterParameter.OFFSET_X
     * JRExporterParameter.OFFSET_Y
     *
     * if propertly requested by the user with the general export options panel.
     *
     * @return
     */
    public JRExporter createExporter();

}
