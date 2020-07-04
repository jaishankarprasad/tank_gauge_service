package com.rainiersoft.tankgauge.reportsgenerator.core.inventorydata;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.Properties;
import java.util.List;

import com.rainiersoft.tankgauge.reportsgenerator.main.TankGaugingReportsGenerator;
import com.rainiersoft.tankgauge.reportsgenerator.interfaces.GenericSQLReportsResultsetProcessor;

/**
 * @author Abhijeet Katakam
 *
 */

public class InventoryDataReportsProcessor implements GenericSQLReportsResultsetProcessor {
	
	public List<String[]> processSQLResultset(ResultSet sqlResultset, Properties repConfigProperties) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Started processing sql resultset...");

		List<String[]> inventoryListRowData = processResultColumns(sqlResultset);
		
		return inventoryListRowData;

	}

	public List<String[]> processResultColumns(ResultSet sqlResultset) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Reading sql resultset...");
		
		List<String[]> inventoryListRowData = new ArrayList<String[]>();

		try {

			TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Reading column names...");
			
			ResultSetMetaData rsMetaData = sqlResultset.getMetaData();
			int resultSetMetaDataCount = rsMetaData.getColumnCount();

			while (sqlResultset.next()) {

				String[] inventoryRowData = new String[resultSetMetaDataCount];

				for (int colIndex = 1; colIndex < resultSetMetaDataCount + 1; colIndex++) {
					
					inventoryRowData[colIndex - 1] = sqlResultset.getString(colIndex);
					
				}
				
				inventoryListRowData.add(inventoryRowData);

			}
			
		} catch (SQLException sqlExcp) {

			TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : SQL Exception - " + sqlExcp.getMessage());

		} catch (Exception excp) {

			TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Exception - " + excp.getMessage());

		}

		return inventoryListRowData;

	}

}
