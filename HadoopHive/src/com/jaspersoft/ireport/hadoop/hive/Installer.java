
package com.jaspersoft.ireport.hadoop.hive;

import org.openide.modules.ModuleInstall;
import com.jaspersoft.hadoop.hive.HiveDataSource;
import com.jaspersoft.hadoop.hive.HiveQueryExecuterFactory;
import com.jaspersoft.ireport.designer.IReportManager;
import com.jaspersoft.ireport.hadoop.hive.connection.HiveConnectionFactory;
import com.jaspersoft.ireport.hadoop.hive.designer.HiveFieldsProvider;

/**
 * 
 * @author ediaz
 * 
 */
public class Installer extends ModuleInstall {

	@Override
	public void restored() {
		IReportManager.getInstance().addConnectionImplementationFactory(new HiveConnectionFactory());
		IReportManager.getInstance().addQueryExecuterDef(
				new com.jaspersoft.ireport.designer.data.queryexecuters.QueryExecuterDef(HiveDataSource.QUERY_LANGUAGE,
						HiveQueryExecuterFactory.class.getName(), HiveFieldsProvider.class.getName()), true);
	}
}
