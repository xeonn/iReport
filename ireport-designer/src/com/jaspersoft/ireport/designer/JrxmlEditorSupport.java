/*
 * JrxmlEditorSupport.java
 * 
 * Created on Aug 27, 2007, 11:01:45 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * Copyright (C) 2006 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 */

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.JrxmlDataObject;
import java.io.IOException;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.cookies.EditCookie;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.loaders.SaveAsCapable;
import org.openide.nodes.Node.Cookie;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.DataEditorSupport;
import org.openide.util.Lookup;
import org.openide.util.Task;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author gtoffoli
 */
public class JrxmlEditorSupport extends DataEditorSupport implements OpenCookie, EditorCookie, EditCookie, SaveAsCapable  {

    private static Logger LOG = Logger.getLogger(JrxmlEditorSupport.class.getName());
    
    private InstanceContent specialNodeLookupIC = null;
    private Lookup specialNodeLookup = null;
    
    private final SaveCookie saveCookie = new SaveCookie() {
        /** Implements <code>SaveCookie</code> interface. */
            public void save() throws IOException {
                saveDocument();
            }
        };
        
    final MultiViewDescription[] descriptions = {
        new JrxmlVisualView(this),
        new JrxmlTextView(this),
        new JrxmlPreviewView(this)
    };
    
    private JasperDesign currentModel = null;

    @Override
    protected Task reloadDocument() {
        
        // Force a document refresh...
        ((JrxmlVisualView)descriptions[0]).refreshModel();
        return super.reloadDocument();
    }
    
    public MultiViewDescription[] getDescriptions()
    {
        return descriptions;
    }
    
    private JrxmlEditorSupport(JrxmlDataObject obj) {
        super(obj, new JrxmlEnv(obj));
        specialNodeLookupIC = new InstanceContent();
        specialNodeLookup = new AbstractLookup(specialNodeLookupIC);
    }
    
    public static JrxmlEditorSupport create(JrxmlDataObject obj) {
         JrxmlEditorSupport ed = new JrxmlEditorSupport(obj);
         ed.setMIMEType("text/xml");
         return ed;
    }
    
    protected CloneableEditorSupport.Pane createPane() {
        return (CloneableEditorSupport.Pane)MultiViewFactory.
                createCloneableMultiView(descriptions, descriptions[0]);
    }
    
    protected boolean notifyModified() {
        boolean retValue;
        retValue = super.notifyModified();
        if (retValue) {
            JrxmlDataObject obj = (JrxmlDataObject)getDataObject();
            if(obj.getCookie(SaveCookie.class) == null) {
                obj.getIc().add( saveCookie );
                specialNodeLookupIC.add(saveCookie);
                obj.setModified(true);
                //((JrxmlDataNode)obj.getNodeDelegate()).cookieSetChanged();
            }
            ((JrxmlPreviewView)descriptions[2]).setNeedRefresh(true);
        }
        return retValue;
    }
    
    protected void notifyUnmodified() {
        super.notifyUnmodified();
        JrxmlDataObject obj = (JrxmlDataObject)getDataObject();
        
        Cookie cookie = obj.getCookie(SaveCookie.class);

        if(cookie != null && cookie.equals( saveCookie )) {
            obj.getIc().remove(saveCookie);
            specialNodeLookupIC.remove(saveCookie);
            obj.setModified(false);
            //((JrxmlDataNode)obj.getNodeDelegate()).cookieSetChanged();
        }
    }
    
    public DataEditorSupport.Env  getEnv()
    {
        return (Env)this.env;
    }
    
    public void saveDocument() throws IOException {
            
            if (getCurrentModel() != null)
            {
                //set the document content...
                JasperDesign jd = getCurrentModel();
                try {
                    String content = JRXmlWriter.writeReport(jd, "UTF-8"); // IReportManager.getInstance().getProperty("jrxmlEncoding", System.getProperty("file.encoding") ));
                    getDocument().remove(0, getDocument().getLength());
                    getDocument().insertString(0, content, null);
                    ((JrxmlVisualView)descriptions[0]).setNeedModelRefresh(false);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            
            super.saveDocument();
        }

    public Lookup getSpecialNodeLookup() {
        return specialNodeLookup;
    }

    public void setSpecialNodeLookup(Lookup specialNodeLookup) {
        this.specialNodeLookup = specialNodeLookup;
    }
    
    public static final class JrxmlEnv extends DataEditorSupport.Env {
    
        public JrxmlEnv(JrxmlDataObject obj) {
            super(obj);
        }
        
        protected FileObject getFile() {
            return super.getDataObject().getPrimaryFile();
        }
        
        protected FileLock takeLock() throws IOException {
            return ((JrxmlDataObject)super.getDataObject()).getPrimaryEntry().takeLock();
        }

    }

    public JasperDesign getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(JasperDesign currentModel) {
        this.currentModel = currentModel;
    }

}
