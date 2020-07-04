package com.rainiersoft.tankgauge.reportsgenerator.util;

import java.util.ArrayList;
import java.util.List;

import com.rainiersoft.tankgauge.reportsgenerator.definitions.InventoryReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.main.TankGaugingReportsGenerator;

/**
 * @author Abhijeet Katakam
 * Class to convert Rows available in DB to Columns format for generating PDF file
 */

public class RowsToColumnsConverter {

	// List of properties
	private String[] inventoryPropertiesList = TankGaugingReportsGenerator.repConfigProperties
			.getProperty(InventoryReportConstants.INVENTORY_PROPERTIES_LIST)
			.split(InventoryReportConstants.INVENTORY_GROUP_NAMES_DELIMITER);

	private List<String[]> stitchedRowsToColumnsData = new ArrayList<String[]>();
	private List<String[]> inventoryData = null;

	public List<String[]> convertRowsToColumns(List<String> uniqueTankNames, List<String[]> inventoryData) {

		this.inventoryData = inventoryData;

		for (String tankName : uniqueTankNames) {

			// Process records when there is data
			
			if (tankName != null) {
				
				String inventoryRecords[] = new String[inventoryPropertiesList.length];
				inventoryRecords = getPropertyNamesAndValues(tankName);
				stitchedRowsToColumnsData.add(inventoryRecords);
				
			}

		}

		return stitchedRowsToColumnsData;

	}

	private String[] getPropertyNamesAndValues(String tankName) {

		String[] tankSpecificProperties = getTankSpecificPropertyNamesAndValues(tankName);
		String[] tankPropertiesAndValues = new String[inventoryPropertiesList.length + 1];
		
		// Add tank name initially
		tankPropertiesAndValues[0] = tankName;
		
		for (int propIndex = 0; propIndex < inventoryPropertiesList.length; propIndex++) {

			boolean isPropertyAdded = false;
			String propName = inventoryPropertiesList[propIndex];
			
			for (String tankProperty : tankSpecificProperties) {
				
				int firstIndex = tankProperty.indexOf(":");
				String propertyName = tankProperty.substring(0, firstIndex);
				String propertyValue = tankProperty.substring(firstIndex + 1, tankProperty.length());
				
				if (propName.equals(propertyName)) {
					
					tankPropertiesAndValues[propIndex + 1] = propertyValue;
					isPropertyAdded = true;
					break;
				}
				
			}
			
			if(!isPropertyAdded) tankPropertiesAndValues[propIndex + 1] = "NA";
			
		}
		
		return tankPropertiesAndValues;

	}

	private String[] getTankSpecificPropertyNamesAndValues(String tankName) {

		List<String> tankSpecificPropertyNamesAndValues = new ArrayList<String>();
//		tankSpecificPropertyNamesAndValues.add(tankName);

		for (String[] inventoryRow : inventoryData) {

			if (tankName.equals(inventoryRow[0])) {

				tankSpecificPropertyNamesAndValues.add(inventoryRow[1] + ":" + inventoryRow[2]);

			}

		}

		return (String[]) tankSpecificPropertyNamesAndValues
				.toArray(new String[tankSpecificPropertyNamesAndValues.size()]);

	}

}