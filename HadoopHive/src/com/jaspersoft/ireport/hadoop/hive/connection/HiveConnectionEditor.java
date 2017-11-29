/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.hadoop.hive.connection;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openide.util.NbBundle;

import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;

/**
 * 
 * @author Eric Diaz
 * 
 */
public class HiveConnectionEditor extends JPanel implements IReportConnectionEditor {
	private JTextField urlField;

	public HiveConnectionEditor() {
		initComponents();
	}

	private void initComponents() {
		JPanel propertiesPanel = new JPanel(new GridBagLayout());
		JLabel urlLabel = new JLabel(NbBundle.getMessage(HiveConnection.class, "hiveURL").trim());
		urlField = new JTextField("jdbc:hive://HOST:10000/default");
		GridBagConstraints contraints = new GridBagConstraints();
		contraints.gridx = 0;
		contraints.gridy = 0;
		contraints.fill = GridBagConstraints.NONE;
		contraints.insets = new Insets(5, 5, 2, 5);
		contraints.anchor = GridBagConstraints.WEST;
		contraints.weightx = 1.0;
		contraints.weighty = 1.0;
		propertiesPanel.add(urlLabel, contraints);
		contraints = new GridBagConstraints();
		contraints.gridx = 0;
		contraints.gridy = 1;
		contraints.fill = GridBagConstraints.HORIZONTAL;
		contraints.anchor = GridBagConstraints.WEST;
		contraints.insets = new Insets(2, 5, 5, 5);
		contraints.ipadx = 40;
		contraints.weightx = 1.0;
		contraints.weighty = 1.0;
		propertiesPanel.add(urlField, contraints);

		contraints = new GridBagConstraints();
		contraints.gridx = 0;
		contraints.gridy = 0;
		contraints.fill = GridBagConstraints.HORIZONTAL;
		contraints.anchor = GridBagConstraints.WEST;
		setLayout(new GridBagLayout());
		add(propertiesPanel, contraints);
		contraints = new GridBagConstraints();
		contraints.gridx = 0;
		contraints.gridy = 1;
		contraints.fill = GridBagConstraints.BOTH;
		contraints.anchor = GridBagConstraints.WEST;
		contraints.weightx = 1.0;
		contraints.weighty = 1.0;
		add(new JPanel(), contraints);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setSize(400, 400);
		frame.add(new HiveConnectionEditor());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public IReportConnection getIReportConnection() {
		HiveConnection connection = new HiveConnection();
		connection.setUrl(urlField.getText().trim());
		return connection;
	}

	@Override
	public void setIReportConnection(IReportConnection connection) {
		if (connection instanceof HiveConnection) {
			HiveConnection hiveConnection = (HiveConnection) connection;
			urlField.setText(hiveConnection.getUrl());
		}
	}
}