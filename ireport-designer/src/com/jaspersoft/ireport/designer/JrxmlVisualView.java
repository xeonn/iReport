/*
 * JrxmlVisualView.java
 * 
 * Created on Aug 27, 2007, 11:08:52 PM
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

import com.jaspersoft.ireport.JrxmlDataNode;
import com.jaspersoft.ireport.JrxmlDataObject;
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.outline.nodes.ElementNode;
import com.jaspersoft.ireport.designer.outline.nodes.ReportNode;
import com.jaspersoft.ireport.designer.outline.OutlineTopComponent;
import com.jaspersoft.ireport.designer.outline.nodes.CellNode;
import com.jaspersoft.ireport.designer.outline.nodes.CrosstabNode;
import com.jaspersoft.ireport.designer.outline.nodes.NullCellNode;
import com.jaspersoft.ireport.designer.tools.JrxmlEditorToolbar;
import com.jaspersoft.ireport.designer.undo.UndoRedoManager;
import com.jaspersoft.ireport.designer.utils.Misc;
import java.awt.BorderLayout;
import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.core.api.multiview.MultiViewHandler;
import org.netbeans.core.api.multiview.MultiViewPerspective;
import org.netbeans.core.api.multiview.MultiViews;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.spi.navigator.NavigatorLookupHint;
import org.netbeans.spi.palette.PaletteController;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.TopComponentGroup;
import org.openide.windows.WindowManager;

/**
 *
 * @author gtoffoli
 */
public class JrxmlVisualView extends TopComponent 
        implements ObjectSceneListener, MultiViewDescription, MultiViewElement,
        DocumentListener, JrxmlDataNode.ChangeCallback, Runnable, ExplorerManager.Provider {
    
    /**
     * The explorer manager selection (and content) is always synchronized with the outline view.
     */
    private transient ExplorerManager explorerManager;
    public static final String PREFERRED_ID = "ireport_visual_view";
    
    private JrxmlEditorSupport support;
    private ReportDesignerPanel reportDesignerPanel = null;
    private boolean elementInitialized = false;
    
    /**
     * This is the model. It could be null if the underline jrxml is not valid of a parsing
     * error has occurred.
     */
    private JasperDesign jasperDesign = null;
        
    private boolean settingSelection = false;
    
    private synchronized boolean isSettingSelection()
    {
        return settingSelection;
    }
    
    private synchronized void  setSettingSelection(boolean b)
    {
        settingSelection = b;
    }
    
    public JrxmlEditorSupport getEditorSupport()
    {
        return support;
    }
    
    private ReportNode model = null;
    public ReportNode getModel() {
        return model;
    }

    public ReportDesignerPanel getReportDesignerPanel() {
        return reportDesignerPanel;
    }
    private boolean needModelRefresh = true;
    MultiViewElementCallback callback = null;
    
    InstanceContent ic;
    AbstractLookup abstractLookup;
    final JasperDesignerTypeLookupHint hint = new JasperDesignerTypeLookupHint();
    private ProxyLookup lookup = null;
    
    public static int num = 0;
    
    private static PaletteController pc;

    public static PaletteController getPaletteFromMimeType( String mimeType ) {
        MimePath path = MimePath.get( mimeType );
        Lookup lkp = MimeLookup.getLookup( path );
        return (PaletteController) lkp.lookup(org.netbeans.spi.palette.PaletteController.class);
    }
    
    public JrxmlVisualView(JrxmlEditorSupport ed) {
        super();
        support = ed;
    }
    
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }
    
    @Override
    public String getDisplayName() {
        return "Designer";
    }
    
    @Override
    public Image getIcon() {
        Node nd = ((JrxmlDataObject)support.getDataObject()).getNodeDelegate();
        return nd.getIcon( BeanInfo.ICON_COLOR_16x16);
    }
    
    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    @Override
    public String preferredID() {
        return PREFERRED_ID;
    }
    
    public MultiViewElement createElement() {
            
        if (!elementInitialized)
        {
            try {
                assert java.awt.EventQueue.isDispatchThread();

                
                    elementInitialized = true;

                    ic = new InstanceContent();
                explorerManager = new ExplorerManager();

                ActionMap map = getActionMap();
                //setActionMap(map);
                map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(explorerManager));
                map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(explorerManager));
                map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(explorerManager));
                map.put("delete", ExplorerUtils.actionDelete(explorerManager, true));

                abstractLookup = new AbstractLookup(ic);
                //if (pc != null)

                if (pc == null)
                {
                    pc = getPaletteFromMimeType("text/x-jrxml+xml");
                }

                lookup = new ProxyLookup(new Lookup[]{
                    abstractLookup,
                    //Lookups.fixed(hint), //support.getDataObject().getNodeDelegate()
                    ExplorerUtils.createLookup(explorerManager, map),
                    support.getDataObject().getLookup(),
                    Lookups.fixed(pc)
                    });

                associateLookup(lookup);
                //setActivatedNodes( new Node[]{support.getDataObject().getNodeDelegate()});


                explorerManager.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName()))
                        {

                            if (OutlineTopComponent.getDefault() != null)
                            {
                                try {
                                    OutlineTopComponent.getDefault().getExplorerManager().setSelectedNodes( explorerManager.getSelectedNodes() );
                                } catch (Exception ex) {}
                            }
                        }
                    }
                });

                removeAll();
                support.openDocument().addDocumentListener(this);
                reportDesignerPanel = new com.jaspersoft.ireport.designer.ReportDesignerPanel();

                setLayout(new java.awt.BorderLayout());
                add(reportDesignerPanel, BorderLayout.CENTER);

                reportDesignerPanel.addObjectSelectionListener(this);

            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return this;
    }
    
    public JComponent getVisualRepresentation() {
        return this;
    }
    
    public JComponent getToolbarRepresentation() {
        return new JrxmlEditorToolbar();
    }
    
    @Override
    public void componentOpened() {
        setNeedModelRefresh(false);
        refreshModel();
    }
    
    @Override
    public void componentClosed() {
    }
    
    @Override
    public void componentShowing() {
        
        if (isNeedModelRefresh())
        {
            refreshModel();
        }
        else
        {
            support.setCurrentModel( jasperDesign  );
        }
        
        if (OutlineTopComponent.getDefault() != null) {
             OutlineTopComponent.getDefault().setCurrentJrxmlVisualView(this);
        }
        
    }
    
    private static Boolean groupVisible = null;

    @SuppressWarnings("unchecked")
    public void setSelectedNodes(Node[] selectedNodes) {
        
        if (isSettingSelection()) return;
        
        try {
            setSettingSelection(true);
            explorerManager.setSelectedNodes(selectedNodes);
            
            // Find the objects related to these nodes...
            HashSet selectedObjects = new HashSet();

            for (int i=0; i<selectedNodes.length; ++i)
            {               
                if (selectedNodes[i] instanceof ElementNode)
                {
                    selectedObjects.add( ((ElementNode)selectedNodes[i]).getElement() );
                }
                else if (selectedNodes[i] instanceof CellNode)
                {
                    selectedObjects.add( ((CellNode)selectedNodes[i]).getCellContents() );
                }
            }
            
            // We have to find the scene with this object...
            getReportDesignerPanel().setSelectedObjects(selectedObjects);
                        
        } catch (PropertyVetoException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            setSettingSelection(false);
        }
        
    }
    
    private void updateGroupVisibility() {
        WindowManager wm = WindowManager.getDefault();
        final TopComponentGroup group = wm.findTopComponentGroup("ireport"); // NOI18N
        if (group == null) {
            return; // group not found (should not happen)
        }
        //
        boolean isVisualViewSelected = false;
        Iterator it = wm.getModes().iterator();
        while (it.hasNext()) {
            Mode mode = (Mode) it.next();
            TopComponent selected = mode.getSelectedTopComponent();
            if (selected != null) {
            MultiViewHandler mvh = MultiViews.findMultiViewHandler(selected);
                if (mvh != null) {
                    MultiViewPerspective mvp = mvh.getSelectedPerspective();
                    if (mvp != null) {
                        String id = mvp.preferredID();
                        if (JrxmlVisualView.PREFERRED_ID.equals(id)) {
                            isVisualViewSelected = true;
                            break;
                        }
                    }
                }
            }
        }
        //
        if (isVisualViewSelected && !Boolean.TRUE.equals(groupVisible)) {
            group.open();
        } else if (!isVisualViewSelected && !Boolean.FALSE.equals(groupVisible)) {
            group.close();
        }
        //
        groupVisible = isVisualViewSelected ? Boolean.TRUE : Boolean.FALSE;
        
    }
    
    public void refreshModel()
    {
        // This method should run in a separate thread...
        try {
            model = null;
            RequestProcessor.getDefault().post(this);
        } finally {
            
            // set the model as refreshed even if the model loading is failed
            // this to avoid to reload a errouneus file again.
            setNeedModelRefresh(false);
        }
    }
    
    @Override
    public void componentHidden() {
        
        //TopComponentGroup group = WindowManager.getDefault().findTopComponentGroup("reportdesigner");
        //if (group != null)
        //{
        //    group.close();
        //}
        if (OutlineTopComponent.getDefault() != null) {
             OutlineTopComponent.getDefault().closingJrxmlVisualView(this);
        }
    }
    
    @Override
    public void componentActivated() {
        
        ic.add(this.getReportDesignerPanel());
        updateGroupVisibility();
        if (getReportDesignerPanel() != null &&
            getReportDesignerPanel().getActiveScene() != null &&
            getReportDesignerPanel().getActiveScene().getView() != null)
        {
            getReportDesignerPanel().getActiveScene().getView().requestFocusInWindow();
        }
        
        
    }
    
    public void componentDeactivated() {
        ic.remove(this.getReportDesignerPanel());
        updateGroupVisibility();
    }
    
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
        updateName();
    }
    
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
   
    public void insertUpdate(DocumentEvent e) {
        setNeedModelRefresh(true);
    }
    
    public void removeUpdate(DocumentEvent e) {
        setNeedModelRefresh(true);
    }
    
    public void changedUpdate(DocumentEvent e) {
        setNeedModelRefresh(true);
    }
    
    public synchronized void setNeedModelRefresh(boolean b) {
        needModelRefresh = b;
    }
    
    public boolean isNeedModelRefresh() {
        return needModelRefresh;
    }
    
    boolean loading = false;
    public boolean isLoading()
    {
        return loading;
    }

    public void run()
    {
        loading = true;
        try {
            //
            support.setCurrentModel( null );
            
            
            JrxmlLoader jrxmlLoader = new JrxmlLoader();
            jasperDesign = jrxmlLoader.reloadJasperDesign(support.getInputStream());
            
            if (jasperDesign == null)
            {
                
                // I'm loading the document for the first time...
                if (reportDesignerPanel.getJasperDesign() == null)
                {
                    
                }
            }
            else
            {
                javax.swing.SwingUtilities.invokeAndWait(new java.lang.Runnable() {
                    public void run() {
                        
                            reportDesignerPanel.setJasperDesign(jasperDesign);
                            
                            model = new ReportNode(jasperDesign, support.getSpecialNodeLookup());
                            getUndoRedoManager().discardAllEdits();
                            explorerManager.setRootContext(model);
                            
                            support.setCurrentModel( jasperDesign  );
                            
                            if (OutlineTopComponent.getDefault() != null) {
                                if (OutlineTopComponent.getDefault().getCurrentJrxmlVisualView() != JrxmlVisualView.this)
                                {
                                    // The window we are loading is not the active one...
                                    //OutlineTopComponent.getDefault().setCurrentJrxmlVisualView(JrxmlVisualView.this);
                                }
                                else
                                {
                                    OutlineTopComponent.getDefault().getExplorerManager().setRootContext(model);
                                }
                            }
                            
                            try {
                                explorerManager.setSelectedNodes(new Node[]{model});
                            } catch (Exception ex) { 
                            }
                    }
                });
            }
        } catch (JRException ex) {
            try {
                //Exceptions.printStackTrace(ex);
                javax.swing.SwingUtilities.invokeAndWait(new java.lang.Runnable() {

                    public void run() {

                        reportDesignerPanel.setJasperDesign(null);
                        AbstractNode mNode = new AbstractNode(Children.LEAF);
                        mNode.setDisplayName("Invalid report");
                        
                        getUndoRedoManager().discardAllEdits();
                        explorerManager.setRootContext(mNode);

                        if (OutlineTopComponent.getDefault() != null) {
                            if (OutlineTopComponent.getDefault().getCurrentJrxmlVisualView() != JrxmlVisualView.this) {
                                OutlineTopComponent.getDefault().setCurrentJrxmlVisualView(JrxmlVisualView.this);
                            }
                            OutlineTopComponent.getDefault().getExplorerManager().setRootContext(mNode);
                        }

                        try {
                            explorerManager.setSelectedNodes(new Node[]{mNode});
                        } catch (Exception ex) {
                        }
                    }
                });
                
            } catch (InterruptedException ex1) {
                //Exceptions.printStackTrace(ex1);
            } catch (InvocationTargetException ex1) {
                //Exceptions.printStackTrace(ex1);
            }
            
            Misc.showErrorMessage("Error loading the report template:\n" + ex.getMessage(), "Error loading the report template", ex);

        
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            Misc.showErrorMessage("Error loading the report template:\n"+ex.getMessage(), "Error loading the report template", ex);
        }
        finally{
            
            loading = false;
        }
    }
    
    
    public void updateName() {
        
        Runnable run = new Runnable() {
            public void run() {
                MultiViewElementCallback c = callback;
                
                if (c == null) {
                    return;
                }
                TopComponent tc = c.getTopComponent();
                if (tc == null) {
                    return;
                }
                Node nd = ((JrxmlDataObject)support.getDataObject()).getNodeDelegate();
                tc.setName(nd.getName() );
                tc.setDisplayName(nd.getDisplayName());
                tc.setHtmlDisplayName(nd.getHtmlDisplayName());
                
            }
        };
        
        if (SwingUtilities.isEventDispatchThread ())
            run.run();
        else
            SwingUtilities.invokeLater (run);
    }

  
    class JasperDesignerTypeLookupHint implements NavigatorLookupHint {
            public String getContentType () {
                return "my-jasper-designer-mimetype";
            }
        }

    public void objectAdded(ObjectSceneEvent event, Object addedObject) {
        //
    }

    public void objectRemoved(ObjectSceneEvent event, Object removedObject) {
        //
    }

    public void objectStateChanged(ObjectSceneEvent event, Object changedObject, ObjectState previousState, ObjectState newState) {
        //
    }

    /**
     *  When the selection changes in the visual designer, we want sync our ExplorerManager selection.
     *  If no elements are selectes, let's try to select the root node (the document)
     */
    public void selectionChanged(ObjectSceneEvent event, Set<Object> previousSelection, Set<Object> newSelection) {
        
        if (isSettingSelection()) return;
        
        try {
            //if (newSelection.size() == 0 && previousSelection.size() == 0) return; // selection is not changed...
            
            setSettingSelection(true);
            
            if (newSelection.size() == 0 &&
                getExplorerManager().getRootContext() != null)
            {
                //
                // If no elements are selected, avoid to change
                // the selection...
                Node[] selectedNodes = explorerManager.getSelectedNodes();
                for (int i=0; i<selectedNodes.length; ++i)
                {
                    if (selectedNodes[i] instanceof ElementNode) // .getLookup().lookup(JRDesignElement.class) != null)
                    {
                        // In case of a cell, the lookup contains the crosstab, so we have to skip
                        // this particular case...
                        if (selectedNodes[i].getLookup().lookup(JRCellContents.class) != null ||
                            event.getObjectScene() instanceof CrosstabObjectScene)
                        {
                            continue;
                        }
                        
                        
                        explorerManager.setSelectedNodes( new Node[]{getExplorerManager().getRootContext()} );
                        break;
                    }
                }
                return;
            }
            
            List<Node> nodes = new ArrayList<Node>();
            // find the node...
            for (Object element : newSelection)
            {
                if (element instanceof JRElement)
                {
                    Node node = ModelUtils.findElementNode(model, (JRElement)element);
                    if (node != null)
                    {
                        nodes.add(node);
                    }
                }
            }
            
            Node[] nodesArray = new Node[ nodes.size() ];
            explorerManager.setSelectedNodes(nodes.toArray(nodesArray));     
            
                    
        } catch (PropertyVetoException ex) {
            Exceptions.printStackTrace(ex);
        }
        finally
        {
            setSettingSelection(false);
        }
    }

    public void highlightingChanged(ObjectSceneEvent event, Set<Object> previousHighlighting, Set<Object> newHighlighting) {
        //
    }

    public void hoverChanged(ObjectSceneEvent event, Object previousHoveredObject, Object newHoveredObject) {
        //
    }

    public void focusChanged(ObjectSceneEvent event, Object previousFocusedObject, Object newFocusedObject) {
        //
    }

    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    private UndoRedoManager undoRedoManager = null;
            
    @Override
    public UndoRedo getUndoRedo(){       
        return getUndoRedoManager();   
    }     
    
    @Override
    public Lookup getLookup()
    {
        return super.getLookup();
    }
    
    public UndoRedo.Manager getUndoRedoManager()
    {
        if (undoRedoManager == null)
        {
            undoRedoManager = new UndoRedoManager();           
            undoRedoManager.setLimit(300);     
        }
        return undoRedoManager;
    }

    public void modelChanged(JasperDesign model) throws IllegalArgumentException {
        // Model changed...
    }
    
    
    public void requestActive() {
            if (callback != null) {
                callback.requestActive();
            } else {
                super.requestActive();
            }
        }
    
}
