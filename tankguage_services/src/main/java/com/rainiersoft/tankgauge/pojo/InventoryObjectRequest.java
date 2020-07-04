package com.rainiersoft.tankgauge.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class InventoryObjectRequest {
	
	String reportType;
	String lastUpdated;
	String productType;
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}

}
