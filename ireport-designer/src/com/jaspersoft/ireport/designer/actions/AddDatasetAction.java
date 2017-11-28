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
package com.jaspersoft.ireport.designer.actions;

import com.jaspersoft.ireport.locale.I18n;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.designer.outline.nodes.ReportNode;
import com.jaspersoft.ireport.designer.undo.AddDatasetUndoableEdit;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

public final class AddDatasetAction extends NodeAction {

    private static AddDatasetAction instance = null;
    
    public static synchronized AddDatasetAction getInstance()
    {
        if (instance == null)
        {
            instance = new AddDatasetAction();
        }
        
        return instance;
    }
    
    private AddDatasetAction()
    {
        super();
    }
    
    
    public String getName() {
        return I18n.getString("AddDatasetAction.Name.CTL_AddDatasetAction");
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    protected void performAction(org.openide.nodes.Node[] activatedNodes) {
        
        if (activatedNodes.length > 0 &&
            activatedNodes[0] instanceof ReportNode)
        {
            try {
                ReportNode node = (ReportNode) activatedNodes[0];
                JRDesignDataset newDataset = new JRDesignDataset(false);
                String name = "dataset";
                for (int i = 1;; i++) {
                    if (!node.getJasperDesign().getDatasetMap().containsKey(name + i)) {
                        newDataset.setName(name + i);
                        break;
                    }
                }

                node.getJasperDesign().addDataset(newDataset);
                AddDatasetUndoableEdit edit = new AddDatasetUndoableEdit(newDataset, node.getJasperDesign());
                IReportManager.getInstance().addUndoableEdit(edit);
            } catch (JRException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    protected boolean enable(org.openide.nodes.Node[] activatedNodes) {
        if (activatedNodes == null || activatedNodes.length == 0) return false;
        return (activatedNodes.length > 0 && activatedNodes[0] instanceof ReportNode);
    }
}