/*
 * Copyright (C) 2005 - 2007 JasperSoft Corporation.  All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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
package com.jaspersoft.ireport.jasperserver.ui.inputcontrols.impl;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.InputControlQueryDataRow;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author gtoffoli
 */
public // The combobox's renderer...
	class ItemRenderer extends JPanel implements ListCellRenderer
	{
		private JLabel[]	labels = null;
		//private JLabel		nameLabel = new JLabel(" ");
		//private JLabel		valueLabel = new JLabel(" ");
		int columns = 0;
 
		public ItemRenderer(int columns)
		{
			setOpaque(true);
			//setLayout(new GridBagLayout());
                        GridLayout g = new GridLayout(1,columns);
                        setLayout(g);

                        this.columns = columns;
 			labels = new JLabel[columns];
                        
                        //java.awt.GridBagConstraints gridBagConstraints = null;
                                
 			for (int i=0; i<columns; ++i)
 			{
 			   labels[i] = new JLabel(" ");
                           //gridBagConstraints = new java.awt.GridBagConstraints();
                           //gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                           //gridBagConstraints.weightx = 1.0;
                           //gridBagConstraints.weighty = 1.0;
                           
 			   add(labels[i]); //, gridBagConstraints);
 			}
 		}
 
 
		public Component getListCellRendererComponent(
							JList list,
							Object value,
							int index,
							boolean isSelected,
							boolean cellHasFocus )
		{
                    
                        if (value != null && value instanceof InputControlQueryDataRow)
                        {
                            InputControlQueryDataRow icqdr = (InputControlQueryDataRow)value;
                            if (value != null)
                            {
                                for (int i=0; i<this.columns; ++i)
                                {
                                    String s = " ";
                                    try {
                                       if (icqdr.getColumnValues().get(i) != null)
                                       {
                                           s = ""+icqdr.getColumnValues().get(i);
                                           
                                       }
                                    } catch (Exception ex) { }
                                    
                                    getLabels()[i].setText( s );
                                
                                }
                                this.updateUI();
                            }
                            
                        }
                        else
                        {
                            getLabels()[0].setText(value+"");
                            
                            for (int i=1; i<this.columns; ++i)
                            {
                                getLabels()[i].setText(" ");
                            }
                        }
                     
			
			if (isSelected)
			{
				setBackground(Color.black);
                                for (int i=0; i<this.columns; ++i)
                                {
                                    getLabels()[i].setForeground(Color.white);
                                }
			}
 
			else
			{
				setBackground(Color.white);
                                for (int i=0; i<this.columns; ++i)
                                {
                                    getLabels()[i].setForeground(Color.black);
                                }
			}
			
			return this;
		}

    public JLabel[] getLabels() {
        return labels;
    }

    public void setLabels(JLabel[] labels) {
        this.labels = labels;
    }
	}
