/*
 * iReport - Visual Designer for JasperReports.
 * Copyright (C) 2002 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.ireport.designer.compatibility;

import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.openide.util.Exceptions;
import org.openide.util.Mutex;


/**
 *
 * @author gtoffoli
 */
public class JRXmlWriterHelper {

    private static final Map<String, Class> writers = new HashMap();

    static {

        writers.put("3_7_4", JRXmlWriter_3_7_4.class);
        writers.put("3_7_3", JRXmlWriter_3_7_3.class);
        writers.put("3_7_1", JRXmlWriter_3_7_1.class);
        writers.put("3_6_2", JRXmlWriter_3_6_2.class);
        writers.put("3_6_1", JRXmlWriter_3_6_1.class);
        writers.put("3_6_0", JRXmlWriter_3_6_0.class);
        writers.put("3_5_2", JRXmlWriter_3_5_2.class);
        writers.put("3_5_1", JRXmlWriter_3_5_1.class);
        writers.put("3_5_0", JRXmlWriter_3_5_0.class);
        writers.put("3_1_4", JRXmlWriter_3_1_4.class);
        writers.put("3_1_3", JRXmlWriter_3_1_3.class);
        writers.put("3_1_2", JRXmlWriter_3_1_2.class);
        writers.put("3_1_0", JRXmlWriter_3_1_0.class);
        writers.put("3_0_1", JRXmlWriter_3_0_1.class);
        writers.put("3_0_0", JRXmlWriter_3_0_0.class);
        writers.put("2_0_5", JRXmlWriter_2_0_5.class);
        writers.put("2_0_4", JRXmlWriter_2_0_4.class);
        writers.put("2_0_3", JRXmlWriter_2_0_3.class);
        writers.put("2_0_2", JRXmlWriter_2_0_2.class);
    }

    private static VersionWarningDialog dialog = null;

    public static String writeReport(JRReport report, String encoding, String version) throws Exception
    {

        if (IReportManager.getPreferences().getBoolean("show_compatibility_warning", true))
        {
            setDialog(null); // force the instance of a new dialog...

           getDialog().setVersion(version);

           if (SwingUtilities.isEventDispatchThread())
           {
               getDialog().setVisible(true);
           }
           else
           {
               SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        getDialog().setVisible(true);
                    }
                });
           }
           
           int res = getDialog().getDialogResult();

           if (!getDialog().askAgain())
           {
               IReportManager.getPreferences().putBoolean("show_compatibility_warning", false);
           }

           if (res == VersionWarningDialog.DIALOG_RESULT_USE_LAST_VERSION)
           {
               version = "";
           }

        }
        if (writers.containsKey(version))
        {
            Class clazz = writers.get(version);
            return (String)clazz.getMethod("writeReport", new Class[]{JRReport.class, String.class}).invoke(null, new Object[]{report, encoding});
        }
        else if (version.length() == 0)
        {
            return JRXmlWriter.writeReport(report, encoding);
        }

        throw new Exception("XML writer for version " +version + " not found.");
    }

    /**
     * @return the dialog
     */
    public static VersionWarningDialog getDialog() {
        if (dialog == null)
        {
            // The operation Misc.getMainFrame() requires an AWT thread...
           Runnable run = new Runnable(){
                    public void run() {
                            setDialog(new VersionWarningDialog(Misc.getMainFrame(), true));
                        }
               };

               if (SwingUtilities.isEventDispatchThread())
               {
                   run.run();
               }
               else
               {
                try {
                    SwingUtilities.invokeAndWait(run);
                } catch (Exception ex) {
                }
               }
        }
        return dialog;
    }

    /**
     * @param dialog the dialog to set
     */
    public static void setDialog(VersionWarningDialog dlg) {
        dialog = dlg;
    }


}
