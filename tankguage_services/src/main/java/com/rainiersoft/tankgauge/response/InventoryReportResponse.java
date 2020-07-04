package com.rainiersoft.tankgauge.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.rainiersoft.tankgauge.pojo.InventoryReportPropertyItem;


public class InventoryReportResponse
{
	public String tankName;
	public int tankId;
    public Date reportGeneratedOn;
    
    public HashMap<String, List<InventoryReportPropertyItem>> map = new HashMap<String, List<InventoryReportPropertyItem>>();
  
    
    public HashMap<String, List<InventoryReportPropertyItem>> getMap() {
		return map;
	}
	public void setMap(HashMap<String, List<InventoryReportPropertyItem>> map) {
		this.map = map;
	}
	public String getTankName() {
		return tankName;
	}
	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public Date getReportGeneratedOn() {
		return reportGeneratedOn;
	}
	public void setReportGeneratedOn(Date reportGeneratedOn) {
		this.reportGeneratedOn = reportGeneratedOn;
	}
 
}
