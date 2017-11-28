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
package com.jaspersoft.ireport.designer;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.crosstab.CrosstabObjectScene;
import com.jaspersoft.ireport.designer.crosstab.CrosstabPanel;
import com.jaspersoft.ireport.designer.dnd.DesignerDropTarget;
import com.jaspersoft.ireport.designer.ruler.RulerPanel;
import com.jaspersoft.ireport.designer.utils.MultilineToolbarLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.netbeans.api.visual.model.ObjectSceneEvent;
import org.netbeans.api.visual.model.ObjectSceneEventType;
import org.netbeans.api.visual.model.ObjectSceneListener;
import org.netbeans.api.visual.model.ObjectState;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.Mutex;

        
/**
 *
 * @author  gtoffoli
 */
public class ReportDesignerPanel extends javax.swing.JPanel implements ObjectSceneListener {
 
    private boolean adjustingSelection = false;
    
    
    private Set<ObjectSceneListener> listeners = new HashSet<ObjectSceneListener>(1); // or can use ChangeSupport in NB 6.0
    public final void addObjectSelectionListener(ObjectSceneListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void removeObjectSelectionListener(ObjectSceneListener l) {
        synchronized (listeners) {
        listeners.remove(l);
        }
    }
    
    protected final void fireSelectionChangeEvent(ObjectSceneEvent arg0, Set<Object> arg1, Set<Object> arg2) {
        Iterator<ObjectSceneListener> it;
        synchronized (listeners) {
        it = new HashSet<ObjectSceneListener>(listeners).iterator();
        }
        
        while (it.hasNext()) {
            if (!isAdjustingSelection())
            {
                it.next().selectionChanged(arg0,arg1,arg2);
            }
        }
    }
    
    
    java.util.List<CrosstabPanel> crosstabs = new ArrayList<CrosstabPanel>();
    JasperDesign jasperDesign = null;
    
    private int activeCrosstabIndex = -1;
    
    
    private RulerPanel hRuler = null;
    private RulerPanel vRuler = null;
    
    public RulerPanel getHRuler() {
        return hRuler;
    }

    public RulerPanel getVRuler() {
        return vRuler;
    }
    
    
    public boolean isGridVisible() {
        return getActiveScene().isGridVisible();
    }

    public void setGridVisible(boolean b) {
        
        getActiveScene().setGridVisible(b);
    }
    
    public boolean isSnapToGrid() {
        return getActiveScene().isSnapToGrid();
    }

    public void setSnapToGrid(boolean b) {
        getActiveScene().setSnapToGrid(b);
    }

    /**
     * The jasperDesign is loaded in background, since it involves in some I/O operations.
     * To get the JasperDesign currenctly active invoke getJasperDesign. If it returns true,
     * register yourself as JasperDesignObserver to get the design as soon it is available.
     * The Observer return the JasperDesign. If you get null again, this is
     * because the JasperDesign loading is failed for some reason (like a wrong XML syntax).
     */
    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }
    
    /**
     * Update the JasperDesign to edit and notify all the JasperDesignObserver(s) registered using
     * addJasperDesignObserver.
     * When an observer is notified, it is removed from the list of objects to notify.
     * See getJasperDesign for more details.
     */
    public void setJasperDesign(JasperDesign jasperDesign) {
        this.jasperDesign = jasperDesign;
        
        getScene().setJasperDesign(jasperDesign);

        // Remove all the listeners...
        for (CrosstabPanel p : crosstabs)
        {
            p.getScene().removeObjectSceneListener(this, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);
        }
        
        crosstabs.clear();
        activeCrosstabIndex = -1;
        if (jasperDesign != null)
        {
            // Look for crosstabs in the report...
            List<JRBand> bands = ModelUtils.getBands(jasperDesign);
            for (JRBand b : bands)
            {
                List l = ((JRDesignBand)b).getChildren();
                for (int i=0; i<l.size(); ++i)
                {
                    if (l.get(i) instanceof JRDesignCrosstab)
                    {
                        JRDesignCrosstab ct = (JRDesignCrosstab)l.get(i);
                        addCrosstabPanel(ct, jasperDesign);
                    }
                }
            }
        }
        
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    updateCrosstabPanels();
                }
            });
            
    }
    
    
    public CrosstabPanel getCrosstabPanel(JRDesignCrosstab crosstab)
    {
        for (CrosstabPanel p : crosstabs)
        {
            if (p.getScene().getDesignCrosstab() == crosstab) return p;
        }
        return null;
    }
    
    public boolean addCrosstabPanel(JRDesignCrosstab crosstab, JasperDesign design)
    {
        // check if there is already this crosstab...
        if (getCrosstabPanel(crosstab) != null) return false;
        
       CrosstabPanel panel = new CrosstabPanel(crosstab, design);
       crosstabs.add(panel);
       panel.getScene().addObjectSceneListener(this, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);
       
       return true;
    }
    

    private ReportObjectScene scene = null;
    private JComponent myView = null;
    
    /** Creates new form ReportDesignerPanel */
    public ReportDesignerPanel() {
        initComponents();
        
        scene = new ReportObjectScene();
        myView = scene.getJComponent();

        jScrollPaneMainReport.setViewportView(myView);
        myView.addMouseWheelListener(new MouseWheelListener() {

            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getModifiers() == 0)
                {

                    int units = e.getUnitsToScroll()*16;
                    int v = jScrollPaneMainReport.getVerticalScrollBar().getValue();
                    v = Math.max(v, jScrollPaneMainReport.getVerticalScrollBar().getMinimum());
                    v = Math.min(v, jScrollPaneMainReport.getVerticalScrollBar().getMaximum());
                    jScrollPaneMainReport.getVerticalScrollBar().setValue( v + units);
                }
            }
        });
        
        hRuler = new RulerPanel(scene);
        myView.addMouseMotionListener(hRuler);
        hRuler.addGuideLineChangedListener(scene);
        jPanel1.add(hRuler, BorderLayout.CENTER);
    
        vRuler = new RulerPanel(scene);
        vRuler.setVertical(true);
        myView.addMouseMotionListener(vRuler);
        vRuler.addGuideLineChangedListener(scene);
        jPanel2.add(vRuler, BorderLayout.CENTER);
        myView.setDropTarget(new DesignerDropTarget(scene));
        
        scene.addObjectSceneListener(this, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);
        scene.addObjectSceneListener(this, ObjectSceneEventType.OBJECT_REMOVED);
        scene.addObjectSceneListener(this, ObjectSceneEventType.OBJECT_ADDED);
        
        IReportManager.getPreferences().addPreferenceChangeListener(new PreferenceChangeListener() {

            public void preferenceChange(PreferenceChangeEvent evt) {
                try {
                    Mutex.EVENT.readAccess(new Runnable() {

                        public void run() {

                            try {
                                if (setRefreshing(true)) return;
                                getScene().refreshDocument();
                                getScene().validate();
                             } catch (Exception ex) {}
                            setRefreshing(false);
                        }
                    });
                    
                } catch (Exception ex) {}
            }
        });
    }

    boolean refreshing = false;

    public synchronized boolean setRefreshing(boolean b)
    {
        boolean old = refreshing;
        refreshing =b;
        return old;
    }

    public boolean isRefreshing()
    {
        return refreshing;
    }


    static private double[] zoomSteps = new double[]{0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    public void zoomIn() {
        
        AbstractReportObjectScene sc = getActiveScene();
        
        //SceneAnimator sa = new SceneAnimator(sc);//let's do animation later, after we fix all bugs
        double zoom = sc.getZoomFactor();
        
        for (int i=0; i<zoomSteps.length; ++i)
        {
            if (zoom < zoomSteps[i])
            {
                zoom = zoomSteps[i];
                break;
            }
        }
        
        //sa.animateZoomFactor(zoom);
        sc.setZoomFactor(zoom);
        sc.validate();
    }
    
    public void zoomOut() {
        
        AbstractReportObjectScene sc = getActiveScene();
        
        //SceneAnimator sa = new SceneAnimator(sc);
        double zoom = sc.getZoomFactor();
        
        for (int i=zoomSteps.length-1; i>=0; --i)
        {
            if (zoom > zoomSteps[i])
            {
                zoom = zoomSteps[i];
                break;
            }
        }
        
        //sa.animateZoomFactor(zoom);
        sc.setZoomFactor(zoom);
        sc.validate();
    }

    /**
     * For each available scene, this method creates a subset of selected objects
     * and sets the corresponding selections.
     * 
     * @param selectedObjects
     */
    public void setSelectedObjects(Set selectedObjects) {
 
        boolean oldValue = setAdjustingSelection(true);
        
        try {    
            int count0 = setSelectedObjects(selectedObjects, getScene());
            int selectedIndex = -1;
            int cIndex = 0;
            
            // If the selected object is just a crosstab, do nothing...
            if (selectedObjects.size() == 1 &&
                selectedObjects.iterator().next() instanceof JRCrosstab)
            {
                // do nothing
                selectedIndex = getActiveCrosstabIndex();
            }
            else
            {
                for (CrosstabPanel p : crosstabs)
                {
                    CrosstabObjectScene sc = p.getScene();
                    int count = setSelectedObjects(selectedObjects, sc );
                    if (count0 < count) selectedIndex = cIndex;
                    cIndex++;
                }
            }
            
            if (selectedObjects.size() == 0)
            {
                // do nothing
            }
            else if (crosstabs.size() > 0)
            {
                setActiveCrosstabIndex(selectedIndex);
            }
            
            getActiveScene().validate();
        } finally {
            setAdjustingSelection(oldValue);
        }
        
    } 
    
    public int setSelectedObjects(Set selectedObjects, AbstractReportObjectScene sc) {
        
        boolean oldValue = setAdjustingSelection(true);
        List list = new ArrayList();
        int otherObjects = 0;
        try { 
            
            for (Iterator iter = selectedObjects.iterator(); iter.hasNext(); )
            {
                Object obj = iter.next();
                if (sc.getObjects().contains( obj ))
                {
                    list.add(obj);
                }
                else if (obj instanceof JRCellContents &&
                         sc instanceof CrosstabObjectScene)
                {
                    if (ModelUtils.getAllCells( ((CrosstabObjectScene)sc).getDesignCrosstab()).contains(obj))
                    {
                        otherObjects++;
                    }
                }
            }

            sc.setSelectedObjects( new HashSet(list) ); 
        } finally {
            setAdjustingSelection(oldValue);
        }
        
        return list.size() + otherObjects;
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelMainReport = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPaneMainReport = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        jToggleButtonMain = new javax.swing.JToggleButton();
        jPanelContainer = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 20));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setMinimumSize(new java.awt.Dimension(20, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(20, 0));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPaneMainReport.setBorder(null);

        org.jdesktop.layout.GroupLayout jPanelMainReportLayout = new org.jdesktop.layout.GroupLayout(jPanelMainReport);
        jPanelMainReport.setLayout(jPanelMainReportLayout);
        jPanelMainReportLayout.setHorizontalGroup(
            jPanelMainReportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMainReportLayout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(jPanelMainReportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .add(jScrollPaneMainReport, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)))
        );
        jPanelMainReportLayout.setVerticalGroup(
            jPanelMainReportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMainReportLayout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(jPanelMainReportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .add(jScrollPaneMainReport, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        buttonGroup1.add(jToggleButtonMain);
        jToggleButtonMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/report-16.png"))); // NOI18N
        jToggleButtonMain.setText(org.openide.util.NbBundle.getMessage(ReportDesignerPanel.class, "ReportDesignerPanel.jToggleButtonMain.text")); // NOI18N
        jToggleButtonMain.setFocusable(false);
        jToggleButtonMain.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToggleButtonMain.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButtonMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonMainActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButtonMain);

        setLayout(new java.awt.BorderLayout());

        jPanelContainer.setLayout(new java.awt.BorderLayout());
        add(jPanelContainer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButtonMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonMainActionPerformed
        setActiveCrosstabIndex(-1);
    }//GEN-LAST:event_jToggleButtonMainActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JPanel jPanelMainReport;
    private javax.swing.JScrollPane jScrollPaneMainReport;
    private javax.swing.JToggleButton jToggleButtonMain;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    
    public ReportObjectScene getScene() {
        return scene;
    }

    public void setScene(ReportObjectScene scene) {
        this.scene = scene;
    }

    private void updateCrosstabPanels() {
        
        if (crosstabs.size() > 0)
        {
            jToolBar1.removeAll();
            jToolBar1.add(jToggleButtonMain);
            jToolBar1.setLayout(new MultilineToolbarLayout());
            
            for (int i=0; i<crosstabs.size(); ++i)
            {
                JToggleButton jToggleButton = new JToggleButton();
                buttonGroup1.add(jToggleButton);
                jToggleButton.setText("Crosstab " + (i+1)); // NOI18N
                jToggleButton.setFocusable(false);
                jToggleButton.setActionCommand(""+i);
                jToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                jToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                jToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jaspersoft/ireport/designer/resources/crosstab-16.png"))); // NOI18N
                jToggleButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {

                        int cindex = Integer.parseInt(evt.getActionCommand());
                        setActiveCrosstabIndex(cindex);
                    }
                });
                jToolBar1.add(jToggleButton);
            }
            
            add(jToolBar1, BorderLayout.SOUTH);
            // force un update
            setActiveCrosstabIndex(this.activeCrosstabIndex);
        }
        else
        {
            remove(jToolBar1);
            setActiveCrosstabIndex(-1);
        }
        
        
        updateUI();
    }

    public int getActiveCrosstabIndex() {
        return activeCrosstabIndex;
    }

    public void setActiveCrosstabIndex(int cIndex) {
        
        if (cIndex >= crosstabs.size()) throw new IndexOutOfBoundsException();
        this.activeCrosstabIndex = cIndex;
        jPanelContainer.removeAll();
        if (cIndex == -1)
        {
            jPanelContainer.add(jPanelMainReport, BorderLayout.CENTER);
            jToggleButtonMain.setSelected(true);
        }
        else
        {
            jPanelContainer.add(crosstabs.get(cIndex), BorderLayout.CENTER);
            ((JToggleButton)jToolBar1.getComponent(cIndex+1)).setSelected(true);
        }

        try {
            if (getActiveScene() != null && !isAdjustingSelection())
            {
                // TODO: this piece of code essentially clear the selection.
                // this is to avoid problems when using formatting tool with
                // several elements selected...
                List<JRDesignElement> elements = getActiveScene().getSelectionManager().getSelectedElements();
                if (elements != null)
                {
                    // sync the selection with the scene selection...
                    ExplorerManager manager = ExplorerManager.find(this);
                    
                    ArrayList<Node> nodeList = new ArrayList<Node>();
                    
                    for (JRDesignElement ele : elements)
                    {
                        Node node = IReportManager.getInstance().findNodeOf(ele, manager.getRootContext());
                        if (node != null) nodeList.add(node);
                    }
                    
                    manager.setSelectedNodes(nodeList.toArray(new Node[nodeList.size()]));
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        jPanelContainer.updateUI();
    }
    
    
    /**
     * Returns the currently visible scene.
     * 
     * @return
     */
    public AbstractReportObjectScene getActiveScene()
    {
        if (getActiveCrosstabIndex() == -1) return getScene();
        else return crosstabs.get(getActiveCrosstabIndex()).getScene();
    }

    public void objectAdded(ObjectSceneEvent evt, Object elem) {
        
        
        
//        if (arg0.getObjectScene() instanceof AbstractReportObjectScene &&
//            ((AbstractReportObjectScene)arg0.getObjectScene()).isUpdatingView())  
//        {
//                return;
//        }
        
        if (elem instanceof JRDesignCrosstab)
        {
            if (getCrosstabPanel((JRDesignCrosstab)elem) == null)
            {
                addCrosstabPanel((JRDesignCrosstab)elem, getJasperDesign());
                activeCrosstabIndex = crosstabs.size()-1;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        updateCrosstabPanels();
                    }
                });
            }
            //setActiveCrosstabIndex(crosstabs.size()-1);
        }
        
    }

    public void objectRemoved(ObjectSceneEvent arg0, Object arg1) {
        
        
        
//        if (arg0.getObjectScene() instanceof AbstractReportObjectScene &&
//            ((AbstractReportObjectScene)arg0.getObjectScene()).isUpdatingView())  
//        {
//                return;
//        }
        
        if (arg1 instanceof JRDesignCrosstab)
        {
            // Find the right crosstab panel...
            for (int i=0; i<crosstabs.size(); ++i)
            {
                CrosstabPanel panel = crosstabs.get(i);
                if (panel.getScene().getDesignCrosstab() == arg1)
                {
                    panel.getScene().removeObjectSceneListener(this, ObjectSceneEventType.OBJECT_SELECTION_CHANGED);
                    panel.getScene().removeObjectSceneListener(this, ObjectSceneEventType.OBJECT_REMOVED);
                    panel.getScene().removeObjectSceneListener(this, ObjectSceneEventType.OBJECT_ADDED);
                    
                    if (activeCrosstabIndex >= i)
                    {
                        activeCrosstabIndex--;
                    }
                    crosstabs.remove(panel);
                    
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            updateCrosstabPanels();
                        }
                    });
                    
                    return;
                }
            }
        }
    }

    public void objectStateChanged(ObjectSceneEvent arg0, Object arg1, ObjectState arg2, ObjectState arg3) {
    }

    public void selectionChanged(ObjectSceneEvent se, Set<Object> arg1, Set<Object> arg2) {

        if (isAdjustingSelection()) return; // Ignore this event...
        fireSelectionChangeEvent(se,arg1,arg2);
    }

    public void highlightingChanged(ObjectSceneEvent arg0, Set<Object> arg1, Set<Object> arg2) {
    }

    public void hoverChanged(ObjectSceneEvent arg0, Object arg1, Object arg2) {
    }

    public void focusChanged(ObjectSceneEvent arg0, Object arg1, Object arg2) {
    }
    
    
    /**
     * This methods returns the AbstractReportObjectScene that contains that object
     * If noone of the scene shown by this panel have the requested object, the
     * method returns null.
     * 
     * @param obj
     * @return
     */
    public AbstractReportObjectScene getSceneOf(Object obj)
    {
        if (getScene().getObjects().contains(obj)) return getScene();
        for (CrosstabPanel p : crosstabs)
        {
            CrosstabObjectScene sc = p.getScene();
            if (sc.getObjects().contains(obj) ) return sc;
        }
        
        return null; // No scene found...
    }

    public boolean isAdjustingSelection() {
        return adjustingSelection;
    }

    /**
     * Set the flag AdjustingSelection to the new value.
     * Return the old value. The old value should be always restored
     * by who call the setAdjustingSelection() method in a finally construct
     * 
     * @param adjustingSelection
     * @return
     */
    public boolean setAdjustingSelection(boolean adjustingSelection) {
        boolean oldValue = this.adjustingSelection;
        this.adjustingSelection = adjustingSelection;
        return oldValue;
    }
}

