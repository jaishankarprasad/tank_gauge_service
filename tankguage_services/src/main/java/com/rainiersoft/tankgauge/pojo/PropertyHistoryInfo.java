package com.rainiersoft.tankgauge.pojo;

import java.util.List;

public class PropertyHistoryInfo 
{
	private String dateAndTime;

	List<PropertyIn> propInfo ;
	
	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public List<PropertyIn> getPropInfo() {
		return propInfo;
	}

	public void setPropInfo(List<PropertyIn> propInfo) {
		this.propInfo = propInfo;
	}

}
