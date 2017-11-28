/*
 * JrxmlPreviewView.java
 * 
 * Created on 14-nov-2007, 11.07.18
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.JrxmlDataObject;
import com.jaspersoft.ireport.designer.compiler.CompilationStatusEvent;
import com.jaspersoft.ireport.designer.compiler.CompilationStatusListener;
import com.jaspersoft.ireport.designer.menu.RunReportAction;
import com.jaspersoft.ireport.designer.tools.JrxmlPreviewToolbar;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewerController;
import net.sf.jasperreports.swing.JRViewerPanel;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;

/**
 *
 * @author gtoffoli
 */
public class JrxmlPreviewView extends TopComponent 
        implements MultiViewDescription, MultiViewElement, CompilationStatusListener {
    
    private boolean needRefresh = true;

    MultiViewElementCallback multiViewCallback = null;
    
    JRViewerController viewerContext = new JRViewerController(null, null);
    JrxmlPreviewToolbar viewerToolbar = new JrxmlPreviewToolbar(this, viewerContext);
    
    private JrxmlEditorSupport support;
    
    public JrxmlPreviewView(JrxmlEditorSupport ed) {
        this.support = ed;
    }
    
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }
    
    @Override
    public String getDisplayName() {
        return "Preview";
    }
    
    @Override
    public Image getIcon() {
        Node nd = ((JrxmlDataObject)support.getDataObject()).getNodeDelegate();
        return nd.getIcon( BeanInfo.ICON_COLOR_16x16);
    }
    
    
    public String preferredID() {
        return "Preview";
    }
    
    public void componentDeactivated() {
    }
    
    public void componentActivated() {
    }
    
    public void componentHidden() {
    }
    
    public void componentShowing() {
        
        if (isNeedRefresh())
        {
            setJasperPrint(null);
            updateUI();
            RunReportAction.runReport( support );
        }
    }
    
    public void componentClosed() {
    }
    
    public void componentOpened() {
    }
    
    public void setJasperPrint(final JasperPrint print)
    {
        if (print != null)
        {
            setNeedRefresh(false);
        }
        
        ThreadUtils.invokeInAWTThread( new Runnable()
        {
            public void run()
            {
                removeAll();
        
                if (print != null)
                {
                    JRViewerPanel viewerPanel = new JRViewerPanel(viewerContext);
                    add(viewerPanel, BorderLayout.CENTER);
                    viewerContext.loadReport(print);
                    viewerToolbar.init();
                    viewerContext.refreshPage();
                    viewerPanel.updateUI();
                }
                updateUI();
                
            }
        });
        
    }
    
    
    public MultiViewElement createElement() {
        this.setLayout(new BorderLayout());
        return this;
    }

    public JComponent getVisualRepresentation() {
       return this;
    }

    public JComponent getToolbarRepresentation() {
        return new JrxmlPreviewToolbar(this, viewerContext);
    }

    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.multiViewCallback = callback;
    }

    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
    
    public void requestActive() {
            if (multiViewCallback != null) {
                multiViewCallback.requestActive();
            } else {
                super.requestActive();
            }
        }

    public

    boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    public void actionPerformed(ActionEvent e) {
        
        
        
    }

    int currentStatus = CompilationStatusEvent.STATUS_UNDEFINED;
    org.jdesktop.swingx.JXBusyLabel label = new org.jdesktop.swingx.JXBusyLabel();
                
    public void compilationStatus(CompilationStatusEvent e) {
    
        if (e.getStatus() == CompilationStatusEvent.STATUS_RUNNING)
        {
            if (e.getStatus() != currentStatus)
            {
                ThreadUtils.invokeInAWTThread( new Runnable()
                {
                    public void run()
                    {
                        removeAll();
                        JPanel p = new JPanel();
                        p.setLayout(new GridBagLayout());
                        label.setBusy(true);
                        p.add(label);
                        add(p, BorderLayout.CENTER);
                        updateUI();
                    }
                });
            }
        }
        else
        {
            ThreadUtils.invokeInAWTThread( new Runnable()
            {   
                public void run()
                {
                    label.setBusy(false);
                }
            });
            
        }
        
        currentStatus = e.getStatus();
    
    }

    
}
