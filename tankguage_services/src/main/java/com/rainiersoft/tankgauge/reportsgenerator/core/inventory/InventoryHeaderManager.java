package com.rainiersoft.tankgauge.reportsgenerator.core.inventory;

import java.util.Map;

import com.rainiersoft.tankgauge.reportsgenerator.core.GenericHeaderGeneratorManager;
import com.rainiersoft.tankgauge.reportsgenerator.definitions.InventoryReportConstants;

public class InventoryHeaderManager implements GenericHeaderGeneratorManager {

	String startDateAndTime;
	String endDateAndTime;
	String printDateAndTime;

	public String getStartDateAndTime() {
		return startDateAndTime;
	}

	public void setStartDateAndTime(String startDateAndTime) {
		this.startDateAndTime = startDateAndTime;
	}

	public String getEndDateAndTime() {
		return endDateAndTime;
	}

	public void setEndDateAndTime(String endDateAndTime) {
		this.endDateAndTime = endDateAndTime;
	}

	public String getPrintDateAndTime() {
		return printDateAndTime;
	}

	public void setPrintDateAndTime(String printDateAndTime) {
		this.printDateAndTime = printDateAndTime;
	}

	public void setHeaders() {

		setStartDateAndTime(InventoryReportConstants.START_DATE_TIME);
		setEndDateAndTime(InventoryReportConstants.END_DATE_TIME);
		setPrintDateAndTime(InventoryReportConstants.PRINT_DATE_TIME);
		
	}

	public Map<String, String> getHeaders() {

		headerKeys.put(InventoryReportConstants.START_DATE_TIME, getStartDateAndTime());
		headerKeys.put(InventoryReportConstants.END_DATE_TIME, getEndDateAndTime());
		headerKeys.put(InventoryReportConstants.PRINT_DATE_TIME, getPrintDateAndTime());
		
		return headerKeys;
		
	}
	
}
