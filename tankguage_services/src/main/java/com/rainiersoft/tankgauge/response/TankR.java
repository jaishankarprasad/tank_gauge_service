package com.rainiersoft.tankgauge.response;


import java.util.ArrayList;
import java.util.List;

import com.rainiersoft.tankgauge.pojo.PropertyDetails;
import com.rainiersoft.tankgauge.pojo.PropertyHistoryInfo;
import com.rainiersoft.tankgauge.pojo.PropertyValue;
import com.rainiersoft.tankgauge.pojo.TankPropertyInfo;

public class TankR
{
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<PropertyHistoryInfo> getPropDataList() {
		return propDataList;
	}
	public void setPropDataList(List<PropertyHistoryInfo> propDataList) {
		this.propDataList = propDataList;
	}

	String message;
	boolean status;
	List<PropertyHistoryInfo> propDataList;

}