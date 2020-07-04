package com.rainiersoft.tankgauge.pojo;

import java.util.List;

public class InventoryResponsePropertySet 
{

	public String categoryName;
	
	public List<InventoryReportPropertyItem> inventoryReportPropertyItems;
	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<InventoryReportPropertyItem> getInventoryReportPropertyItems() {
		return inventoryReportPropertyItems;
	}

	public void setInventoryReportPropertyItems(List<InventoryReportPropertyItem> inventoryReportPropertyItems) {
		this.inventoryReportPropertyItems = inventoryReportPropertyItems;
	}
}
