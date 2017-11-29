package com.jaspersoft.ireport.hadoop.hive.connection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;

import org.openide.util.NbBundle;

import com.jaspersoft.hadoop.hive.HiveDataSource;
import com.jaspersoft.ireport.designer.IReportConnection;
import com.jaspersoft.ireport.designer.IReportConnectionEditor;
import com.jaspersoft.ireport.designer.data.WizardFieldsProvider;
import com.jaspersoft.ireport.designer.utils.Misc;
import com.jaspersoft.ireport.hadoop.hive.designer.HiveFieldsProvider;

/**
 * 
 * @author Eric Diaz
 * 
 */
public class HiveConnection extends IReportConnection implements WizardFieldsProvider {
	private static final String URL = "HiveURL";

	private String url;

	public HiveConnection() {
		super();
		setName(NbBundle.getMessage(HiveConnection.class, "connectionName"));
	}

	@Override
	public String getDescription() {
		return NbBundle.getMessage(HiveConnection.class, "connectionType");
	}

	@Override
	public IReportConnectionEditor getIReportConnectionEditor() {
		return new HiveConnectionEditor();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public Connection getConnection() {
		try {
			return new com.jaspersoft.hadoop.hive.HiveConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void test() throws Exception {
		com.jaspersoft.hadoop.hive.HiveConnection connection = null;
		boolean success = false;
		String message = "";
		try {
			connection = new com.jaspersoft.hadoop.hive.HiveConnection(url);
			success = connection.test();
		} catch (Exception e) {
			success = false;
			message = e.getMessage();
		} finally {
			// Clean up
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		if (!success) {
			JOptionPane.showMessageDialog(Misc.getMainWindow(), "Connection test failed!\n" + message, "",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(Misc.getMainWindow(), "Connection test successful!", "",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return;
	}

	@Override
	public String designQuery(String query) {
		return query;
	}

	@Override
	public String getQueryLanguage() {
		return HiveDataSource.QUERY_LANGUAGE;
	}

	@Override
	public boolean supportsDesign() {
		return false;
	}

	@Override
	public boolean isJDBCConnection() {
		return true;
	}

	@Override
	public boolean isJRDataSource() {
		return false;
	}

	@Override
	public void loadProperties(HashMap map) {
		setUrl((String) map.get(URL));
	}

	@Override
	public HashMap getProperties() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(URL, getUrl());
		return map;
	}

	@Override
	public List<JRDesignField> readFields(String query) throws Exception {
		HiveFieldsProvider provider = new HiveFieldsProvider();
		List<JRDesignField> result = new ArrayList<JRDesignField>();
		JRDesignDataset dataset = new JRDesignDataset(true);
		JRDesignQuery designQuery = new JRDesignQuery();
		designQuery.setLanguage(HiveDataSource.QUERY_LANGUAGE);
		designQuery.setText(query);
		dataset.setQuery(designQuery);
		JRField[] fields = provider.getFields(this, dataset, new HashMap<Object, Object>());
		for (int i = 0; i < fields.length; ++i) {
			result.add((JRDesignField) fields[i]);
		}
		return result;
	}

}