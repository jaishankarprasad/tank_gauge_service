package com.rainiersoft.tankgauge.dao;

import java.util.List;

import com.rainiersoft.tankgauge.pojo.InventoryObjectRequest;
import com.rainiersoft.tankgauge.response.InventoryReportResponse;

public interface InventoryObjectDAO 
{	
	public List<InventoryReportResponse> getInventoryObject(InventoryObjectRequest inventoryObjectRequest);	

}
